package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Habitacion.Habitacion;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Hotel;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Factura;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Reserva;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Usuario;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.AlojamientoRepositorioImpl;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.ReservaRepositorioImpl;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.UsuarioRepositorioImpl;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.EnvioEmail;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.GeneradorQR;

import java.time.LocalDateTime;
import java.util.List;

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
        Factura factura = new Factura(monto, monto, LocalDateTime.now(), reserva);        billeteraServicio.pagarReserva(usuario.getBilletera(), monto, reserva);
        alojamiento.getReservasAlojamientoActivas().add(reserva);
        alojamientoRepositorio.actualizar(alojamiento);
        reservaRepositorio.guardar(reserva);
        confirmarReserva(reserva);

        UsuarioRepositorioImpl usuarioRepositorio = new UsuarioRepositorioImpl();
        usuarioRepositorio.actualizarUsuario(usuario);
        String qrData = "Factura ID: " + factura.getId() + "\nMonto: " + factura.getTotal();
        String qrPath = "data/factura_" + factura.getId() + ".png";
        GeneradorQR.generarQR(qrData, qrPath);
        String mensaje = "Reserva realizada con éxito.\nMonto: " + monto + "\nFactura ID: " + factura.getId();
        EnvioEmail.enviarNotificacion(usuario.getEmail(), "Confirmación de Reserva", mensaje);

    }

    public void confirmarReserva(Reserva reserva) throws Exception {

        if (reserva == null || reserva.getUsuario().getEmail() == null || reserva.getId() == null) {
            throw new Exception("La reserva o sus datos no pueden ser nulos.");
        }
        String qrData = "https://bookyourstay.com/reserva/" + reserva.getId();
        String qrPath = System.getProperty("java.io.tmpdir") + "/qr_" + reserva.getId() + ".png";
        String asunto = "Confirmación de Reserva #" + reserva.getId();
        String mensaje = "Hola,\n\n" +
                "Tu reserva ha sido confirmada con los siguientes detalles:\n" +
                "Alojamiento: " + reserva.getAlojamientoReservado().getNombre() + "\n" +
                "ID de Reserva: " + reserva.getId() + "\n" +
                "Fecha de entrada: " + reserva.getFechaEntrada() + "\n" +
                "Fecha de salida: " + reserva.getFechaSalida() + "\n" +
                "Adjunto encontrarás un código QR con la información de tu reserva.";

        EnvioEmail.enviarEmailConQR(
                reserva.getUsuario().getEmail(),
                asunto,
                mensaje,
                qrData,
                qrPath
        );
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