package com.juniorhunt.controller;

import com.juniorhunt.model.TypeSkill;
import com.juniorhunt.model.User;
import com.juniorhunt.service.SkillService;
import com.juniorhunt.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/junior")
public class UserController {
    private final UserService userService;
    private final SkillService skillService;

    public UserController(UserService userService, SkillService skillService) {
        this.userService = userService;
        this.skillService = skillService;
    }

    @GetMapping
    public String userPage(HttpServletRequest request, Model model) {
        User user = userService.findByUser(request.getRemoteUser());
        if (user != null) {
            model.addAttribute("hardSkills", skillService.listTypeSkillWithoutUserSkills(TypeSkill.HARD, user.getSkills()));
            model.addAttribute("softSkills", skillService.listTypeSkillWithoutUserSkills(TypeSkill.SOFT, user.getSkills()));
            model.addAttribute("tools", skillService.listTypeSkillWithoutUserSkills(TypeSkill.TOOL, user.getSkills()));
            model.addAttribute("listLangs", userService.findByUserLanguages(user));
            model.addAttribute("userHardSkills", skillService.listTypeUserSkills(TypeSkill.HARD, user.getSkills()));
            model.addAttribute("userSoftSkills", skillService.listTypeUserSkills(TypeSkill.SOFT, user.getSkills()));
            model.addAttribute("userToolSkills", skillService.listTypeUserSkills(TypeSkill.TOOL, user.getSkills()));
            model.addAttribute("user", user);
        }
        return "junior";
    }

}
