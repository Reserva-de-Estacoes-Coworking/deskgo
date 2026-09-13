package br.edu.iff.ccc.DeskGo.dto;

import br.edu.iff.ccc.DeskGo.entities.Estacao;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto de retorno com a disponibilidade da Estação")
public class EstacaoDisponibilidadeDTO {
    
    @Schema(description = "Dados da Estação")
    private Estacao estacao;
    
    @Schema(description = "Status de disponibilidade da Estação para a data solicitada")
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
