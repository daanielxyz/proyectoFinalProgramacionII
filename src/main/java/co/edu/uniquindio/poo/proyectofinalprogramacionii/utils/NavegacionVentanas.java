package co.edu.uniquindio.poo.proyectofinalprogramacionii.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavegacionVentanas {
    public static void cargarVentana(String fxmlPath, String titulo) throws Exception {
        FXMLLoader loader = new FXMLLoader(NavegacionVentanas.class.getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }

    public static void cambiarEscena(Node node, String fxmlPath) throws Exception {
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(NavegacionVentanas.class.getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
    }

    public static void cerrarVentana(Node node) {
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}