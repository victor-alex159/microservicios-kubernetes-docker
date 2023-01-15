/*package com.victor.microserviciousuarios.security;

import com.victor.microserviciousuarios.models.entities.Usuario;
import com.victor.microserviciousuarios.models.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomServerDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if(usuario == null) {
            throw new UsernameNotFoundException(String.format("Usuario no existe", email));
        }
        return UserPrincipal.create(usuario);
    }
}
*/