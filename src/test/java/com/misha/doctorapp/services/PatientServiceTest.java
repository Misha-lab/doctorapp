package com.misha.doctorapp.services;

import com.misha.doctorapp.common.TestData;
import com.misha.doctorapp.entities.Patient;
import com.misha.doctorapp.repositories.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private TestData testData = new TestData();


    @Test
    public void addTest() {
        Patient patient = testData.getTestPatient();
        Assertions.assertDoesNotThrow(() -> patientService.add(patient));
    }

    @Test
    public void addTest_WithoutFirstname() {
        Patient patient = testData.getTestPatient();
        patient.setFirstName(null);

        Assertions.assertThrows(NoSuchElementException.class, () -> patientService.add(patient));
    }

    @Test
    public void addTest_WithoutLastname() {
        Patient patient = testData.getTestPatient();
        patient.setLastName(null);

        Assertions.assertThrows(NoSuchElementException.class, () -> patientService.add(patient));
    }

    @Test
    public void addTest_WithoutBirthday() {
        Patient patient = testData.getTestPatient();
        patient.setBirthday(null);

        Assertions.assertThrows(NoSuchElementException.class, () -> patientService.add(patient));
    }
}

