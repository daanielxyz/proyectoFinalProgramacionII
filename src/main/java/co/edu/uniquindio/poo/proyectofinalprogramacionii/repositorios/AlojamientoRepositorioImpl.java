package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Constantes;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Persistencia;
import java.util.ArrayList;
import java.util.List;

public class AlojamientoRepositorioImpl implements AlojamientoRepositorio {
    private List<Alojamiento> alojamientos;

    public AlojamientoRepositorioImpl() {
        this.alojamientos = leerDatos();
    }

    private List<Alojamiento> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_ALOJAMIENTOS);
            return datos != null ? (List<Alojamiento>) datos : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void guardarDatos() {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_ALOJAMIENTOS, alojamientos);
        } catch (Exception e) {
        }
    }

    @Override
    public void guardar(Alojamiento alojamiento) {
        alojamientos.add(alojamiento);
        guardarDatos();
    }

    @Override
    public Alojamiento buscarPorNombre(String nombre) {
        return alojamientos.stream()
                .filter(a -> a.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void actualizar(Alojamiento alojamiento) {
        for (int i = 0; i < alojamientos.size(); i++) {
            if (alojamientos.get(i).getNombre().equals(alojamiento.getNombre())) {
                alojamientos.set(i, alojamiento);
                guardarDatos();
                break;
            }
        }
    }

    @Override
    public void eliminar(String nombre) {
        alojamientos.removeIf(a -> a.getNombre().equals(nombre));
        guardarDatos();
    }

    @Override
    public List<Alojamiento> listarTodos() {
        return new ArrayList<>(alojamientos);
    }
}