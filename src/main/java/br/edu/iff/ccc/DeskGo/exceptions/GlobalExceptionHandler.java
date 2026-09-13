package br.edu.iff.ccc.DeskGo.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ModelAndView handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        ModelAndView mav = new ModelAndView("error/404");
        mav.addObject("mensagem", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ModelAndView handleRegraDeNegocio(RegraDeNegocioException ex) {
        ModelAndView mav = new ModelAndView("error/400");
        mav.addObject("mensagem", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(EntidadeDuplicadaException.class)
    public ModelAndView handleEntidadeDuplicada(EntidadeDuplicadaException ex) {
        ModelAndView mav = new ModelAndView("error/400");
        mav.addObject("mensagem", ex.getMessage());
        return mav;
    }
    
    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public ModelAndView handleTypeMismatch(Exception ex) {
        ModelAndView mav = new ModelAndView("error/400");
        mav.addObject("mensagem", "Parâmetro inválido na URL.");
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex) {
        ModelAndView mav = new ModelAndView("error/500");
        mav.addObject("mensagem", "Ocorreu um erro interno no servidor.");
        return mav;
    }
}
