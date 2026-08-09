# 📊 Diagrama de Classes — DeskGo

Este diagrama representa a estrutura de dados principal do sistema DeskGo.

```mermaid
classDiagram
    note for Usuario "consultarHistorico() é implementado via\nReservaUseCase (separação entre entidade e regra de negócio)."
    class Usuario {
        +UUID id
        +String nome
        +String email
        +String senha
        +Perfil perfil
        +consultarHistorico()
    }

    class Perfil {
        <<enumeration>>
        USUARIO
        GESTOR
    }

    class Estacao {
        +UUID id
        +String nome
        +String descricao
        +StatusEstacao status
        +List~Caracteristica~ caracteristicas
    }

    class Caracteristica {
        <<enumeration>>
        JANELA
        TOMADA
        SILENCIOSO
        DUPLA
    }

    class StatusEstacao {
        <<enumeration>>
        ATIVO
        INATIVO
        MANUTENCAO
    }

    class Reserva {
        +UUID id
        +LocalDate data
        +Usuario usuario
        +Estacao estacao
        +editarData(novaData)
        +cancelar()
    }

    Usuario "1" --> "0..*" Reserva : realiza
    Estacao "1" --> "0..*" Reserva : possui
    Usuario --> Perfil
    Estacao --> Caracteristica
    Estacao --> StatusEstacao
```

> **Nota Arquitetural:** No diagrama, a classe `Usuario` apresenta o método `consultarHistorico()`, e a `Reserva` apresenta `editarData(novaData)`. Na implementação real do sistema (MVC + UseCases), essas funcionalidades existem encapsuladas no `ReservaUseCase`. Essa decisão reflete uma separação de responsabilidades (Clean Architecture), mantendo as entidades mais limpas e delegando regras de negócio complexas aos Casos de Uso (como as verificações de conflito de datas na edição de reservas).
