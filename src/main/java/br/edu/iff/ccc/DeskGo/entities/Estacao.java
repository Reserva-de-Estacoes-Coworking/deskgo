package br.edu.iff.ccc.DeskGo.entities;

import java.util.List;
import java.util.UUID;

/**
 * Estação de Trabalho
 */
public class Estacao {
    private UUID id;
    private String nome;
    private String descricao;
    private StatusEstacao status;
    private List<Caracteristica> caracteristicas;

    public Estacao() {
    }

    public Estacao(UUID id, String nome, String descricao, StatusEstacao status, List<Caracteristica> caracteristicas) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.status = status;
        this.caracteristicas = caracteristicas;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public StatusEstacao getStatus() {
        return status;
    }

    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setStatus(StatusEstacao status) {
        this.status = status;
    }

    public void setCaracteristicas(List<Caracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }
}
