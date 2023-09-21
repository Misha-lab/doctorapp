package com.misha.doctorapp.controllers;

import com.misha.doctorapp.entities.Patient;
import com.misha.doctorapp.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Контроллер, реализующий работу CRUD-операций для сущности patient.
 */

@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/api/v1/patient/add")
    public void add(@RequestBody Patient patient) {
        patientService.add(patient);
    }

    @GetMapping("/api/v1/patients")
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    @PostMapping("/api/v1/patient/remove")
    public void remove(@RequestBody Patient patient) {
        patientService.remove(patient);
    }

    @PostMapping("/api/v1/patient/remove-by-id")
    public void remove(@RequestBody int id) {
        patientService.remove(id);
    }
}
