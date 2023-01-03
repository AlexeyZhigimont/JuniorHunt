package com.juniorhunt.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String position;
    @ManyToMany
    @JoinTable(name ="position_skill",
                joinColumns = @JoinColumn(name = "position_id"),
                inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills;
}
