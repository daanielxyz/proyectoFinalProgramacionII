package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AlojamientoServicio {
    private final AlojamientoRepositorioImpl alojamientoRepositorio;

    public AlojamientoServicio() {
        this.alojamientoRepositorio = new AlojamientoRepositorioImpl();
    }

    public void crearAlojamiento(Alojamiento alojamiento) throws Exception {
        alojamientoRepositorio.guardar(alojamiento);
    }

    public void actualizarAlojamiento(Alojamiento alojamiento) throws Exception {
        alojamientoRepositorio.actualizar(alojamiento);
    }

    public void eliminarAlojamiento(Alojamiento alojamiento) throws Exception {
        if (!alojamiento.getReservasAlojamientoActivas().isEmpty()) {
            throw new Exception("No se puede eliminar un alojamiento con reservas activas");
        }
        alojamientoRepositorio.eliminar(alojamiento);
    }

    public List<Alojamiento> consultarAlojamientos(Ciudad ciudad) {
        return alojamientoRepositorio.listarPorCiudad(ciudad);
    }

    public List<Alojamiento> buscarAlojamientosAleatorios(){
        List<Alojamiento> alojamientosIniciales = alojamientoRepositorio.listarTodos();
        Collections.shuffle(alojamientosIniciales);
        return alojamientosIniciales.subList(0, 3);
    }
}