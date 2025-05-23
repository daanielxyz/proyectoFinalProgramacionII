package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Oferta;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Constantes;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OfertaRepositorioImpl implements OfertaRepositorio {
    private List<Oferta> ofertas;

    public OfertaRepositorioImpl() {
        this.ofertas = leerDatos();
    }

    private List<Oferta> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_OFERTAS);
            return datos != null ? (List<Oferta>) datos : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void guardarDatos() {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_OFERTAS, ofertas);
        } catch (Exception e) {
            // Log error
        }
    }

    @Override
    public void guardar(Oferta oferta) {
        ofertas.add(oferta);
        guardarDatos();
    }

    @Override
    public Oferta buscarPorId(String id) {
        return ofertas.stream()
                .filter(o -> o.getId().toString().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Oferta> listarPorAlojamiento(String alojamientoNombre) {
        return ofertas.stream()
                .filter(o -> o.getAlojamientoNombre().equals(alojamientoNombre))
                .collect(Collectors.toList());
    }

    @Override
    public List<Oferta> listarTodas() {
        return new ArrayList<>(ofertas);
    }
}