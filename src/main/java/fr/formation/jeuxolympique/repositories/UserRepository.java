package fr.formation.jeuxolympique.repositories;


import fr.formation.jeuxolympique.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Integer> {

    Optional<User> findByEmailAndPassword(String email, String pword);

    // Optional<User> findByEmail(String email);

     public User findByEmail(String email);
}
