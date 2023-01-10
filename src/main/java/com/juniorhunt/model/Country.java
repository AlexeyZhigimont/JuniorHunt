package com.juniorhunt.model;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;
    private String country;


    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<User> users;

    public Country(String country) {
        this.country = country;
    }
}
