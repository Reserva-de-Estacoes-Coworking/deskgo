package br.edu.iff.ccc.DeskGo.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "br.edu.iff.ccc.DeskGo.controller.view")
public class MvcExceptionHandler {
     @ExceptionHandler(RecursoNaoEncontradoException.class)
    public String handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex, Model model) {
        model.addAttribute("mensagem", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler({RegraDeNegocioException.class, EntidadeDuplicadaException.class})
    public String handleErroDeNegocio(RuntimeException ex, Model model) {
        model.addAttribute("mensagem", ex.getMessage());
        return "error/400";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("mensagem", "Ocorreu um erro interno no servidor.");
        return "error/500";
    }
}
