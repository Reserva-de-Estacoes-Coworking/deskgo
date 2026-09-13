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

import br.edu.iff.ccc.DeskGo.exceptions.RecursoNaoEncontradoException;
import br.edu.iff.ccc.DeskGo.exceptions.RegraDeNegocioException;

@Service
public class EstacaoUseCase {
    private final EstacaoRepositorio estacaoRepositorio;
    private final ReservaRepositorio reservaRepositorio;
    
    public EstacaoUseCase(EstacaoRepositorio estacaoRepositorio, ReservaRepositorio reservaRepositorio) {
        this.estacaoRepositorio = estacaoRepositorio;
        this.reservaRepositorio = reservaRepositorio;
    }

    public void criarEstacao(EstacaoRequest request) {
        StatusEstacao statusInicial = (request.getStatus() != null) ? request.getStatus() : StatusEstacao.ATIVO;
        Estacao novaEstacao = new Estacao();
        novaEstacao.setNome(request.getNome());
        novaEstacao.setDescricao(request.getDescricao());
        novaEstacao.setStatus(statusInicial);
        novaEstacao.setCaracteristicas(request.getCaracteristicas());
        this.estacaoRepositorio.save(novaEstacao);
    } 

    public List<Estacao> listarEstacoes() {
        return this.estacaoRepositorio.findAll();
    }

    public void atualizarEstacao(UUID id, EstacaoRequest request) {
        Estacao estacao = this.estacaoRepositorio.findById(id).orElse(null);
        if (estacao == null) {
            throw new RecursoNaoEncontradoException("Estação não encontrada.");
        }
        
        estacao.setNome(request.getNome());
        estacao.setDescricao(request.getDescricao());
        if (request.getStatus() != null) {
            estacao.setStatus(request.getStatus());
        }
        if (request.getCaracteristicas() != null) {
            estacao.setCaracteristicas(request.getCaracteristicas());
        }
        
        this.estacaoRepositorio.save(estacao);
    }

    public void deletarEstacao(UUID id) {
        // Verifica se há reservas atreladas
        List<Reserva> reservas = this.reservaRepositorio.findAll();
        for (Reserva r : reservas) {
            if (r.getEstacao().getId().equals(id)) {
                throw new RegraDeNegocioException("Não é possível remover uma estação que possui reservas.");
            }
        }
        this.estacaoRepositorio.deleteById(id);
    }

    public Estacao buscarEstacao(UUID id) {

        return this.estacaoRepositorio.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Estação não encontrada."));
    }

    public void validarEstacao() {
        // Lógica para validar uma estação (Stub)
    }
}
