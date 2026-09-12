package br.edu.iff.ccc.DeskGo.controller.apirest;

import br.edu.iff.ccc.DeskGo.dto.UsuarioRequest;
import br.edu.iff.ccc.DeskGo.dto.UsuarioResponseDTO;
import br.edu.iff.ccc.DeskGo.entities.Usuario;
import br.edu.iff.ccc.DeskGo.services.UsuarioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários do sistema DeskGo")
public class UsuarioRestController {

    private final UsuarioUseCase usuarioUseCase;

    public UsuarioRestController(UsuarioUseCase usuarioUseCase) {
        this.usuarioUseCase = usuarioUseCase;
    }

    @Operation(summary = "Criar um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (erro de validação)"),
            @ApiResponse(responseCode = "409", description = "Conflito (e-mail já cadastrado)")
    })
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@Valid @RequestBody UsuarioRequest request) {
        Usuario criado = usuarioUseCase.cadastrarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioResponseDTO(criado));
    }

    @Operation(summary = "Listar todos os usuários")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> lista = usuarioUseCase.listarUsuarios().stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar um usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarUsuario(@PathVariable UUID id) {
        Usuario usuario = usuarioUseCase.buscarUsuario(id);
        return ResponseEntity.ok(new UsuarioResponseDTO(usuario));
    }

    @Operation(summary = "Atualizar um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de e-mail")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable UUID id, @Valid @RequestBody UsuarioRequest request) {
        usuarioUseCase.atualizarUsuario(id, request);
        Usuario atualizado = usuarioUseCase.buscarUsuario(id);
        return ResponseEntity.ok(new UsuarioResponseDTO(atualizado));
    }

    @Operation(summary = "Remover um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        usuarioUseCase.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
