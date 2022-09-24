package com.victor.microserviciousuarios.clientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-cursos", url = "localhost:8002")
public interface ICursoClienteRest {

    @DeleteMapping("/curso/eliminar-curso-usuario/{usuarioId}")
    public void eliminarUsuarioPorId(@PathVariable Integer usuarioId);

}
