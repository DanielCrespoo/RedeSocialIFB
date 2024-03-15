package br.com.ifb.redesocial.repositorios;

import br.com.ifb.redesocial.entidades.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilRepositorio extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByEmail(String email);

}
