package com.victor.microserviciousuarios.models.services.Impl;

import com.victor.microserviciousuarios.clientes.ICursoClienteRest;
import com.victor.microserviciousuarios.models.entities.Usuario;
import com.victor.microserviciousuarios.models.repository.IUsuarioRepository;
import com.victor.microserviciousuarios.models.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ICursoClienteRest cursoClienteRest;


    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() throws Exception {
        List<Usuario> listaUsuarios = (List<Usuario>)usuarioRepository.findAll();
        if(listaUsuarios != null) {
            return listaUsuarios;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario obtenerUsuario(Integer id) throws Exception {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = false)
    public Usuario guardar(Usuario usuario) throws Exception {
        Usuario usuarioNuevo = new Usuario();
        if(usuario != null) {
            usuarioNuevo = usuarioRepository.save(usuario);
            return usuarioNuevo;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public void eliminar(Integer id) throws Exception {
        Usuario usuario = obtenerUsuario(id);
        if(usuario != null) {
            usuarioRepository.deleteById(id);
            cursoClienteRest.eliminarUsuarioPorId(id);
        }
        else {
            throw new Exception("USUARIO NO ENCONTRADO");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarPorIds(Iterable<Integer> ids) throws Exception {
        return (List<Usuario>)usuarioRepository.findAllById(ids);
    }

}
