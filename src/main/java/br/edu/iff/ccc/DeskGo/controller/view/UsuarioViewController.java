package br.edu.iff.ccc.DeskGo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.DeskGo.entities.Usuario;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/painel")
public class UsuarioViewController {

    @GetMapping
    public String getPainel(Model model, HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuarioLogado", logado);
        return "painel";
    }

    @GetMapping("/reservar-estacao")
    public String getReservarEstacao(Model model, HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuarioLogado", logado);
        return "reservarEstacao";
    }

    @GetMapping("/minhas-reservas")
    public String getMinhasReservas(Model model, HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuarioLogado", logado);
        return "minhasReservas";
    }
}