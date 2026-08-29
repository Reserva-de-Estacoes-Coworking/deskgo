package br.edu.iff.ccc.DeskGo.controller.view;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

import br.edu.iff.ccc.DeskGo.dto.ReservaRequest;
import br.edu.iff.ccc.DeskGo.entities.Usuario;
import br.edu.iff.ccc.DeskGo.services.EstacaoUseCase;
import br.edu.iff.ccc.DeskGo.services.ReservaUseCase;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

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
    public String criarReserva(@Valid ReservaRequest reservaRequest, BindingResult result, Model model, HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            model.addAttribute("usuarioLogado", logado);
            model.addAttribute("estacoes", this.estacaoUseCase.listarEstacoes());
            return "reservarEstacao";
        }

        this.reservaUseCase.criarReserva(reservaRequest, logado);

        return "redirect:/painel/minhas-reservas";
    }

    @PostMapping("/cancelar-reserva/{id}")
    public String cancelarReserva(@PathVariable("id") UUID id, RedirectAttributes redirectAttributes, HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null) {
            return "redirect:/login";
        }

        this.reservaUseCase.cancelarReserva(id, logado);

        return "redirect:/painel/minhas-reservas";
    }

    @PostMapping("/editar-reserva/{id}")
    public String editarReserva(
            @PathVariable("id") UUID id,
            @RequestParam("novaData") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate novaData,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null) {
            return "redirect:/login";
        }

        this.reservaUseCase.atualizarDataReserva(id, novaData, logado);

        return "redirect:/painel/minhas-reservas";
    }
}
