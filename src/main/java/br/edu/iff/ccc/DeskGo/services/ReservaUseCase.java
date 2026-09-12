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

import br.edu.iff.ccc.DeskGo.exceptions.EntidadeDuplicadaException;
import br.edu.iff.ccc.DeskGo.exceptions.RecursoNaoEncontradoException;
import br.edu.iff.ccc.DeskGo.exceptions.RegraDeNegocioException;

@Service
public class ReservaUseCase {
    private final ReservaRepositorio reservaRepositorio;
    private final EstacaoRepositorio estacaoRepositorio;
    private final br.edu.iff.ccc.DeskGo.repository.UsuarioRepositorio usuarioRepositorio;

    public ReservaUseCase(ReservaRepositorio reservaRepositorio, EstacaoRepositorio estacaoRepositorio, br.edu.iff.ccc.DeskGo.repository.UsuarioRepositorio usuarioRepositorio) {
        this.reservaRepositorio = reservaRepositorio;
        this.estacaoRepositorio = estacaoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void criarReserva(ReservaRequest request, Usuario usuarioLogado) {
        // 1) A estacao precisa existir de verdade
        Estacao estacao = this.estacaoRepositorio.findById(request.getEstacaoId()).orElse(null);
        if (estacao == null) {
            throw new RecursoNaoEncontradoException("Estação não encontrada.");
        }

        // Verifica status da estação
        if (estacao.getStatus() != StatusEstacao.ATIVO) {
            throw new RegraDeNegocioException("Esta estação não está disponível para reservas (inativa ou em manutenção).");
        }

        // 2) Não pode reservar para uma data que já passou
        if (request.getData() == null || request.getData().isBefore(LocalDate.now())) {
            throw new RegraDeNegocioException("A data da reserva não pode ser no passado.");
        }

        // 3) RN07 - Regra de negócio principal: não pode haver duas reservas
        // para a mesma estação na mesma data
        boolean conflito = this.reservaRepositorio.existsByEstacaoIdAndData(
                request.getEstacaoId(), request.getData());
        if (conflito) {
            throw new EntidadeDuplicadaException("Esta estação já está reservada para a data selecionada.");
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
            throw new RecursoNaoEncontradoException("Reserva não encontrada.");
        }

        // Só o dono da reserva pode cancelá-la
        if (!reserva.getUsuario().getId().equals(usuarioLogado.getId())) {
            throw new RegraDeNegocioException("Você não tem permissão para cancelar esta reserva.");
        }

        this.reservaRepositorio.deleteById(reservaId);
    }

    public void atualizarDataReserva(UUID reservaId, LocalDate novaData, Usuario usuarioLogado) {
        Reserva reserva = this.reservaRepositorio.findById(reservaId).orElse(null);

        if (reserva == null) {
            throw new RecursoNaoEncontradoException("Reserva não encontrada.");
        }

        if (!reserva.getUsuario().getId().equals(usuarioLogado.getId())) {
            throw new RegraDeNegocioException("Você não tem permissão para alterar esta reserva.");
        }

        if (novaData == null || novaData.isBefore(LocalDate.now())) {
            throw new RegraDeNegocioException("A nova data não pode ser no passado.");
        }

        if (novaData.equals(reserva.getData())) {
            return;
        }

        boolean conflito = this.reservaRepositorio.existsByEstacaoIdAndData(reserva.getEstacao().getId(), novaData);
        if (conflito) {
            throw new EntidadeDuplicadaException("Esta estação já está reservada para a nova data selecionada.");
        }

        reserva.setData(novaData);
        this.reservaRepositorio.save(reserva); // Persist updated entity
    }
    public Reserva criarReserva(UUID estacaoId, LocalDate data, UUID usuarioId) {
        Estacao estacao = this.estacaoRepositorio.findById(estacaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Estação não encontrada."));

        if (estacao.getStatus() != StatusEstacao.ATIVO) {
            throw new RegraDeNegocioException("Esta estação não está disponível para reservas (inativa ou em manutenção).");
        }

        if (data == null || data.isBefore(LocalDate.now())) {
            throw new RegraDeNegocioException("A data da reserva não pode ser no passado.");
        }

        boolean conflito = this.reservaRepositorio.existsByEstacaoIdAndData(estacaoId, data);
        if (conflito) {
            throw new EntidadeDuplicadaException("Esta estação já está reservada para a data selecionada.");
        }

        Usuario usuario = this.usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

        Reserva novaReserva = new Reserva();
        novaReserva.setData(data);
        novaReserva.setUsuario(usuario);
        novaReserva.setEstacao(estacao);
        return this.reservaRepositorio.save(novaReserva);
    }

    public Reserva buscarReserva(UUID reservaId) {
        return this.reservaRepositorio.findById(reservaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Reserva não encontrada."));
    }

    public List<Reserva> listarTodas() {
        return this.reservaRepositorio.findAll();
    }

    public void cancelarReserva(UUID reservaId) {
        Reserva reserva = buscarReserva(reservaId);
        this.reservaRepositorio.deleteById(reservaId);
    }

    public void atualizarDataReserva(UUID reservaId, LocalDate novaData) {
        Reserva reserva = buscarReserva(reservaId);

        if (novaData == null || novaData.isBefore(LocalDate.now())) {
            throw new RegraDeNegocioException("A nova data não pode ser no passado.");
        }

        if (novaData.equals(reserva.getData())) {
            return;
        }

        boolean conflito = this.reservaRepositorio.existsByEstacaoIdAndData(reserva.getEstacao().getId(), novaData);
        if (conflito) {
            throw new EntidadeDuplicadaException("Esta estação já está reservada para a nova data selecionada.");
        }

        reserva.setData(novaData);
        this.reservaRepositorio.save(reserva);
    }
}
