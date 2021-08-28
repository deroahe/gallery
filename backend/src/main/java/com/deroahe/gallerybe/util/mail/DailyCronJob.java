package com.deroahe.gallerybe.util.mail;

import com.deroahe.gallerybe.model.Hashtag;
import com.deroahe.gallerybe.model.Image;
import com.deroahe.gallerybe.model.User;
import com.deroahe.gallerybe.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class DailyCronJob {

    private UserServiceImpl userService;
    private ImageServiceImpl imageService;
    private HashtagServiceImpl hashtagService;
    private EmailService emailService;
    private JavaMailSender javaMailSender;

    @Autowired
    public DailyCronJob(UserServiceImpl userService, ImageServiceImpl imageService, HashtagServiceImpl hashtagService, EmailService emailService, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.imageService = imageService;
        this.hashtagService = hashtagService;
        this.emailService = emailService;
        this.javaMailSender = javaMailSender;
    }

    @Scheduled(cron = "0 15 13 * * ?")
    @Transactional
    public void CronSendDailyEmail(){

        log.info("Daily newsletter has started");

        List<User> allUsers = userService.findAllUsers();
        List<User> userRecipientList = new ArrayList<>();
        List<Hashtag> hashtagList = new ArrayList<>();
        for(User user : allUsers){
            if(user.isUserSubscriber()){
                userRecipientList.add(user);
            }
        }
        List<Image> allImages = imageService.findAllImages();
        Collections.shuffle(allImages);

        List<Hashtag> allHashtags = hashtagService.findAllHashtags();
        Collections.shuffle(allHashtags);


        List<Hashtag> hashtagListTop = hashtagService.findMostUsedHashtags();
        for(int i=0; i<3; i++){
            hashtagList.add(hashtagListTop.get(i));
        }

        MailText mailText = new MailText();
        List<String> trivia = mailText.getTriviaList();

        for(User userRecipient : userRecipientList){
            javaMailSender.send(emailService.constructRoundNotificationEmail(userRecipient, allHashtags.get(0), allImages.get(0), hashtagList,trivia.get(0)));
        }

        log.info("Daily email has been sent to all subscribers");
    }


}