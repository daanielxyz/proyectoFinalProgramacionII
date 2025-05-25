package co.edu.uniquindio.poo.proyectofinalprogramacionii.controladores;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Administrador;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioSesionController {
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private Stage stage;
    private final Administrador admin = Administrador.getInstancia();

    @FXML
    private TextField txtEmail, txtContraseña;


    public InicioSesionController() {
    }

    @FXML
    public void initialize() {
        controladorPrincipal.getPlataformaServicio().asignarAdministrador(admin);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }    @FXML
    private void iniciarSesion() {
        try {
            Object persona = ControladorPrincipal.getInstancia().getPlataformaServicio().iniciarSesion(txtEmail.getText(), txtContraseña.getText());
            FXMLLoader loader;
            if (persona instanceof Administrador admin) {
                loader = new FXMLLoader(getClass().getResource("/views/Admin.fxml"));
                AdminController adminController = new AdminController(admin);
                loader.setController(adminController);
                Scene scene = new Scene(loader.load());
                adminController.setStage(stage);
                stage.setScene(scene);
            } else if (persona instanceof Usuario usuario) {
                loader = new FXMLLoader(getClass().getResource("/views/Reserva.fxml"));
                ReservaController reservaController = new ReservaController(usuario);
                loader.setController(reservaController);
                Scene scene = new Scene(loader.load());
                reservaController.setStage(stage);
                stage.setScene(scene);
            } else {
                throw new Exception("Tipo de usuario no reconocido");
            }
            stage.show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }    @FXML
    private void irARegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Registro.fxml"));
            loader.setController(new RegistroController());
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            RegistroController controller = loader.getController();
            controller.setStage(stage);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Error al cargar registro: " + e.getMessage()).show();
        }
    }

    @FXML
    private void irARecuperarContrasena() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CambioContraseña.fxml"));
            CambioContrasenaController controller = new CambioContrasenaController();
            loader.setController(controller);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            controller.setStage(stage);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Error al cargar recuperación de contraseña: " + e.getMessage()).show();
        }
    }
}