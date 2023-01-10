package com.juniorhunt.controller;

import com.juniorhunt.model.TypeSkill;
import com.juniorhunt.model.User;
import com.juniorhunt.service.CountryService;
import com.juniorhunt.service.PositionService;
import com.juniorhunt.service.SkillService;
import com.juniorhunt.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/junior")
public class UserController {
    private final UserService userService;
    private final SkillService skillService;
    private final CountryService countryService;
    private final PositionService positionService;

    public UserController(UserService userService, SkillService skillService, CountryService countryService, PositionService positionService) {
        this.userService = userService;
        this.skillService = skillService;
        this.countryService = countryService;
        this.positionService = positionService;
    }

    @GetMapping
    public String userPage(HttpServletRequest request, Model model) {
        User user = userService.findByUser(request.getRemoteUser());
        addAttributeModel(model, user);
        return "junior";
    }

    @GetMapping("/{username}")
    public String updateProfileForm(@PathVariable String username, Model model) {
        model.addAttribute("user", userService.loadUserByUsername(username));
        model.addAttribute("countries", countryService.findAll());
        model.addAttribute("positions", positionService.findAll());
        return "edit";
    }

    @PostMapping("edit")
    public String updateProfileUser(
            @RequestParam Map<String, String> userProfile,
            @RequestParam("urlImgProfile") MultipartFile file,
            Model model) throws IOException {
        if (!file.isEmpty())
            userProfile.put(file.getName(), userService.savePathFile(file));
        model.addAttribute("user", userService.updateUserProfile(userProfile));
        addAttributeModel(model, userService.updateUserProfile(userProfile));
        return "redirect:/junior";
    }

    @PostMapping("lang")
    public String updateLanguage(@RequestParam Map<String, String> userProfile, Model model) {
        addAttributeModel(model, userService.updateUserProfile(userProfile));
        return "redirect:/junior";
    }

    @PostMapping("aboutMe")
    public String updateAboutMe(@RequestParam Map<String, String> userProfile, Model model) {
        addAttributeModel(model, userService.updateUserProfile(userProfile));
        return "redirect:/junior";
    }

    @PostMapping
    public String updateSkills(@RequestParam Map<String, String> listSkills, Model model) {
        addAttributeModel(model, userService.updateUserProfile(listSkills));
        return "junior";
    }

    @PostMapping("experience")
    public String updateExperience(@RequestParam Map<String, String> userProfile, Model model) {
        addAttributeModel(model, userService.updateUserProfile(userProfile));
        return "redirect:/junior";
    }

    @PostMapping("courses")
    public String updateCourses(@RequestParam Map<String, String> userProfile, Model model) {
        addAttributeModel(model, userService.updateUserProfile(userProfile));
        return "redirect:/junior";
    }


    private void addAttributeModel(Model model, User user) {
        model.addAttribute("hardSkills", skillService.listTypeSkillWithoutUserSkills(TypeSkill.HARD, user.getSkills()));
        model.addAttribute("softSkills", skillService.listTypeSkillWithoutUserSkills(TypeSkill.SOFT, user.getSkills()));
        model.addAttribute("toolSkills", skillService.listTypeSkillWithoutUserSkills(TypeSkill.TOOL, user.getSkills()));
        model.addAttribute("listLanguages", userService.listLanguages(user));
        model.addAttribute("userHardSkills", skillService.listTypeUserSkills(TypeSkill.HARD, user.getSkills()));
        model.addAttribute("userSoftSkills", skillService.listTypeUserSkills(TypeSkill.SOFT, user.getSkills()));
        model.addAttribute("userToolSkills", skillService.listTypeUserSkills(TypeSkill.TOOL, user.getSkills()));
        model.addAttribute("user", user);
    }
}


























