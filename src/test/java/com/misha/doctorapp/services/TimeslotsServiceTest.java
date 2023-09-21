package com.misha.doctorapp.services;

import com.misha.doctorapp.common.TestData;
import com.misha.doctorapp.entities.Doctor;
import com.misha.doctorapp.entities.Patient;
import com.misha.doctorapp.entities.Ticket;
import com.misha.doctorapp.helpers.TimeslotsServiceHelper;
import com.misha.doctorapp.repositories.DoctorRepository;
import com.misha.doctorapp.repositories.TicketRepository;
import misha.doctorapp.generator.Rules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TimeslotsServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private TimeslotsService timeslotsService;

    private TestData testData = new TestData();

    private Rules rules;
    private Doctor testDoctor = testData.getTestDoctor();
    private Patient testPatient = testData.getTestPatient();
    private List<Ticket> testTickets = testData.getTestTickets();

    @BeforeEach
    public void setup() {
        rules = testData.getTestRules();
    }


    @Test
    public void generateTicketsTest() {
        Mockito.when(doctorRepository.findById(testDoctor.getId())).thenReturn(Optional.of(testDoctor));
        List<Ticket> result = timeslotsService.generateTickets(rules, testDoctor.getId());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(rules.getTicketsCount(), result.size());
    }

    @Test
    public void generateTicketsTest_DoctorNotExists() {
        Mockito.when(doctorRepository.findById(testDoctor.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> timeslotsService.generateTickets(rules, testDoctor.getId()));
    }

    @Test
    public void generateTicketsTest_BadRules_StartAfterEnd() {
        XMLGregorianCalendar temp = rules.getStartTime();
        rules.setStartTime(rules.getEndTime());
        rules.setEndTime(temp);

        Assertions.assertThrows(RuntimeException.class, () -> timeslotsService.generateTickets(rules, testDoctor.getId()));
    }

    @Test
    public void generateTicketsTest_BadRules_NoTimeForAllTickets() {
        rules.setTicketsCount(1000);

        Assertions.assertThrows(RuntimeException.class, () -> timeslotsService.generateTickets(rules, testDoctor.getId()));
    }

    @Test
    public void generateTicketsTest_BadRules_IncorrectTimeForDinner_BeforeStart() {
        rules.setDinnerStartTime(TimeslotsServiceHelper.plusDuration(rules.getStartTime(), -60));

        Assertions.assertThrows(RuntimeException.class, () -> timeslotsService.generateTickets(rules, testDoctor.getId()));
    }

    @Test
    public void generateTicketsTest_BadRules_IncorrectTimeForDinner_AfterEnd() {
        rules.setDinnerEndTime(TimeslotsServiceHelper.plusDuration(rules.getEndTime(), 20));

        Assertions.assertThrows(RuntimeException.class, () -> timeslotsService.generateTickets(rules, testDoctor.getId()));
    }

    @Test
    public void generateTicketsTest_BadRules_NoTimeForDinner() {
        rules.setTicketsCount(32);

        Assertions.assertThrows(RuntimeException.class, () -> timeslotsService.generateTickets(rules, testDoctor.getId()));
    }

}
