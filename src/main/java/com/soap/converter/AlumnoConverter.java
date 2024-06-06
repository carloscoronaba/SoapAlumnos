package com.soap.converter;

import com.soap.gen.Alumno;
import com.soap.model.AlumnoModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AlumnoConverter {
    public AlumnoModel convertAlumnoToAlumnoModel(Alumno alumno){
        AlumnoModel alumnoModel = new AlumnoModel();
        alumnoModel.setId(alumno.getId());
        alumnoModel.setNombre(alumno.getNombre());
        alumnoModel.setEdad(alumno.getEdad());
        return alumnoModel;
    }

    public Alumno convertAlumnoModelToAlumno(AlumnoModel alumnoModel){
        Alumno alumno = new Alumno();
        alumno.setId(alumnoModel.getId());
        alumno.setNombre(alumnoModel.getNombre());
        alumno.setEdad(alumnoModel.getEdad());
        return alumno;
    }

    public List<AlumnoModel> convertAlumnosToAlumnoModels(List<Alumno> alumnos){
        List<AlumnoModel> alumnoModels = new ArrayList<>();

        for(Alumno alumno: alumnos){
            alumnoModels.add(convertAlumnoToAlumnoModel(alumno));
        }
        return alumnoModels;
    }

    public List<Alumno> convertAlumnoModelsToAlumnos(List<AlumnoModel> alumnoModels){
        List<Alumno> alumnos = new ArrayList<>();

        for(AlumnoModel alumnoModel: alumnoModels){
            alumnos.add(convertAlumnoModelToAlumno(alumnoModel));
        }

        return alumnos;
    }
}
