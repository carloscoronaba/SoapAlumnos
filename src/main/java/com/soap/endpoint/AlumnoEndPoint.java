package com.soap.endpoint;

import com.soap.converter.AlumnoConverter;
import com.soap.gen.*;
import com.soap.model.AlumnoModel;
import com.soap.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class AlumnoEndPoint {
    private static final String NAMESPACE_URI = "http://www.soap.com/gen";

    @Autowired
    AlumnoRepository alumnoRepository;

    @Autowired
    AlumnoConverter alumnoConverter;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "postAgregarAlumnoRequest")
    @ResponsePayload
    public PostAgregarAlumnoResponse guardarAlumno(@RequestPayload PostAgregarAlumnoRequest postAgregarAlumnoRequest){
        PostAgregarAlumnoResponse postAgregarAlumnoResponse = new PostAgregarAlumnoResponse();
        AlumnoModel alumnoModel = alumnoConverter.convertAlumnoToAlumnoModel(postAgregarAlumnoRequest.getAlumno());
        Alumno alumno = alumnoConverter.convertAlumnoModelToAlumno(alumnoRepository.save(alumnoModel));
        postAgregarAlumnoResponse.setAlumno(alumno);
        return postAgregarAlumnoResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBuscarAlumnoRequest")
    @ResponsePayload
    public GetBuscarAlumnoResponse buscarAlumno(@RequestPayload GetBuscarAlumnoRequest getBuscarAlumnoRequest){
        GetBuscarAlumnoResponse getBuscarAlumnoResponse = new GetBuscarAlumnoResponse();
        AlumnoModel alumnoModel = alumnoRepository.findByNombre(getBuscarAlumnoRequest.getNombre());
        getBuscarAlumnoResponse.setAlumno(alumnoConverter.convertAlumnoModelToAlumno(alumnoModel));
        return getBuscarAlumnoResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMostrarAlumnosRequest")
    @ResponsePayload
    public GetMostrarAlumnosResponse mostrarAlumnos(@RequestPayload GetMostrarAlumnosRequest getMostrarAlumnosRequest){
        GetMostrarAlumnosResponse getMostrarAlumnosResponse = new GetMostrarAlumnosResponse();
        List<AlumnoModel> listaAlumnos = alumnoRepository.findAll();
        List<Alumno>alumnos = alumnoConverter.convertAlumnoModelsToAlumnos(listaAlumnos);
        getMostrarAlumnosResponse.getAlumnos().addAll(alumnos);
        return getMostrarAlumnosResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAlumnoRequest")
    @ResponsePayload
    public DeleteAlumnoResponse eliminarAlumno(@RequestPayload DeleteAlumnoRequest deleteAlumnoRequest){
        DeleteAlumnoResponse deleteAlumnoResponse = new DeleteAlumnoResponse();
        alumnoRepository.deleteById(deleteAlumnoRequest.getId());
        deleteAlumnoResponse.setStatus("Alumno eliminado");
        return deleteAlumnoResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateAlumnoRequest")
    @ResponsePayload
    public UpdateAlumnoResponse editarAlumno(@RequestPayload UpdateAlumnoRequest updateAlumnoRequest){
        UpdateAlumnoResponse updateAlumnoResponse = new UpdateAlumnoResponse();
        AlumnoModel alumnoModel = alumnoConverter.convertAlumnoToAlumnoModel(updateAlumnoRequest.getAlumno());
        Alumno alumno = alumnoConverter.convertAlumnoModelToAlumno(alumnoRepository.save(alumnoModel));
        updateAlumnoResponse.setAlumno(alumno);
        return updateAlumnoResponse;
    }
}
