package br.edu.iff.ccc.DeskGo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import br.edu.iff.ccc.DeskGo.entities.Estacao;
import br.edu.iff.ccc.DeskGo.entities.Perfil;
import br.edu.iff.ccc.DeskGo.entities.Reserva;
import br.edu.iff.ccc.DeskGo.entities.StatusEstacao;
import br.edu.iff.ccc.DeskGo.entities.Usuario;
import br.edu.iff.ccc.DeskGo.repository.EstacaoRepositorio;
import br.edu.iff.ccc.DeskGo.repository.ReservaRepositorio;
import br.edu.iff.ccc.DeskGo.repository.UsuarioRepositorio;

@DataJpaTest
public class ReservaRepositorioTest {

    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Autowired
    private EstacaoRepositorio estacaoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    private Usuario usuario;
    private Estacao estacao;

    @BeforeEach
    public void setup() {
        usuario = new Usuario();
        usuario.setNome("Joao Silva");
        usuario.setEmail("joao.silva@example.com");
        usuario.setSenha("123456");
        usuario.setPerfil(Perfil.USUARIO);
        usuario = usuarioRepositorio.save(usuario);

        estacao = new Estacao();
        estacao.setNome("Mesa 01");
        estacao.setStatus(StatusEstacao.ATIVO);
        estacao = estacaoRepositorio.save(estacao);
    }

    @Test
    public void testSalvarReservaComSucesso() {
        Reserva reserva = new Reserva();
        reserva.setData(LocalDate.now());
        reserva.setUsuario(usuario);
        reserva.setEstacao(estacao);

        Reserva salva = reservaRepositorio.save(reserva);

        assertNotNull(salva.getId());
        assertEquals(LocalDate.now(), salva.getData());
        assertEquals(usuario.getId(), salva.getUsuario().getId());
    }

    @Test
    public void testListarPorUsuario() {
        Reserva reserva = new Reserva();
        reserva.setData(LocalDate.now());
        reserva.setUsuario(usuario);
        reserva.setEstacao(estacao);
        reservaRepositorio.save(reserva);

        List<Reserva> reservas = reservaRepositorio.findByUsuarioId(usuario.getId());

        assertFalse(reservas.isEmpty());
        assertEquals(1, reservas.size());
    }

    @Test
    public void testExisteReservaParaEstacaoNaData() {
        Reserva reserva = new Reserva();
        reserva.setData(LocalDate.now());
        reserva.setUsuario(usuario);
        reserva.setEstacao(estacao);
        reservaRepositorio.save(reserva);

        boolean existe = reservaRepositorio.existsByEstacaoIdAndData(estacao.getId(), LocalDate.now());
        boolean naoExiste = reservaRepositorio.existsByEstacaoIdAndData(estacao.getId(), LocalDate.now().plusDays(1));

        assertTrue(existe);
        assertFalse(naoExiste);
    }
}
