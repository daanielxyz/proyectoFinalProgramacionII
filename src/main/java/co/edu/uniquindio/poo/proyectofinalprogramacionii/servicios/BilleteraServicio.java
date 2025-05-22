package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera.Billetera;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Reserva;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.BilleteraRepositorio;

public class BilleteraServicio {
    private final BilleteraRepositorio billeteraRepositorio;

    public BilleteraServicio(BilleteraRepositorio billeteraRepositorio) {
        this.billeteraRepositorio = billeteraRepositorio;
    }

    public void recargarBilletera(Billetera billetera, double monto) {
        billetera.recargarBilletera(monto);
        billeteraRepositorio.actualizar(billetera);
    }

    public void pagarReserva(Billetera billetera, double monto, Reserva reserva) {
        billetera.pagarReserva(monto, reserva);
        billeteraRepositorio.actualizar(billetera);
    }
}