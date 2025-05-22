package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.AlojamientoRepositorio;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Alojamiento> buscarAlojamientos(String nombre, String tipo, String ciudad, Double precioMin, Double precioMax) {
        return alojamientoRepositorio.listarTodos().stream()
                .filter(a -> (nombre == null || a.getNombre().toLowerCase().contains(nombre.toLowerCase())))
                .filter(a -> (tipo == null || a.getClass().getSimpleName().toLowerCase().contains(tipo.toLowerCase())))
                .filter(a -> (ciudad == null || a.getCiudad().getNombre().toLowerCase().contains(ciudad.toLowerCase())))
                .filter(a -> (precioMin == null || a.getPrecioPorNocheTotal() >= precioMin))
                .filter(a -> (precioMax == null || a.getPrecioPorNocheTotal() <= precioMax))
                .collect(Collectors.toList());
    }
}