package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Reserva;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.ReservaRepositorio;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EstadisticaServicio {
    private final ReservaRepositorio reservaRepositorio;

    public EstadisticaServicio(ReservaRepositorio reservaRepositorio) {
        this.reservaRepositorio = reservaRepositorio;
    }

    public double calcularOcupacion(Alojamiento alojamiento, LocalDateTime inicio, LocalDateTime fin) {
        long diasTotales = ChronoUnit.DAYS.between(inicio.toLocalDate(), fin.toLocalDate());
        if (diasTotales <= 0) return 0.0;
        long diasOcupados = reservaRepositorio.listarTodas().stream()
                .filter(r -> r.getAlojamientoReservado().getNombre().equals(alojamiento.getNombre()))
                .filter(r -> !r.getFechaEntrada().isAfter(fin) && !r.getFechaSalida().isBefore(inicio))
                .mapToLong(r -> ChronoUnit.DAYS.between(
                        r.getFechaEntrada().isAfter(inicio) ? r.getFechaEntrada() : inicio,
                        r.getFechaSalida().isBefore(fin) ? r.getFechaSalida() : fin))
                .sum();
        return (double) diasOcupados / diasTotales * 100;
    }

    public double calcularGanancias(Alojamiento alojamiento) {
        return reservaRepositorio.listarTodas().stream()
                .filter(r -> r.getAlojamientoReservado().getNombre().equals(alojamiento.getNombre()))
                .mapToDouble(r -> r.getFactura().getTotal())
                .sum();
    }

    public List<Alojamiento> listarAlojamientosPopularesPorCiudad(String ciudad) {
        return reservaRepositorio.listarTodas().stream()
                .filter(r -> r.getAlojamientoReservado().getCiudad().getNombre().equalsIgnoreCase(ciudad))
                .collect(Collectors.groupingBy(
                        r -> r.getAlojamientoReservado(),
                        Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Map<String, Double> calcularRentabilidadPorTipo() {
        return reservaRepositorio.listarTodas().stream()
                .collect(Collectors.groupingBy(
                        r -> r.getAlojamientoReservado().getClass().getSimpleName(),
                        Collectors.summingDouble(r -> r.getFactura().getTotal())));
    }
}