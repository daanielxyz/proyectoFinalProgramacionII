package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Habitacion.Habitacion;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Hotel;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.EnvioEmail;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.GeneradorQR;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ReservaServicio {
    private final ReservaRepositorioImpl reservaRepositorio;
    private final AlojamientoRepositorioImpl alojamientoRepositorio;
    private final BilleteraServicio billeteraServicio;

    public ReservaServicio() {
        this.reservaRepositorio = new ReservaRepositorioImpl();
        this.alojamientoRepositorio = new AlojamientoRepositorioImpl();
        this.billeteraServicio = new BilleteraServicio();
    }

    public void realizarReserva(Reserva reserva, Usuario usuario) throws Exception {
        Alojamiento alojamiento = reserva.getAlojamientoReservado();
        Habitacion habitacion = reserva.getHabitacion();
        for (Reserva activa : alojamiento.getReservasAlojamientoActivas()) {
            if (!(reserva.getFechaSalida().isBefore(activa.getFechaEntrada()) ||
                    reserva.getFechaEntrada().isAfter(activa.getFechaSalida()))) {
                throw new Exception("El alojamiento no está disponible en las fechas seleccionadas");
            }
        }
        if (reserva.getNumeroHuespedes() > alojamiento.getHuespedesMaximos()) {
            throw new Exception("El alojamiento no soporta tantos huéspedes");
        }
        if (habitacion != null && reserva.getNumeroHuespedes() > habitacion.getCapacidad()) {
            throw new Exception("La habitación no soporta tantos huéspedes");
        }
        double noches = Reserva.calcularNochesDeReserva(reserva.getFechaEntrada(), reserva.getFechaSalida());
        double precioBase = alojamiento instanceof Hotel && habitacion != null
                ? ((Hotel) alojamiento).getPrecioPorNocheTotal(habitacion)
                : alojamiento.getPrecioPorNocheTotal();
        double monto = precioBase * noches;
        Factura factura = new Factura(monto, monto, LocalDateTime.now(), reserva);
        billeteraServicio.pagarReserva(usuario.getBilletera(), monto, reserva);
        alojamiento.getReservasAlojamientoActivas().add(reserva);
        alojamientoRepositorio.actualizar(alojamiento);
        reservaRepositorio.guardar(reserva);
        String qrData = "Factura ID: " + factura.getId() + "\nMonto: " + factura.getTotal();
        String qrPath = "data/factura_" + factura.getId() + ".png";
        GeneradorQR.generarQR(qrData, qrPath);
        String mensaje = "Reserva realizada con éxito.\nMonto: " + monto + "\nFactura ID: " + factura.getId();
        EnvioEmail.enviarNotificacion(usuario.getEmail(), "Confirmación de Reserva", mensaje);
    }

    public void cancelarReserva(Reserva reserva, Usuario usuario) throws Exception {
        if (!reserva.getUsuario().equals(usuario)) {
            throw new Exception("No puedes cancelar una reserva de otro usuario");
        }
        Alojamiento alojamiento = reserva.getAlojamientoReservado();
        alojamiento.getReservasAlojamientoActivas().remove(reserva);
        alojamiento.getReservasAlojamientoHistoricas().add(reserva);
        alojamientoRepositorio.actualizar(alojamiento);
        reservaRepositorio.eliminar(reserva);
    }

    public List<Reserva> consultarReservasPorUsuario(Usuario usuario) {
        return reservaRepositorio.listarPorUsuario(usuario);
    }
}