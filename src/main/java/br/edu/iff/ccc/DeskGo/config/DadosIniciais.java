package br.edu.iff.ccc.DeskGo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.edu.iff.ccc.DeskGo.dto.UsuarioRequest;
import br.edu.iff.ccc.DeskGo.entities.Perfil;
import br.edu.iff.ccc.DeskGo.services.UsuarioUseCase;

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
}