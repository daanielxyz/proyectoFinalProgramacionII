package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Cliente;
import lombok.*;



@Data
@RequiredArgsConstructor
public class Transaccion {
    @NonNull private LocalDateTime fecha;
    @NonNull private Billetera destinatario;
    @NonNull private Billetera origen;
    @NonNull private float monto;
    private UUID identificador;

    public String obtenerTipoTransaccion(Cliente clienteActual) {
        return origen.getPropietario().equals(clienteActual) ? "Retiro" : "Depósito";
    }

    public Cliente obtenerUsuarioInvolucrado(Cliente clienteActual) {
        return origen.getPropietario().equals(clienteActual) ? destinatario.getPropietario() : origen.getPropietario();
    }
}