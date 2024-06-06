package com.soap.repository;

import com.soap.model.AlumnoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<AlumnoModel, Integer> {
    AlumnoModel findByNombre(String nombre);
}
