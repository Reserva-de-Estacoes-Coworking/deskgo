package br.edu.iff.ccc.DeskGo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/painel")
public class PainelViewController {

    @GetMapping
    public String getPainel() {
        return "painel";
    }

    @GetMapping("/reservar-mesa")
    public String getReservarMesa() {
        return "reservarMesa";
    }

    @GetMapping("/minhas-reservas")
    public String getMinhasReservas() {
        return "minhasReservas";
    }

    @org.springframework.web.bind.annotation.PostMapping("/reservar-mesa")
    public String postReservarMesa() {
        // Lógica para validar disponibilidade e criar a reserva virá aqui
        return "redirect:/painel/minhas-reservas";
    }

    @org.springframework.web.bind.annotation.PostMapping("/cancelar-reserva/{id}")
    public String postCancelarReserva(@org.springframework.web.bind.annotation.PathVariable("id") Long id) {
        // Lógica para cancelar a reserva virá aqui
        return "redirect:/painel/minhas-reservas";
    }
}