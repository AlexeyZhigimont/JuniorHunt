package com.juniorhunt.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDateTime createdDate = LocalDateTime.now();
    private String aboutMe;
    private String experience;
    private String courses;
    @Enumerated(EnumType.STRING)
    private Set<Language> languages = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private Role role;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    @ManyToOne
    private Position position;
    @ManyToMany
    @JoinTable(name = "user_skills",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills;




}
