package fr.formation.jeuxolympique.repositories;

import fr.formation.jeuxolympique.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository <Ticket, Integer> {
}
