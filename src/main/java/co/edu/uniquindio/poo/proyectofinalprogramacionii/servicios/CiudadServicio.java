package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Ciudad;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.CiudadRepositorio;

public class CiudadServicio {
    private final CiudadRepositorio ciudadRepositorio;

    public CiudadServicio(CiudadRepositorio ciudadRepositorio) {
        this.ciudadRepositorio = ciudadRepositorio;
    }

    public void guardarCiudad(Ciudad ciudad) {
        if (ciudadRepositorio.buscarPorNombre(ciudad.getNombre()) != null) {
            throw new IllegalArgumentException("La ciudad ya existe");
        }
        ciudadRepositorio.guardar(ciudad);
    }
}