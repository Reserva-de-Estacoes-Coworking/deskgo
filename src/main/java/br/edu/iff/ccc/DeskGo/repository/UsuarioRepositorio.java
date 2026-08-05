package br.edu.iff.ccc.DeskGo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.DeskGo.entities.Usuario;

@Repository
public class UsuarioRepositorio {
    private List<Usuario> usuarios;

    public UsuarioRepositorio() {
        this.usuarios = new ArrayList<>();
    }

    public void salvar(Usuario usuario) {
        this.usuarios.add(usuario);
        System.out.println("Usuário salvo com sucesso: " + usuario.getNome());
    }

    public List<Usuario> listarTodos() {
        return this.usuarios;
    }

    public Usuario buscarPorId(UUID id) {
        for (Usuario usuario : this.usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario buscarPorEmail(String email) {
        for (Usuario usuario : this.usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                return usuario;
            }
        }
        return null;
    }

    public void deletar(UUID id) {
        this.usuarios.removeIf(usuario -> usuario.getId().equals(id));
    }
}