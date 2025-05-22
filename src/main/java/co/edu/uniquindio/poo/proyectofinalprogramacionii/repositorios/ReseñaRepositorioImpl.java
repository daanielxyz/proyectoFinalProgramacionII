package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Reseña;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Constantes;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Persistencia;
import java.util.ArrayList;
import java.util.List;

public class ReseñaRepositorioImpl implements ReseñaRepositorio {
    private List<Reseña> reseñas;

    public ReseñaRepositorioImpl() {
        this.reseñas = leerDatos();
    }

    private List<Reseña> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_RESEÑAS);
            return datos != null ? (List<Reseña>) datos : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void guardarDatos() {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_RESEÑAS, reseñas);
        } catch (Exception e) {
        }
    }

    @Override
    public void guardar(Reseña reseña) {
        reseñas.add(reseña);
        guardarDatos();
    }

    @Override
    public Reseña buscarPorId(String id) {
        return reseñas.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Reseña> listarTodas() {
        return new ArrayList<>(reseñas);
    }
}