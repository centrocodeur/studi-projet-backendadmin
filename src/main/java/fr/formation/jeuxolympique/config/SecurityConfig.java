package fr.formation.jeuxolympique.config;


import fr.formation.jeuxolympique.models.RoleType;
import fr.formation.jeuxolympique.services.Impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http //.csrf(csrf->csrf.disable()).cors(cors->cors.disable())
                .authorizeHttpRequests(requests->requests
                        .requestMatchers( "/").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers("/register").hasRole("ADMIN_MANAGER")
                        .requestMatchers("/tickets/create").hasAnyRole("ADMIN","ADMIN_MANAGER")
                        .requestMatchers("/tickets/edit").hasAnyRole("ADMIN","ADMIN_MANAGER")
                        .anyRequest().authenticated())

                        .formLogin(form->form.loginPage("/login")
                                .defaultSuccessUrl("/dashbord", true))
                .logout(config -> config.logoutSuccessUrl("/index"));




        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder();}


    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }





}
