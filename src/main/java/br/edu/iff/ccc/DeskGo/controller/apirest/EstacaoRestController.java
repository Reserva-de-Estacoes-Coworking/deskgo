package br.edu.iff.ccc.DeskGo.controller.apirest;

import br.edu.iff.ccc.DeskGo.dto.EstacaoDisponibilidadeDTO;
import br.edu.iff.ccc.DeskGo.dto.EstacaoRequest;
import br.edu.iff.ccc.DeskGo.dto.EstacaoResponseDTO;
import br.edu.iff.ccc.DeskGo.entities.Estacao;
import br.edu.iff.ccc.DeskGo.services.EstacaoUseCase;
import br.edu.iff.ccc.DeskGo.services.ReservaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/estacoes")
@Tag(name = "Estações", description = "Endpoints para gerenciamento de estações de trabalho")
public class EstacaoRestController {

    private final EstacaoUseCase estacaoUseCase;
    private final ReservaUseCase reservaUseCase;

    public EstacaoRestController(EstacaoUseCase estacaoUseCase, ReservaUseCase reservaUseCase) {
        this.estacaoUseCase = estacaoUseCase;
        this.reservaUseCase = reservaUseCase;
    }

    @Operation(summary = "Cadastrar uma nova estação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação ou requisição malformada"),
            @ApiResponse(responseCode = "409", description = "Nome da estação já existe")
    })
    @PostMapping
    public ResponseEntity<EstacaoResponseDTO> criarEstacao(@Valid @RequestBody EstacaoRequest request) {
        Estacao criada = estacaoUseCase.criarEstacao(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EstacaoResponseDTO(criada));
    }

    @Operation(summary = "Listar todas as estações")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<EstacaoResponseDTO>> listarEstacoes() {
        List<EstacaoResponseDTO> lista = estacaoUseCase.listarEstacoes().stream()
                .map(EstacaoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar estação por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estação encontrada"),
            @ApiResponse(responseCode = "404", description = "Estação não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstacaoResponseDTO> buscarEstacao(@PathVariable UUID id) {
        Estacao estacao = estacaoUseCase.buscarEstacao(id);
        return ResponseEntity.ok(new EstacaoResponseDTO(estacao));
    }

    @Operation(summary = "Atualizar uma estação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estação atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Estação não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstacaoResponseDTO> atualizarEstacao(@PathVariable UUID id, @Valid @RequestBody EstacaoRequest request) {
        estacaoUseCase.atualizarEstacao(id, request);
        Estacao atualizada = estacaoUseCase.buscarEstacao(id);
        return ResponseEntity.ok(new EstacaoResponseDTO(atualizada));
    }

    @Operation(summary = "Remover uma estação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Estação removida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de negócio (estação tem reservas)"),
            @ApiResponse(responseCode = "404", description = "Estação não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEstacao(@PathVariable UUID id) {
        estacaoUseCase.deletarEstacao(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Consultar disponibilidade de estações para uma data")
    @ApiResponse(responseCode = "200", description = "Disponibilidade retornada com sucesso")
    @GetMapping("/disponibilidade")
    public ResponseEntity<List<EstacaoDisponibilidadeDTO>> consultarDisponibilidade(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<EstacaoDisponibilidadeDTO> disponibilidade = reservaUseCase.listarEstacoesDisponiveisNaData(data);
        return ResponseEntity.ok(disponibilidade);
    }
}
