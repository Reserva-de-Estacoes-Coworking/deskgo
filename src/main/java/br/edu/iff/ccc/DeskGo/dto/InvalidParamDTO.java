package br.edu.iff.ccc.DeskGo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalhes sobre um parâmetro inválido na requisição")
public class InvalidParamDTO {

    @Schema(description = "Nome do campo/parâmetro que falhou na validação", example = "nome")
    private String name;

    @Schema(description = "Motivo da falha", example = "O nome é obrigatório")
    private String reason;

    public InvalidParamDTO() {}

    public InvalidParamDTO(String name, String reason) {
        this.name = name;
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
