package com.juniorhunt.model;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;


    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private Set<User> users;

    public Country(String country) {
        this.country = country;
    }
}
