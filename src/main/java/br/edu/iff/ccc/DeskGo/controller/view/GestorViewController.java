package br.edu.iff.ccc.DeskGo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.DeskGo.entities.Perfil;
import br.edu.iff.ccc.DeskGo.entities.Usuario;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/painel/gestor")
public class GestorViewController {

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
        return "painelGestor";
    }
}