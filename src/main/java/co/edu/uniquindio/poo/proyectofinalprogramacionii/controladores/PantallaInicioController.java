package co.edu.uniquindio.poo.proyectofinalprogramacionii.controladores;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Oferta;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.NavegacionVentanas;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.stream.Collectors;

public class PantallaInicioController {
    @FXML
    private ListView<Alojamiento> listaAlojamientos;
    @FXML
    private TextField busquedaField;
    @FXML
    private HBox ofertasBox;

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();

    @FXML
    private void initialize() {
        try {
            // Cargar alojamientos aleatorios
            List<Alojamiento> alojamientos = controladorPrincipal.getPlataformaServicio().buscarAlojamientosAleatorios();
            listaAlojamientos.setItems(FXCollections.observableArrayList(alojamientos));

            // Cargar ofertas
            List<Oferta> ofertas = controladorPrincipal.getPlataformaServicio().listarOfertasPorAlojamiento("");
            for (Oferta oferta : ofertas) {
                Alojamiento alojamiento = controladorPrincipal.getPlataformaServicio().buscarAlojamientosAleatorios()
                        .stream()
                        .filter(a -> a.getNombre().equals(oferta.getAlojamientoNombre()))
                        .findFirst()
                        .orElse(null);
                if (alojamiento != null) {
                    ImageView imageView = new ImageView(alojamiento.getImagen());
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    ofertasBox.getChildren().add(imageView);
                }
            }
        } catch (Exception e) {
            controladorPrincipal.crearAlerta("Error cargando alojamientos u ofertas", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void buscarAlojamientos() {
        try {
            String busqueda = busquedaField.getText().toLowerCase();
            List<Alojamiento> alojamientos = controladorPrincipal.getPlataformaServicio().buscarAlojamientosAleatorios()
                    .stream()
                    .filter(a -> a.getNombre().toLowerCase().contains(busqueda) ||
                            a.getCiudad().toString().toLowerCase().contains(busqueda) ||
                            a.getClass().getSimpleName().toLowerCase().contains(busqueda) ||
                            String.valueOf(a.getPrecioPorNocheTotal()).contains(busqueda))
                    .collect(Collectors.toList());
            listaAlojamientos.setItems(FXCollections.observableArrayList(alojamientos));
        } catch (Exception e) {
            controladorPrincipal.crearAlerta("Error en la búsqueda", Alert.AlertType.ERROR);
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