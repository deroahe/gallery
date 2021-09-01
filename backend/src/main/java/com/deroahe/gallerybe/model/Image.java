package com.deroahe.gallerybe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Table(name = "images")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Image {

    @Column(name = "IMAGE_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "commentImage")
    private List<Comment> imageComments;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "IMAGE_USER")
    private User imageUser;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "IMAGE_HASHTAG",
            joinColumns = @JoinColumn(name = "IMAGE_ID"),
            inverseJoinColumns = @JoinColumn(name = "HASHTAG_ID"))
    private List<Hashtag> imageHashtags;

    public Image(String imageUrl, User imageUser, int imageScore) {
        this.imageUrl = imageUrl;
        this.imageUser = imageUser;
    }

    public Image(String imageUrl, int imageScore) {
        this.imageUrl = imageUrl;
    }

    public Image(int imageId, String imageUrl, int imageScore) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
    }


}
