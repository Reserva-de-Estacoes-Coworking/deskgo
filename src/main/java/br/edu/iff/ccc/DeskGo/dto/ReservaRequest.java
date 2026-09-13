package br.edu.iff.ccc.DeskGo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Objeto de requisição para criar uma Reserva")
public class ReservaRequest {

    @Schema(description = "ID da Estação", example = "c5b2d63d-a233-4123-8478-316827052591")
    @NotNull(message = "A estação é obrigatória")
    private UUID estacaoId;

    @Schema(description = "Data da Reserva", example = "2026-12-31")
    @NotNull(message = "A data da reserva é obrigatória")
    @FutureOrPresent(message = "A data da reserva deve ser no presente ou no futuro")
    private LocalDate data;

    @Schema(description = "ID do Usuário", example = "f8a1c42b-7b12-4321-9123-456789abcdef")
    @NotNull(message = "O ID do usuário é obrigatório")
    private UUID usuarioId;
 
    public ReservaRequest() {}
 
    public ReservaRequest(UUID estacaoId, LocalDate data, UUID usuarioId) {
        this.estacaoId = estacaoId;
        this.data = data;
        this.usuarioId = usuarioId;
    }
 
    public UUID getEstacaoId() {
        return estacaoId;
    }
 
    public void setEstacaoId(UUID estacaoId) {
        this.estacaoId = estacaoId;
    }
 
    public LocalDate getData() {
        return data;
    }
 
    public void setData(LocalDate data) {
        this.data = data;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }
}