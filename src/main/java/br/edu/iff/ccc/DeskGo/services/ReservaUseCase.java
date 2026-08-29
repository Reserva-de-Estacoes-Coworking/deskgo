package br.edu.iff.ccc.DeskGo.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.DeskGo.dto.EstacaoDisponibilidadeDTO;
import br.edu.iff.ccc.DeskGo.dto.ReservaRequest;
import br.edu.iff.ccc.DeskGo.entities.Estacao;
import br.edu.iff.ccc.DeskGo.entities.Reserva;
import br.edu.iff.ccc.DeskGo.entities.StatusEstacao;
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
        Estacao estacao = this.estacaoRepositorio.findById(request.getEstacaoId()).orElse(null);
        if (estacao == null) {
            throw new IllegalArgumentException("Estação não encontrada.");
        }

        // Verifica status da estação
        if (estacao.getStatus() != StatusEstacao.ATIVO) {
            throw new IllegalArgumentException("Esta estação não está disponível para reservas (inativa ou em manutenção).");
        }

        // 2) Não pode reservar para uma data que já passou
        if (request.getData() == null || request.getData().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data da reserva não pode ser no passado.");
        }

        // 3) RN07 - Regra de negócio principal: não pode haver duas reservas
        // para a mesma estação na mesma data
        boolean conflito = this.reservaRepositorio.existsByEstacaoIdAndData(
                request.getEstacaoId(), request.getData());
        if (conflito) {
            throw new IllegalArgumentException("Esta estação já está reservada para a data selecionada.");
        }

        // 4) Um usuário não pode reservar mais de uma estação por dia
        List<Reserva> reservasDoUsuario = this.reservaRepositorio.findByUsuarioId(usuarioLogado.getId());
        for (Reserva r : reservasDoUsuario) {
            if (r.getData().equals(request.getData())) {
                throw new IllegalArgumentException("Você já possui uma reserva para esta data.");
            }
        }

        Reserva novaReserva = new Reserva();
        novaReserva.setData(request.getData());
        novaReserva.setUsuario(usuarioLogado);
        novaReserva.setEstacao(estacao);
        this.reservaRepositorio.save(novaReserva);
    }

    public List<EstacaoDisponibilidadeDTO> listarEstacoesDisponiveisNaData(LocalDate data) {
        List<Estacao> todasEstacoes = this.estacaoRepositorio.findAll();
        List<EstacaoDisponibilidadeDTO> estacoesComDisponibilidade = new java.util.ArrayList<>();

        for (Estacao estacao : todasEstacoes) {
            if (estacao.getStatus() != StatusEstacao.ATIVO) {
                estacoesComDisponibilidade.add(new EstacaoDisponibilidadeDTO(estacao, false));
                continue;
            }
            boolean ocupada = this.reservaRepositorio.existsByEstacaoIdAndData(estacao.getId(), data);
            estacoesComDisponibilidade.add(new EstacaoDisponibilidadeDTO(estacao, !ocupada));
        }

        return estacoesComDisponibilidade;
    }

    public List<Reserva> listarPorUsuario(UUID usuarioId) {
        return this.reservaRepositorio.findByUsuarioId(usuarioId);
    }

    public void cancelarReserva(UUID reservaId, Usuario usuarioLogado) {
        Reserva reserva = this.reservaRepositorio.findById(reservaId).orElse(null);

        if (reserva == null) {
            throw new IllegalArgumentException("Reserva não encontrada.");
        }

        // Só o dono da reserva pode cancelá-la
        if (!reserva.getUsuario().getId().equals(usuarioLogado.getId())) {
            throw new IllegalArgumentException("Você não tem permissão para cancelar esta reserva.");
        }

        this.reservaRepositorio.deleteById(reservaId);
    }

    public void atualizarDataReserva(UUID reservaId, LocalDate novaData, Usuario usuarioLogado) {
        Reserva reserva = this.reservaRepositorio.findById(reservaId).orElse(null);

        if (reserva == null) {
            throw new IllegalArgumentException("Reserva não encontrada.");
        }

        if (!reserva.getUsuario().getId().equals(usuarioLogado.getId())) {
            throw new IllegalArgumentException("Você não tem permissão para alterar esta reserva.");
        }

        if (novaData == null || novaData.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A nova data não pode ser no passado.");
        }

        if (novaData.equals(reserva.getData())) {
            return;
        }

        boolean conflito = this.reservaRepositorio.existsByEstacaoIdAndData(reserva.getEstacao().getId(), novaData);
        if (conflito) {
            throw new IllegalArgumentException("Esta estação já está reservada para a nova data selecionada.");
        }

        List<Reserva> reservasDoUsuario = this.reservaRepositorio.findByUsuarioId(usuarioLogado.getId());
        for (Reserva r : reservasDoUsuario) {
            if (!r.getId().equals(reservaId) && r.getData().equals(novaData)) {
                throw new IllegalArgumentException("Você já possui uma reserva para esta data.");
            }
        }

        reserva.setData(novaData);
        this.reservaRepositorio.save(reserva); // Persist updated entity
    }

}
