package com.misha.doctorapp.repositories;

import com.misha.doctorapp.entities.Doctor;
import com.misha.doctorapp.entities.Patient;
import com.misha.doctorapp.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByDoctor(Doctor doctor);

    List<Ticket> findByPatient(Patient patient);

    List<Ticket> findByDoctorId(int doctorId);

    List<Ticket> findByPatientId(int patientId);
}
