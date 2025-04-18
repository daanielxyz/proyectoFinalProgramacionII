package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera.Billetera;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Cliente {
    //ATRIBUTOS
        @NonNull private String id;
        @NonNull private String nombre;
        @NonNull private String telefono;
        @NonNull private String correo;
        @NonNull private String contraseña;

    //INSTANCIAS
        private List<Reserva> listaReservasClienteHistoricas;
        private List<Reserva> listaReservasClienteActivas;

    //METODOS DEL USUARIO

        //VALIDAR FECHAS DE RESERVA
            public boolean validarFechasDeReserva(Reserva nuevaReserva) {
                List<Reserva> reservasExistentes = nuevaReserva.getAlojamientoReservado().getReservasAlojamientoActivas();
                return reservasExistentes.stream().anyMatch(r -> nuevaReserva.getFechaEntrada().isBefore(r.getFechaSalida()) && r.getFechaEntrada().isBefore(nuevaReserva.getFechaSalida()));
            }

        //VALIDAR RESERVA
            public void validarReserva(Reserva reserva, Billetera billetera) throws Exception {
                if(validarFechasDeReserva(reserva)) {
                    throw new Exception("Para las fechas indicadas el alojamiento está reservado.");
                } else if(reserva.getFechaEntrada().isAfter(reserva.getFechaSalida())){
                    throw new Exception("La fecha de entrada esta seleccionada para después de la de salida");
                } else if(reserva.getFechaSalida().isBefore(reserva.getFechaEntrada())){
                    throw new Exception("La fecha de salida esta seleccionada para antes de la fecha de entrada");
                } else if(reserva.getNumHuespedes() > reserva.getAlojamientoReservado().getHuespedesMaximos()){
                    throw new Exception("La reserva excede la cantidad maxima de huéspedes permitidos por el alojamiento");
                } else if (billetera.getSaldo() < reserva.getAlojamientoReservado().getPrecioPorNocheTotal() * Reserva.calcularNochesDeReserva(reserva.getFechaEntrada(), reserva.getFechaSalida())){
                    throw new Exception("No hay saldo suficiente para completar la reserva");
                }
            }

        //REALIZAR RESERVA
            public Reserva realizarReserva(Cliente clienteHospedado, Alojamiento alojamientoReservado, LocalDateTime fechaEntrada, LocalDateTime fechaSalida, int numHuespedes) throws Exception {
                Reserva reserva = new Reserva(clienteHospedado, alojamientoReservado, fechaEntrada, fechaSalida, numHuespedes);
                validarReserva(reserva, Plataforma.getInstancia().obtenerBilleteraDeUnCliente(clienteHospedado));
                listaReservasClienteHistoricas.add(reserva);
                listaReservasClienteActivas.add(reserva);
                Plataforma.getInstancia().getListaReservasTotalesHistoricas().add(reserva);
                Plataforma.getInstancia().getListaReservasTotalesActivas().add(reserva);
                alojamientoReservado.getReservasAlojamientoActivas().add(reserva);
                alojamientoReservado.getReservasAlojamientoHistoricas().add(reserva);
                return reserva;
            }

        //CANCELAR RESERVA
            public void cancelarReserva(Reserva reserva) throws Exception{
                if(LocalDateTime.now().isAfter(reserva.getFechaEntrada())){
                    throw new Exception("No se puede cancelar la reserva después de la fecha de entrada estipulada");
                }
                listaReservasClienteHistoricas.remove(reserva);
                listaReservasClienteActivas.remove(reserva);
                Plataforma.getInstancia().getListaReservasTotalesHistoricas().remove(reserva);
                Plataforma.getInstancia().getListaReservasTotalesActivas().remove(reserva);
                reserva.getAlojamientoReservado().getReservasAlojamientoActivas().remove(reserva);
                reserva.getAlojamientoReservado().getReservasAlojamientoHistoricas().remove(reserva);
            }


}
