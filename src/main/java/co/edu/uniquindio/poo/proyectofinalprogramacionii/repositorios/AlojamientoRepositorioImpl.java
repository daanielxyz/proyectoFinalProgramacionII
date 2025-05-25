package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Ciudad;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Valoracion;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Constantes;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlojamientoRepositorioImpl implements AlojamientoRepositorio {
    private List<Alojamiento> alojamientos;
    private final List<Valoracion> valoraciones = new ArrayList<>();

    public AlojamientoRepositorioImpl() {
        this.alojamientos = leerDatos();
    }

    private List<Alojamiento> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_ALOJAMIENTOS);
            @SuppressWarnings("unchecked")
            List<Alojamiento> lista = datos != null ? (List<Alojamiento>) datos : new ArrayList<>();
            return lista;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void guardarDatos() {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_ALOJAMIENTOS, alojamientos);
        } catch (Exception e) {
            // Log error
        }
    }    @Override
    public void guardar(Alojamiento alojamiento) {
        alojamientos.add(alojamiento);
        guardarDatos();
    }

    @Override
    public void actualizar(Alojamiento alojamiento) {
        alojamientos.removeIf(a -> a.getNombre().equals(alojamiento.getNombre()) && a.getCiudad().equals(alojamiento.getCiudad()));
        alojamientos.add(alojamiento);
        guardarDatos();
    }

    @Override
    public void eliminar(Alojamiento alojamiento) {
        alojamientos.remove(alojamiento);
        guardarDatos();
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