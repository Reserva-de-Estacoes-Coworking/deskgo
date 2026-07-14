package br.edu.iff.ccc.DeskGo.repository;

import java.util.ArrayList;
import java.util.List;

import br.edu.iff.ccc.DeskGo.entities.Estacao;

public class EstacaoRepositorio {
     private List<Estacao> estacoes;

    public EstacaoRepositorio() {
        this.estacoes = new ArrayList<Estacao>();
    }

    public void salvar(Estacao estacao) {
        this.estacoes.add(estacao);
        System.out.println("Estação salva: " + estacao.getNome());
    }

    public Estacao buscarPorId(String id) {
        // Lógica para buscar uma estação pelo ID
        return null;
    }
    
}
