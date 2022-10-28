package com.victor.microservicioscursos.clientes;

import com.victor.microservicioscursos.models.entities.Bean.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio-usuarios", url = "microservicio-usuarios:8001")
public interface IUsuarioClientRest {

    @GetMapping("/usuario/{id}")
    public Usuario obtenerUsuario(@PathVariable Integer id);

    @PostMapping("/usuario/guardar")
    public Usuario guardarUsuario(@RequestBody Usuario usuario);

    @GetMapping("/usuario/usuarios-por-curso")
    public List<Usuario> listarUsuariosPorIds(@RequestParam List<Integer> ids);

}
