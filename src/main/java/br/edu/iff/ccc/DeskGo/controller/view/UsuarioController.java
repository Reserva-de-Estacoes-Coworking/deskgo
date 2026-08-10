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
    public String criarUsuario(UsuarioRequest usuarioRequest, Model model) {
        usuarioRequest.setPerfil(Perfil.USUARIO);

        try {
            this.usuarioUseCase.cadastrarUsuario(usuarioRequest);
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("usuario", usuarioRequest);
            return "cadastrarUsuario";
        }

        return "redirect:/login";
    }

    // A partir daqui: self-service. O "id" nunca vem da URL,
    // sempre do usuário que está na sessão (evita editar/excluir conta alheia).

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
    public String atualizarPerfil(UsuarioRequest usuarioRequest, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null) {
            return "redirect:/login";
        }

        // Não deixa o próprio usuário se promover a Gestor pelo formulário
        usuarioRequest.setPerfil(logado.getPerfil());

        try {
            this.usuarioUseCase.atualizarUsuario(logado.getId(), usuarioRequest);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/usuario/perfil";
        }

        // Atualiza a sessão com os dados novos
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