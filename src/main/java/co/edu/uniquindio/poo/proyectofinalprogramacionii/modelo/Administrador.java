package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Administrador implements Serializable {
    private static Administrador instancia;
    private String email = "admin@bookyourstay.com";
    private String contraseña = "admin123";
    private String codigoActivacion;
    private boolean cuentaActiva = true;

    public Administrador(String email, String contraseña, boolean cuentaActiva) {
        this.email = email;
        this.contraseña = contraseña;
    }


    public static Administrador getInstancia() {
        if (instancia == null) {
            instancia = new Administrador("admin@bookyourstay.com", "admin123", true);
        }
        return instancia;
    }


}