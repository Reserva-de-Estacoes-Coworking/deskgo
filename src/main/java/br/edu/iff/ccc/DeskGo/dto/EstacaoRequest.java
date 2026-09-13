package br.edu.iff.ccc.DeskGo.dto;

import java.util.List;
import br.edu.iff.ccc.DeskGo.entities.StatusEstacao;
import br.edu.iff.ccc.DeskGo.entities.Caracteristica;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto de requisição para cadastro de Estação")
public class EstacaoRequest {

    @Schema(description = "Nome da Estação", example = "Mesa 01")
    @NotBlank(message = "O nome da estação é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Schema(description = "Descrição da Estação", example = "Mesa perto da janela")
    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 255, message = "A descrição não pode exceder 255 caracteres")
    private String descricao;

    @Schema(description = "Status da Estação", example = "ATIVO")
    @NotNull(message = "O status da estação é obrigatório")
    private StatusEstacao status;

    @Schema(description = "Características da Estação")
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
