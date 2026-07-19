package br.edu.iff.ccc.DeskGo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.DeskGo.dto.EstacaoRequest;
import br.edu.iff.ccc.DeskGo.services.EstacaoUseCase;

@Controller
@RequestMapping("/estacao")
public class EstacaoController {
    private final EstacaoUseCase estacaoUseCase;

    public EstacaoController(EstacaoUseCase estacaoUseCase) {
        this.estacaoUseCase = estacaoUseCase;
    }

    @GetMapping("/novo")
    public String novaEstacao(Model model) {
        EstacaoRequest novaEstacao = new EstacaoRequest();
        
        model.addAttribute("estacao", novaEstacao); 
        
        return "cadastrarEstacao"; 
    }
    
    @PostMapping
    public String criarEstacao(EstacaoRequest estacaoRequest) {
        this.estacaoUseCase.criarEstacao(estacaoRequest);
        
        return "redirect:/estacao"; 
    }   

    @GetMapping
    public String listarEstacoes(Model model) {
        model.addAttribute("estacoes", this.estacaoUseCase.listarEstacoes());
        
        return "painelGestor";
    }
}
