SELECT * FROM alumno_model;

COMMIT;

CREATE OR REPLACE PROCEDURE AGREGARALUMNO(
    p_nombre IN VARCHAR2,
    p_edad IN NUMBER
)
IS
    v_id NUMBER; -- Declaración de variable para el ID
BEGIN
    -- Obtener el próximo valor de la secuencia para el ID
    SELECT alumno_model_seq.NEXTVAL INTO v_id FROM dual;
    
    -- Insertar los datos en la tabla usando el ID generado
    INSERT INTO alumno_model (id, nombre, edad) VALUES (v_id, UPPER(p_nombre), p_edad);
    
    -- Confirmar la transacción
    COMMIT;
END AGREGARALUMNO;
/

ALTER SEQUENCE alumno_model_seq INCREMENT BY 1;
ALTER SEQUENCE alumno_model_seq INCREMENT BY 50; -- o el valor original antes de tu modificación




