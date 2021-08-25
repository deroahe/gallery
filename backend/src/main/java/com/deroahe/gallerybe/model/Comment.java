package com.deroahe.gallerybe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Comment {

    @Column(name = "COMMENT_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int commentId;

    @Column(name = "COMMENT_STRING")
    private String commentString;

    @ManyToOne
    @JoinColumn(name = "COMMENT_IMAGE")
    private Image commentImage;

    @ManyToOne
    @JoinColumn(name = "COMMENT_USER")
    private User commentUser;

    @Column(name = "COMMENT_REPLY_TO_COMMENT")
    int commentReplyToComment;

    public Comment(String commentString) {
        this.commentString = commentString;
    }

    public Comment(String commentString, User commentUser, Image commentImage) {
        this.commentString = commentString;
        this.commentUser = commentUser;
        this.commentImage = commentImage;
    }
}
