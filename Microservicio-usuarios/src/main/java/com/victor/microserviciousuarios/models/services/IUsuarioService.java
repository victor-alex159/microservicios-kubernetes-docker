package com.victor.microserviciousuarios.models.services;

import com.victor.microserviciousuarios.models.entities.Usuario;

import java.util.List;

public interface IUsuarioService {

    public List<Usuario> listar() throws Exception;
    public Usuario obtenerUsuario(Integer id) throws Exception;
    public Usuario guardar(Usuario usuario) throws Exception;
    public void eliminar(Integer id) throws Exception;
    List<Usuario> listarPorIds(Iterable<Integer> ids);

}
