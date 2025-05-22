package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Oferta;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.OfertaRepositorio;

import java.time.LocalDateTime;
import java.util.List;

public class OfertaServicio {
    private final OfertaRepositorio ofertaRepositorio;

    public OfertaServicio(OfertaRepositorio ofertaRepositorio) {
        this.ofertaRepositorio = ofertaRepositorio;
    }

    public void crearOferta(Oferta oferta) {
        if (oferta.getDescuento() < 0 || oferta.getDescuento() > 1) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 1");
        }
        ofertaRepositorio.guardar(oferta);
    }

    public double aplicarOferta(Alojamiento alojamiento, LocalDateTime fecha) {
        List<Oferta> ofertas = ofertaRepositorio.listarPorAlojamiento(alojamiento.getNombre());
        return ofertas.stream()
                .filter(o -> !fecha.isBefore(o.getFechaInicio()) && !fecha.isAfter(o.getFechaFin()))
                .mapToDouble(Oferta::getDescuento)
                .findFirst()
                .orElse(0.0);
    }

    public List<Oferta> listarOfertasPorAlojamiento(String alojamientoNombre) {
        return ofertaRepositorio.listarPorAlojamiento(alojamientoNombre);
    }
}