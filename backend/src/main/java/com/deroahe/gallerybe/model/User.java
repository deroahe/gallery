package com.deroahe.gallerybe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "USER_EMAIL"),
                @UniqueConstraint(columnNames = "USER_USERNAME")
        })
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private int userId;

    @Column(name = "USER_EMAIL")
    @NotBlank
    @Size(max = 50)
    @Email
    private String userEmail;

    @Column(name = "USER_USERNAME")
    @NotBlank
    @Size(max = 20)
    private String userUsername;

    @Column(name = "USER_PASSWORD")
    @NotBlank
    @Size(max = 120)
    private String userPassword;

    @Column(name = "USER_SUBSCRIBED")
    private boolean userSubscriber = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> userRoles = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "imageUser")
    @Column(name = "USER_IMAGES")
    private List<Image> userImages;

    @JsonIgnore
    @OneToMany(mappedBy = "commentUser")
    @Column(name = "USER_COMMENTS")
    private List<Comment> userComments;

    public User(String userEmail, String userUsername, String userPassword) {
        this.userUsername = userUsername;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public User(String userEmail, String userUsername) {
        this.userUsername = userUsername;
        this.userEmail = userEmail;
    }

    public User(int userId, @NotBlank @Size(max = 50) @Email String userEmail, @NotBlank @Size(max = 20) String userUsername, @NotBlank @Size(max = 120) String userPassword) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userUsername = userUsername;
        this.userPassword = userPassword;
    }
}
