SELECT * FROM alumno_model;

COMMIT;

///////////////////////////////////////////////////////////
CREATE OR REPLACE PROCEDURE AGREGARALUMNO(
    p_nombre IN VARCHAR2,
    p_edad IN NUMBER
)
IS
    v_id NUMBER; -- Declaración de variable para el ID
    v_count NUMBER; -- Declaración de variable para contar registros
BEGIN
    -- Verificar si ya existe un alumno con el mismo nombre
    SELECT COUNT(*) INTO v_count
    FROM alumno_model
    WHERE UPPER(nombre) = UPPER(p_nombre);
    
    -- Si v_count es mayor que 0, significa que ya existe un alumno con ese nombre
    IF v_count > 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Ya existe un alumno con ese nombre.');
    ELSE
        -- Obtener el próximo valor de la secuencia para el ID
        SELECT alumno_model_seq.NEXTVAL INTO v_id FROM dual;
        
        -- Insertar los datos en la tabla usando el ID generado
        INSERT INTO alumno_model (id, nombre, edad) VALUES (v_id, UPPER(p_nombre), p_edad);
        
        -- Confirmar la transacción
        COMMIT;
    END IF;
END AGREGARALUMNO;


ALTER SEQUENCE alumno_model_seq INCREMENT BY 1;
ALTER SEQUENCE alumno_model_seq INCREMENT BY 50; -- o el valor original antes de tu modificación


///////////////////////////////////////////////////////////
CREATE OR REPLACE PROCEDURE ELIMINARALUMNO(
    p_id IN NUMBER
)
IS
BEGIN
    DELETE FROM alumno_model WHERE id = p_id;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20001, 'Error al eliminar el alumno: ' || SQLERRM);
END ELIMINARALUMNO;



///////////////////////////////////////////////////////////
CREATE OR REPLACE PROCEDURE EDITARALUMNO(
    p_id IN NUMBER,
    p_nombre IN VARCHAR2,
    p_edad IN NUMBER
)
IS
    v_count NUMBER;
BEGIN
    -- Verificar si ya existe un alumno con el mismo nombre
    SELECT COUNT(*)
    INTO v_count
    FROM alumno_model
    WHERE UPPER(nombre) = UPPER(p_nombre)
    AND id <> p_id; -- Excluir al alumno actual

    IF v_count > 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Ya existe otro alumno con el nombre proporcionado');
    END IF;

    -- Realizar la actualización del alumno
    UPDATE alumno_model
    SET nombre = p_nombre,
        edad = p_edad
    WHERE id = p_id;

    COMMIT;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20002, 'No se encontró ningún registro para el ID proporcionado');
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20003, 'Error al editar el alumno: ' || SQLERRM);
END EDITARALUMNO;


