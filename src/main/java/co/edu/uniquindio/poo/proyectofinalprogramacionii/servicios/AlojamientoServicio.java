package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.AlojamientoRepositorio;
import java.util.List;

public class AlojamientoServicio {
    private final AlojamientoRepositorio alojamientoRepositorio;

    public AlojamientoServicio(AlojamientoRepositorio alojamientoRepositorio) {
        this.alojamientoRepositorio = alojamientoRepositorio;
    }

    public void crearAlojamiento(Alojamiento alojamiento) {
        if (alojamientoRepositorio.buscarPorNombre(alojamiento.getNombre()) != null) {
            throw new IllegalArgumentException("El alojamiento ya existe");
        }
        alojamientoRepositorio.guardar(alojamiento);
    }

    public List<Alojamiento> buscarAlojamientosAleatorios() {
        return alojamientoRepositorio.listarTodos();
    }
}