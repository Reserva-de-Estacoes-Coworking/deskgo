package br.edu.iff.ccc.DeskGo.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.DeskGo.dto.EstacaoRequest;
import br.edu.iff.ccc.DeskGo.entities.Estacao;
import br.edu.iff.ccc.DeskGo.repository.EstacaoRepositorio;
import br.edu.iff.ccc.DeskGo.entities.StatusEstacao;

@Service
public class EstacaoUseCase {
    private final EstacaoRepositorio estacaoRepositorio;
    
    public EstacaoUseCase(EstacaoRepositorio estacaoRepositorio) {
        this.estacaoRepositorio = estacaoRepositorio;
    }

    public void criarEstacao(EstacaoRequest request) {
        UUID id = UUID.randomUUID();
        StatusEstacao statusInicial = (request.getStatus() != null) ? request.getStatus() : StatusEstacao.ATIVO;
        Estacao novaEstacao = new Estacao(id, request.getNome(), request.getDescricao(), statusInicial, request.getCaracteristicas());
        this.estacaoRepositorio.salvar(novaEstacao);
    } 

    public List<Estacao> listarEstacoes() {
        return this.estacaoRepositorio.listarTodos();
    }

    public void atualizarEstacao() {
        // Lógica para atualizar uma estação (Stub)
    }

    public void deletarEstacao() {
        // Lógica para deletar uma estação (Stub)
    }

    public void buscarEstacao() {
        // Lógica para buscar uma estação (Stub)
    }

    public void validarEstacao() {
        // Lógica para validar uma estação (Stub)
    }
}
