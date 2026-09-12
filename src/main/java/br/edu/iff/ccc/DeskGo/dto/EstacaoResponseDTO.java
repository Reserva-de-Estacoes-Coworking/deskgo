package br.edu.iff.ccc.DeskGo.dto;

import br.edu.iff.ccc.DeskGo.entities.Caracteristica;
import br.edu.iff.ccc.DeskGo.entities.Estacao;
import br.edu.iff.ccc.DeskGo.entities.StatusEstacao;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(description = "Objeto de resposta com dados da Estação")
public class EstacaoResponseDTO {

    @Schema(description = "ID da Estação")
    private UUID id;

    @Schema(description = "Nome da Estação")
    private String nome;

    @Schema(description = "Descrição da Estação")
    private String descricao;

    @Schema(description = "Status atual da Estação")
    private StatusEstacao status;

    @Schema(description = "Características da Estação")
    private List<Caracteristica> caracteristicas;

    public EstacaoResponseDTO() {}

    public EstacaoResponseDTO(Estacao estacao) {
        this.id = estacao.getId();
        this.nome = estacao.getNome();
        this.descricao = estacao.getDescricao();
        this.status = estacao.getStatus();
        this.caracteristicas = estacao.getCaracteristicas();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public StatusEstacao getStatus() { return status; }
    public void setStatus(StatusEstacao status) { this.status = status; }

    public List<Caracteristica> getCaracteristicas() { return caracteristicas; }
    public void setCaracteristicas(List<Caracteristica> caracteristicas) { this.caracteristicas = caracteristicas; }
}
