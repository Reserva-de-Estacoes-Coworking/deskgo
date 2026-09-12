package br.edu.iff.ccc.DeskGo;

import br.edu.iff.ccc.DeskGo.controller.apirest.ReservaRestController;
import br.edu.iff.ccc.DeskGo.dto.ReservaApiRequest;
import br.edu.iff.ccc.DeskGo.entities.Estacao;
import br.edu.iff.ccc.DeskGo.entities.Reserva;
import br.edu.iff.ccc.DeskGo.entities.Usuario;
import br.edu.iff.ccc.DeskGo.exceptions.EntidadeDuplicadaException;
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

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservaRestController.class)
public class ReservaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservaUseCase reservaUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private Reserva reservaMock;
    private UUID estacaoId;
    private UUID usuarioId;

    @BeforeEach
    void setUp() {
        estacaoId = UUID.randomUUID();
        usuarioId = UUID.randomUUID();
        
        Usuario u = new Usuario();
        u.setId(usuarioId);
        u.setNome("Teste");

        Estacao e = new Estacao();
        e.setId(estacaoId);
        e.setNome("Mesa 01");

        reservaMock = new Reserva();
        reservaMock.setId(UUID.randomUUID());
        reservaMock.setData(LocalDate.now().plusDays(1));
        reservaMock.setUsuario(u);
        reservaMock.setEstacao(e);
    }

    @Test
    void criarReserva_ComDadosValidos_Retorna201() throws Exception {
        ReservaApiRequest request = new ReservaApiRequest(estacaoId, LocalDate.now().plusDays(1), usuarioId);
        
        Mockito.when(reservaUseCase.criarReserva(eq(estacaoId), any(LocalDate.class), eq(usuarioId))).thenReturn(reservaMock);

        mockMvc.perform(post("/api/v1/reservas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeUsuario").value("Teste"))
                .andExpect(jsonPath("$.nomeEstacao").value("Mesa 01"));
    }

    @Test
    void criarReserva_ComDataNoPassado_Retorna400EProblemDetail() throws Exception {
        ReservaApiRequest request = new ReservaApiRequest(estacaoId, LocalDate.now().minusDays(1), usuarioId);

        mockMvc.perform(post("/api/v1/reservas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.invalid_params").exists());
    }

    @Test
    void criarReserva_ComConflito_Retorna409EProblemDetail() throws Exception {
        ReservaApiRequest request = new ReservaApiRequest(estacaoId, LocalDate.now().plusDays(1), usuarioId);

        Mockito.when(reservaUseCase.criarReserva(eq(estacaoId), any(LocalDate.class), eq(usuarioId)))
               .thenThrow(new EntidadeDuplicadaException("Esta estação já está reservada para a data selecionada."));

        mockMvc.perform(post("/api/v1/reservas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Conflito de Entidade"));
    }
}
