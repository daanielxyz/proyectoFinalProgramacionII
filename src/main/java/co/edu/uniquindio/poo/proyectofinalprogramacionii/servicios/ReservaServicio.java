package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Habitacion.Habitacion;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Hotel;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.ReservaRepositorio;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.EnvioEmail;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.GeneradorQR;

import java.time.LocalDateTime;

public class ReservaServicio {
    private final ReservaRepositorio reservaRepositorio;
    private final BilleteraServicio billeteraServicio;

    public ReservaServicio(ReservaRepositorio reservaRepositorio, BilleteraServicio billeteraServicio) {
        this.reservaRepositorio = reservaRepositorio;
        this.billeteraServicio = billeteraServicio;
    }

    public void realizarReserva(Reserva reserva, Usuario usuario, Habitacion habitacion) throws Exception {
        double noches = Reserva.calcularNochesDeReserva(reserva.getFechaEntrada(), reserva.getFechaSalida());
        double monto;
        Alojamiento alojamiento = reserva.getAlojamientoReservado();

        // Calcular precio según el tipo de alojamiento
        if (alojamiento instanceof Hotel hotel) {
            if (habitacion == null) {
                throw new Exception("Se requiere una habitación para reservar un hotel");
            }
            monto = hotel.getPrecioPorNocheTotal(habitacion) * noches;
        } else {
            monto = alojamiento.getPrecioPorNocheTotal() * noches;
        }

        // Crear factura
        Factura factura = Factura.builder()
                .subtotal(monto)
                .total(monto)
                .fecha(LocalDateTime.now())
                .reserva(reserva)
                .build();

        // Pagar con billetera
        billeteraServicio.pagarReserva(usuario.getBilletera(), monto, reserva);

        // Guardar reserva
        reservaRepositorio.guardar(reserva);

        // Generar QR (para el Día 2)
        String qrData = "Factura ID: " + factura.getId() + "\nMonto: " + factura.getTotal();
        String qrPath = "data/factura_" + factura.getId() + ".png";
        GeneradorQR.generarQR(qrData, qrPath);

        // Enviar correo
        String mensaje = "Reserva realizada con éxito.\nMonto: " + monto + "\nFactura ID: " + factura.getId();
        EnvioEmail.enviarNotificacion(usuario.getEmail(), "Confirmación de Reserva", mensaje);
    }
}