package br.edu.iff.ccc.DeskGo.controller.view;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.DeskGo.dto.ReservaRequest;
import br.edu.iff.ccc.DeskGo.entities.Usuario;
import br.edu.iff.ccc.DeskGo.services.EstacaoUseCase;
import br.edu.iff.ccc.DeskGo.services.ReservaUseCase;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/painel")
public class ReservaController {
    private final ReservaUseCase reservaUseCase;
    private final EstacaoUseCase estacaoUseCase;

    public ReservaController(ReservaUseCase reservaUseCase, EstacaoUseCase estacaoUseCase) {
        this.reservaUseCase = reservaUseCase;
        this.estacaoUseCase = estacaoUseCase;
    }

    @PostMapping("/reservar-estacao")
    public String criarReserva(ReservaRequest reservaRequest, Model model, HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null) {
            return "redirect:/login";
        }

        try {
            this.reservaUseCase.criarReserva(reservaRequest, logado);
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("usuarioLogado", logado);
            model.addAttribute("estacoes", this.estacaoUseCase.listarEstacoes());
            return "reservarEstacao";
        }

        return "redirect:/painel/minhas-reservas";
    }

    @PostMapping("/cancelar-reserva/{id}")
    public String cancelarReserva(@PathVariable("id") UUID id, Model model, HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null) {
            return "redirect:/login";
        }

        try {
            this.reservaUseCase.cancelarReserva(id, logado);
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
        }

        return "redirect:/painel/minhas-reservas";
    }
    
}
