package br.edu.iff.ccc.DeskGo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/painel/gestor")
public class GestorViewController {

    @GetMapping
    public String getPainelGestor() {
        return "painelGestor";
    }
}