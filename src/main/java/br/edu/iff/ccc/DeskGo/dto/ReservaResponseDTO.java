package br.edu.iff.ccc.DeskGo.dto;

import br.edu.iff.ccc.DeskGo.entities.Reserva;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Objeto de resposta com dados da Reserva")
public class ReservaResponseDTO {

    @Schema(description = "ID da Reserva")
    private UUID id;

    @Schema(description = "Data da Reserva")
    private LocalDate data;

    @Schema(description = "ID do Usuário")
    private UUID usuarioId;

    @Schema(description = "Nome do Usuário")
    private String nomeUsuario;

    @Schema(description = "ID da Estação")
    private UUID estacaoId;

    @Schema(description = "Nome da Estação")
    private String nomeEstacao;

    public ReservaResponseDTO() {}

    public ReservaResponseDTO(Reserva reserva) {
        this.id = reserva.getId();
        this.data = reserva.getData();
        if (reserva.getUsuario() != null) {
            this.usuarioId = reserva.getUsuario().getId();
            this.nomeUsuario = reserva.getUsuario().getNome();
        }
        if (reserva.getEstacao() != null) {
            this.estacaoId = reserva.getEstacao().getId();
            this.nomeEstacao = reserva.getEstacao().getNome();
        }
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }

    public UUID getEstacaoId() { return estacaoId; }
    public void setEstacaoId(UUID estacaoId) { this.estacaoId = estacaoId; }

    public String getNomeEstacao() { return nomeEstacao; }
    public void setNomeEstacao(String nomeEstacao) { this.nomeEstacao = nomeEstacao; }
}
