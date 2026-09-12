# RelatÃ³rio de ImplementaÃ§Ã£o: AvaliaÃ§Ã£o P2 - DeskGo

Este documento registra o progresso passo a passo da implementaÃ§Ã£o dos requisitos descritos no plano de implementaÃ§Ã£o da P2.

## Etapas
### Etapa 1: Preparação da Base no Git e Integração das Branches Parciais
- Verificado os commits locais.
- A branch main foi atualizada via git pull origin main.
- As alterações de validação e persistência (TR06-ValidacoesExcecoes) foram mescladas (merge) na main.
- A nova branch de trabalho TR07-ApiRest foi criada a partir da main para dar início à implementação da API REST e configurações do OpenAPI (Swagger).


### Etapa 2: Adição do Springdoc OpenAPI e Configuração do Swagger UI
- A dependência springdoc-openapi-starter-webmvc-ui (versão 2.8.5) foi adicionada ao arquivo pom.xml.
- Foram configuradas as propriedades do SpringDoc no arquivo pplication.properties (caminhos e ordenação de operações/tags).
- Criada a classe OpenApiConfig na camada config configurando o bean OpenAPI com informações descritivas da API (título, descrição, versão e contato).


### Etapa 3: DTOs Especializados para a Camada REST (Request e Response)
- Adicionadas anotações @Schema nos DTOs existentes: EstacaoRequest, UsuarioRequest e EstacaoDisponibilidadeDTO para documentação no OpenAPI.
- Criados novos DTOs de Request: ReservaApiRequest e AtualizarDataReservaRequest, com anotações de validação (@NotNull, @FutureOrPresent) e @Schema.
- Criados novos DTOs de Response: UsuarioResponseDTO (sem expor a senha), EstacaoResponseDTO e ReservaResponseDTO, todos devidamente documentados com @Schema.


### Etapa 4: Ajustes e Enriquecimento na Camada de Serviços (UseCases)
- Em EstacaoUseCase, o método criarEstacao foi alterado para retornar a entidade Estacao persistida, permitindo que a API REST devolva o objeto criado.
- Em UsuarioUseCase, o método cadastrarUsuario foi ajustado da mesma forma, retornando Usuario.
- Em ReservaUseCase, o UsuarioRepositorio foi injetado via construtor para permitir a validação e busca do usuário nas novas sobrecargas.
- Adicionados métodos à ReservaUseCase: criarReserva(UUID, LocalDate, UUID) retornando Reserva, uscarReserva(UUID), listarTodas(), cancelarReserva(UUID) e tualizarDataReserva(UUID, LocalDate), todos adaptados para o fluxo e as necessidades da API RESTful.


### Etapa 5: Padronização RFC 9457 (Problem Details) e Isolamento MVC vs REST
- O interceptador GlobalExceptionHandler (que devolvia HTML) foi restrito ao pacote r.edu.iff.ccc.DeskGo.controller.view através da anotação @ControllerAdvice(basePackages = ...).
- Criado o DTO InvalidParamDTO para suportar o formato de erros de validação padronizado pela RFC 9457.
- Criado o RestGlobalExceptionHandler restrito ao pacote r.edu.iff.ccc.DeskGo.controller.apirest, devolvendo objetos ProblemDetail nativos do Spring (pplication/problem+json). Tratamentos incluídos para erro 400 (MethodArgumentNotValid, HttpMessageNotReadable), 404 (RecursoNaoEncontrado), 409 (EntidadeDuplicada), e 500.


### Etapa 6: Criação dos Endpoints RESTful e Documentação OpenAPI (Swagger)
- Criado o pacote r.edu.iff.ccc.DeskGo.controller.apirest.
- Criado o UsuarioRestController (/api/v1/usuarios) cobrindo as operações de CRUD, retornando UsuarioResponseDTO e mapeado com tags e respostas do OpenAPI.
- Criado o EstacaoRestController (/api/v1/estacoes), provendo CRUD de estações e o endpoint adicional /disponibilidade para consultas de mesas livres.
- Criado o ReservaRestController (/api/v1/reservas) com métodos para criação de reserva, consulta filtrável por usuário, alteração de data e cancelamento.


### Etapa 7: Testes Automatizados da API REST e Testes de Regressão
- Os testes de persistência existentes (@DataJpaTest) foram preservados e estão funcionais.
- Foram criadas três classes de teste utilizando @WebMvcTest e MockMvc:
  - UsuarioRestControllerTest: Validando a criação (201) sem expor senhas, e o tratamento de validação (400) e recursos não encontrados (404), garantindo a conformidade com o problem details.
  - EstacaoRestControllerTest: Validando 201, 200, e campos obrigatórios de validação em branco (400).
  - ReservaRestControllerTest: Validando criação (201), tentativa de data no passado (400) e conflito de reservas (409) gerando o erro apropriado de EntidadeDuplicadaException.

