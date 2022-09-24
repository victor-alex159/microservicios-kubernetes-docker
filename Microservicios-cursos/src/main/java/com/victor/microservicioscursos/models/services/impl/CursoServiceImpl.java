package com.victor.microservicioscursos.models.services.impl;

import com.victor.microservicioscursos.clientes.IUsuarioClientRest;
import com.victor.microservicioscursos.models.entities.Bean.Usuario;
import com.victor.microservicioscursos.models.entities.Curso;
import com.victor.microservicioscursos.models.entities.CursoUsuario;
import com.victor.microservicioscursos.models.repository.ICursoRepository;
import com.victor.microservicioscursos.models.services.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements ICursoService {

    @Autowired
    private ICursoRepository cursoRepository;

    @Autowired
    private IUsuarioClientRest usuarioClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() throws Exception {
        List<Curso> listaCursos = (List<Curso>)cursoRepository.findAll();
        if(listaCursos != null) {
            return listaCursos;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Curso obtenerCurso(Integer id) throws Exception {
        return cursoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = false)
    public Curso guardarCurso(Curso curso) throws Exception {
        return cursoRepository.save(curso);
    }

    @Override
    public void eliminar(Integer id) throws Exception {
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Usuario asignarUsuario(Usuario usuario, Integer cursoId) throws Exception {
        Curso curso = cursoRepository.findById(cursoId).orElse(null);
        if(curso != null) {
            Usuario usuarioEncontrado = usuarioClientRest.obtenerUsuario(usuario.getId());
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioEncontrado.getId());

            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return usuarioEncontrado;
        }
        return null;
    }

    @Override
    @Transactional
    public Usuario crearUsuario(Usuario usuario, Integer cursoId) throws Exception {
        Curso curso = cursoRepository.findById(cursoId).orElse(null);
        if(curso != null) {
            Usuario usuarioNuevo = usuarioClientRest.guardarUsuario(usuario);
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevo.getId());

            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return usuarioNuevo;
        }
        return null;
    }

    @Override
    @Transactional
    public Usuario eliminarUsuarioDelCurso(Usuario usuario, Integer cursoId) throws Exception {
        Curso curso = cursoRepository.findById(cursoId).orElse(null);
        if(curso != null) {
            Usuario usuarioEncontrado = usuarioClientRest.obtenerUsuario(usuario.getId());
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioEncontrado.getId());

            curso.removeCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return usuarioEncontrado;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Curso porIdConUsuarios(Integer id) throws Exception {
        Curso curso = cursoRepository.findById(id).orElse(null);
        if(curso != null) {
            if(curso.getCursoUsuarios() != null && !curso.getCursoUsuarios().isEmpty()) {
                List<Integer> idsUsuarios = curso.getCursoUsuarios()
                                                    .stream()
                                                    .map(cu -> cu.getUsuarioId())
                                                    .collect(Collectors.toList());

                List<Usuario> usuarios = usuarioClientRest.listarUsuariosPorIds(idsUsuarios);
                curso.setUsuarios(usuarios);
            }
            return curso;
        }
        return null;
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Integer usuarioId) throws Exception {
        cursoRepository.eliminarCursoUsuarioPorId(usuarioId);
    }


}
