package co.edu.uniquindio.poo.proyectofinalprogramacionii;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.controladores.InicioSesionController;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.DataManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Inicializar la gestión de datos
        System.out.println("Inicializando BookYourStay...");
        DataManager.inicializarDirectorios();
        DataManager.verificarArchivos();

        PlataformaServicio plataforma = new PlataformaServicio();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/InicioSesion.fxml"));
        loader.setController(new InicioSesionController());
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        InicioSesionController controller = loader.getController();
        controller.setStage(stage);
        stage.setTitle("BookYourStay");

        // Configurar cierre de aplicación
        stage.setOnCloseRequest(event -> {
            DataManager.cerrarAplicacion();
        });

        stage.show();
        System.out.println("BookYourStay iniciado correctamente con persistencia de datos");
    }

    public static void main(String[] args) {
        launch(args);
    }
}