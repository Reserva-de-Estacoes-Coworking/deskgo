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

    @GetMapping("/cadastrar-estacao")
    public String getCadastrarEstacao() {
        return "cadastrarEstacao";
    }

    @org.springframework.web.bind.annotation.PostMapping("/cadastrar-estacao")
    public String postCadastrarEstacao() {
        // Lógica para salvar a estação virá aqui
        return "redirect:/painel/gestor";
    }

    @GetMapping("/editar-estacao/{id}")
    public String getEditarEstacao(@org.springframework.web.bind.annotation.PathVariable("id") Long id) {
        // Lógica para carregar a estação pelo ID virá aqui
        return "editarEstacao"; // View a ser criada futuramente
    }

    @org.springframework.web.bind.annotation.PostMapping("/editar-estacao/{id}")
    public String postEditarEstacao(@org.springframework.web.bind.annotation.PathVariable("id") Long id) {
        // Lógica para atualizar a estação virá aqui
        return "redirect:/painel/gestor";
    }

    @org.springframework.web.bind.annotation.PostMapping("/excluir-estacao/{id}")
    public String postExcluirEstacao(@org.springframework.web.bind.annotation.PathVariable("id") Long id) {
        // Lógica para excluir a estação virá aqui
        return "redirect:/painel/gestor";
    }
}