package com.victor.microserviciousuarios.models.repository;

import com.victor.microserviciousuarios.models.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IUsuarioRepository extends CrudRepository<Usuario, Integer> {

    @Query("select u from Usuario u where u.email = :email")
    Usuario findByEmail(@Param("email") String email);


}
