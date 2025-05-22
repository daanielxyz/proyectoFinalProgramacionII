package co.edu.uniquindio.poo.proyectofinalprogramacionii.controladores;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.NavegacionVentanas;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.util.List;

public class PantallaInicioController {
    @FXML
    private ListView<Alojamiento> listaAlojamientos;

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @FXML
    private void initialize() {
        try {
            List<Alojamiento> alojamientos = controladorPrincipal.getPlataformaServicio().buscarAlojamientosAleatorios();
            listaAlojamientos.setItems(FXCollections.observableArrayList(alojamientos));
        } catch (Exception e) {
            controladorPrincipal.crearAlerta("Error cargando alojamientos", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void irARegistro() {
        try {
            NavegacionVentanas.cargarVentana("/views/Registro.fxml", "Registro");
        } catch (Exception e) {
            controladorPrincipal.crearAlerta("Error al abrir registro", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void irAInicioSesion() {
        try {
            NavegacionVentanas.cargarVentana("/views/InicioSesion.fxml", "Inicio de Sesión");
        } catch (Exception e) {
            controladorPrincipal.crearAlerta("Error al abrir inicio de sesión", Alert.AlertType.ERROR);
        }
    }
}