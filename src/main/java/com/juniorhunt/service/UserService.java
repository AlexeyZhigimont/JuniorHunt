package com.juniorhunt.service;

import com.juniorhunt.model.Language;
import com.juniorhunt.model.Role;
import com.juniorhunt.model.User;
import com.juniorhunt.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public void remove(String id) {
        User user = userRepository.findById(Long.valueOf(id)).orElse(null);
        assert user != null;
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
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
        return false;
    }

    public Language findByUserLanguages(User user) {
        User userLang = findByUser(user.getUsername());
        return userLang.getLanguages();
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

    private Optional<User> findByUsername(String username) {
        return findAll().stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }
}
