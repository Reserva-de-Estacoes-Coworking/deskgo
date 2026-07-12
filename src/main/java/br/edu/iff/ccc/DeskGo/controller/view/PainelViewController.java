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
}