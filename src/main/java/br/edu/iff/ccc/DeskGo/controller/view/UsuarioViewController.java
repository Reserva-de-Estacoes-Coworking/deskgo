package br.edu.iff.ccc.DeskGo.controller.view;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.iff.ccc.DeskGo.entities.Reserva;
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
        
        List<Reserva> reservas = this.reservaUseCase.listarPorUsuario(logado.getId());
        
        LocalDate hoje = LocalDate.now();
        Reserva proximaReserva = reservas.stream()
            .filter(r -> !r.getData().isBefore(hoje))
            .min(Comparator.comparing(Reserva::getData))
            .orElse(null);
            
        model.addAttribute("proximaReserva", proximaReserva);
        model.addAttribute("totalReservas", reservas.size());
        
        return "painel";
    }

    @GetMapping("/reservar-estacao")
    public String getReservarEstacao(
            @RequestParam(value = "data", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            Model model,
            HttpSession session) {

        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuarioLogado", logado);
        model.addAttribute("dataSelecionada", data);

        if (data != null) {
            model.addAttribute("estacoes", this.reservaUseCase.listarEstacoesDisponiveisNaData(data));
        } else {
            java.util.List<br.edu.iff.ccc.DeskGo.dto.EstacaoDisponibilidadeDTO> dtos = new java.util.ArrayList<>();
            for (br.edu.iff.ccc.DeskGo.entities.Estacao e : this.estacaoUseCase.listarEstacoes()) {
                dtos.add(new br.edu.iff.ccc.DeskGo.dto.EstacaoDisponibilidadeDTO(e, true));
            }
            model.addAttribute("estacoes", dtos);
        }

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