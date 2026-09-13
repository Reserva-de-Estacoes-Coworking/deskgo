package br.edu.iff.ccc.DeskGo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Objeto de requisição para atualizar a data de uma Reserva")
public class AtualizarDataReservaRequest {

    @Schema(description = "Nova Data da Reserva", example = "2024-12-31")
    @NotNull(message = "A nova data é obrigatória")
    @FutureOrPresent(message = "A nova data deve ser no presente ou futuro")
    private LocalDate novaData;

    public AtualizarDataReservaRequest() {}

    public AtualizarDataReservaRequest(LocalDate novaData) {
        this.novaData = novaData;
    }

    public LocalDate getNovaData() {
        return novaData;
    }

    public void setNovaData(LocalDate novaData) {
        this.novaData = novaData;
    }
}
