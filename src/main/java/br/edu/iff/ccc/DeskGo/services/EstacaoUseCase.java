package br.edu.iff.ccc.DeskGo.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.DeskGo.dto.EstacaoRequest;
import br.edu.iff.ccc.DeskGo.entities.Estacao;
import br.edu.iff.ccc.DeskGo.entities.Reserva;
import br.edu.iff.ccc.DeskGo.repository.EstacaoRepositorio;
import br.edu.iff.ccc.DeskGo.repository.ReservaRepositorio;
import br.edu.iff.ccc.DeskGo.entities.StatusEstacao;

@Service
public class EstacaoUseCase {
    private final EstacaoRepositorio estacaoRepositorio;
    private final ReservaRepositorio reservaRepositorio;
    
    public EstacaoUseCase(EstacaoRepositorio estacaoRepositorio, ReservaRepositorio reservaRepositorio) {
        this.estacaoRepositorio = estacaoRepositorio;
        this.reservaRepositorio = reservaRepositorio;
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

    public void atualizarEstacao(UUID id, EstacaoRequest request) {
        Estacao estacao = this.estacaoRepositorio.buscarPorId(id);
        if (estacao == null) {
            throw new IllegalArgumentException("Estação não encontrada.");
        }
        
        estacao.setNome(request.getNome());
        estacao.setDescricao(request.getDescricao());
        if (request.getStatus() != null) {
            estacao.setStatus(request.getStatus());
        }
        if (request.getCaracteristicas() != null) {
            estacao.setCaracteristicas(request.getCaracteristicas());
        }
        
        this.estacaoRepositorio.atualizar(estacao);
    }

    public void deletarEstacao(UUID id) {
        // Verifica se há reservas atreladas
        List<Reserva> reservas = this.reservaRepositorio.listarTodos();
        for (Reserva r : reservas) {
            if (r.getEstacao().getId().equals(id)) {
                throw new IllegalArgumentException("Não é possível remover uma estação que possui reservas.");
            }
        }
        this.estacaoRepositorio.deletar(id);
    }

    public Estacao buscarEstacao(UUID id) {
        return this.estacaoRepositorio.buscarPorId(id);
    }

    public void validarEstacao() {
        // Lógica para validar uma estação (Stub)
    }
}
