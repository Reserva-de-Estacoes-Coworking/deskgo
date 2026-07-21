package br.edu.iff.ccc.DeskGo.dto;

import java.util.List;
import br.edu.iff.ccc.DeskGo.entities.StatusEstacao;
import br.edu.iff.ccc.DeskGo.entities.Caracteristica;

public class EstacaoRequest {
    private String nome;
    private String descricao;
    private StatusEstacao status;
    private List<Caracteristica> caracteristicas;

    public EstacaoRequest(String nome, String descricao, StatusEstacao status, List<Caracteristica> caracteristicas) {
        this.nome = nome;
        this.descricao = descricao;
        this.status = status;
        this.caracteristicas = caracteristicas;
    }

    public EstacaoRequest() {
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

    public StatusEstacao getStatus() {
        return status;
    }

    public void setStatus(StatusEstacao status) {
        this.status = status;
    }

    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<Caracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }
}
