package com.misha.doctorapp.services;

import com.misha.doctorapp.common.TestData;
import com.misha.doctorapp.entities.Doctor;
import com.misha.doctorapp.repositories.DoctorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    private TestData testData = new TestData();


    @Test
    public void addTest() {
        Doctor doctor = testData.getTestDoctor();
        Assertions.assertDoesNotThrow(() -> doctorService.add(doctor));
    }

    @Test
    public void addTest_WithoutFirstname() {
        Doctor doctor = testData.getTestDoctor();
        doctor.setFirstName(null);

        Assertions.assertThrows(NoSuchElementException.class, () -> doctorService.add(doctor));
    }

    @Test
    public void addTest_WithoutLastname() {
        Doctor doctor = testData.getTestDoctor();
        doctor.setLastName(null);

        Assertions.assertThrows(NoSuchElementException.class, () -> doctorService.add(doctor));
    }
}
