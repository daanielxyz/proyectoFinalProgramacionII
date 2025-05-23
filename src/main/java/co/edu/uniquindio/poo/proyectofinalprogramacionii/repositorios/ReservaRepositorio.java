package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Reserva;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Usuario;

import java.util.List;

public interface ReservaRepositorio {
    void guardar(Reserva reserva);
    void eliminar(Reserva reserva);
    List<Reserva> listarPorUsuario(Usuario usuario);
}