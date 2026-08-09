package br.edu.iff.ccc.DeskGo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.DeskGo.dto.EstacaoRequest;
import br.edu.iff.ccc.DeskGo.entities.Estacao;
import br.edu.iff.ccc.DeskGo.services.EstacaoUseCase;
import java.util.UUID;

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

    @GetMapping("/editar/{id}")
    public String editarEstacao(@org.springframework.web.bind.annotation.PathVariable("id") UUID id, Model model) {
        Estacao estacao = this.estacaoUseCase.buscarEstacao(id);
        if (estacao == null) {
            return "redirect:/estacao";
        }
        
        EstacaoRequest request = new EstacaoRequest();
        request.setNome(estacao.getNome());
        request.setDescricao(estacao.getDescricao());
        request.setStatus(estacao.getStatus());
        request.setCaracteristicas(estacao.getCaracteristicas());
        
        model.addAttribute("estacao", request);
        model.addAttribute("isEdit", true);
        model.addAttribute("estacaoId", id);
        
        return "cadastrarEstacao";
    }

    @PostMapping("/editar/{id}")
    public String salvarEdicaoEstacao(@org.springframework.web.bind.annotation.PathVariable("id") UUID id, EstacaoRequest estacaoRequest) {
        this.estacaoUseCase.atualizarEstacao(id, estacaoRequest);
        return "redirect:/estacao";
    }

    @PostMapping("/deletar/{id}")
    public String deletarEstacao(@org.springframework.web.bind.annotation.PathVariable("id") UUID id) {
        this.estacaoUseCase.deletarEstacao(id);
        return "redirect:/estacao";
    }
}
