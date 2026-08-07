package br.edu.iff.ccc.DeskGo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.DeskGo.entities.Usuario;
import br.edu.iff.ccc.DeskGo.services.EstacaoUseCase;
import br.edu.iff.ccc.DeskGo.services.ReservaUseCase;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/painel")
public class UsuarioViewController {

    private final EstacaoUseCase estacaoUseCase;
    private final ReservaUseCase reservaUseCase;

    public UsuarioViewController(EstacaoUseCase estacaoUseCase, ReservaUseCase reservaUseCase) {
        this.estacaoUseCase = estacaoUseCase;
        this.reservaUseCase = reservaUseCase;
    }

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
        model.addAttribute("estacoes", this.estacaoUseCase.listarEstacoes());
        return "reservarEstacao";
    }

    @GetMapping("/minhas-reservas")
    public String getMinhasReservas(Model model, HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuarioLogado", logado);
        model.addAttribute("reservas", this.reservaUseCase.listarPorUsuario(logado.getId()));
        return "minhasReservas";
    }
}