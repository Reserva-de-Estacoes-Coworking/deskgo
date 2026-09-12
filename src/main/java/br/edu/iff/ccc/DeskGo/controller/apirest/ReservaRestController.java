package br.edu.iff.ccc.DeskGo.controller.apirest;

import br.edu.iff.ccc.DeskGo.dto.AtualizarDataReservaRequest;
import br.edu.iff.ccc.DeskGo.dto.ReservaApiRequest;
import br.edu.iff.ccc.DeskGo.dto.ReservaResponseDTO;
import br.edu.iff.ccc.DeskGo.entities.Reserva;
import br.edu.iff.ccc.DeskGo.services.ReservaUseCase;
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
@RequestMapping("/api/v1/reservas")
@Tag(name = "Reservas", description = "Endpoints para agendamento, consulta e cancelamento de reservas")
public class ReservaRestController {

    private final ReservaUseCase reservaUseCase;

    public ReservaRestController(ReservaUseCase reservaUseCase) {
        this.reservaUseCase = reservaUseCase;
    }

    @Operation(summary = "Criar uma nova reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação, data no passado, ou estação indisponível"),
            @ApiResponse(responseCode = "404", description = "Estação ou usuário não encontrados"),
            @ApiResponse(responseCode = "409", description = "Estação já reservada na data")
    })
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> criarReserva(@Valid @RequestBody ReservaApiRequest request) {
        Reserva criada = reservaUseCase.criarReserva(request.getEstacaoId(), request.getData(), request.getUsuarioId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ReservaResponseDTO(criada));
    }

    @Operation(summary = "Listar todas as reservas (opcionalmente filtrando por usuário)")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReservas(@RequestParam(required = false) UUID usuarioId) {
        List<Reserva> reservas;
        if (usuarioId != null) {
            reservas = reservaUseCase.listarPorUsuario(usuarioId);
        } else {
            reservas = reservaUseCase.listarTodas();
        }

        List<ReservaResponseDTO> lista = reservas.stream()
                .map(ReservaResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar reserva por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarReserva(@PathVariable UUID id) {
        Reserva reserva = reservaUseCase.buscarReserva(id);
        return ResponseEntity.ok(new ReservaResponseDTO(reserva));
    }

    @Operation(summary = "Alterar a data de uma reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data da reserva alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação ou data no passado"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada"),
            @ApiResponse(responseCode = "409", description = "Estação já reservada na nova data")
    })
    @PutMapping("/{id}/data")
    public ResponseEntity<ReservaResponseDTO> atualizarDataReserva(
            @PathVariable UUID id,
            @Valid @RequestBody AtualizarDataReservaRequest request) {
        reservaUseCase.atualizarDataReserva(id, request.getNovaData());
        Reserva atualizada = reservaUseCase.buscarReserva(id);
        return ResponseEntity.ok(new ReservaResponseDTO(atualizada));
    }

    @Operation(summary = "Cancelar (excluir) uma reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserva cancelada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarReserva(@PathVariable UUID id) {
        reservaUseCase.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }
}
