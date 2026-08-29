package br.edu.iff.ccc.DeskGo.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
 
import org.springframework.stereotype.Repository;
 
import br.edu.iff.ccc.DeskGo.entities.Reserva;

@Repository
public class ReservaRepositorio {
    private List<Reserva> reservas;
 
    public ReservaRepositorio() {
        this.reservas = new ArrayList<>();
    }
 
    public void salvar(Reserva reserva) {
        this.reservas.add(reserva);
    }
 
    public List<Reserva> listarTodos() {
        return this.reservas;
    }
 
    public Reserva buscarPorId(UUID id) {
        for (Reserva reserva : this.reservas) {
            if (reserva.getId().equals(id)) {
                return reserva;
            }
        }
        return null;
    }
 
    public List<Reserva> listarPorUsuario(UUID usuarioId) {
        List<Reserva> resultado = new ArrayList<>();
        for (Reserva reserva : this.reservas) {
            if (reserva.getUsuario().getId().equals(usuarioId)) {
                resultado.add(reserva);
            }
        }
        return resultado;
    }
 
    // Base da RN07: verifica se já existe reserva para essa estacao nessa data
    public boolean existeReservaParaEstacaoNaData(UUID estacaoId, LocalDate data) {
        for (Reserva reserva : this.reservas) {
            if (reserva.getEstacao().getId().equals(estacaoId) && reserva.getData().equals(data)) {
                return true;
            }
        }
        return false;
    }
 
    public void deletar(UUID id) {
        this.reservas.removeIf(reserva -> reserva.getId().equals(id));
    }
    
}
