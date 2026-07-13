package br.edu.iff.ccc.DeskGo.controller.apirest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @GetMapping
    public Map<String, Object> apiInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ONLINE");
        response.put("message", "DeskGo API v1 - Em Construção / Preparação da Estrutura");
        response.put("version", "0.0.1-SNAPSHOT");
        return response;
    }
}
