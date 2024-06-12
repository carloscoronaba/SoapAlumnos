package com.soap.repository;

import com.soap.model.AlumnoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<AlumnoModel, Integer> {
    AlumnoModel findByNombre(String nombre);

    @Procedure(name = "agregar_alumno")
    void agregarAlumno(String p_nombre, int p_edad);
}
