package br.edu.iff.ccc.DeskGo.entities;

import java.util.UUID;
import br.edu.iff.ccc.DeskGo.enums.Caracteristica;
import br.edu.iff.ccc.DeskGo.enums.StatusEstacao;

public class Estacao {
      private UUID id;
    private String nome;
    private String descricao;
    private StatusEstacao statusEstacao;
    private Caracteristica caracteristica;

    public Estacao(UUID id, String nome, String descricao,
                   StatusEstacao statusEstacao,
                   Caracteristica caracteristica) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.statusEstacao = statusEstacao;
        this.caracteristica = caracteristica;
    }

    public Estacao() {
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

    public StatusEstacao getStatusEstacao() {
        return statusEstacao;
    }

    public Caracteristica getCaracteristica() {
        return caracteristica;
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

    public void setStatusEstacao(StatusEstacao statusEstacao) {
        this.statusEstacao = statusEstacao;
    }

    public void setCaracteristica(Caracteristica caracteristica) {
        this.caracteristica = caracteristica;
    }
    
}
