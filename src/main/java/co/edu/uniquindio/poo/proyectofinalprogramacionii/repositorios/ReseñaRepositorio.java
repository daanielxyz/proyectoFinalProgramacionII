package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Reseña;

import java.util.List;

public interface ReseñaRepositorio {
    void guardar(Reseña reseña);
    Reseña buscarPorId(String id);
    List<Reseña> listarTodas();
}