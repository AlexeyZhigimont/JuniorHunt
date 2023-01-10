package com.juniorhunt.service;

import com.juniorhunt.model.Skill;
import com.juniorhunt.model.TypeSkill;
import com.juniorhunt.model.User;
import com.juniorhunt.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public void updateSKills(Map<String, String> dataSkills, User user) {
        String typeName = dataSkills.get("skills");
        TypeSkill type = TypeSkill.valueOf(typeName);

        skillRepository.findAll().stream()
                .filter(s -> s.getTypeSkill().equals(type))
                .filter(skill -> dataSkills.containsKey(skill.getSkill()))
                .forEach(skill -> user.getSkills().add(skill));
    }


    public List<String> listTypeSkills(TypeSkill typeSkill) {
        return skillRepository.findAll().stream().filter(t -> t.getTypeSkill().equals(typeSkill))
                .map(Skill::getSkill).toList();
    }

    public List<String> listTypeUserSkills(TypeSkill typeSkill, Set<Skill> userSkills) {
        return userSkills.stream().filter(t -> t.getTypeSkill().equals(typeSkill))
                .map(Skill::getSkill).toList();
    }

    public List<String> listTypeSkillWithoutUserSkills(TypeSkill typeSkill, Set<Skill> userSkills) {
        List<Skill> listSkills = skillRepository.findAllByTypeSkill(typeSkill);
        listSkills.removeIf(userSkills::contains);
        return listSkills.stream().map(Skill::getSkill).collect(Collectors.toList());
    }


    public void save(Skill skill) {
        skillRepository.save(skill);
    }
}
