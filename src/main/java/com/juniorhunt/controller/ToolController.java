package com.juniorhunt.controller;

import com.juniorhunt.model.Skill;
import com.juniorhunt.model.TypeSkill;
import com.juniorhunt.service.PositionService;
import com.juniorhunt.service.SkillService;
import com.juniorhunt.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tool")
@PreAuthorize("hasAuthority('ADMIN')")
public class ToolController {
    private final String HARDSKILLS = "hardSkills";
    private final String SOFTSKILLS = "softSkills";
    private final String TOOLS = "tools";
    private final String USERS = "users";
    private final String POSITIONS = "positions";
    private final PositionService positionService;
    private final SkillService skillService;
    private final UserService userService;

    public ToolController(PositionService positionService, SkillService skillService, UserService userService) {
        this.positionService = positionService;
        this.skillService = skillService;
        this.userService = userService;
    }

    @GetMapping
    public String toolPage(Model model) {
        model.addAttribute(POSITIONS, positionService.findAll());
        model.addAttribute(HARDSKILLS, skillService.listTypeSkills(TypeSkill.HARD));
        model.addAttribute(SOFTSKILLS, skillService.listTypeSkills(TypeSkill.SOFT));
        model.addAttribute(TOOLS, skillService.listTypeSkills(TypeSkill.TOOL));
        model.addAttribute(USERS, userService.findAll());
        return "tool";
    }

    @PostMapping("position")
    public String addPosition(@RequestParam String position, Model model) {
        positionService.save(position);
        model.addAttribute(POSITIONS, positionService.findAll());
        return "redirect:/tool";
    }

    @PostMapping("hard")
    public String addHardSkill(@RequestParam(name = "hardSkill", required = false) String skillName, Model model) {
        final Skill skill = new Skill(skillName, TypeSkill.HARD);
        skillService.save(skill);
        model.addAttribute(HARDSKILLS, skillService.listTypeSkills(TypeSkill.HARD));
        return "redirect:/tool";
    }

    @PostMapping("soft")
    public String addSoftSkill(@RequestParam(name = "softSkill", required = false) String skillName, Model model) {
        final Skill skill = new Skill(skillName, TypeSkill.SOFT);
        skillService.save(skill);
        model.addAttribute(SOFTSKILLS, skillService.listTypeSkills(TypeSkill.SOFT));
        return "redirect:/tool";
    }

    @PostMapping("tool")
    public String addTool(@RequestParam(name = "tool", required = false) String skillName, Model model) {
        final Skill skill = new Skill(skillName, TypeSkill.TOOL);
        skillService.save(skill);
        model.addAttribute(TOOLS, skillService.listTypeSkills(TypeSkill.TOOL));
        return "redirect:/tool";
    }

    @GetMapping("remove/{id}")
    public String removeUser(@PathVariable String id, Model model) {
        userService.remove(id);
        model.addAttribute(USERS, userService.findAll());
        return "redirect:/tool";
    }
}
