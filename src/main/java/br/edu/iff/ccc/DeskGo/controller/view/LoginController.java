package br.edu.iff.ccc.DeskGo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.iff.ccc.DeskGo.entities.Usuario;
import br.edu.iff.ccc.DeskGo.services.UsuarioUseCase;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final UsuarioUseCase usuarioUseCase;

    public LoginController(UsuarioUseCase usuarioUseCase) {
        this.usuarioUseCase = usuarioUseCase;
    }

    @GetMapping("/login")
    public String telaLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String fazerLogin(
            @RequestParam("email") String email,
            @RequestParam("senha") String senha,
            Model model,
            HttpSession session) {

        Usuario usuario = this.usuarioUseCase.autenticar(email, senha);

        if (usuario == null) {
            model.addAttribute("erro", "E-mail ou senha inválidos.");
            return "login";
        }

        session.setAttribute("usuarioLogado", usuario);

        return "redirect:/painel";
    }

    @GetMapping("/logout")
    public String fazerLogout(HttpSession session) {
        session.invalidate();

        return "redirect:/login";
    }
}