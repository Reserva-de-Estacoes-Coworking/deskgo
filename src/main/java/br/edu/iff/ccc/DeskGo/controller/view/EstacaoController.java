package br.edu.iff.ccc.DeskGo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.iff.ccc.DeskGo.dto.EstacaoRequest;
import br.edu.iff.ccc.DeskGo.entities.Estacao;
import br.edu.iff.ccc.DeskGo.entities.Perfil;
import br.edu.iff.ccc.DeskGo.entities.Usuario;
import br.edu.iff.ccc.DeskGo.services.EstacaoUseCase;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import java.util.UUID;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/estacao")
public class EstacaoController {
    private final EstacaoUseCase estacaoUseCase;

    public EstacaoController(EstacaoUseCase estacaoUseCase) {
        this.estacaoUseCase = estacaoUseCase;
    }

    @GetMapping("/novo")
    public String novaEstacao(Model model, HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null)
            return "redirect:/login";
        if (logado.getPerfil() != Perfil.GESTOR)
            return "redirect:/painel";

        EstacaoRequest novaEstacao = new EstacaoRequest();

        model.addAttribute("estacao", novaEstacao);
        model.addAttribute("usuarioLogado", logado);

        return "cadastrarEstacao";
    }

    @PostMapping
    public String criarEstacao(
            @org.springframework.web.bind.annotation.ModelAttribute("estacao") @Valid EstacaoRequest estacaoRequest,
            BindingResult result,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model) {

        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null)
            return "redirect:/login";
        if (logado.getPerfil() != Perfil.GESTOR)
            return "redirect:/painel";

        if (result.hasErrors()) {
            model.addAttribute("usuarioLogado", logado);
            return "cadastrarEstacao";
        }

        this.estacaoUseCase.criarEstacao(estacaoRequest);
        redirectAttributes.addFlashAttribute("sucesso", "Estação criada com sucesso!");

        return "redirect:/painel/gestor";
    }

    @GetMapping
    public String listarEstacoes() {
        return "redirect:/painel/gestor";
    }

    @GetMapping("/editar/{id}")
    public String editarEstacao(@org.springframework.web.bind.annotation.PathVariable("id") UUID id, Model model,
            HttpSession session) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null)
            return "redirect:/login";
        if (logado.getPerfil() != Perfil.GESTOR)
            return "redirect:/painel";

        Estacao estacao = this.estacaoUseCase.buscarEstacao(id);
        if (estacao == null) {
            return "redirect:/painel/gestor";
        }

        EstacaoRequest request = new EstacaoRequest();
        request.setNome(estacao.getNome());
        request.setDescricao(estacao.getDescricao());
        request.setStatus(estacao.getStatus());
        request.setCaracteristicas(estacao.getCaracteristicas());

        model.addAttribute("estacao", request);
        model.addAttribute("isEdit", true);
        model.addAttribute("estacaoId", id);
        model.addAttribute("usuarioLogado", logado);

        return "cadastrarEstacao";
    }

    @PostMapping("/editar/{id}")
    public String salvarEdicaoEstacao(@org.springframework.web.bind.annotation.PathVariable("id") UUID id,
            @org.springframework.web.bind.annotation.ModelAttribute("estacao") @Valid EstacaoRequest estacaoRequest,
            BindingResult result,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model) {

        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null)
            return "redirect:/login";
        if (logado.getPerfil() != Perfil.GESTOR)
            return "redirect:/painel";

        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);
            model.addAttribute("estacaoId", id);
            model.addAttribute("usuarioLogado", logado);
            return "cadastrarEstacao";
        }

        this.estacaoUseCase.atualizarEstacao(id, estacaoRequest);
        redirectAttributes.addFlashAttribute("sucesso", "Estação atualizada com sucesso!");

        return "redirect:/painel/gestor";
    }

    @PostMapping("/deletar/{id}")
    public String deletarEstacao(@org.springframework.web.bind.annotation.PathVariable("id") UUID id,
            HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        if (logado == null)
            return "redirect:/login";
        if (logado.getPerfil() != Perfil.GESTOR)
            return "redirect:/painel";

        this.estacaoUseCase.deletarEstacao(id);
        redirectAttributes.addFlashAttribute("sucesso", "Estação removida com sucesso!");

        return "redirect:/painel/gestor";
    }
}
