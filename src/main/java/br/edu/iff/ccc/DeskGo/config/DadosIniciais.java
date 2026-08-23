package br.edu.iff.ccc.DeskGo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.edu.iff.ccc.DeskGo.dto.EstacaoRequest;
import br.edu.iff.ccc.DeskGo.dto.UsuarioRequest;
import br.edu.iff.ccc.DeskGo.entities.Caracteristica;
import br.edu.iff.ccc.DeskGo.entities.Perfil;
import br.edu.iff.ccc.DeskGo.services.EstacaoUseCase;
import br.edu.iff.ccc.DeskGo.services.UsuarioUseCase;

import java.util.List;

@Configuration
public class DadosIniciais {

    @org.springframework.context.annotation.Bean
    public CommandLineRunner criarContaGestorPadrao(UsuarioUseCase usuarioUseCase) {
        return args -> {
            // Conta administrativa padrão do sistema.
            // Não existe auto-cadastro de Gestor (por segurança/RN12),
            // então o sistema já nasce com uma conta de Gestor disponível.
            usuarioUseCase.cadastrarUsuario(
                new UsuarioRequest("Administrador DeskGo", "gestor@deskgo.com", "admin123", Perfil.GESTOR)
            );
            System.out.println("Conta de Gestor padrão criada: gestor@deskgo.com (senha: admin123)");
        };
    }

    // Dados de exemplo para agilizar testes durante o desenvolvimento.
    @org.springframework.context.annotation.Bean
    public CommandLineRunner criarEstacoesDeExemplo(EstacaoUseCase estacaoUseCase) {
        return args -> {
            estacaoUseCase.criarEstacao(new EstacaoRequest(
                "Estação 05",
                "Perto da janela, com tomada e cadeira ergonômica.",
                null,
                List.of(Caracteristica.JANELA, Caracteristica.TOMADA)
            ));
            estacaoUseCase.criarEstacao(new EstacaoRequest(
                "Estação 12",
                "Área silenciosa, ideal para foco profundo.",
                null,
                List.of(Caracteristica.SILENCIOSO, Caracteristica.TOMADA)
            ));
            System.out.println("Estações de exemplo criadas: Estação 05, Estação 12");
        };
    }
}