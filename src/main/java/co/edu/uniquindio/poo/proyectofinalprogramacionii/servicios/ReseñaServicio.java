package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Reseña;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.ReseñaRepositorio;

public class ReseñaServicio {
    private final ReseñaRepositorio reseñaRepositorio;

    public ReseñaServicio(ReseñaRepositorio reseñaRepositorio) {
        this.reseñaRepositorio = reseñaRepositorio;
    }

    public void guardarReseña(Reseña reseña) {
        reseñaRepositorio.guardar(reseña);
    }
}