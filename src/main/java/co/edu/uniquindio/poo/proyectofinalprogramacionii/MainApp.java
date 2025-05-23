package co.edu.uniquindio.poo.proyectofinalprogramacionii;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.controladores.InicioSesionController;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        PlataformaServicio plataforma = new PlataformaServicio();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/InicioSesion.fxml"));
        loader.setController(new InicioSesionController());
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        InicioSesionController controller = loader.getController();
        controller.setStage(stage);
        stage.setTitle("BookYourStay");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}