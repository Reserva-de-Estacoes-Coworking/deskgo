package br.edu.iff.ccc.DeskGo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Objeto de requisição para criar uma Reserva na API")
public class ReservaApiRequest {

    @Schema(description = "ID da Estação")
    @NotNull(message = "O ID da estação é obrigatório")
    private UUID estacaoId;

    @Schema(description = "Data da Reserva", example = "2024-12-31")
    @NotNull(message = "A data da reserva é obrigatória")
    @FutureOrPresent(message = "A data da reserva deve ser no presente ou futuro")
    private LocalDate data;

    @Schema(description = "ID do Usuário")
    @NotNull(message = "O ID do usuário é obrigatório")
    private UUID usuarioId;

    public ReservaApiRequest() {}

    public ReservaApiRequest(UUID estacaoId, LocalDate data, UUID usuarioId) {
        this.estacaoId = estacaoId;
        this.data = data;
        this.usuarioId = usuarioId;
    }

    public UUID getEstacaoId() { return estacaoId; }
    public void setEstacaoId(UUID estacaoId) { this.estacaoId = estacaoId; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
}
