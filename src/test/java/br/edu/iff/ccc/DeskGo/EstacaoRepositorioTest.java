package br.edu.iff.ccc.DeskGo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import br.edu.iff.ccc.DeskGo.entities.Caracteristica;
import br.edu.iff.ccc.DeskGo.entities.Estacao;
import br.edu.iff.ccc.DeskGo.entities.StatusEstacao;
import br.edu.iff.ccc.DeskGo.repository.EstacaoRepositorio;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
public class EstacaoRepositorioTest {

    @Autowired
    private EstacaoRepositorio estacaoRepositorio;

    @Test
    public void testSalvarEstacaoComSucesso() {
        Estacao estacao = new Estacao();
        estacao.setNome("Mesa 01");
        estacao.setDescricao("Mesa com vista para a janela");
        estacao.setStatus(StatusEstacao.ATIVO);
        estacao.setCaracteristicas(List.of(Caracteristica.JANELA, Caracteristica.TOMADA));

        Estacao salva = estacaoRepositorio.save(estacao);

        assertNotNull(salva.getId());
        assertEquals("Mesa 01", salva.getNome());
        assertEquals(2, salva.getCaracteristicas().size());
    }

    @Test
    public void testBuscarEstacaoPorId() {
        Estacao estacao = new Estacao();
        estacao.setNome("Mesa 02");
        estacao.setStatus(StatusEstacao.ATIVO);
        Estacao salva = estacaoRepositorio.save(estacao);

        Optional<Estacao> encontrada = estacaoRepositorio.findById(salva.getId());

        assertTrue(encontrada.isPresent());
        assertEquals("Mesa 02", encontrada.get().getNome());
    }

    @Test
    public void testNomeDuplicado() {
        Estacao estacao1 = new Estacao();
        estacao1.setNome("Mesa Unica");
        estacao1.setStatus(StatusEstacao.ATIVO);
        estacaoRepositorio.save(estacao1);

        Estacao estacao2 = new Estacao();
        estacao2.setNome("Mesa Unica"); // Mesmo nome
        estacao2.setStatus(StatusEstacao.ATIVO);

        assertThrows(Exception.class, () -> {
            estacaoRepositorio.saveAndFlush(estacao2); // Flush para forçar a query e dar erro
        });
    }
}
