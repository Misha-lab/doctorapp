package com.misha.doctorapp.services;

import com.misha.doctorapp.entities.Ticket;
import com.misha.doctorapp.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис, реализующий работу с CRUD-операциями для сущности ticket.
 */

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    /**
     * Метод для добавления талона в базу данных.
     *
     * @param ticket - данные о талоне.
     */
    public void add(Ticket ticket) {
        Optional.ofNullable(ticket.getDoctor()).orElseThrow();
        Optional.ofNullable(ticket.getStartTime()).orElseThrow();
        Optional.ofNullable(ticket.getEndTime()).orElseThrow();
        ticketRepository.save(ticket);
    }

    /**
     * Метод для получения списка талонов.
     *
     * @return - список талонов.
     */
    public List<Ticket> getTickets() {
        return ticketRepository.findAll();
    }

    /**
     * Метод для удаления указанного талона из базы данных.
     *
     * @param ticket - данные о талоне.
     */
    public void remove(Ticket ticket) {
        ticketRepository.delete(ticket);
    }

    /**
     * Метод для удаления талона из базы данных по его id.
     *
     * @param ticketId - идентификатор талона.
     */
    public void remove(int ticketId) {
        ticketRepository.deleteById(ticketId);
    }
}
