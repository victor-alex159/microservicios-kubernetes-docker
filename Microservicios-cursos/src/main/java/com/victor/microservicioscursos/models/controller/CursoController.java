package com.victor.microservicioscursos.models.controller;

import com.victor.microservicioscursos.models.entities.Bean.Usuario;
import com.victor.microservicioscursos.models.entities.Curso;
import com.victor.microservicioscursos.models.services.ICursoService;
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
@RequestMapping(value = "/curso")
public class CursoController {

    @Autowired
    private ICursoService cursoService;


    @GetMapping("/listar")
    public ResponseEntity<?> listarCursos() throws Exception {
        List<Curso> listaCursos = cursoService.listar();
        if(listaCursos != null) {
            return ResponseEntity.status(HttpStatus.OK).body(listaCursos);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCurso(@PathVariable Integer id) throws Exception {
        Curso curso = cursoService.porIdConUsuarios(id);
        if(curso != null) {
            return ResponseEntity.status(HttpStatus.OK).body(curso);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarCurso(@Valid @RequestBody Curso curso, BindingResult result) throws Exception {
        if(result.hasErrors()) {
            return validarError(result);
        }

        Curso cursoNuevo = cursoService.guardarCurso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoNuevo);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> editarCurso(@Valid @RequestBody Curso curso, BindingResult result) throws Exception {
        if(result.hasErrors()) {
            return validarError(result);
        }

        Curso cursoEncontrado = cursoService.obtenerCurso(curso.getId());
        if(cursoEncontrado != null) {
            Curso cursoMod = new Curso();
            cursoMod.setNombre(curso.getNombre());
            Curso cursoEdit = cursoService.guardarCurso(cursoMod);
            return ResponseEntity.status(HttpStatus.OK).body(cursoEdit);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCurso(@PathVariable Integer id) throws Exception {
        Curso cursoEncontrado = cursoService.obtenerCurso(id);
        if(cursoEncontrado != null) {
            cursoService.eliminar(id);
            return ResponseEntity.ok(cursoEncontrado);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    private static ResponseEntity<Map<String, String>> validarError(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }


    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asginarUsuario(@RequestBody Usuario usuario, @PathVariable Integer cursoId) throws Exception {
        Usuario usuarioAsignado = cursoService.asignarUsuario(usuario, cursoId);
        if(usuarioAsignado != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioAsignado);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Integer cursoId) throws Exception {
        Usuario usuarioNuevo = cursoService.crearUsuario(usuario, cursoId);
        int[] x = {1, 2,3 };
        if(usuarioNuevo != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNuevo);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Integer cursoId) throws Exception {
        Usuario usuarioNuevo = cursoService.eliminarUsuarioDelCurso(usuario, cursoId);
        if(usuarioNuevo != null) {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioNuevo);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar-curso-usuario/{usuarioId}")
    public ResponseEntity<?> eliminarUsuarioPorId(@PathVariable Integer usuarioId) throws Exception {
        cursoService.eliminarCursoUsuarioPorId(usuarioId);
        return ResponseEntity.noContent().build();
    }




}
