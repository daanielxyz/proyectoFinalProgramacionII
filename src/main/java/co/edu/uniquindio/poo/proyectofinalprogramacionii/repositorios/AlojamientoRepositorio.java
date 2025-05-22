package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import java.util.List;

public interface AlojamientoRepositorio {
    void guardar(Alojamiento alojamiento);
    Alojamiento buscarPorNombre(String nombre);
    void actualizar(Alojamiento alojamiento);
    void eliminar(String nombre);
    List<Alojamiento> listarTodos();
}