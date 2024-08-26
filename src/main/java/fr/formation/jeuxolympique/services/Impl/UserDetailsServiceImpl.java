package fr.formation.jeuxolympique.services.Impl;

import fr.formation.jeuxolympique.entities.Role;

import fr.formation.jeuxolympique.entities.User;

import fr.formation.jeuxolympique.models.RoleType;
import fr.formation.jeuxolympique.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;





@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userAdmin = userRepository.findByEmail(username);
        if(userAdmin==null){
            return null;
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(userAdmin.getUsername())
                .password(userAdmin.getPassword())
                .roles(userAdmin.getRole().getRoleType().toString())
                .build();




    }




}
