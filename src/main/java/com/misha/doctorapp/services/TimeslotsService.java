package com.misha.doctorapp.services;

import com.misha.doctorapp.entities.Doctor;
import com.misha.doctorapp.entities.Ticket;
import com.misha.doctorapp.repositories.DoctorRepository;
import com.misha.doctorapp.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import misha.doctorapp.generator.Rules;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static com.misha.doctorapp.helpers.TimeslotsServiceHelper.plusDuration;
import static com.misha.doctorapp.helpers.TimeslotsServiceHelper.xmlGregorianCalendarToLocalDateTime;

/**
 * Сервис, реализующий функцию генерации талонов к врачу по некоторым правилам.
 */

@Service
@RequiredArgsConstructor
public class TimeslotsService {

    private final TicketRepository ticketRepository;
    private final DoctorRepository doctorRepository;

    /**
     * Метод, который создает расписание (список талонов) для врача (по его id) по заданным правилам.
     *
     * @param rules    - правила, по которым генерируются талоны.
     * @param doctorId - идентификатор доктора, для которого генерируются талоны.
     * @return - список сгенерированных талонов.
     */
    public List<Ticket> generateTickets(Rules rules, int doctorId) {
        List<Ticket> tickets = new ArrayList<>();

        XMLGregorianCalendar curTime = rules.getStartTime();
        curTime = plusDuration(curTime, 0);
        XMLGregorianCalendar endTime = rules.getEndTime();
        endTime = plusDuration(endTime, 0);

        LocalDateTime dinnerStartTime = xmlGregorianCalendarToLocalDateTime(rules.getDinnerStartTime());
        LocalDateTime dinnerEndTime = xmlGregorianCalendarToLocalDateTime(rules.getDinnerEndTime());

        int duration = rules.getTicketDurationInMinutes();
        int ticketsCountRemain = rules.getTicketsCount();

        validateRules(rules);

        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        while (curTime.compare(endTime) == DatatypeConstants.LESSER) {
            LocalDateTime startTimeOfTicket = xmlGregorianCalendarToLocalDateTime(curTime);
            curTime = plusDuration(curTime, duration);
            LocalDateTime endTimeOfTicket = xmlGregorianCalendarToLocalDateTime(curTime);

            if (ticketsCountRemain > 0) {

                // Пропуск талонов, попадающих на время обеда у доктора
                if ((startTimeOfTicket.isAfter(dinnerStartTime) && startTimeOfTicket.isBefore(dinnerEndTime))
                        || (endTimeOfTicket.isAfter(dinnerStartTime) && endTimeOfTicket.isBefore(dinnerEndTime))) {

                    curTime = rules.getDinnerEndTime();
                    startTimeOfTicket = xmlGregorianCalendarToLocalDateTime(curTime);
                    curTime = plusDuration(curTime, duration);
                    endTimeOfTicket = xmlGregorianCalendarToLocalDateTime(curTime);
                }

                Ticket ticket = new Ticket();
                ticket.setStartTime(startTimeOfTicket);
                ticket.setEndTime(endTimeOfTicket);
                ticket.setDoctor(doctor);

                try {
                    tickets.add(ticket);

                    ticketsCountRemain--;
                } catch (DataIntegrityViolationException ex) {
                    ex.printStackTrace();
                }
            } else break;
        }
        ticketRepository.saveAll(tickets);
        return tickets;
    }

    /**
     * Метод для проверки корректности правил.
     *
     * @param rules - правила, необходимые для генерации талонов.
     */
    private void validateRules(Rules rules) {
        int durationOfTicketInMinutes = rules.getTicketDurationInMinutes();
        int ticketsCount = rules.getTicketsCount();

        LocalDateTime startTime = xmlGregorianCalendarToLocalDateTime(rules.getStartTime());
        LocalDateTime endTime = xmlGregorianCalendarToLocalDateTime(rules.getEndTime());

        LocalDateTime dinnerStartTime = xmlGregorianCalendarToLocalDateTime(rules.getDinnerStartTime());
        LocalDateTime dinnerEndTime = xmlGregorianCalendarToLocalDateTime(rules.getDinnerEndTime());

        Duration duration = Duration.between(startTime, endTime);
        long durationInMinutes = duration.toMinutes();

        if (startTime.isAfter(endTime)) {
            throw new RuntimeException("Время начало приёма > время конца приёма");
        }
        if ((long) durationOfTicketInMinutes * ticketsCount > durationInMinutes) {
            throw new RuntimeException("Время, необходимое для приёма пациентов, превышает длительность приёма!");
        }

        if (dinnerStartTime != null && dinnerEndTime != null) {

            // Проверка на то, не находится ли время на обед вне времени приёма
            if (dinnerStartTime.isAfter(dinnerEndTime)
                    || dinnerStartTime.isBefore(startTime) || dinnerStartTime.isAfter(endTime)
                    || dinnerEndTime.isBefore(startTime) || dinnerEndTime.isAfter(endTime)) {

                throw new RuntimeException("Выбрано некорректное время для перерыва!");
            }

            // Проверка на то, сможет ли доктор принять всех пациентов, с учётом времени на перерыв
            Duration durationOfDinner = Duration.between(dinnerStartTime, dinnerEndTime);
            long durationOfDinnerInMinutes = durationOfDinner.toMinutes();
            if ((long) durationOfTicketInMinutes * ticketsCount > durationInMinutes - durationOfDinnerInMinutes) {
                throw new RuntimeException("Доктору не хватит времени на перерывы!");
            }
        }
    }
}
