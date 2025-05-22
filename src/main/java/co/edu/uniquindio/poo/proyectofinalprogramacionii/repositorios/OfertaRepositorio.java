package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Oferta;

import java.util.List;

public interface OfertaRepositorio {
    void guardar(Oferta oferta);
    Oferta buscarPorId(String id);
    List<Oferta> listarPorAlojamiento(String alojamientoNombre);
    List<Oferta> listarTodas();
}