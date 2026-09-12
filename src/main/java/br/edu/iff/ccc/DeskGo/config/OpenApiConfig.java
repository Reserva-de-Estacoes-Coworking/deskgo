package br.edu.iff.ccc.DeskGo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI deskGoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DeskGo API Coworking")
                        .description("API RESTful para gerenciamento de espaços e mesas de trabalho")
                        .version("v1")
                        .contact(new Contact()
                                .name("Suporte DeskGo")
                                .email("suporte@deskgo.iff.edu.br")));
    }
}
