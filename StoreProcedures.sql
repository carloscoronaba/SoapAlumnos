SELECT * FROM alumno_model;
SELECT * FROM alumno_auditoria;

COMMIT;

///////////////////////////////////////////////////////////
CREATE OR REPLACE PROCEDURE AGREGARALUMNO(
    p_nombre IN VARCHAR2,
    p_edad IN NUMBER
)
IS
    v_id NUMBER; -- Declaraci�n de variable para el ID
    v_count NUMBER; -- Declaraci�n de variable para contar registros
BEGIN
    -- Verificar si ya existe un alumno con el mismo nombre
    SELECT COUNT(*) INTO v_count
    FROM alumno_model
    WHERE UPPER(nombre) = UPPER(p_nombre);

    -- Si v_count es mayor que 0, significa que ya existe un alumno con ese nombre
    IF v_count > 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Ya existe un alumno con ese nombre.');
    ELSE
        -- Obtener el pr�ximo valor de la secuencia para el ID
        SELECT alumno_model_seq.NEXTVAL INTO v_id FROM dual;

        -- Insertar los datos en la tabla usando el ID generado
        INSERT INTO alumno_model (id, nombre, edad) VALUES (v_id, UPPER(p_nombre), p_edad);

        -- Confirmar la transacci�n
        COMMIT;
    END IF;
END AGREGARALUMNO;


ALTER SEQUENCE alumno_model_seq INCREMENT BY 1;
ALTER SEQUENCE alumno_model_seq INCREMENT BY 50; -- o el valor original antes de tu modificaci�n


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

    -- Realizar la actualizaci�n del alumno
    UPDATE alumno_model
    SET nombre = UPPER(p_nombre),
        edad = p_edad
    WHERE id = p_id;

    COMMIT;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20002, 'No se encontr� ning�n registro para el ID proporcionado');
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20003, 'Error al editar el alumno: ' || SQLERRM);
END EDITARALUMNO;


///////////////////////////////////////////////////////////
CREATE TABLE alumno_auditoria (
    id NUMBER PRIMARY KEY,
    id_alumno NUMBER,
    operacion VARCHAR2(10),
    nombre VARCHAR2(100),
    edad NUMBER,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE SEQUENCE alumno_auditoria_seq START WITH 1;



CREATE OR REPLACE TRIGGER trg_alumno_insert
AFTER INSERT ON alumno_model
FOR EACH ROW
BEGIN
    INSERT INTO alumno_auditoria (id, id_alumno, operacion, nombre, edad)
    VALUES (alumno_auditoria_seq.NEXTVAL, :NEW.id, 'INSERT', :NEW.nombre, :NEW.edad);
END;


CREATE OR REPLACE TRIGGER trg_alumno_update
AFTER UPDATE ON alumno_model
FOR EACH ROW
BEGIN
    INSERT INTO alumno_auditoria (id, id_alumno, operacion, nombre, edad)
    VALUES (alumno_auditoria_seq.NEXTVAL, :NEW.id, 'UPDATE', :NEW.nombre, :NEW.edad);
END;


CREATE OR REPLACE TRIGGER trg_alumno_delete
AFTER DELETE ON alumno_model
FOR EACH ROW
BEGIN
    INSERT INTO alumno_auditoria (id, id_alumno, operacion, nombre, edad)
    VALUES (alumno_auditoria_seq.NEXTVAL, :OLD.id, 'DELETE', :OLD.nombre, :OLD.edad);
END;

