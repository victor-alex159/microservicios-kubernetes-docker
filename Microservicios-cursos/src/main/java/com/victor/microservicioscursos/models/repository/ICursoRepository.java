package com.victor.microservicioscursos.models.repository;

import com.victor.microservicioscursos.models.entities.Curso;
import org.springframework.data.repository.CrudRepository;

public interface ICursoRepository extends CrudRepository<Curso, Integer> {
}
