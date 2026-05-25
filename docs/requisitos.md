# 📋 Requisitos de Negócio — DeskGo

## Descrição do Sistema

O DeskGo é um sistema de gerenciamento de reservas de estações de coworking. O sistema permite que usuários consultem mesas disponíveis, realizem reservas para datas específicas e acompanhem seus agendamentos, enquanto gestores administram as estações cadastradas.

---

# 🧩 Requisitos de Negócio

## RN01 - Visualização de Estações

O sistema deve permitir que usuários visualizem as estações de coworking cadastradas de forma organizada.

---

## RN02 - Consulta de Detalhes das Estações

O sistema deve permitir a visualização das características de cada estação, como proximidade da janela, tomada disponível e ambiente silencioso.

---

## RN03 - Filtragem de Estações

O sistema deve permitir a filtragem de estações com base em características específicas.

---

## RN04 - Consulta de Disponibilidade

O sistema deve permitir que usuários consultem a disponibilidade das mesas em uma data específica.

---

## RN05 - Realização de Reservas

O sistema deve permitir que usuários realizem reservas de estações disponíveis para uma data determinada.

---

## RN06 - Cancelamento de Reservas

O sistema deve permitir o cancelamento de reservas realizadas anteriormente.

---

## RN07 - Controle de Conflitos de Reserva

O sistema não deve permitir que uma mesma estação seja reservada mais de uma vez na mesma data.

---

## RN08 - Histórico de Reservas

O sistema deve permitir que usuários visualizem o histórico de reservas realizadas.

---

## RN09 - Cadastro de Estações

O sistema deve permitir que gestores cadastrem novas estações de coworking.

---

## RN10 - Edição de Estações

O sistema deve permitir que gestores atualizem informações e características das estações cadastradas.

---

## RN11 - Remoção de Estações

O sistema deve permitir que gestores removam estações cadastradas do sistema.

---

## RN12 - Controle de Acesso Administrativo

O sistema deve restringir funcionalidades administrativas apenas para usuários gestores.

---

# Regra de Negócio Principal

O sistema deve garantir que não existam duas reservas para a mesma estação na mesma data.

---

# 👥 Perfis do Sistema

## Usuário
- Visualizar estações
- Consultar disponibilidade
- Realizar reservas
- Cancelar reservas
- Consultar histórico

## Gestor
- Cadastrar estações
- Editar estações
- Remover estações
- Gerenciar características das mesas
