package com.victor.microservicioscursos.models.services;

import com.victor.microservicioscursos.models.entities.Bean.Usuario;
import com.victor.microservicioscursos.models.entities.Curso;

import java.util.List;

public interface ICursoService {

    public List<Curso> listar() throws Exception;
    public Curso obtenerCurso(Integer id) throws Exception;
    public Curso guardarCurso(Curso curso) throws Exception;
    public void eliminar(Integer id) throws Exception;

    Usuario asignarUsuario(Usuario usuario, Integer cursoId) throws Exception;
    Usuario crearUsuario(Usuario usuario, Integer cursoId) throws Exception;
    Usuario eliminarUsuarioDelCurso(Usuario usuario, Integer cursoId) throws Exception;
    Curso porIdConUsuarios(Integer id) throws Exception;

}
