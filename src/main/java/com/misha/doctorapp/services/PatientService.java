package com.misha.doctorapp.services;

import com.misha.doctorapp.entities.Patient;
import com.misha.doctorapp.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис, реализующий работу с CRUD-операциями для сущности patient.
 */

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    /**
     * Метод для добавления пациента в базу данных.
     *
     * @param patient - данные о пациенте.
     */
    public void add(Patient patient) {
        Optional.ofNullable(patient.getFirstName()).orElseThrow();
        Optional.ofNullable(patient.getLastName()).orElseThrow();
        Optional.ofNullable(patient.getBirthday()).orElseThrow();
        if (patient.getUuid() == null) {
            patient.setUuid(UUID.randomUUID());
        }
        patientRepository.save(patient);
    }

    /**
     * Метод для получения списка пациентов.
     *
     * @return - список пациентов.
     */
    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    /**
     * Метод для удаления указанного пациента из базы данных.
     *
     * @param patient - данные о пациенте.
     */
    public void remove(Patient patient) {
        patientRepository.delete(patient);
    }

    /**
     * Метод для удаления пациента из базы данных по его id.
     *
     * @param patientId - идентификатор пациента.
     */
    public void remove(int patientId) {
        patientRepository.deleteById(patientId);
    }
}
