package co.edu.uniquindio.poo.proyectofinalprogramacionii;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {


    @Override
    public void start(Stage stage) throws Exception {


        FXMLLoader loader = new FXMLLoader(App.class.getResource("/PantallaInicio.fxml"));
        Parent parent = loader.load();


        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Bienvenido");
        stage.show();
    }


    public static void main(String[] args) {
        launch(App.class, args);
    }
}

