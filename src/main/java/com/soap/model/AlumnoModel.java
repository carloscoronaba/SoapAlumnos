package com.soap.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AlumnoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alumno_model_seq")
    @SequenceGenerator(name = "alumno_model_seq", sequenceName = "alumno_model_seq", allocationSize = 1)
    private int id;

    private String nombre;
    private int edad;
}
