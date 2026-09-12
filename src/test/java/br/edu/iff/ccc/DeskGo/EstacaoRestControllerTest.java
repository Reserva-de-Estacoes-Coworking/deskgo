package br.edu.iff.ccc.DeskGo;

import br.edu.iff.ccc.DeskGo.controller.apirest.EstacaoRestController;
import br.edu.iff.ccc.DeskGo.dto.EstacaoRequest;
import br.edu.iff.ccc.DeskGo.entities.Estacao;
import br.edu.iff.ccc.DeskGo.entities.StatusEstacao;
import br.edu.iff.ccc.DeskGo.services.EstacaoUseCase;
import br.edu.iff.ccc.DeskGo.services.ReservaUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EstacaoRestController.class)
public class EstacaoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstacaoUseCase estacaoUseCase;

    @MockBean
    private ReservaUseCase reservaUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private Estacao estacaoMock;

    @BeforeEach
    void setUp() {
        estacaoMock = new Estacao("Mesa 01", "Mesa na janela", StatusEstacao.ATIVO, null);
        estacaoMock.setId(UUID.randomUUID());
    }

    @Test
    void criarEstacao_ComDadosValidos_Retorna201() throws Exception {
        EstacaoRequest request = new EstacaoRequest("Mesa 01", "Mesa na janela", StatusEstacao.ATIVO, null);
        
        Mockito.when(estacaoUseCase.criarEstacao(any(EstacaoRequest.class))).thenReturn(estacaoMock);

        mockMvc.perform(post("/api/v1/estacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Mesa 01"));
    }

    @Test
    void listarEstacoes_Retorna200() throws Exception {
        Mockito.when(estacaoUseCase.listarEstacoes()).thenReturn(Collections.singletonList(estacaoMock));

        mockMvc.perform(get("/api/v1/estacoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Mesa 01"));
    }

    @Test
    void criarEstacao_ComNomeEmBranco_Retorna400EProblemDetail() throws Exception {
        EstacaoRequest request = new EstacaoRequest("", "Mesa na janela", StatusEstacao.ATIVO, null);

        mockMvc.perform(post("/api/v1/estacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalid_params").exists());
    }
}
