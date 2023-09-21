package com.misha.doctorapp.services;

import com.misha.doctorapp.common.TestData;
import com.misha.doctorapp.entities.Doctor;
import com.misha.doctorapp.entities.Patient;
import com.misha.doctorapp.entities.Ticket;
import com.misha.doctorapp.repositories.PatientRepository;
import com.misha.doctorapp.repositories.TicketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FunctionalServiceTest {
    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private FunctionalService functionalService;

    private TestData testData = new TestData();

    private Doctor testDoctor = testData.getTestDoctor();
    private Patient testPatient = testData.getTestPatient();
    private List<Ticket> testTickets = testData.getTestTickets();


    @Test
    public void getFreeTicketsToDoctorByDateTest() {
        Mockito.when(ticketRepository.findByDoctorId(2)).thenReturn(testTickets);
        List<Ticket> result = functionalService.getFreeTicketsToDoctorByDate(2, LocalDate.of(2023, 9, 21));

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());
        Assertions.assertNotEquals(testTickets, result);
    }

    @Test
    public void takeTicketTest() {
        Mockito.when(ticketRepository.findById(4)).thenReturn(Optional.of(testTickets.get(3)));
        Mockito.when(patientRepository.existsById(1000)).thenReturn(true);
        functionalService.takeTicket(testPatient, 4);

        Assertions.assertFalse(testTickets.get(3).isFree());
        Assertions.assertEquals(testPatient, testTickets.get(3).getPatient());
    }

    @Test
    public void takeTicketTest_PatientNotExists() {
        Mockito.when(ticketRepository.findById(4)).thenReturn(Optional.of(testTickets.get(3)));
        Mockito.when(patientRepository.existsById(2000)).thenReturn(false);

        Assertions.assertThrows(RuntimeException.class, () -> functionalService.takeTicket(testPatient, 4));
    }

    @Test
    public void takeTicketTest_TicketNotExists() {
        Mockito.when(ticketRepository.findById(44)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> functionalService.takeTicket(testPatient, 4));
    }

    @Test
    public void getTicketsOfPatientByIdTest() {
        Mockito.when(ticketRepository.findByPatientId(testPatient.getId())).thenReturn(List.of(testTickets.get(3)));
        List<Ticket> result = functionalService.getTicketsOfPatientById(1000);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(testTickets.get(3), result.get(0));
    }

    @Test
    public void getTicketsOfPatientByUuidTest() {
        Mockito.when(ticketRepository.findByPatient(testPatient)).thenReturn(List.of(testTickets.get(3)));
        Mockito.when(patientRepository.findByUuid(testPatient.getUuid())).thenReturn(Optional.of(testPatient));
        List<Ticket> result = functionalService.getTicketsOfPatientByUuid(testPatient.getUuid());

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(testTickets.get(3), result.get(0));
    }

    @Test
    public void getTicketsOfPatientByUuidTest_PatientNotExists() {
        Mockito.when(patientRepository.findByUuid(testPatient.getUuid())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class,
                () -> functionalService.getTicketsOfPatientByUuid(testPatient.getUuid()));

    }
}
