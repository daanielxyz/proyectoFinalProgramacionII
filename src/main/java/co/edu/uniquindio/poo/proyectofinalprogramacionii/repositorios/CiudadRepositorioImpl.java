package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Ciudad;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Constantes;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Persistencia;

import java.util.ArrayList;
import java.util.List;

public class CiudadRepositorioImpl implements CiudadRepositorio {
    private List<Ciudad> ciudades;

    public CiudadRepositorioImpl() {
        this.ciudades = leerDatos();
    }

    private List<Ciudad> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_CIUDADES);
            return datos != null ? (List<Ciudad>) datos : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void guardarDatos() {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_CIUDADES, ciudades);
        } catch (Exception e) {
        }
    }

    @Override
    public void guardar(Ciudad ciudad) {
        ciudades.add(ciudad);
        guardarDatos();
    }

    @Override
    public Ciudad buscarPorNombre(String nombre) {
        return ciudades.stream()
                .filter(c -> c.toString().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void actualizar(Ciudad ciudad) {
        for (int i = 0; i < ciudades.size(); i++) {
            if (ciudades.get(i).toString().equalsIgnoreCase(ciudad.toString())) {
                ciudades.set(i, ciudad);
                guardarDatos();
                break;
            }
        }
    }

    @Override
    public void eliminar(String nombre) {
        ciudades.removeIf(c -> c.toString().equalsIgnoreCase(nombre));
        guardarDatos();
    }

    @Override
    public List<Ciudad> listarTodos() {
        return new ArrayList<>(ciudades);
    }
}