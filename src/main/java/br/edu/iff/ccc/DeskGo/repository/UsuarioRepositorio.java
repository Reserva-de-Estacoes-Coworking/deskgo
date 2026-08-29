package br.edu.iff.ccc.DeskGo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.DeskGo.entities.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, UUID> {

    Usuario findByEmail(String email);

}