package br.edu.iff.ccc.DeskGo.dto;

import br.edu.iff.ccc.DeskGo.entities.Perfil;
import br.edu.iff.ccc.DeskGo.entities.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Objeto de resposta com dados do Usuário")
public class UsuarioResponseDTO {

    @Schema(description = "ID do Usuário")
    private UUID id;

    @Schema(description = "Nome do Usuário")
    private String nome;

    @Schema(description = "E-mail do Usuário")
    private String email;

    @Schema(description = "Perfil do Usuário")
    private Perfil perfil;

    public UsuarioResponseDTO() {}

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.perfil = usuario.getPerfil();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }
}
