package co.edu.uniquindio.poo.proyectofinalprogramacionii.controladores;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Administrador;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios.PlataformaServicio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CambioContrasenaController {
    @FXML
    private TextField emailField, codigoField;
    @FXML
    private PasswordField nuevaContrasenaField;

    private final PlataformaServicio plataformaServicio = ControladorPrincipal.getInstancia().getPlataformaServicio();
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void solicitarCodigo() {
        try {
            String email = emailField.getText();

            if (email.isEmpty()) {
                ControladorPrincipal.getInstancia().crearAlerta("Por favor ingresa tu correo electrónico", Alert.AlertType.WARNING);
                return;
            }

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

            if (email.isEmpty() || codigo.isEmpty() || nuevaContrasena.isEmpty()) {
                ControladorPrincipal.getInstancia().crearAlerta("Por favor completa todos los campos", Alert.AlertType.WARNING);
                return;
            }

            if (email.equals(Administrador.getInstancia().getEmail())) {
                // Manejar administrador
                plataformaServicio.cambiarContraseña(email, codigo, nuevaContrasena);
                Administrador.getInstancia().setContraseña(nuevaContrasena);
            } else {
                plataformaServicio.cambiarContraseña(email, codigo, nuevaContrasena);
            }
            ControladorPrincipal.getInstancia().crearAlerta("Contraseña cambiada con éxito", Alert.AlertType.INFORMATION);

            volverAInicioSesion();
        } catch (Exception e) {
            ControladorPrincipal.getInstancia().crearAlerta("Error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void volverAInicioSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/InicioSesion.fxml"));
            InicioSesionController controller = new InicioSesionController();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            controller.setStage(stage);
            stage.show();
        } catch (IOException e) {
            ControladorPrincipal.getInstancia().crearAlerta("Error al cargar inicio de sesión: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
