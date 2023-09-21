package com.misha.doctorapp.services;

import com.misha.doctorapp.entities.Patient;
import com.misha.doctorapp.entities.Ticket;
import com.misha.doctorapp.repositories.PatientRepository;
import com.misha.doctorapp.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Сервис, реализующий работу основного функционала программы.
 */

@Service
@RequiredArgsConstructor
public class FunctionalService {

    private final TicketRepository ticketRepository;
    private final PatientRepository patientRepository;

    /**
     * Метод для получения свободных слотов времени к указанному врачу на указанную дату.
     *
     * @param doctorId  - идентификатор врача.
     * @param localDate - дата, на которую необходимо найти свободные талоны.
     * @return - список талонов, удовлетворяющих указанным в аргументах требованиях.
     */
    public List<Ticket> getFreeTicketsToDoctorByDate(int doctorId, LocalDate localDate) {
        return ticketRepository.findByDoctorId(doctorId).stream()
                .filter(ticket -> ticket.getStartTime().toLocalDate().equals(localDate))
                .filter(Ticket::isFree)
                .toList();
    }

    /**
     * Метод, позволяющий пациенту взять талон.
     *
     * @param patient  - данные пациента, который хочет взять талон.
     * @param ticketId - идентификатор талона.
     */
    @Transactional
    public void takeTicket(Patient patient, int ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() ->
                new RuntimeException("Такого талона нет!"));
        if (!patientRepository.existsById(patient.getId()))
            throw new RuntimeException("Пациент не найден!");

        if (ticket.isFree()) {
            ticket.setPatient(patient);
            ticket.setFree(false);
        } else throw new RuntimeException("Талон уже занят!");
    }

    /**
     * Метод для получения талонов, закрепленных за указанным пациентом (по id).
     *
     * @param patientId - идентификатор пациента (id).
     * @return - список талонов пациента.
     */
    public List<Ticket> getTicketsOfPatientById(int patientId) {
        return ticketRepository.findByPatientId(patientId);
    }

    /**
     * Метод для получения талонов, закрепленных за указанным пациентом (по uuid).
     *
     * @param patientUuid - идентификатор пациента (uuid).
     * @return - список талонов пациента.
     */
    public List<Ticket> getTicketsOfPatientByUuid(UUID patientUuid) {
        Patient patient = patientRepository.findByUuid(patientUuid).orElseThrow();
        return ticketRepository.findByPatient(patient);
    }
}
