package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Reserva;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservaRepositorioImpl implements ReservaRepositorio {
    private final List<Reserva> reservas = new ArrayList<>();

    @Override
    public void guardar(Reserva reserva) {
        reservas.add(reserva);
    }

    @Override
    public void eliminar(Reserva reserva) {
        reservas.remove(reserva);
    }

    @Override
    public List<Reserva> listarPorUsuario(Usuario usuario) {
        return reservas.stream()
                .filter(r -> r.getUsuario().equals(usuario))
                .collect(Collectors.toList());
    }
}