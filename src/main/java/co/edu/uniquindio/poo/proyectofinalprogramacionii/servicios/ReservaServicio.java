package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Habitacion.Habitacion;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Hotel;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.OfertaRepositorioImpl;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.ReservaRepositorio;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.EnvioEmail;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.GeneradorQR;

import java.time.LocalDateTime;
import java.util.List;

public class ReservaServicio {
    private final ReservaRepositorio reservaRepositorio;
    private final BilleteraServicio billeteraServicio;

    public ReservaServicio(ReservaRepositorio reservaRepositorio, BilleteraServicio billeteraServicio) {
        this.reservaRepositorio = reservaRepositorio;
        this.billeteraServicio = billeteraServicio;
    }

    public void realizarReserva(Reserva reserva, Usuario usuario, Habitacion habitacion) throws Exception {
        Alojamiento alojamiento = reserva.getAlojamientoReservado();
        OfertaServicio ofertaServicio = new OfertaServicio(new OfertaRepositorioImpl()); // Inyección manual, idealmente usar DI

        // Validar capacidad
        if (reserva.getNumHuespedes() > alojamiento.getHuespedesMaximos()) {
            throw new Exception("El número de huéspedes excede la capacidad máxima del alojamiento");
        }

        // Validar disponibilidad
        List<Reserva> reservasActivas = alojamiento.getReservasAlojamientoActivas();
        for (Reserva r : reservasActivas) {
            if (!(reserva.getFechaSalida().isBefore(r.getFechaEntrada()) ||
                    reserva.getFechaEntrada().isAfter(r.getFechaSalida()))) {
                throw new Exception("El alojamiento no está disponible en las fechas solicitadas");
            }
        }

        double noches = Reserva.calcularNochesDeReserva(reserva.getFechaEntrada(), reserva.getFechaSalida());
        double monto;

        // Calcular precio según el tipo de alojamiento
        if (alojamiento instanceof Hotel hotel) {
            if (habitacion == null) {
                throw new Exception("Se requiere una habitación para reservar un hotel");
            }
            monto = hotel.getPrecioPorNocheTotal(habitacion) * noches;
        } else {
            monto = alojamiento.getPrecioPorNocheTotal() * noches;
        }

        // Aplicar descuento si hay oferta
        double descuento = ofertaServicio.aplicarOferta(alojamiento, reserva.getFechaEntrada());
        double montoConDescuento = monto * (1 - descuento);

        // Crear factura
        Factura factura = Factura.builder()
                .subtotal(monto)
                .total(montoConDescuento)
                .fecha(LocalDateTime.now())
                .reserva(reserva)
                .build();

        // Pagar con billetera
        billeteraServicio.pagarReserva(usuario.getBilletera(), montoConDescuento, reserva);

        // Generar QR
        String qrData = "Factura ID: " + factura.getId() + "\nMonto: " + factura.getTotal() +
                "\nReserva: " + reserva.getAlojamientoReservado().getNombre();
        String qrPath = "data/factura_" + factura.getId() + ".png";
        GeneradorQR.generarQR(qrData, qrPath);
        factura.setQrPath(qrPath);

        // Guardar reserva
        alojamiento.getReservasAlojamientoActivas().add(reserva);
        reservaRepositorio.guardar(reserva);

        // Enviar correo con QR
        String mensaje = "Reserva realizada con éxito.\nMonto: " + montoConDescuento + "\nFactura ID: " + factura.getId();
        EnvioEmail.enviarNotificacion(usuario.getEmail(), "Confirmación de Reserva", mensaje, qrPath);
    }
}