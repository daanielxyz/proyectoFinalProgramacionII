package co.edu.uniquindio.poo.proyectofinalprogramacionii.controladores;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Usuario;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios.interfaces.IPlataformaServicio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroController {
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private Stage stage;

    @FXML
    private TextField txtCedula, txtNombre, txtTelefono, txtEmail, txtContraseña, txtCodigo;

    public RegistroController() {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void registrar() {
        try {
            Usuario usuario = new Usuario(
                    txtCedula.getText(),
                    txtNombre.getText(),
                    txtTelefono.getText(),
                    txtEmail.getText(),
                    txtContraseña.getText()
            );
            controladorPrincipal.getPlataformaServicio().registrarUsuario(usuario);
            new Alert(Alert.AlertType.INFORMATION, "Usuario registrado. Usa el código de activación: " + usuario.getCodigoActivacion()).show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void activarCuenta() {
        try {
            controladorPrincipal.getPlataformaServicio().activarCuenta(txtEmail.getText(), txtCodigo.getText());
            new Alert(Alert.AlertType.INFORMATION, "Cuenta activada").show();
            volverAInicio();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void volverAInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/InicioSesion.fxml"));
            loader.setController(new InicioSesionController());
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            InicioSesionController controller = loader.getController();
            controller.setStage(stage);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Error al cargar inicio: " + e.getMessage()).show();
        }
    }
}