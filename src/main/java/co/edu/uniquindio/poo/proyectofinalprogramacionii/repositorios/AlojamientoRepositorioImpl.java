package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Ciudad;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Valoracion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlojamientoRepositorioImpl implements AlojamientoRepositorio {
    private final List<Alojamiento> alojamientos = new ArrayList<>();
    private final List<Valoracion> valoraciones = new ArrayList<>();

    @Override
    public void guardar(Alojamiento alojamiento) {
        alojamientos.add(alojamiento);
    }

    @Override
    public void actualizar(Alojamiento alojamiento) {
        alojamientos.removeIf(a -> a.getNombre().equals(alojamiento.getNombre()) && a.getCiudad().equals(alojamiento.getCiudad()));
        alojamientos.add(alojamiento);
    }

    @Override
    public void eliminar(Alojamiento alojamiento) {
        alojamientos.remove(alojamiento);
    }

    @Override
    public List<Alojamiento> listarPorCiudad(Ciudad ciudad) {
        return alojamientos.stream()
                .filter(a -> a.getCiudad().equals(ciudad))
                .collect(Collectors.toList());
    }

    @Override
    public List<Alojamiento> listarTodos() {
        return new ArrayList<>(alojamientos);
    }

    @Override
    public List<Valoracion> valoraciones() {
        return new ArrayList<>(valoraciones);
    }
}