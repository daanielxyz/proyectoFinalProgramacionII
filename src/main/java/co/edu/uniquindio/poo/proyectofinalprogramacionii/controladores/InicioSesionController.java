package co.edu.uniquindio.poo.proyectofinalprogramacionii.controladores;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios.PlataformaServicio;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios.interfaces.IPlataformaServicio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioSesionController {
    private final PlataformaServicio plataformaServicio;
    private Stage stage;

    @FXML
    private TextField txtEmail, txtContraseña;

    public InicioSesionController() {
        this.plataformaServicio = ControladorPrincipal.getInstancia().getPlataformaServicio();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void iniciarSesion() {
        try {
            Object persona = plataformaServicio.iniciarSesion(txtEmail.getText(), txtContraseña.getText());
            FXMLLoader loader;
            if (persona instanceof Administrador admin) {
                loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/proyectofinalprogramacionii/Admin.fxml"));
                loader.setController(new AdminController(plataformaServicio, admin));
            } else if (persona instanceof Usuario usuario) {
                loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/proyectofinalprogramacionii/Reserva.fxml"));
                loader.setController(new ReservaController(plataformaServicio, usuario));
            } else {
                throw new Exception("Tipo de usuario no reconocido");
            }
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void irARegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/poo/proyectofinalprogramacionii/Registro.fxml"));
            loader.setController(new RegistroController(plataformaServicio));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            RegistroController controller = loader.getController();
            controller.setStage(stage);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Error al cargar registro: " + e.getMessage()).show();
        }
    }
}