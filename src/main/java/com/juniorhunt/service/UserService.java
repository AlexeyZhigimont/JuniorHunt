package com.juniorhunt.service;

import com.juniorhunt.model.Language;
import com.juniorhunt.model.Role;
import com.juniorhunt.model.User;
import com.juniorhunt.repository.CountryRepository;
import com.juniorhunt.repository.PositionRepository;
import com.juniorhunt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final String ID = "userId";
    private final String EMAIL = "email";
    private final String PASSWORD = "password";
    private final String FIRST_NAME = "firstName";
    private final String LAST_NAME = "lastName";
    private final String PHONE = "phone";
    private final String ROLE = "role";
    private final String ABOUT = "aboutMe";
    private final String EXPERIENCE = "experience";
    private final String COURSES = "courses";
    private final String LANGUAGE = "language";
    private final String URL_IMG_PROFILE = "urlImgProfile";
    private final String URL_TELEGRAM = "urlTelegram";
    private final String URL_LINKEDIN = "urlLinkedin";
    private final String COUNTRY = "country";
    private final String POSITION = "position";
    private final String SKILLS = "skills";
    private final String IS_ACTION = "isAction";
    private final UserRepository userRepository;
    private final SkillService skillService;
    private final CountryRepository countryRepository;
    private final PositionRepository positionRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public UserService(UserRepository userRepository, SkillService skillService, CountryRepository countryRepository, PositionRepository positionRepository) {
        this.userRepository = userRepository;
        this.skillService = skillService;
        this.countryRepository = countryRepository;
        this.positionRepository = positionRepository;
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User findByUser(String username) {
        Optional<User> user = findByUsername(username);
        if (user.isPresent()) {
            return userRepository.findByUsername(username);
        }
        return null;
    }

    private Optional<User> findByUsername(String username) {
        return findAll().stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public Language findByUserLanguages(User user) {
        User userLang = findByUser(user.getUsername());
        return userLang.getLanguages();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean save(User user) {
        Optional<User> newUser = findByUsername(user.getUsername());
        if (newUser.isEmpty()) {
            createNewUserFromDB(user);
            return true;
        }
        userRepository.save(user);
        return false;
    }

    public void remove(String id) {
        User user = userRepository.findById(Long.valueOf(id)).orElse(null);
        assert user != null;
        userRepository.delete(user);
    }

    public User updateUserProfile(Map<String, String> userProfile) {
        Long userId = Long.valueOf(userProfile.get(ID));
        final User user = findById(userId);

        userProfile.keySet().forEach(System.out::println);


        assert user != null;
        if (userProfile.containsKey(FIRST_NAME)) {
            user.setFirstName(userProfile.get(FIRST_NAME));
        }
        if (userProfile.containsKey(LAST_NAME)) {
            user.setLastName(userProfile.get(LAST_NAME));
        }
        if (userProfile.containsKey(PASSWORD)) {
            user.setPassword(userProfile.get(PASSWORD));
        }
        if (userProfile.containsKey(ROLE)) {
            user.setRole(Role.valueOf(userProfile.get(ROLE)));
        }
        if (userProfile.containsKey(ABOUT)) {
            user.setAboutMe(userProfile.get(ABOUT));
        }
        if (userProfile.containsKey(EXPERIENCE)) {
            user.setExperience(userProfile.get(EXPERIENCE));
        }
        if (userProfile.containsKey(COURSES)) {
            user.setCourses(userProfile.get(COURSES));
        }
        if (userProfile.containsKey(LANGUAGE)) {
            user.setLanguages(updateLanguage(userProfile.get(LANGUAGE)));
        }
        if (userProfile.containsKey(URL_IMG_PROFILE)) {
            user.setUrlImgProfile(userProfile.get(URL_IMG_PROFILE));
        }
        if (userProfile.containsKey(URL_TELEGRAM)) {
            user.setUrlTelegram(userProfile.get(URL_TELEGRAM));
        }
        if (userProfile.containsKey(URL_LINKEDIN)) {
            user.setUrlLinkedin(userProfile.get(URL_LINKEDIN));
        }
        if (userProfile.containsKey(IS_ACTION)) {
            user.setActive(Boolean.parseBoolean(userProfile.get(IS_ACTION)));
        }
        if (userProfile.containsKey(COUNTRY)) {
            user.setCountry(countryRepository.findByCountry(userProfile.get(COUNTRY)));
        }
        if (userProfile.containsKey(PHONE)) {
            user.setPhone(userProfile.get(PHONE));
        }
        if (userProfile.containsKey(EMAIL)) {
            user.setEmail(userProfile.get(EMAIL));
        }
        if (userProfile.containsKey(POSITION)) {
            user.setPosition(positionRepository.findByPosition(userProfile.get(POSITION)));
        }
        if (userProfile.containsKey(SKILLS)) {

            skillService.updateSKills(userProfile, user);
        }
        userRepository.save(user);
        return user;
    }


    private void createNewUserFromDB(User user) {
        final User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder(user.getPassword()));
        newUser.setCreatedDate(user.getCreatedDate().plusHours(3));
        newUser.setRole(Role.USER);
        newUser.setActive(true);
        userRepository.save(newUser);
    }

    private String passwordEncoder(String stringPass) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
        return encoder.encode(stringPass);
    }

    public List<Language> listLanguages(User user) {
        Optional<Language> language = Optional.ofNullable(user.getLanguages());
        if (language.isEmpty()) {
            return Arrays.stream(Language.values()).collect(Collectors.toList());
        }
        return Arrays.stream(Language.values()).filter(l -> !l.getName().equals(user.getLanguages().getName())).toList();
    }

    public Language updateLanguage(String languageName) {
        return Arrays.stream(Language.values()).filter(l -> l.getName().equals(languageName)).findFirst().orElse(null);
    }


    public String savePathFile(MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String uuidFile = UUID.randomUUID().toString();
        String fileName = uuidFile + file.getOriginalFilename();
        file.transferTo(new File(uploadDir + "/" + fileName));
        return fileName;
    }
}
