package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Ciudad;
import java.util.List;

public interface CiudadRepositorio {
    void guardar(Ciudad ciudad);
    Ciudad buscarPorNombre(String nombre);
    void actualizar(Ciudad ciudad);
    void eliminar(String nombre);
    List<Ciudad> listarTodos();
}