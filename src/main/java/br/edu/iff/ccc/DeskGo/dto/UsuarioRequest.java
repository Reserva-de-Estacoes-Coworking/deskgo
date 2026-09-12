package br.edu.iff.ccc.DeskGo.dto;

import br.edu.iff.ccc.DeskGo.entities.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Objeto de requisição para cadastro e edição de Usuário")
public class UsuarioRequest {

    @Schema(description = "Nome do Usuário", example = "João da Silva")
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Schema(description = "E-mail do Usuário", example = "joao.silva@email.com")
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O formato do e-mail é inválido")
    private String email;

    @Schema(description = "Senha do Usuário (em branco não altera)", example = "12345")
    @jakarta.validation.constraints.Pattern(regexp = "^$|.{5,}", message = "A senha deve ter no mínimo 5 caracteres")
    private String senha;

    @Schema(description = "Perfil de acesso do Usuário", example = "COMUM")
    private Perfil perfil;

    public UsuarioRequest(String nome, String email, String senha, Perfil perfil) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
    }

    public UsuarioRequest() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}