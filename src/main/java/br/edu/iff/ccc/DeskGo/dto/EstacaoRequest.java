package br.edu.iff.ccc.DeskGo.dto;

import br.edu.iff.ccc.DeskGo.model.enums.Caracteristica;
import br.edu.iff.ccc.DeskGo.model.enums.StatusEstacao;

public class EstacaoRequest {
    private String nome;
    private String descricao;
    private StatusEstacao statusEstacao;
    private Set<Caracteristica> caracteristicas;

    public EstacaoRequest(String nome, String descricao,
                          StatusEstacao statusEstacao,
                          Caracteristica caracteristica) {
        this.nome = nome;
        this.descricao = descricao;
        this.statusEstacao = statusEstacao;
        this.caracteristica = caracteristica;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusEstacao getStatusEstacao() {
        return statusEstacao;
    }

    public void setStatusEstacao(StatusEstacao statusEstacao) {
        this.statusEstacao = statusEstacao;
    }

    public Caracteristica getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(Caracteristica caracteristica) {
        this.caracteristica = caracteristica;
    }
}
