package com.victor.microserviciousuarios.models.repository;

import com.victor.microserviciousuarios.models.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioRepository extends CrudRepository<Usuario, Integer> {



}
