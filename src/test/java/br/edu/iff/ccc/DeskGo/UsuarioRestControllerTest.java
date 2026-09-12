package br.edu.iff.ccc.DeskGo;

import br.edu.iff.ccc.DeskGo.controller.apirest.UsuarioRestController;
import br.edu.iff.ccc.DeskGo.dto.UsuarioRequest;
import br.edu.iff.ccc.DeskGo.entities.Perfil;
import br.edu.iff.ccc.DeskGo.entities.Usuario;
import br.edu.iff.ccc.DeskGo.exceptions.RecursoNaoEncontradoException;
import br.edu.iff.ccc.DeskGo.services.UsuarioUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioRestController.class)
public class UsuarioRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioUseCase usuarioUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        usuarioMock = new Usuario("João Silva", "joao@email.com", "12345", Perfil.USUARIO);
        usuarioMock.setId(UUID.randomUUID());
    }

    @Test
    void criarUsuario_ComDadosValidos_Retorna201EDTO() throws Exception {
        UsuarioRequest request = new UsuarioRequest("João Silva", "joao@email.com", "12345", Perfil.USUARIO);
        
        Mockito.when(usuarioUseCase.cadastrarUsuario(any(UsuarioRequest.class))).thenReturn(usuarioMock);

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(usuarioMock.getId().toString()))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"))
                .andExpect(jsonPath("$.senha").doesNotExist());
    }

    @Test
    void criarUsuario_ComEmailInvalido_Retorna400EProblemDetail() throws Exception {
        UsuarioRequest request = new UsuarioRequest("João Silva", "email-invalido", "12345", Perfil.USUARIO);

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Violação de Validação"))
                .andExpect(jsonPath("$.invalid_params").isArray())
                .andExpect(jsonPath("$.invalid_params[0].name").value("email"));
    }

    @Test
    void buscarUsuario_NaoExistente_Retorna404EProblemDetail() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(usuarioUseCase.buscarUsuario(id)).thenThrow(new RecursoNaoEncontradoException("Usuário não encontrado."));

        mockMvc.perform(get("/api/v1/usuarios/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Recurso Não Encontrado"))
                .andExpect(jsonPath("$.detail").value("Usuário não encontrado."));
    }
}
