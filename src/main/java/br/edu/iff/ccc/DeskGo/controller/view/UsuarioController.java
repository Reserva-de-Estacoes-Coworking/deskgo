package br.edu.iff.ccc.DeskGo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.iff.ccc.DeskGo.dto.UsuarioRequest;
import br.edu.iff.ccc.DeskGo.entities.Perfil;
import br.edu.iff.ccc.DeskGo.entities.Usuario;
import br.edu.iff.ccc.DeskGo.services.UsuarioUseCase;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioUseCase usuarioUseCase;

    public UsuarioController(UsuarioUseCase usuarioUseCase) {
        this.usuarioUseCase = usuarioUseCase;
    }

    // Cadastro público (sem login) — cria sempre um Usuario comum
    @GetMapping("/novo")
    public String novoUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioRequest());
        return "cadastrarUsuario";
    }

    @PostMapping
    public String criarUsuario(@Valid @ModelAttribute("usuario") UsuarioRequest usuarioRequest, BindingResult result, Model model) {
        
        if (result.hasErrors()) {
            return "cadastrarUsuario";
        }

        usuarioRequest.setPerfil(Perfil.USUARIO);
        this.usuarioUseCase.cadastrarUsuario(usuarioRequest);

        return "redirect:/login";
    }

    @GetMapping("/perfil")
    public String verPerfil(Model model, HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", logado);
        return "perfilUsuario";
    }

    @PostMapping("/perfil")
    public String atualizarPerfil(@Valid @ModelAttribute("usuario") UsuarioRequest usuarioRequest, BindingResult result, HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            return "perfilUsuario";
        }

        usuarioRequest.setPerfil(logado.getPerfil());
        
        this.usuarioUseCase.atualizarUsuario(logado.getId(), usuarioRequest);

        Usuario atualizado = this.usuarioUseCase.buscarUsuario(logado.getId());
        session.setAttribute("usuarioLogado", atualizado);

        return "redirect:/usuario/perfil";
    }

    @PostMapping("/excluir")
    public String excluirPropriaConta(HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null) {
            return "redirect:/login";
        }

        this.usuarioUseCase.deletarUsuario(logado.getId());
        session.invalidate();

        return "redirect:/login";
    }
}