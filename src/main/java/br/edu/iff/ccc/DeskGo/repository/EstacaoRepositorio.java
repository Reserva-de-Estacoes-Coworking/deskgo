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
       return null; 
    }
}
