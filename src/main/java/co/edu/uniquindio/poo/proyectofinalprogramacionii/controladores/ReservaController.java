package co.edu.uniquindio.poo.proyectofinalprogramacionii.controladores;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Habitacion.Habitacion;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Hotel;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Ciudad;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Reserva;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReservaController {
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private final Usuario usuario;
    private Stage stage;    @FXML
    private ComboBox<String> cbCiudad;
    @FXML
    private ListView<Alojamiento> lvAlojamientos;
    @FXML
    private ListView<Habitacion> lvHabitaciones;
    @FXML
    private DatePicker dpFechaEntrada, dpFechaSalida;
    @FXML
    private TextField txtHuespedes, txtReseña, txtValoracion;
    @FXML
    private ListView<Reserva> lvReservas;
    @FXML
    private Label lblSaldo;

    public ReservaController(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }    @FXML
    private void initialize() {
        // Crear lista con opción "TODAS LAS CIUDADES" y los valores del enum
        List<String> opcionesCiudad = new ArrayList<>();
        opcionesCiudad.add("TODAS LAS CIUDADES");
        for (Ciudad ciudad : Ciudad.values()) {
            opcionesCiudad.add(ciudad.toString());
        }
        cbCiudad.setItems(FXCollections.observableArrayList(opcionesCiudad));

        // Agregar listener para filtrado automático al cambiar selección
        cbCiudad.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filtrarAlojamientosPorSeleccion(newValue);
            }
        });
        // Establecer "TODAS LAS CIUDADES" como selección inicial
        cbCiudad.getSelectionModel().select("TODAS LAS CIUDADES");

        lblSaldo.setText("Saldo: $" + usuario.getBilletera().getSaldo());
        actualizarReservas();
        cargarTodosLosAlojamientos();
    }

    @FXML
    private void buscarAlojamientos() {
        String ciudadSeleccionada = cbCiudad.getValue();
        if (ciudadSeleccionada == null) {
            new Alert(Alert.AlertType.WARNING, "Selecciona una opción").show();
            return;
        }
        filtrarAlojamientosPorSeleccion(ciudadSeleccionada);
    }

    @FXML
    private void mostrarHabitaciones() {
        Alojamiento alojamiento = lvAlojamientos.getSelectionModel().getSelectedItem();
        if (alojamiento instanceof Hotel hotel) {
            lvHabitaciones.setItems(FXCollections.observableArrayList(hotel.getHabitaciones()));
        } else {
            lvHabitaciones.setItems(FXCollections.observableArrayList());
        }
    }

    @FXML
    private void crearReserva() {
        try {
            Alojamiento alojamiento = lvAlojamientos.getSelectionModel().getSelectedItem();
            Habitacion habitacion = lvHabitaciones.getSelectionModel().getSelectedItem();
            LocalDate fechaEntrada = dpFechaEntrada.getValue();
            LocalDate fechaSalida = dpFechaSalida.getValue();
            int huespedes = Integer.parseInt(txtHuespedes.getText());

            if (alojamiento == null || fechaEntrada == null || fechaSalida == null || huespedes <= 0) {
                throw new Exception("Completa todos los campos");
            }
            if (fechaEntrada.isBefore(LocalDate.now()) || fechaSalida.isBefore(fechaEntrada)) {
                throw new Exception("Fechas inválidas");
            }
            if (alojamiento instanceof Hotel && habitacion == null) {
                throw new Exception("Selecciona una habitación para el hotel");
            }

            Reserva reserva = new Reserva(
                    UUID.randomUUID().toString(),
                    fechaEntrada,
                    fechaSalida,
                    usuario,
                    alojamiento,
                    huespedes,
                    habitacion
            );
            controladorPrincipal.getPlataformaServicio().crearReserva(reserva, usuario, habitacion);
            new Alert(Alert.AlertType.INFORMATION, "Reserva creada").show();
            lblSaldo.setText("Saldo: $" + usuario.getBilletera().getSaldo());
            actualizarReservas();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void cancelarReserva() {
        Reserva reserva = lvReservas.getSelectionModel().getSelectedItem();
        if (reserva == null) {
            new Alert(Alert.AlertType.WARNING, "Selecciona una reserva").show();
            return;
        }
        try {
            controladorPrincipal.getPlataformaServicio().cancelarReserva(reserva, usuario);
            new Alert(Alert.AlertType.INFORMATION, "Reserva cancelada").show();
            actualizarReservas();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void agregarReseña() {
        Alojamiento alojamiento = lvAlojamientos.getSelectionModel().getSelectedItem();
        String reseña = txtReseña.getText();
        if (alojamiento == null || reseña.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Selecciona un alojamiento y escribe una reseña").show();
            return;
        }
        try {
            controladorPrincipal.getPlataformaServicio().agregarReseña(alojamiento, reseña, usuario);
            new Alert(Alert.AlertType.INFORMATION, "Reseña añadida").show();
            txtReseña.clear();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void agregarValoracion() {
        Alojamiento alojamiento = lvAlojamientos.getSelectionModel().getSelectedItem();
        String valoracionText = txtValoracion.getText();
        if (alojamiento == null || valoracionText.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Selecciona un alojamiento y escribe una valoración").show();
            return;
        }
        try {
            int valoracion = Integer.parseInt(valoracionText);
            if (valoracion < 1 || valoracion > 5) {
                throw new Exception("La valoración debe estar entre 1 y 5");
            }
            controladorPrincipal.getPlataformaServicio().agregarValoracion(alojamiento, valoracion, usuario);
            new Alert(Alert.AlertType.INFORMATION, "Valoración añadida").show();
            txtValoracion.clear();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void recargarBilletera() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Recargar Billetera");
        dialog.setContentText("Monto:");
        dialog.showAndWait().ifPresent(montoText -> {
            try {
                double monto = Double.parseDouble(montoText);
                controladorPrincipal.getPlataformaServicio().recargarBilletera(usuario, monto);
                new Alert(Alert.AlertType.INFORMATION, "Billetera recargada").show();
                lblSaldo.setText("Saldo: $" + usuario.getBilletera().getSaldo());
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        });
    }
    private void actualizarReservas() {
        try {
            List<Reserva> reservas = controladorPrincipal.getPlataformaServicio().consultarReservasUsuario(usuario);
            lvReservas.setItems(FXCollections.observableArrayList(reservas));
            lvReservas.refresh();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void cargarTodosLosAlojamientos() {
        try {
            List<Alojamiento> alojamientos = controladorPrincipal.getPlataformaServicio().consultarAlojamientos(null);
            lvAlojamientos.setItems(FXCollections.observableArrayList(alojamientos));
            lvHabitaciones.setItems(FXCollections.observableArrayList());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void filtrarAlojamientosPorSeleccion(String ciudadSeleccionada) {
        try {
            List<Alojamiento> alojamientos;
            if ("TODAS LAS CIUDADES".equals(ciudadSeleccionada)) {
                // Mostrar todos los alojamientos
                alojamientos = controladorPrincipal.getPlataformaServicio().consultarAlojamientos(null);
            } else {
                // Convertir el string a enum Ciudad y buscar por ciudad específica
                Ciudad ciudad = Ciudad.valueOf(ciudadSeleccionada);
                alojamientos = controladorPrincipal.getPlataformaServicio().consultarAlojamientos(ciudad);
            }
            lvAlojamientos.setItems(FXCollections.observableArrayList(alojamientos));
            lvHabitaciones.setItems(FXCollections.observableArrayList());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error al filtrar alojamientos: " + e.getMessage()).show();
        }
    }

    @FXML
    private void cerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/InicioSesion.fxml"));
            loader.setController(new InicioSesionController());
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            InicioSesionController controller = loader.getController();
            controller.setStage(stage);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Error al cerrar sesión: " + e.getMessage()).show();
        }
    }
}