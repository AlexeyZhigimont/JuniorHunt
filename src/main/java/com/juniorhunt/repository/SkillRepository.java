package com.juniorhunt.repository;

import com.juniorhunt.model.Skill;
import com.juniorhunt.model.TypeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByTypeSkill(TypeSkill typeSkill);

    Skill findBySkill(String skill);
}
