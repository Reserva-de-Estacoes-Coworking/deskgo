package br.edu.iff.ccc.DeskGo.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.DeskGo.dto.UsuarioRequest;
import br.edu.iff.ccc.DeskGo.entities.Perfil;
import br.edu.iff.ccc.DeskGo.entities.Usuario;
import br.edu.iff.ccc.DeskGo.repository.UsuarioRepositorio;

@Service
public class UsuarioUseCase {
    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioUseCase(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void cadastrarUsuario(UsuarioRequest request) {
        if (this.usuarioRepositorio.buscarPorEmail(request.getEmail()) != null) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com este e-mail.");
        }

        UUID id = UUID.randomUUID();
        Perfil perfilInicial = (request.getPerfil() != null) ? request.getPerfil() : Perfil.USUARIO;
        Usuario novoUsuario = new Usuario(id, request.getNome(), request.getEmail(), request.getSenha(), perfilInicial);
        this.usuarioRepositorio.salvar(novoUsuario);
    }

    public List<Usuario> listarUsuarios() {
        return this.usuarioRepositorio.listarTodos();
    }

    public Usuario buscarUsuario(UUID id) {
        return this.usuarioRepositorio.buscarPorId(id);
    }

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = this.usuarioRepositorio.buscarPorEmail(email);

        if (usuario == null) {
            return null;
        }

        if (!usuario.getSenha().equals(senha)) {
            return null;
        }

        return usuario;
    }

    public void atualizarUsuario(UUID id, UsuarioRequest request) {
        Usuario usuario = this.usuarioRepositorio.buscarPorId(id);
 
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
 
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(request.getSenha());
        if (request.getPerfil() != null) {
            usuario.setPerfil(request.getPerfil());
        }
    }

    public void deletarUsuario(UUID id) {
        this.usuarioRepositorio.deletar(id);
    }
}