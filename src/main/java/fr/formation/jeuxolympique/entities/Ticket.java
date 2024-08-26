package fr.formation.jeuxolympique.entities;


import fr.formation.jeuxolympique.models.TicketCategory;
import fr.formation.jeuxolympique.models.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 500)
    private String description;

    private TicketCategory category;

    private Double price;

    private String imageFileName;

    private Date evenDate;

    private String City;

    private  String site;

    private TicketStatus status;

}
