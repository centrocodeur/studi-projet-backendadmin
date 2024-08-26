package fr.formation.jeuxolympique.controllers;

import fr.formation.jeuxolympique.dto.TicketDto;
import fr.formation.jeuxolympique.entities.Ticket;
import fr.formation.jeuxolympique.models.TicketStatus;
import fr.formation.jeuxolympique.repositories.TicketRepository;
import fr.formation.jeuxolympique.services.Impl.TicketServiceImpl;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    //private TicketServiceImpl ticketService;


    @GetMapping({"","/"})
    public String showTicket(Model model){
        List<Ticket> ticketList = ticketRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        model.addAttribute("ticketList", ticketList);
        return "tickets/index";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model){
        TicketDto ticketDto = new TicketDto();

        model.addAttribute("ticketDto", ticketDto);
        return "tickets/createTicket";
    }



    @PostMapping("/create")
    public String createTicket(@Valid @ModelAttribute TicketDto ticketDto, BindingResult result){
        if(ticketDto.getImageFile().isEmpty()){
            result.addError(new FieldError("ticketDto", "imageFile", "The image file is required"));
        }

        if(result.hasErrors()){
            return "tickets/createTicket";
        }

        // Save image file
        MultipartFile image = ticketDto.getImageFile();
        Date createdAt= new Date();
        String storageFileName= createdAt.getTime()+ "_"+ image.getOriginalFilename();

        try {
            String uploadDir= "public/images/";
            Path uploadPath= Paths.get(uploadDir);
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = image.getInputStream()){
                Files.copy(inputStream, Paths.get(uploadDir+storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }

        } catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }

        Ticket ticket =new Ticket();

        ticket.setDescription(ticketDto.getDescription());

        ticket.setCategory(ticketDto.getCategory());
        ticket.setPrice(ticketDto.getPrice());
        ticket.setStatus(ticketDto.getStatus());
        ticket.setEvenDate(ticketDto.getEvenDate());
        ticket.setImageFileName(storageFileName);
        ticket.setSite(ticketDto.getSite());
        ticket.setCity(ticketDto.getCity());


        ticketRepository.save(ticket);


        return "redirect:/tickets";
    }

    @GetMapping(value = "/edit")
    public String showEditPage(Model model, @RequestParam int id){

        try{
            Ticket ticket = ticketRepository.findById(id).get();
            //Ticket ticket = ticketService.get(id);
            model.addAttribute("ticket", ticket);

            TicketDto ticketDto = new TicketDto();
            ticketDto.setDescription(ticket.getDescription());
            ticketDto.setCategory(ticket.getCategory());
            ticketDto.setPrice(ticket.getPrice());
            ticketDto.setStatus(ticket.getStatus());
            ticketDto.setSite(ticket.getSite());
            ticketDto.setEvenDate(ticket.getEvenDate());
            ticketDto.setCity(ticket.getCity());

           model.addAttribute("ticketDto", ticketDto);
        }
        catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/tickets";
        }

        return "tickets/editTicket";

    }



    @PostMapping(value = "/edit")
    public String updateProduct(Model model, @RequestParam(required = false, defaultValue = "id") int id,
                                @Valid @ModelAttribute TicketDto ticketDto,
                                BindingResult result){


        try {
            Ticket ticket = ticketRepository.findById(id).get()
                    ;
           // Ticket ticket = ticketService.get(id);

            model.addAttribute("ticket",ticket);

            if (result.hasErrors()){
                return "tickets/editTicket";
            }

            if(!ticketDto.getImageFile().isEmpty()){
                //delete old image
                String uploadDir= "public/images/";
                Path odlImagePath= Paths.get(uploadDir + ticket.getImageFileName());

                try{
                    Files.delete(odlImagePath);

                }
                catch (Exception ex){
                    System.out.println("Exception: "+ ex.getMessage());

                }
                //Save new image file
                MultipartFile image= ticketDto.getImageFile();
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime()+ "_"+ image.getOriginalFilename();
                try (InputStream inputStream = image.getInputStream()){
                    Files.copy(inputStream, Paths.get(uploadDir+ storageFileName), StandardCopyOption.REPLACE_EXISTING);

                }
                ticket.setImageFileName(storageFileName);
            }

            ticket.setDescription(ticketDto.getDescription());
            ticket.setCategory(ticketDto.getCategory());
            ticket.setPrice(ticketDto.getPrice());
            ticket.setStatus(ticketDto.getStatus());
            ticket.setEvenDate(ticketDto.getEvenDate());
            ticket.setSite(ticketDto.getSite());
            ticket.setCity(ticketDto.getCity());


            ticketRepository.save(ticket);

        }
        catch (Exception ex){
            System.out.println("Exception: "+ ex.getMessage());
        }

        return "redirect:/tickets";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id){
        try {

            Ticket ticket = ticketRepository.findById(id).get();

            //delete product image
            Path imagePath = Paths.get("public/images/" + ticket.getImageFileName());
            try {
                Files.delete(imagePath);
            }
            catch (Exception ex){
                System.out.println("Exception: "+ ex.getMessage());
            }

            ticketRepository.delete(ticket);

        }
        catch (Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/tickets";
    }





}


