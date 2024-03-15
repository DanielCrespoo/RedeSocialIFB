package br.com.ifb.redesocial.servicos;

import br.com.ifb.redesocial.entidades.Perfil;
import br.com.ifb.redesocial.repositorios.PerfilRepositorio;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PerfilRepositorio perfilRepositorio;

    public UserDetailsServiceImpl(PerfilRepositorio perfilRepositorio) {
        this.perfilRepositorio = perfilRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Perfil perfil = perfilRepositorio.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + username));

        return User.withUsername(perfil.getEmail()).password(perfil.getSenha()).build();

    }
}
