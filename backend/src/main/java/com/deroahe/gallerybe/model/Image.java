package com.deroahe.gallerybe.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IMAGE_ID")
    private int imageId;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "IMAGE_UPLOADED_BY")
    private Long imageUploadedBy;

    // todo upload-date

    @OneToMany(mappedBy = "image")
    private List<Comment> comments;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "IMAGE_HASHTAG", joinColumns =
    @JoinColumn(name = "IMAGE_ID"),
            inverseJoinColumns = @JoinColumn(name = "HASHTAG_ID")
    )
    private List<Hashtag> hashtags;

}
