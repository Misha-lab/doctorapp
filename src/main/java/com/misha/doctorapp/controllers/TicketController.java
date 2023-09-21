package com.misha.doctorapp.controllers;

import com.misha.doctorapp.entities.Ticket;
import com.misha.doctorapp.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Контроллер, реализующий работу CRUD-операций для сущности ticket.
 */

@RestController
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/api/v1/ticket/add")
    public void add(@RequestBody Ticket ticket) {
        ticketService.add(ticket);
    }

    @GetMapping("/api/v1/tickets")
    public List<Ticket> getTickets() {
        return ticketService.getTickets();
    }

    @PostMapping("/api/v1/ticket/remove")
    public void remove(@RequestBody Ticket ticket) {
        ticketService.remove(ticket);
    }

    @PostMapping("/api/v1/ticket/remove-by-id")
    public void remove(@RequestBody int id) {
        ticketService.remove(id);
    }

}
