package com.misha.doctorapp.common;

import com.misha.doctorapp.entities.Doctor;
import com.misha.doctorapp.entities.Patient;
import com.misha.doctorapp.entities.Ticket;
import lombok.Getter;
import misha.doctorapp.generator.Rules;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.*;
import java.util.*;

@Getter
public class TestData {

    private final Doctor testDoctor = new Doctor(2, UUID.randomUUID(), "Иван", "Иванов", "Иванович");
    private final Patient testPatient = new Patient(1000, UUID.randomUUID(), "Пётр", "Петров", "Петрович",
            LocalDate.of(2011, 2, 2));

    private final List<Ticket> testTickets = List.of(
            new Ticket(1, testDoctor, null,
                    LocalDateTime.of(2023, 9, 21, 13, 0),
                    LocalDateTime.of(2023, 9, 21, 13, 20),
                    true),
            new Ticket(2, testDoctor, null,
                    LocalDateTime.of(2023, 9, 21, 13, 20),
                    LocalDateTime.of(2023, 9, 21, 13, 40),
                    true),
            new Ticket(3, testDoctor, testPatient,
                    LocalDateTime.of(2023, 9, 21, 13, 40),
                    LocalDateTime.of(2023, 9, 21, 14, 0),
                    false),
            new Ticket(4, testDoctor, null,
                    LocalDateTime.of(2023, 9, 21, 16, 30),
                    LocalDateTime.of(2023, 9, 21, 16, 45),
                    true)
    );

    public Rules getTestRules() {
        Rules testRules = new Rules();
        testRules.setStartTime(initXMLDateTime(2023, 9, 22, 8, 0));
        testRules.setEndTime(initXMLDateTime(2023, 9, 22, 16, 0));

        testRules.setDinnerStartTime(initXMLDateTime(2023, 9, 22, 12, 0));
        testRules.setDinnerEndTime(initXMLDateTime(2023, 9, 22, 13, 0));

        testRules.setTicketDurationInMinutes(15);
        testRules.setTicketsCount(28);

        return testRules;
    }

    public XMLGregorianCalendar initXMLDateTime(int year, int month, int day, int hour, int minute) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(year, month, day, hour, minute,
                    0, 0, 0);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
