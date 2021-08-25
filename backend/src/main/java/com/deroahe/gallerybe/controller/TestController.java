package com.deroahe.gallerybe.controller;

import com.deroahe.gallerybe.model.*;
import com.deroahe.gallerybe.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    ImageServiceImpl imageService;
    HashtagServiceImpl hashtagService;
    UserServiceImpl userService;
    CommentServiceImpl commentService;
    RoleServiceImpl roleService;

    @Autowired
    public TestController(UserServiceImpl userService, RoleServiceImpl roleService, ImageServiceImpl imageService, HashtagServiceImpl hashtagService, CommentServiceImpl commentService) {
        this.userService = userService;
        this.imageService = imageService;
        this.roleService = roleService;
        this.hashtagService = hashtagService;
        this.commentService = commentService;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/test-category")
    public void testCategory() {
        List<Image> allImages = imageService.findAllImages();
        for (Image image : allImages) {
            System.out.println("id: " + image.getImageId() + " hashtags: ");
            List<Hashtag> imageHashtags = image.getImageHashtags();
            for (Hashtag hashtag : imageHashtags) {
                System.out.println("Hashtag: " + hashtag.getHashtagName());
            }
        }
        System.out.println("\n\n\n\n");
        List<Image> images = imageService.returnImagesByCategory("Animals");
        for(Image i : images) {
            System.out.println("id -> " + i.getImageId() + "     " + i.getImageUrl());
        }
    }

    @GetMapping("/populate-db")
    public void populateDb() {
        /*Hashtag ha = new Hashtag("a");
        Hashtag hb = new Hashtag("b");
        Hashtag hc = new Hashtag("c");
        Hashtag hd = new Hashtag("d");
        Hashtag he = new Hashtag("e");
        Hashtag hf = new Hashtag("f");
        Hashtag hh = new Hashtag("g");
        Hashtag hg = new Hashtag("h");
        Hashtag hi = new Hashtag("i");
        Hashtag hj = new Hashtag("j");
        Hashtag hk = new Hashtag("k");
        Hashtag hl = new Hashtag("l");
        Hashtag hm = new Hashtag("m");

        hashtagService.saveHashtag(ha);
        hashtagService.saveHashtag(hb);
        hashtagService.saveHashtag(hc);
        hashtagService.saveHashtag(hd);
        hashtagService.saveHashtag(he);
        hashtagService.saveHashtag(hf);
        hashtagService.saveHashtag(hg);
        hashtagService.saveHashtag(hh);
        hashtagService.saveHashtag(hi);
        hashtagService.saveHashtag(hj);
        hashtagService.saveHashtag(hk);
        hashtagService.saveHashtag(hl);
        hashtagService.saveHashtag(hm);


        List<Image> imageList = imageService.getAll();

        if (imageList.size() != 0) {
            List<Hashtag> hashtagList1 = imageList.get(0).getImageHashtags();
            hashtagList1.add(ha);
            hashtagList1.add(hb);
            hashtagList1.add(hc);
            hashtagList1.add(hd);
            hashtagList1.add(he);
            hashtagList1.add(hf);
            imageList.get(0).setImageHashtags(hashtagList1);
            imageService.saveImage(imageList.get(0));


            List<Hashtag> hashtagList2 = imageList.get(1).getImageHashtags();
            hashtagList2.add(hm);
            hashtagList2.add(hl);
            hashtagList2.add(hk);
            hashtagList2.add(hj);
            hashtagList2.add(hi);
            hashtagList2.add(hg);
            hashtagList2.add(hf);
            hashtagList2.add(he);
            imageList.get(1).setImageHashtags(hashtagList2);
            imageService.saveImage(imageList.get(1));

            List<Hashtag> hashtagList3 = imageList.get(2).getImageHashtags();
            hashtagList3.add(ha);
            hashtagList3.add(hh);
            hashtagList3.add(hb);
            hashtagList3.add(hc);
            hashtagList3.add(hm);
            hashtagList3.add(hk);
            hashtagList3.add(hf);
            hashtagList3.add(he);
            imageList.get(2).setImageHashtags(hashtagList3);
            imageService.saveImage(imageList.get(2));
        }*/

        // populating the DB tables and keeping them as lists

        List<Role> roles = populateRoleList();
        for (Role role : roles) {
            roleService.saveRole(role);
        }
        roles = roleService.findAllRoles();

        // creating relations between tables
        // User MtM Role
        Set<Role> setRoleUser = new HashSet<>();
        Set<Role> setRoleAdmin = new HashSet<>();
        Set<Role> setRoleModerator = new HashSet<>();
        Set<Role> setRoleAll = new HashSet<>();
        setRoleUser.add(roles.get(0));
        setRoleModerator.add(roles.get(1));
        setRoleAdmin.add(roles.get(2));
        setRoleAll.add(roles.get(0));
        setRoleAll.add(roles.get(1));
        setRoleAll.add(roles.get(2));

        List<User> users = populateUserList();
        users.get(0).setUserRoles(setRoleUser);
        users.get(1).setUserRoles(setRoleModerator);
        users.get(2).setUserRoles(setRoleAdmin);
        users.get(3).setUserRoles(setRoleAll);
        users.get(4).setUserRoles(setRoleUser);
        userService.saveAllUsers(users);

        // Image MtM Hashtag
        List<Hashtag> hashtags = populateHashtagList();
        hashtagService.saveAllHashtags(hashtags);
        hashtags = hashtagService.findAllHashtags();
        List<Hashtag> imageHashtags1 = new ArrayList<>();
        List<Hashtag> imageHashtags2 = new ArrayList<>();
        List<Hashtag> imageHashtags3 = new ArrayList<>();
        List<Hashtag> imageHashtags4 = new ArrayList<>();
        List<Hashtag> imageHashtags5 = new ArrayList<>();
        imageHashtags1.add(hashtags.get(4));
        imageHashtags2.add(hashtags.get(5));
        imageHashtags2.add(hashtags.get(3));
        imageHashtags2.add(hashtags.get(2));
        imageHashtags3.add(hashtags.get(3));
        imageHashtags3.add(hashtags.get(2));
        imageHashtags4.add(hashtags.get(4));
        imageHashtags4.add(hashtags.get(5));
        imageHashtags4.add(hashtags.get(6));
        imageHashtags4.add(hashtags.get(5));
        imageHashtags5.add(hashtags.get(1));
        imageHashtags5.add(hashtags.get(0));

        List<Image> images = populateImageList();
        images.get(0).setImageHashtags(imageHashtags1);
        images.get(1).setImageHashtags(imageHashtags2);
        images.get(2).setImageHashtags(imageHashtags3);
        images.get(3).setImageHashtags(imageHashtags4);
        images.get(4).setImageHashtags(imageHashtags5);
        images.get(5).setImageHashtags(imageHashtags1);
        images.get(6).setImageHashtags(imageHashtags1);
        images.get(7).setImageHashtags(imageHashtags3);
        images.get(8).setImageHashtags(imageHashtags2);
        images.get(9).setImageHashtags(imageHashtags1);
        imageService.saveAllImages(images);

        // User OtM Comment && Image OtM Comment
        List<Comment> comments = populateCommentList();
        Image image1 = imageService.findImageById(1);
        Image image2 = imageService.findImageById(2);
        Image image3 = imageService.findImageById(3);
        User user1 = userService.findUserById(1);
        User user2 = userService.findUserById(2);
        comments.get(0).setCommentImage(image1);
        comments.get(0).setCommentUser(users.get(0));
        comments.get(1).setCommentImage(image1);
        comments.get(1).setCommentUser(users.get(1));
        comments.get(2).setCommentImage(image1);
        comments.get(2).setCommentUser(users.get(1));
        comments.get(3).setCommentImage(image1);
        comments.get(3).setCommentUser(users.get(1));
        comments.get(4).setCommentImage(image1);
        comments.get(4).setCommentUser(users.get(4));
        comments.get(5).setCommentImage(image1);
        comments.get(5).setCommentUser(users.get(3));
        comments.get(6).setCommentImage(image1);
        comments.get(6).setCommentUser(users.get(4));
        commentService.saveAllComments(comments);
        image1 = imageService.findImageById(image1.getImageId());
        image1.setImageComments(commentService.findCommentsByImage(image1));
        imageService.updateImage(image1);
        image1 = imageService.findImageById(image1.getImageId());
        System.out.println("Comments after updateImage" + image1.getImageComments().size() + "\n\n\n\n\n\n\" ");
        user1 = userService.findUserById(user1.getUserId());
        user1.setUserComments(commentService.findCommentsByUser(user1));
        userService.updateUser(user1);
    }

    public List<User> populateUserList() {
        List<User> users = new ArrayList<User>(
                Arrays.asList(
                        new User("bogdan@gmail.com", "bogdan", "$2a$10$NZCFLUAqfOotPafMAjqzlezlDSQNFakX1wufjTKvNSPcVqtm9l8zK"),
                        new User("bogdanuser@gmail.com", "bogdanuser", "$2a$10$NZCFLUAqfOotPafMAjqzlezlDSQNFakX1wufjTKvNSPcVqtm9l8zK"),
                        new User("bogdanmod@gmail.com", "bogdanmod", "$2a$10$NZCFLUAqfOotPafMAjqzlezlDSQNFakX1wufjTKvNSPcVqtm9l8zK"),
                        new User("bogdanadmin@gmail.com", "bogdanadmin", "$2a$10$NZCFLUAqfOotPafMAjqzlezlDSQNFakX1wufjTKvNSPcVqtm9l8zK"),
                        new User("bogdanall@gmail.com", "bogdanall", "$2a$10$NZCFLUAqfOotPafMAjqzlezlDSQNFakX1wufjTKvNSPcVqtm9l8zK")
                )
        );
        return users;
    }

    public List<Role> populateRoleList() {
        List<Role> roles = new ArrayList<Role>(
                Arrays.asList(
                        new Role(ERole.ROLE_USER),
                        new Role(ERole.ROLE_MODERATOR),
                        new Role(ERole.ROLE_ADMIN)
                )
        );
        return roles;
    }

    public List<Image> populateImageList() {
        List<Image> images = new ArrayList<Image>(
                Arrays.asList(
                        new Image("http://res.cloudinary.com/do70ia20i/image/upload/v1629145336/test_be/169A2911Dai.jpg.jpg", 2),
                        new Image("http://res.cloudinary.com/do70ia20i/image/upload/v1629207472/test_be/AndrewKlinger_wizard_sho_res25_sig.jpg.jpg", 10),
                        new Image("http://res.cloudinary.com/do70ia20i/image/upload/v1629205333/test_be/CometSwan_Rhemann_1200.jpg.jpg", 8),
                        new Image("http://res.cloudinary.com/do70ia20i/image/upload/v1629278903/test_be/butterfly_hst_big.jpg.jpg", 13),
                        new Image("http://res.cloudinary.com/do70ia20i/image/upload/v1629279180/test_be/Flame_ARO_1462.jpg.jpg", 9),
                        new Image("http://res.cloudinary.com/do70ia20i/image/upload/v1629146540/test_be/archives_lha120_n44.jpg.jpg", 10),
                        new Image("http://res.cloudinary.com/do70ia20i/image/upload/v1629279333/test_be/Falcon9MoonKatieDarby.jpg.jpg", 16),
                        new Image("http://res.cloudinary.com/do70ia20i/image/upload/v1629279355/test_be/FalconTessLaunchKraus.jpg.jpg", 7),
                        new Image("http://res.cloudinary.com/do70ia20i/image/upload/v1629280116/test_be/HubbleVarOrig_Carnegie_2880.jpg.jpg", 11),
                        new Image("http://res.cloudinary.com/do70ia20i/image/upload/v1629280757/test_be/enceladusstripes_cassini_3237.jpg.jpg", 13)
                )
        );
        return images;
    }

    public List<Hashtag> populateHashtagList() {
        List<Hashtag> hashtags = new ArrayList<>(
                Arrays.asList(
                        new Hashtag("cats"),
                        new Hashtag("dogs"),
                        new Hashtag("planet"),
                        new Hashtag("star"),
                        new Hashtag("galaxy"),
                        new Hashtag("nebula"),
                        new Hashtag("blackhole")
                )
        );
        return hashtags;
    }

    public List<Comment> populateCommentList() {
        List<Comment> comments = new ArrayList<Comment>(
                Arrays.asList(
                        new Comment("Nice!"),
                        new Comment("Cool!"),
                        new Comment("So nice!"),
                        new Comment("So cool!"),
                        new Comment("Even nicer!"),
                        new Comment("Even cooler!"),
                        new Comment("NICE!"),
                        new Comment("COOL!")
                )
        );
        return comments;
    }
}