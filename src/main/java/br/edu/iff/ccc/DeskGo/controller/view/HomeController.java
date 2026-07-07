package br.edu.iff.ccc.DeskGo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping({"/", "/home", "/inicial"})
    public String exibirHome(Model model) {
        model.addAttribute("mensagem", "Seja bem-vindo ao Web Project!");
        return "home";
    }

    @GetMapping("/inicial/{id}")
    public String exibirComParametro(
            @PathVariable("id") String id,
            @RequestParam(value = "param", required = false, defaultValue = "valor") String param,
            Model model) {
        model.addAttribute("id", id);
        model.addAttribute("var1", param);
        model.addAttribute("mensagem", "Página com parâmetros carregada com sucesso!");
        return "home";
    }
}