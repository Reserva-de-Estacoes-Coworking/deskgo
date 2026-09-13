package br.edu.iff.ccc.DeskGo.exceptions;

import br.edu.iff.ccc.DeskGo.dto.InvalidParamDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.util.List;

@RestControllerAdvice(basePackages = "br.edu.iff.ccc.DeskGo.controller.apirest")
public class RestGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Um ou mais campos da requisição são inválidos.");
        pd.setTitle("Violação de Validação");
        pd.setType(URI.create("https://deskgo.iff.edu.br/erros/validacao"));

        List<InvalidParamDTO> invalidParams = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new InvalidParamDTO(error.getField(), error.getDefaultMessage()))
                .toList();
        
        pd.setProperty("invalid_params", invalidParams);
        return pd;
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ProblemDetail handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setTitle("Recurso Não Encontrado");
        pd.setType(URI.create("https://deskgo.iff.edu.br/erros/nao-encontrado"));
        return pd;
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ProblemDetail handleRegraDeNegocio(RegraDeNegocioException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        pd.setTitle("Regra de Negócio Violada");
        pd.setType(URI.create("https://deskgo.iff.edu.br/erros/regra-de-negocio"));
        return pd;
    }

    @ExceptionHandler(EntidadeDuplicadaException.class)
    public ProblemDetail handleEntidadeDuplicada(EntidadeDuplicadaException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        pd.setTitle("Conflito de Entidade");
        pd.setType(URI.create("https://deskgo.iff.edu.br/erros/conflito"));
        return pd;
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ProblemDetail handleBadRequestExceptions(Exception ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "A requisição está malformada ou possui parâmetros inválidos.");
        pd.setTitle("Requisição Malformada");
        pd.setType(URI.create("https://deskgo.iff.edu.br/erros/requisicao-malformada"));
        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro inesperado. Tente novamente mais tarde.");
        pd.setTitle("Erro Interno do Servidor");
        pd.setType(URI.create("https://deskgo.iff.edu.br/erros/erro-interno"));
        return pd;
    }
}
