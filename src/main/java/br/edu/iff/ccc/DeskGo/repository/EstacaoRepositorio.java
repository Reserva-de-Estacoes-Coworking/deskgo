package br.edu.iff.ccc.DeskGo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.DeskGo.entities.Estacao;

@Repository
public interface EstacaoRepositorio extends JpaRepository<Estacao, UUID> {

}
