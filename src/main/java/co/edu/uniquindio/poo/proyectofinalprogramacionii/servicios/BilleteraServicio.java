package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera.Billetera;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera.Transaccion;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Reserva;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.BilleteraRepositorioImpl;

import java.util.UUID;

public class BilleteraServicio {
    private final BilleteraRepositorioImpl billeteraRepositorio;    public BilleteraServicio() {
        this.billeteraRepositorio = new BilleteraRepositorioImpl();
    }

    public void guardarBilletera(Billetera billetera) {
        billeteraRepositorio.guardar(billetera);
    }

    public void recargarBilletera(Billetera billetera, double monto) throws Exception {
        if (monto <= 0) {
            throw new Exception("El monto debe ser positivo");
        }
        billetera.setSaldo(billetera.getSaldo() + monto);
        Transaccion transaccion = new Transaccion(UUID.randomUUID().toString(), monto, "RECARGA", null);
        billetera.agregarTransaccion(transaccion);

        // Actualizar la billetera en el repositorio para persistir los cambios
        billeteraRepositorio.actualizar(billetera);
    }    public void pagarReserva(Billetera billetera, double monto, Reserva reserva) throws Exception {
        if (billetera.getSaldo() < monto) {
            throw new Exception("Saldo insuficiente");
        }
        billetera.setSaldo(billetera.getSaldo() - monto);
        Transaccion transaccion = new Transaccion(UUID.randomUUID().toString(), monto, "PAGO", reserva);
        billetera.agregarTransaccion(transaccion);

        // Actualizar la billetera en el repositorio para persistir los cambios
        billeteraRepositorio.actualizar(billetera);
    }
}