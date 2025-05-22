package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import java.io.Serializable;

public class Administrador implements Serializable {
    private static Administrador instancia;
    private String email = "admin@bookyourstay.com";
    private String contrasena = "admin123";

    private Administrador() {}

    public static Administrador getInstancia() {
        if (instancia == null) {
            instancia = new Administrador();
        }
        return instancia;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}