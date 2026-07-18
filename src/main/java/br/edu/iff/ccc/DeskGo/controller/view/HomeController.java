package br.edu.iff.ccc.DeskGo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/home")
public class HomeController {

    // http://localhost:8080/home
    @GetMapping
    public String getPaginaInicial() {
        return "home.html";
    }

    // http://localhost:8080/home/1?param=valor
    @GetMapping("/{id}")
    public String getExemploParametros(@PathVariable("id") String id, @RequestParam("param") String param, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("var1", param);
        System.out.println("ID: " + id);
        System.out.println("Param: " + param);
        return "home";
    }

}