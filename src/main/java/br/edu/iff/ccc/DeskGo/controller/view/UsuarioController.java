package br.edu.iff.ccc.DeskGo.controller.view;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.DeskGo.dto.UsuarioRequest;
import br.edu.iff.ccc.DeskGo.services.UsuarioUseCase;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioUseCase usuarioUseCase;

    public UsuarioController(UsuarioUseCase usuarioUseCase) {
        this.usuarioUseCase = usuarioUseCase;
    }

    @GetMapping("/novo")
    public String novoUsuario(Model model) {
        UsuarioRequest novoUsuario = new UsuarioRequest();

        model.addAttribute("usuario", novoUsuario);

        return "cadastrarUsuario";
    }

    @PostMapping
    public String criarUsuario(UsuarioRequest usuarioRequest) {
        this.usuarioUseCase.cadastrarUsuario(usuarioRequest);

        return "redirect:/usuario";
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", this.usuarioUseCase.listarUsuarios());

        return "listarUsuarios";
    }

    @GetMapping("/{id}/editar")
    public String editarUsuario(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("usuario", this.usuarioUseCase.buscarUsuario(id));
        model.addAttribute("usuarioId", id);

        return "editarUsuario";
    }

    @PostMapping("/{id}")
    public String atualizarUsuario(@PathVariable("id") UUID id, UsuarioRequest usuarioRequest) {
        this.usuarioUseCase.atualizarUsuario(id, usuarioRequest);

        return "redirect:/usuario";
    }

    @PostMapping("/{id}/deletar")
    public String deletarUsuario(@PathVariable("id") UUID id) {
        this.usuarioUseCase.deletarUsuario(id);

        return "redirect:/usuario";
    }
}