package br.com.ifb.redesocial.servicos;

import br.com.ifb.redesocial.configuracoes.JwtServico;
import br.com.ifb.redesocial.controladoras.auth.AuthenticationResponse;
import br.com.ifb.redesocial.dtos.CriarPerfilDTO;
import br.com.ifb.redesocial.entidades.Perfil;
import br.com.ifb.redesocial.repositorios.PerfilRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PerfilServico {

    private final PerfilRepositorio perfilRepositorio;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtServico jwtServico;

    private final AuthenticationManager authenticationManager;

    public PerfilServico(PerfilRepositorio perfilRepositorio,
                         BCryptPasswordEncoder passwordEncoder,
                         JwtServico jwtServico,
                         AuthenticationManager authenticationManager) {

        this.perfilRepositorio = perfilRepositorio;
        this.passwordEncoder = passwordEncoder;
        this.jwtServico = jwtServico;
        this.authenticationManager = authenticationManager;

    }

    public void criar(CriarPerfilDTO criarPerfilDTO) {

        Perfil perfil = new Perfil();

        perfil.setNome(criarPerfilDTO.getNome());
        perfil.setTelefone(criarPerfilDTO.getTelefone());
        perfil.setEmpresa(criarPerfilDTO.getEmpresa());
        perfil.setEmail(criarPerfilDTO.getEmail());
        perfil.setSenha(passwordEncoder.encode(criarPerfilDTO.getSenha()));

        perfilRepositorio.save(perfil);

    }

    public List<Map<String, String>> buscarTodos() {

        List<Perfil> perfis = perfilRepositorio.findAll();

        return perfis.stream().map(perfil ->
                        Map.of("id", String.valueOf(perfil.getId()), "nome", perfil.getNome(), "email", perfil.getEmail()))
                .toList();

    }

    public Map<String, String> buscarPorId(Long id) {

        Perfil perfil = perfilRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Perfil não encontrado com o id: " + id));


        Map<String, String> perfilEncontrado = new HashMap<>();
        perfilEncontrado.put("id", String.valueOf(perfil.getId()));
        perfilEncontrado.put("nome", perfil.getNome());
        perfilEncontrado.put("email", perfil.getEmail());

        return perfilEncontrado;


    }

    public AuthenticationResponse login(Map<String, String> request) {

        String username = request.get("email");
        String password = request.get("senha");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username,
                password
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var user = perfilRepositorio.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Perfil não encontrado com o email: " + username));

        var jwtToken = jwtServico.generateToken(user);

        return new AuthenticationResponse.AuthenticationResponseBuilder().setToken(jwtToken).build();
    }

    public Map<String, String> buscarPerfilLogado() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Perfil perfil = perfilRepositorio.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("Perfil não encontrado com o email: " + username));


        Map<String, String> perfilEncontrado = new HashMap<>();
        perfilEncontrado.put("id", String.valueOf(perfil.getId()));
        perfilEncontrado.put("nome", perfil.getNome());
        perfilEncontrado.put("email", perfil.getEmail());

        return perfilEncontrado;


    }

}
