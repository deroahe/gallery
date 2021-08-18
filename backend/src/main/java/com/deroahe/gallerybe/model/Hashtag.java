package com.deroahe.gallerybe.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    private String name;

    @ManyToMany(mappedBy = "hashtags")
    private List<Image> images;

    public Hashtag(String name){
        this.name = name;
    }
}
