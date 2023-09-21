package com.misha.doctorapp.controllers;

import com.misha.doctorapp.entities.Patient;
import com.misha.doctorapp.entities.Ticket;
import com.misha.doctorapp.services.FunctionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


/**
 * Контроллер для работы с основным функционалом.
 */

@RestController
@RequiredArgsConstructor
public class FunctionalController {

    private final FunctionalService functionalService;

    @GetMapping("/api/v1/tickets/free/doctor{doctorId}/{localDate}")
    public List<Ticket> getFreeTicketsToDoctorByDate(@PathVariable int doctorId,
                                                     @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate localDate) {
        return functionalService.getFreeTicketsToDoctorByDate(doctorId, localDate);
    }

    @PostMapping("/api/v1/tickets/take{ticketId}")
    public void takeTicket(@RequestBody Patient patient, @PathVariable int ticketId) {
        functionalService.takeTicket(patient, ticketId);
    }

    @GetMapping("/api/v1/tickets/patient/id/{patientId}")
    public List<Ticket> getTicketsOfPatientById(@PathVariable int patientId) {
        return functionalService.getTicketsOfPatientById(patientId);
    }

    @GetMapping("/api/v1/tickets/patient/uuid/{patientUuid}")
    public List<Ticket> getTicketsOfPatientByUuid(@PathVariable UUID patientUuid) {
        return functionalService.getTicketsOfPatientByUuid(patientUuid);
    }

}
