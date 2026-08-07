package br.edu.iff.ccc.DeskGo.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
 
import br.edu.iff.ccc.DeskGo.dto.ReservaRequest;
import br.edu.iff.ccc.DeskGo.entities.Estacao;
import br.edu.iff.ccc.DeskGo.entities.Reserva;
import br.edu.iff.ccc.DeskGo.entities.Usuario;
import br.edu.iff.ccc.DeskGo.repository.EstacaoRepositorio;
import br.edu.iff.ccc.DeskGo.repository.ReservaRepositorio;

@Service
public class ReservaUseCase {
    private final ReservaRepositorio reservaRepositorio;
    private final EstacaoRepositorio estacaoRepositorio;
 
    public ReservaUseCase(ReservaRepositorio reservaRepositorio, EstacaoRepositorio estacaoRepositorio) {
        this.reservaRepositorio = reservaRepositorio;
        this.estacaoRepositorio = estacaoRepositorio;
    }
 
    public void criarReserva(ReservaRequest request, Usuario usuarioLogado) {
        // 1) A estacao precisa existir de verdade
        Estacao estacao = this.estacaoRepositorio.buscarPorId(request.getEstacaoId());
        if (estacao == null) {
            throw new IllegalArgumentException("Estação não encontrada.");
        }
 
        // 2) Não pode reservar para uma data que já passou
        if (request.getData() == null || request.getData().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data da reserva não pode ser no passado.");
        }
 
        // 3) RN07 - Regra de negócio principal: não pode haver duas reservas
        // para a mesma estação na mesma data
        boolean conflito = this.reservaRepositorio.existeReservaParaEstacaoNaData(
                request.getEstacaoId(), request.getData());
        if (conflito) {
            throw new IllegalArgumentException("Esta estação já está reservada para a data selecionada.");
        }
 
        UUID id = UUID.randomUUID();
        Reserva novaReserva = new Reserva(id, request.getData(), usuarioLogado, estacao);
        this.reservaRepositorio.salvar(novaReserva);
    }
 
    public List<Reserva> listarPorUsuario(UUID usuarioId) {
        return this.reservaRepositorio.listarPorUsuario(usuarioId);
    }
 
    public void cancelarReserva(UUID reservaId, Usuario usuarioLogado) {
        Reserva reserva = this.reservaRepositorio.buscarPorId(reservaId);
 
        if (reserva == null) {
            throw new IllegalArgumentException("Reserva não encontrada.");
        }
 
        // Só o dono da reserva pode cancelá-la
        if (!reserva.getUsuario().getId().equals(usuarioLogado.getId())) {
            throw new IllegalArgumentException("Você não tem permissão para cancelar esta reserva.");
        }
 
        this.reservaRepositorio.deletar(reservaId);
    }
    
}
