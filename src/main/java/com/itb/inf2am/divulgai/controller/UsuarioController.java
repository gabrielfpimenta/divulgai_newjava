package com.itb.inf2am.divulgai.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itb.inf2am.divulgai.dto.UsuarioDTO;
import com.itb.inf2am.divulgai.model.entity.RecuperarSenha;
import com.itb.inf2am.divulgai.model.entity.Usuario;
import com.itb.inf2am.divulgai.model.services.EmailService;
import com.itb.inf2am.divulgai.model.services.RecuperarSenhaService;
import com.itb.inf2am.divulgai.model.services.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RecuperarSenhaService recuperarSenhaService;
    private final EmailService emailService;

    public UsuarioController(
            UsuarioService usuarioService,
            RecuperarSenhaService recuperarSenhaService,
            EmailService emailService) {

        this.usuarioService = usuarioService;
        this.recuperarSenhaService = recuperarSenhaService;
        this.emailService = emailService;
    }

    @PostMapping("/create")
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {

        Usuario createdUsuario = usuarioService.create(usuario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdUsuario);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Usuario> editar(
            @PathVariable Long id,
            @RequestPart(required = false) MultipartFile file,
            @RequestPart Usuario usuario) {

        Usuario usuarioAtualizado =
                usuarioService.editar(file, id, usuario);

        return ResponseEntity.ok(usuarioAtualizado);
    }

    @PutMapping("/{id}/foto")
    public ResponseEntity<UsuarioDTO> atualizarFoto(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        return ResponseEntity.ok(
                usuarioService.atualizarFoto(id, body.get("foto")));
    }

    @PutMapping("/{id}/alterar-senha")
    public ResponseEntity<Usuario> alterarSenha(
            @PathVariable Long id,
            @RequestParam String newPassword) {

        Usuario usuario =
                usuarioService.alterarSenha(id, newPassword);

        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}/inativar")
    public ResponseEntity<Usuario> inativar(
            @PathVariable Long id) {

        Usuario usuario = usuarioService.inativar(id);

        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<Usuario> ativar(
            @PathVariable Long id) {

        Usuario usuario = usuarioService.ativar(id);

        return ResponseEntity.ok(usuario);
    }

    @PutMapping(
        value = "/{id}/dados",
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public ResponseEntity<Usuario> editarDados(
        @PathVariable Long id,
        @RequestBody Usuario usuario) {

    Usuario usuarioAtualizado =
            usuarioService.editarDados(id, usuario);

    return ResponseEntity.ok(usuarioAtualizado);
}

    @PostMapping("/recuperar-senha/enviar-codigo")
    public ResponseEntity<String> enviarCodigo(
            @RequestParam String email) {

        Usuario usuario =
                usuarioService.findEntityByUsername(email);

        RecuperarSenha recuperarSenha =
                recuperarSenhaService.gerarCodigo(usuario);

        emailService.enviarCodigo(
                usuario.getUsername(),
                recuperarSenha.getCodigo());

        return ResponseEntity.ok("Código enviado com sucesso.");
    }

    @PostMapping("/recuperar-senha/validar-codigo")
    public ResponseEntity<String> validarCodigo(
            @RequestParam String email,
            @RequestParam String codigo) {

        Usuario usuario =
                usuarioService.findEntityByUsername(email);

        boolean valido =
                recuperarSenhaService.validarCodigo(
                        usuario,
                        codigo);

        if (!valido) {
            return ResponseEntity.badRequest()
                    .body("Código inválido ou expirado.");
        }

        return ResponseEntity.ok("Código válido.");
    }

    @PostMapping("/recuperar-senha/alterar-senha")
    public ResponseEntity<String> alterarSenhaRecuperacao(
            @RequestParam String email,
            @RequestParam String codigo,
            @RequestParam String novaSenha) {

        Usuario usuario =
                usuarioService.findEntityByUsername(email);

        boolean valido =
                recuperarSenhaService.validarCodigo(
                        usuario,
                        codigo);

        if (!valido) {
            return ResponseEntity.badRequest()
                    .body("Código inválido ou expirado.");
        }

        usuarioService.alterarSenha(
                usuario.getId(),
                novaSenha);

        recuperarSenhaService.invalidarCodigo(usuario);

        return ResponseEntity.ok("Senha alterada com sucesso.");
    }

    @GetMapping("/me")
    public UsuarioDTO me(Authentication authentication) {

        return usuarioService.findByUsername(authentication);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UsuarioDTO>> findAll() {

        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                usuarioService.findById(id));
    }

}