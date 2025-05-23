package co.edu.uniquindio.poo.proyectofinalprogramacionii.controladores;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Administrador;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios.PlataformaServicio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class CambioContrasenaController {
    @FXML
    private TextField emailField, codigoField;
    @FXML
    private PasswordField nuevaContrasenaField;

    private final PlataformaServicio plataformaServicio = ControladorPrincipal.getInstancia().getPlataformaServicio();

    @FXML
    private void solicitarCodigo() {
        try {
            String email = emailField.getText();
            if (email.equals(Administrador.getInstancia().getEmail())) {
                // Manejar administrador
                plataformaServicio.solicitarCambioContraseña(email);
            } else {
                plataformaServicio.solicitarCambioContraseña(email);
            }
            ControladorPrincipal.getInstancia().crearAlerta("Código enviado al correo", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            ControladorPrincipal.getInstancia().crearAlerta("Error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cambiarContrasena() {
        try {
            String email = emailField.getText();
            String codigo = codigoField.getText();
            String nuevaContrasena = nuevaContrasenaField.getText();
            if (email.equals(Administrador.getInstancia().getEmail())) {
                // Manejar administrador
                plataformaServicio.cambiarContraseña(email, codigo, nuevaContrasena);
                Administrador.getInstancia().setContraseña(nuevaContrasena);
            } else {
                plataformaServicio.cambiarContraseña(email, codigo, nuevaContrasena);
            }
            ControladorPrincipal.getInstancia().crearAlerta("Contraseña cambiada con éxito", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            ControladorPrincipal.getInstancia().crearAlerta("Error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}