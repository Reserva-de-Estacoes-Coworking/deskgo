package br.edu.iff.ccc.DeskGo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.DeskGo.entities.Reserva;

@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, UUID> {

    List<Reserva> findByUsuarioId(UUID usuarioId);

    boolean existsByEstacaoIdAndData(UUID estacaoId, LocalDate data);

}
