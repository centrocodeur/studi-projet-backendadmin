package fr.formation.jeuxolympique.services.Impl;

import fr.formation.jeuxolympique.dto.CredentialsDto;
import fr.formation.jeuxolympique.dto.UserDto;
import fr.formation.jeuxolympique.entities.Role;
import fr.formation.jeuxolympique.entities.User;
import fr.formation.jeuxolympique.exceptions.AppException;
import fr.formation.jeuxolympique.mappers.UserMapper;
import fr.formation.jeuxolympique.models.RoleType;
import fr.formation.jeuxolympique.repositories.UserRepository;
import fr.formation.jeuxolympique.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder;


    /*

    public User registerUser(String lastName, String firstName, String password, String email, Role role ){

        if (password ==null && email ==null) {
            return  null;
        } else {
            User user = new User();
            user.setLastName(lastName);
            user.setFirstName(firstName);
            user.setPassword(password);
            user.setEmail(email);
            user.setRole(role);
            user.setActivated(true);

           userRepository.save(user);
           return user;

        }

    }

     */




   @PostConstruct
    public void postConstruct() {

        User user = new User();

        Role adminRole = new Role();
        adminRole.setRoleType(RoleType.ADMIN_MANAGER);
        user.setRole(adminRole);
        user.setEmail("adminmanager@gmail.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setActivated(true);
        user.setLastName("adminmanager");
        user.setFirstName("adminmanager");
        userRepository.save(user);
    }











}
