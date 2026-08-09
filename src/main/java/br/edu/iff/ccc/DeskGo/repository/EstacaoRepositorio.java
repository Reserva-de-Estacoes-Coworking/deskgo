package br.edu.iff.ccc.DeskGo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc.DeskGo.entities.Estacao;

@Repository
public class EstacaoRepositorio {
    private List<Estacao> estacoes;

    public EstacaoRepositorio() {
        this.estacoes = new ArrayList<>();
    }

    public void salvar(Estacao estacao) {
        this.estacoes.add(estacao);
        System.out.println("Estação salva com sucesso: " + estacao.getNome());
    }

    public List<Estacao> listarTodos() {
        return this.estacoes;
    }

    public Estacao buscarPorId(UUID id) {
        for (Estacao estacao : this.estacoes) {
            if (estacao.getId().equals(id)) {
                return estacao;
            }
        }
        return null;
    }

    public void atualizar(Estacao estacaoAtualizada) {
        for (int i = 0; i < this.estacoes.size(); i++) {
            if (this.estacoes.get(i).getId().equals(estacaoAtualizada.getId())) {
                this.estacoes.set(i, estacaoAtualizada);
                return;
            }
        }
    }

    public void deletar(UUID id) {
        this.estacoes.removeIf(estacao -> estacao.getId().equals(id));
    }
}
