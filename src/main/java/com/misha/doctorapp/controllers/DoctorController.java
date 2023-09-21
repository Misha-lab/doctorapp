package com.misha.doctorapp.controllers;

import com.misha.doctorapp.entities.Doctor;
import com.misha.doctorapp.services.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Контроллер, реализующий работу CRUD-операций для сущности doctor.
 */

@RestController
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/api/v1/doctor/add")
    public void add(@RequestBody Doctor doctor) {
        doctorService.add(doctor);
    }

    @GetMapping("/api/v1/doctors")
    public List<Doctor> getDoctors() {
        return doctorService.getDoctors();
    }

    @PostMapping("/api/v1/doctor/remove")
    public void remove(@RequestBody Doctor doctor) {
        doctorService.remove(doctor);
    }

    @PostMapping("/api/v1/doctor/remove-by-id")
    public void remove(@RequestBody int id) {
        doctorService.remove(id);
    }
}
