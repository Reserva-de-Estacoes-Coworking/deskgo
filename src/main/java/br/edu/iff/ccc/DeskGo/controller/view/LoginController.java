package br.edu.iff.ccc.DeskGo.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.iff.ccc.DeskGo.entities.Perfil;
import br.edu.iff.ccc.DeskGo.entities.Usuario;
import br.edu.iff.ccc.DeskGo.services.UsuarioUseCase;
import jakarta.servlet.http.HttpSession;
import br.edu.iff.ccc.DeskGo.exceptions.RegraDeNegocioException;

@Controller
public class LoginController {
    private final UsuarioUseCase usuarioUseCase;

    public LoginController(UsuarioUseCase usuarioUseCase) {
        this.usuarioUseCase = usuarioUseCase;
    }

    @GetMapping("/")
    public String raiz() {
        return "redirect:/login";
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

        try {
            Usuario usuario = this.usuarioUseCase.autenticar(email, senha);
            session.setAttribute("usuarioLogado", usuario);

            if (usuario.getPerfil() == Perfil.GESTOR) {
                return "redirect:/painel/gestor";
            }

            return "redirect:/painel";
        } catch (RegraDeNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String fazerLogout(HttpSession session) {
        session.invalidate();

        return "redirect:/login";
    }
}