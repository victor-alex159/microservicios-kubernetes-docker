package com.victor.microservicioscursos.models.repository;

import com.victor.microservicioscursos.models.entities.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ICursoRepository extends CrudRepository<Curso, Integer> {

    @Modifying
    @Query("delete from CursoUsuario cu where cu.usuarioId=:usuarioId")
    public void eliminarCursoUsuarioPorId(@Param("usuarioId") Integer usuarioId);

}
