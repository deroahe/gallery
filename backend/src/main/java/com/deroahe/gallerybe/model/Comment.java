package com.deroahe.gallerybe.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMMENT_ID")
    int commentId;

    @ManyToOne
    @JoinColumn(name = "COMMENT_IMAGE_ID")
    Image image;


    @Column(name = "COMMENT_REPLY_ID")
    int replyToComment;
}
