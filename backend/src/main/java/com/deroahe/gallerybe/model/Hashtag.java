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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "IMAGE_HASHTAG", joinColumns = {
            @JoinColumn(name = "HASHTAG_ID", referencedColumnName = "HASHTAG_ID")},
            inverseJoinColumns = {@JoinColumn(name = "IMAGE_ID", referencedColumnName = "IMAGE_ID")
            })
    private List<Image> images;
}
