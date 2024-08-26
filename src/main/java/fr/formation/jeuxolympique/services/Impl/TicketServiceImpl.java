package fr.formation.jeuxolympique.services.Impl;

import fr.formation.jeuxolympique.entities.Ticket;
import fr.formation.jeuxolympique.repositories.TicketRepository;
import fr.formation.jeuxolympique.services.TicketService;

import java.util.Optional;

public class TicketServiceImpl  implements TicketService {

    private TicketRepository ticketRepository;
    @Override
    public Ticket get(Integer id) {
        Optional <Ticket> ticket = ticketRepository.findById(id);
        if(ticket.isPresent()){
            return ticket.get();
        }
        throw new RuntimeException("Ticket not found with ID "+ id);
    }
}
