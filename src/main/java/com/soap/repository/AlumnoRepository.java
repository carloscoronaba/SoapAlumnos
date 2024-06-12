package com.soap.repository;

import com.soap.model.AlumnoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumnoRepository extends JpaRepository<AlumnoModel, Integer> {
    AlumnoModel findByNombre(String nombre);

    @Procedure(name = "AGREGARALUMNO")
    void agregarAlumno(String p_nombre, int p_edad);

    @Procedure(name = "ELIMINARALUMNO")
    void eliminarAlumno(int p_id);

    @Procedure(name = "EDITARALUMNO")
    void editarAlumno(int p_id, String p_nombre, int edad);
}

