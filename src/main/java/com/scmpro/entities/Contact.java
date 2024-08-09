package com.scmpro.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Contact {

    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    @Column(length = 1000)
    private String description;
    @Builder.Default
    private boolean favorite=false;
    private String websiteLink;
    private String linkedInLink;

    private String cloudnaryImagePublicId;

    @ManyToOne
    @JsonIgnore
    private User user;

     @OneToMany(mappedBy = "contact" , cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SocialLink> links = new ArrayList<>();



}
