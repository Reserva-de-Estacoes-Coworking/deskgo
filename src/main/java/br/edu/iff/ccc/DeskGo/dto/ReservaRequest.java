package br.edu.iff.ccc.DeskGo.dto;

import java.time.LocalDate;
import java.util.UUID;

public class ReservaRequest {
    private UUID estacaoId;
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
