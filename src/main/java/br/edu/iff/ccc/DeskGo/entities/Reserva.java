package br.edu.iff.ccc.DeskGo.entities;

import java.time.LocalDate;
import java.util.UUID;

public class Reserva {
    private UUID id;
    private LocalDate data;
    private Usuario usuario;
    private Estacao estacao;
 
    public Reserva() {
    }
 
    public Reserva(UUID id, LocalDate data, Usuario usuario, Estacao estacao) {
        this.id = id;
        this.data = data;
        this.usuario = usuario;
        this.estacao = estacao;
    }
 
    public UUID getId() {
        return id;
    }
 
    public void setId(UUID id) {
        this.id = id;
    }
 
    public LocalDate getData() {
        return data;
    }
 
    public void setData(LocalDate data) {
        this.data = data;
    }
 
    public Usuario getUsuario() {
        return usuario;
    }
 
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
 
    public Estacao getEstacao() {
        return estacao;
    }
 
    public void setEstacao(Estacao estacao) {
        this.estacao = estacao;
    }
    
}
