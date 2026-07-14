package br.edu.iff.ccc.DeskGo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import br.edu.iff.ccc.DeskGo.dto.EstacaoRequest;
import br.edu.iff.ccc.DeskGo.services.EstacaoUserCase;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/painel/gestor")
public class GestorViewController {

    private final EstacaoUseCase estacaoUseCase;

    public GestorViewController(EstacaoUseCase estacaoUseCase) {
        this.estacaoUseCase = estacaoUseCase;
    }

    @GetMapping
    public String getPainelGestor() {
        return "painelGestor";
    }

    @GetMapping("/cadastrar-estacao")
    public String getCadastrarEstacao() {

        EstacaoRequest estacao = new EstacaoRequest();
        model.addAttribute("estacao", estacao);

        return "cadastrarEstacao";
    }

    @PostMapping("/cadastrar-estacao")
    public String postCadastrarEstacao() {

        estacaoUseCase.criarEstacao(estacaoRequest);
        
        return "redirect:/painel/gestor";
    }

    @GetMapping("/editar-estacao/{id}")
    public String getEditarEstacao(@org.springframework.web.bind.annotation.PathVariable("id") Long id) {
       
        return "editarEstacao"; 
    }

    @PostMapping("/editar-estacao/{id}")
    public String postEditarEstacao(@org.springframework.web.bind.annotation.PathVariable("id") Long id) {
        
        return "redirect:/painel/gestor";
    }

    @PostMapping("/excluir-estacao/{id}")
    public String postExcluirEstacao(@org.springframework.web.bind.annotation.PathVariable("id") Long id) {
        
        return "redirect:/painel/gestor";
    }
}