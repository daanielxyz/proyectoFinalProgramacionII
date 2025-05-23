package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera.Billetera;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera.Transaccion;

import java.util.UUID;

public class BilleteraServicio {
    public void recargarBilletera(Billetera billetera, double monto) throws Exception {
        if (monto <= 0) {
            throw new Exception("El monto debe ser positivo");
        }
        billetera.setSaldo(billetera.getSaldo() + monto);
        Transaccion transaccion = new Transaccion(UUID.randomUUID().toString(), monto, "RECARGA", null);
        billetera.agregarTransaccion(transaccion);
    }

    public void pagarReserva(Billetera billetera, double monto, Reserva reserva) throws Exception {
        if (billetera.getSaldo() < monto) {
            throw new Exception("Saldo insuficiente");
        }
        billetera.setSaldo(billetera.getSaldo() - monto);
        Transaccion transaccion = new Transaccion(UUID.randomUUID().toString(), monto, "PAGO", reserva);
        billetera.agregarTransaccion(transaccion);
    }
}