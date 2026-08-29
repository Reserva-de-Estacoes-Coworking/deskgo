package br.edu.iff.ccc.DeskGo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.DeskGo.entities.Perfil;
import br.edu.iff.ccc.DeskGo.entities.Usuario;
import jakarta.servlet.http.HttpSession;

import br.edu.iff.ccc.DeskGo.services.UsuarioUseCase;

@Controller
@RequestMapping("/painel/gestor")
public class GestorViewController {

    private final UsuarioUseCase usuarioUseCase;
    private final br.edu.iff.ccc.DeskGo.services.EstacaoUseCase estacaoUseCase;

    public GestorViewController(UsuarioUseCase usuarioUseCase, br.edu.iff.ccc.DeskGo.services.EstacaoUseCase estacaoUseCase) {
        this.usuarioUseCase = usuarioUseCase;
        this.estacaoUseCase = estacaoUseCase;
    }

    @GetMapping
    public String getPainelGestor(Model model, HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");

        if (logado == null) {
            return "redirect:/login";
        }

        if (logado.getPerfil() != Perfil.GESTOR) {
            return "redirect:/painel";
        }

        model.addAttribute("usuarioLogado", logado);
        model.addAttribute("estacoes", this.estacaoUseCase.listarEstacoes());
        return "painelGestor";
    }

    @GetMapping("/usuarios")
    public String getPainelGestorUsuarios(Model model, HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");

        if (logado == null) {
            return "redirect:/login";
        }

        if (logado.getPerfil() != Perfil.GESTOR) {
            return "redirect:/painel";
        }

        model.addAttribute("usuarioLogado", logado);
        model.addAttribute("usuarios", this.usuarioUseCase.listarUsuarios());
        return "painelGestorUsuarios";
    }
}