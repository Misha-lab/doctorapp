package com.misha.doctorapp.services;

import com.misha.doctorapp.common.TestData;
import com.misha.doctorapp.entities.Ticket;
import com.misha.doctorapp.repositories.TicketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private TestData testData = new TestData();


    @Test
    public void addTest() {
        Ticket ticket = testData.getTestTickets().get(0);
        Assertions.assertDoesNotThrow(() -> ticketService.add(ticket));
    }

    @Test
    public void addTest_WithoutDoctor() {
        Ticket ticket = testData.getTestTickets().get(0);
        ticket.setDoctor(null);

        Assertions.assertThrows(NoSuchElementException.class, () -> ticketService.add(ticket));
    }

    @Test
    public void addTest_WithoutStartTime() {
        Ticket ticket = testData.getTestTickets().get(0);
        ticket.setStartTime(null);

        Assertions.assertThrows(NoSuchElementException.class, () -> ticketService.add(ticket));
    }

    @Test
    public void addTest_WithoutEndTime() {
        Ticket ticket = testData.getTestTickets().get(0);
        ticket.setEndTime(null);

        Assertions.assertThrows(NoSuchElementException.class, () -> ticketService.add(ticket));
    }
}

