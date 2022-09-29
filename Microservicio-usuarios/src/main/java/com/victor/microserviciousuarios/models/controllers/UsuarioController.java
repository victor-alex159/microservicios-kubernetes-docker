package com.victor.microserviciousuarios.models.controllers;

import com.victor.microserviciousuarios.models.entities.Usuario;
import com.victor.microserviciousuarios.models.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() throws Exception {
        List<Usuario> listaUsuarios = usuarioService.listar();
        return new ResponseEntity<List<Usuario>>(listaUsuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtnerUsuario(@PathVariable Integer id) throws Exception {
        Usuario usuario = usuarioService.obtenerUsuario(id);
        if(usuario != null) {
            return ResponseEntity.status(HttpStatus.OK).body(usuario);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) throws Exception {
        Usuario usuarioNuevo = usuarioService.guardar(usuario);
        if(result.hasErrors()) {
            return validarError(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
    }

    @PutMapping()
    public ResponseEntity<?> editarUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) throws Exception {
        if(result.hasErrors()) {
            return validarError(result);
        }

        Usuario usuarioEncontrado = usuarioService.obtenerUsuario(usuario.getId());
        if(usuarioEncontrado != null) {
            Usuario usuarioEdit = usuarioService.guardar(usuarioEncontrado);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioEdit);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id) throws Exception {
        Usuario usuarioEncontrado = usuarioService.obtenerUsuario(id);
        if(usuarioEncontrado != null) {
            usuarioService.eliminar(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    private static ResponseEntity<Map<String, String>> validarError(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage() + ".");
        });
        return ResponseEntity.badRequest().body(errores);
    }

    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> listarUsuariosPorIds(@RequestParam List<Integer> ids) throws Exception {
        List<Usuario> listaUsuarios = usuarioService.listarPorIds(ids);
        if(listaUsuarios != null) {
            return ResponseEntity.status(HttpStatus.OK).body(listaUsuarios);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

}
