package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera.Transaccion;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Billetera implements Serializable {
    private String id;
    private double saldo;
    private Usuario usuario;
    private List<Transaccion> transacciones;

    public Billetera(String id, Usuario usuario) {
        this.id = id;
        this.saldo = 0.0;
        this.usuario = usuario;
        this.transacciones = new ArrayList<>();
    }

    public void agregarTransaccion(Transaccion transaccion) {
        transacciones.add(transaccion);
    }
}