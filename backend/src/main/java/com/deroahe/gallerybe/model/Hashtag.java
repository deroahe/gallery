package com.deroahe.gallerybe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "hashtags")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "HASHTAG_ID")
    private int hashtagId;

    @Column(name = "HASHTAG_NAME")
    private String hashtagName;

    @JsonIgnore
    @ManyToMany(mappedBy = "imageHashtags")
    private List<Image> hashtagImages;

    public Hashtag(String hashtagName){
        this.hashtagName = hashtagName;
    }

    public Hashtag(int hashtagId, String hashtagName){
        this.hashtagId = hashtagId;
        this.hashtagName = hashtagName;
    }
}
