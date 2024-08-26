package fr.formation.jeuxolympique.controllers;


import fr.formation.jeuxolympique.dto.RegisterDto;
import fr.formation.jeuxolympique.entities.Role;

import fr.formation.jeuxolympique.entities.User;
import fr.formation.jeuxolympique.models.RoleType;
import fr.formation.jeuxolympique.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping({"","/login"})
    public String getLoginPag(){
        return "login";
    }


    @GetMapping({"index"})
    public String home(){
        return "index2";
    }


    @GetMapping("/dashbord")
    public String showdashbord(){
        return "dashbord/dashbord";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute(registerDto);
        model.addAttribute("success", false);

        return "register_page";
    }



    @PreAuthorize("hasRole('ADMIN_MANAGER')")
    @PostMapping("/register")
    public String register(Model model,
                           @Valid @ModelAttribute RegisterDto registerDto,
                           BindingResult result){
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())){
            result.addError(
                    new FieldError("registerDto", "confirmPassword",
                            "Password and Confirm password do not match ")
            );
        }

        User user =userRepository.findByEmail(registerDto.getEmail());
        if(user !=null){
            result.addError(
                    new FieldError("registerDto","email",
                            "Email address is already used")
            );
        }

        if (result.hasErrors()){
            return "register_page";
        }

        try {

            var bCrypteEncoder =new BCryptPasswordEncoder();
            Role adminRole = new Role();
            adminRole.setRoleType(RoleType.ADMIN);
            User newUser = new User();
            newUser.setFirstName(registerDto.getFirstName());
            newUser.setLastName(registerDto.getLastName());
            newUser.setEmail(registerDto.getEmail());
            newUser.setPhone(registerDto.getPhone());
            newUser.setAddress(registerDto.getAddress());
            newUser.setRole(adminRole);
            newUser.setCreatedAdt(new Date());
            newUser.setActivated(true);
            newUser.setPassword(bCrypteEncoder.encode(registerDto.getPassword()));

            userRepository.save(newUser);

            model.addAttribute("registerDto", new RegisterDto());
            model.addAttribute("success", true);

        }
        catch (Exception ex){
            result.addError(
                    new FieldError("registerDto","firstName",
                            ex.getMessage())
            );
        }

        return "register_page";
    }

}
