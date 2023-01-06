package com.juniorhunt.model;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skill;
    @Enumerated(EnumType.STRING)
    private TypeSkill typeSkill;

    public Skill(String skill, TypeSkill typeSkill) {
        this.skill = skill;
        this.typeSkill = typeSkill;
    }

    public Skill() {

    }
}
