package br.edu.iff.ccc.DeskGo.dto;

import br.edu.iff.ccc.DeskGo.entities.Estacao;

public class EstacaoDisponibilidadeDTO {
    private Estacao estacao;
    private boolean disponivel;

    public EstacaoDisponibilidadeDTO(Estacao estacao, boolean disponivel) {
        this.estacao = estacao;
        this.disponivel = disponivel;
    }

    public Estacao getEstacao() {
        return estacao;
    }

    public void setEstacao(Estacao estacao) {
        this.estacao = estacao;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}
