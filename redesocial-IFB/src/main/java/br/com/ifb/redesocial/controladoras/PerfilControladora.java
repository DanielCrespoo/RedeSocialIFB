package br.com.ifb.redesocial.controladoras;

import br.com.ifb.redesocial.controladoras.auth.AuthenticationResponse;
import br.com.ifb.redesocial.dtos.CriarPerfilDTO;
import br.com.ifb.redesocial.servicos.PerfilServico;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:8082"})
@RestController
public class PerfilControladora {

    private final PerfilServico perfilServico;

    public PerfilControladora(PerfilServico perfilServico) {
        this.perfilServico = perfilServico;
    }

    @PostMapping(value = "/perfis")
    public ResponseEntity<String> criar(@RequestBody CriarPerfilDTO criarPerfilDTO) {
        perfilServico.criar(criarPerfilDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Perfil criado com sucesso");
    }

    @GetMapping(value = "/perfis")
    public ResponseEntity<List<Map<String, String>>> buscarTodos() {
        return ResponseEntity.ok().body(perfilServico.buscarTodos());
    }

    @GetMapping(value = "/perfis/{id}")
    public ResponseEntity<Map<String, String>> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(perfilServico.buscarPorId(id));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok().body(perfilServico.login(request));
    }

    @GetMapping(value = "/perfil")
    public ResponseEntity<Map<String, String>> buscarPerfilLogado() {
        return ResponseEntity.ok().body(perfilServico.buscarPerfilLogado());
    }
}
