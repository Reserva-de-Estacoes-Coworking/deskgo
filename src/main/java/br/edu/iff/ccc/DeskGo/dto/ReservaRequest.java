package br.edu.iff.ccc.DeskGo.dto;

import java.time.LocalDate;
import java.util.UUID;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public class ReservaRequest {
    @NotNull(message = "A estação é obrigatória")
    private UUID estacaoId;

    @NotNull(message = "A data da reserva é obrigatória")
    @FutureOrPresent(message = "A data da reserva deve ser no presente ou no futuro")
    private LocalDate data;
 
    public ReservaRequest() {
    }
 
    public ReservaRequest(UUID estacaoId, LocalDate data) {
        this.estacaoId = estacaoId;
        this.data = data;
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
    
}
