package br.edu.iff.ccc.DeskGo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import br.edu.iff.ccc.DeskGo.entities.Perfil;
import br.edu.iff.ccc.DeskGo.entities.Usuario;
import br.edu.iff.ccc.DeskGo.repository.UsuarioRepositorio;

@DataJpaTest
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepositorio usuarioRepository;

    @Test
    @DisplayName("Deve salvar um usuário com sucesso e gerar o UUID")
    void deveSalvarUsuarioComSucesso() {
        // Arrange (Preparação)
        Usuario usuario = new Usuario("Maria Silva", "maria@email.com", "senha123", Perfil.USUARIO);

        // Act (Ação)
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // Assert (Verificação)
        assertNotNull(usuarioSalvo.getId(), "O ID (UUID) não deveria ser nulo após salvar");
        assertEquals("Maria Silva", usuarioSalvo.getNome());
    }

    @Test
    @DisplayName("Deve buscar um usuário existente pelo ID")
    void deveBuscarUsuarioPorId() {
        // Arrange
        Usuario usuario = new Usuario("João Souza", "joao@email.com", "senha123", Perfil.USUARIO);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // Act
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findById(usuarioSalvo.getId());

        // Assert
        assertTrue(usuarioEncontrado.isPresent(), "O usuário deveria ter sido encontrado");
        assertEquals(usuarioSalvo.getId(), usuarioEncontrado.get().getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar salvar usuários com e-mails duplicados (unique=true)")
    void deveLancarExcecaoQuandoEmailDuplicado() {
        // Arrange
        Usuario usuario1 = new Usuario("Gestor Um", "duplicado@email.com", "senha123", Perfil.GESTOR);
        Usuario usuario2 = new Usuario("Gestor Dois", "duplicado@email.com", "outrasenha", Perfil.USUARIO);

        // Salvamos o primeiro (deve funcionar)
        usuarioRepository.save(usuario1);

        // Act & Assert
        // Tentamos salvar o segundo e o JUnit verifica se o Spring lançou a exceção de
        // integridade
        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioRepository.save(usuario2);
            // O flush força a sincronização com o banco imediatamente, disparando a
            // restrição
            usuarioRepository.flush();
        }, "Deveria lançar erro pois configuramos @Column(unique=true) no email");
    }

}
