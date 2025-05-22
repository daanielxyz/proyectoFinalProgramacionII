package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Reserva;
import java.util.List;

public interface ReservaRepositorio {
    void guardar(Reserva reserva);
    Reserva buscarPorId(String id);
    List<Reserva> listarTodas();
}