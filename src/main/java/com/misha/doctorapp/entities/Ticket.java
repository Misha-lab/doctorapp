package com.misha.doctorapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Класс для описания сущности ticket.
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "for_doctor", columnNames = {"doctor_id", "startTime", "endTime"}),
        @UniqueConstraint(name = "for_patient", columnNames = {"patient_id", "startTime", "endTime"})
})
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Patient patient;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
    private boolean isFree = true;

}
