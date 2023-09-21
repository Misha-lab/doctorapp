package com.misha.doctorapp.services;

import com.misha.doctorapp.entities.Doctor;
import com.misha.doctorapp.repositories.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис, реализующий работу с CRUD-операциями для сущности doctor.
 */

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    /**
     * Метод для добавления врача в базу данных.
     *
     * @param doctor - данные о враче.
     */
    public void add(Doctor doctor) {
        Optional.ofNullable(doctor.getFirstName()).orElseThrow();
        Optional.ofNullable(doctor.getLastName()).orElseThrow();
        if (doctor.getUuid() == null) {
            doctor.setUuid(UUID.randomUUID());
        }
        doctorRepository.save(doctor);
    }

    /**
     * Метод для получения списка врачей.
     *
     * @return - список врачей.
     */
    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    /**
     * Метод для удаления указанного врача из базы данных.
     *
     * @param doctor - данные о враче.
     */
    public void remove(Doctor doctor) {
        doctorRepository.delete(doctor);
    }

    /**
     * Метод для удаления врача из базы данных по его id.
     *
     * @param doctorId - идентификатор врача.
     */
    public void remove(int doctorId) {
        doctorRepository.deleteById(doctorId);
    }
}
