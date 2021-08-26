package com.deroahe.gallerybe.util.mail;


import com.deroahe.gallerybe.model.Hashtag;
import com.deroahe.gallerybe.model.Image;
import com.deroahe.gallerybe.model.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;



@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("javaproiectechipe@gmail.com");
        message.setTo("raresalupoaicei@gmail.com");
        message.setSubject("PictureThings");
        message.setText(text);
        emailSender.send(message);

    }


    public MimeMessagePreparator constructRoundNotificationEmail(User user, Hashtag hashtag, Image image, List<Hashtag> hashtagList, String trivia) {

        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("hashtag", hashtag);
        context.setVariable("image", image);
        context.setVariable("hashtagList", hashtagList);
        context.setVariable("trivia", trivia);
        String text = templateEngine.process("mailTemplate", context);



        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare (MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setTo(user.getUserEmail());
                email.setSubject("Your Daily Newsletter ");
                email.setText(text, true);
                email.setFrom(new InternetAddress("javaproiectechipe@gmail.com"));
            }
        };
        return messagePreparator;
    }


}