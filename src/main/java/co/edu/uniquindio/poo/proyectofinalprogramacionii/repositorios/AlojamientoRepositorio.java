package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Ciudad;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Valoracion;

import java.util.List;

public interface AlojamientoRepositorio {
    void guardar(Alojamiento alojamiento);
    void actualizar(Alojamiento alojamiento);
    void eliminar(Alojamiento alojamiento);
    List<Alojamiento> listarPorCiudad(Ciudad ciudad);
    List<Alojamiento> listarTodos();
    List<Valoracion> valoraciones();
}