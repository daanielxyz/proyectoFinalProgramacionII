package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Ciudad;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.CiudadRepositorioImpl;

public class CiudadServicio {
    private final CiudadRepositorioImpl ciudadRepositorio;

    public CiudadServicio() {
        this.ciudadRepositorio = new CiudadRepositorioImpl();
    }

    public void guardarCiudad(Ciudad ciudad) {
        if (ciudadRepositorio.buscarPorNombre(ciudad.toString()) != null) {
            throw new IllegalArgumentException("La ciudad ya existe");
        }
        ciudadRepositorio.guardar(ciudad);
    }
}