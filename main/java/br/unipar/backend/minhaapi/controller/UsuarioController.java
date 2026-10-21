
package br.unipar.backend.minhaapi.controller;

import br.unipar.backend.minhaapi.dto.UsuarioDividaDTO;
import br.unipar.backend.minhaapi.model.Divida;
import br.unipar.backend.minhaapi.model.Usuario;
import br.unipar.backend.minhaapi.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/hello/{nome}")
    public String helloWorld(@PathVariable String nome) {
        System.out.println("Alguém me chamou!");
        return "Hello World, " + nome;
    }

    @GetMapping("/hello-world")
    public String helloWorld2(
            @RequestParam(required = false) String nome) {
        if (nome == null) {
            return "Hello World";
        }

        return "Hello World, " + nome;
    }

    @GetMapping("/listar-usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(
                usuarioService.listarUsuarios());
    }

    @PostMapping("/criar-usuario")
    public ResponseEntity<Usuario> criarUsuario(
            @RequestBody(required = false) Usuario usuario) {

        if (usuario == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(
                usuarioService.criarUsuario(usuario));
    }

    @PutMapping("/editar-usuario/{id}")
    public ResponseEntity<Usuario> editarUsuario(
            @PathVariable Integer id,
            @RequestBody Usuario usuario) {

        return usuarioService.editarUsuario(id, usuario)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deletar-usuario/{id}")
    public ResponseEntity<String> deletarUsuario(
            @PathVariable Integer id) {

        if (!usuarioService.deletarUsuario(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                "Usuário " + id + " deletado com sucesso!");
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String login,
            @RequestParam String senha) {

        if (login.equals("admin") && senha.equals("admin")) {
            return "Bem vindo ADMIN";
        }

        return "Não foi possível logar, usuário ou senha incorreta.";
    }

    @PostMapping("/{id}/dividas")
    public ResponseEntity<Divida> criarDivida(
            @PathVariable Integer id,
            @RequestBody Divida divida) {

        return usuarioService.criarDivida(id, divida)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/resumo-dividas")
    public ResponseEntity<UsuarioDividaDTO> consultarTotalDivida(
            @PathVariable Integer id) {

        return usuarioService.consultarTotalDivida(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
