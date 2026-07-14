package br.edu.iff.ccc.DeskGo.services;

import java.util.UUID;
import org.springframework.stereotype.Service;

import br.edu.iff.ccc.DeskGo.dto.EstacaoRequest;
import br.edu.iff.ccc.DeskGo.entities.Estacao;
import br.edu.iff.ccc.DeskGo.repository.EstacaoRepositorio;

public class EstacaoUserCase {
     private final EstacaoRepositorio estacaoRepositorio;

    public EstacaoUseCase(EstacaoRepositorio estacaoRepositorio) {
        this.estacaoRepositorio = estacaoRepositorio;
    }

    public void criarEstacao(EstacaoRequest estacao) {

        // Gerar um ID único para a estação
        UUID id = UUID.randomUUID();

        Estacao novaEstacao = new Estacao(
                id,
                estacao.getNome(),
                estacao.getDescricao(),
                estacao.getStatusEstacao(),
                estacao.getCaracteristica()
        );

        this.estacaoRepositorio.salvar(novaEstacao);
    }

    public void atualizarEstacao() {
        // Lógica para atualizar uma estação
    }

    public void deletarEstacao() {
        // Lógica para deletar uma estação
    }

    public void buscarEstacao() {
        // Lógica para buscar uma estação
    }

    public void listarEstacoes() {
        // Lógica para listar todas as estações
    }

    public void validarEstacao() {
        // Lógica para validar uma estação
    }
    
}
