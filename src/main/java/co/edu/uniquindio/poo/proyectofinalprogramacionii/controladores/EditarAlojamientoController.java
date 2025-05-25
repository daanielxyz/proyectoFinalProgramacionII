package co.edu.uniquindio.poo.proyectofinalprogramacionii.controladores;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Administrador;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Apartamento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Casa;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Habitacion.Habitacion;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Hotel;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Ciudad;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class EditarAlojamientoController implements Initializable {

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private final Administrador admin = Administrador.getInstancia();
    private Stage stage;
    private Alojamiento alojamientoEditando;
    private AdminController adminController;

    @FXML
    private TextField txtNombre, txtPrecioBase, txtHuespedesMax, txtCostoMantenimiento;
    @FXML
    private TextField txtHabitacionId, txtHabitacionPrecio, txtHabitacionCapacidad;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private ComboBox<Ciudad> cbCiudad;
    @FXML
    private ComboBox<String> cbTipoAlojamiento;
    @FXML
    private ListView<Habitacion> lvHabitaciones;
    @FXML
    private ImageView imgVistaPrevia;
    @FXML
    private Label lblImagenSeleccionada;
    @FXML
    private VBox vboxHabitaciones;

    private String rutaImagenSeleccionada = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarComboBoxes();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setAdminController(AdminController adminController) {
        this.adminController = adminController;
    }

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamientoEditando = alojamiento;
        cargarDatosAlojamiento();
    }

    private void configurarComboBoxes() {
        // Configurar ComboBox de ciudades
        cbCiudad.setItems(FXCollections.observableArrayList(Ciudad.values()));

        // Configurar ComboBox de tipos (solo mostrar, no editable)
        cbTipoAlojamiento.setItems(FXCollections.observableArrayList("Hotel", "Casa", "Apartamento"));
    }

    private void cargarDatosAlojamiento() {
        if (alojamientoEditando == null) return;

        // Cargar datos básicos
        txtNombre.setText(alojamientoEditando.getNombre());
        txtDescripcion.setText(alojamientoEditando.getDescripcion());
        txtPrecioBase.setText(String.valueOf(alojamientoEditando.getPrecioPorNocheBase()));
        txtHuespedesMax.setText(String.valueOf(alojamientoEditando.getHuespedesMaximos()));
        cbCiudad.setValue(alojamientoEditando.getCiudad());

        // Configurar según tipo de alojamiento
        if (alojamientoEditando instanceof Hotel) {
            cbTipoAlojamiento.setValue("Hotel");
            txtCostoMantenimiento.setVisible(false);
            vboxHabitaciones.setVisible(true);
            cargarHabitaciones();
        } else if (alojamientoEditando instanceof Casa) {
            cbTipoAlojamiento.setValue("Casa");
            txtCostoMantenimiento.setText(String.valueOf(((Casa) alojamientoEditando).getCostoMantenimiento()));
            txtCostoMantenimiento.setVisible(true);
            vboxHabitaciones.setVisible(false);
        } else if (alojamientoEditando instanceof Apartamento) {
            cbTipoAlojamiento.setValue("Apartamento");
            txtCostoMantenimiento.setText(String.valueOf(((Apartamento) alojamientoEditando).getCostoMantenimiento()));
            txtCostoMantenimiento.setVisible(true);
            vboxHabitaciones.setVisible(false);
        }

        // Cargar imagen
        cargarImagenActual();
    }

    private void cargarHabitaciones() {
        if (alojamientoEditando instanceof Hotel) {
            Hotel hotel = (Hotel) alojamientoEditando;
            lvHabitaciones.setItems(FXCollections.observableArrayList(hotel.getHabitaciones()));
        }
    }

    private void cargarImagenActual() {
        try {
            String rutaImagen = alojamientoEditando.getImagen();
            if (rutaImagen != null && !rutaImagen.isEmpty()) {
                // Intentar cargar desde resources
                String rutaCompleta = "/images/alojamientos/" + rutaImagen;
                Image imagen = new Image(getClass().getResourceAsStream(rutaCompleta));

                if (!imagen.isError()) {
                    imgVistaPrevia.setImage(imagen);
                    lblImagenSeleccionada.setText(rutaImagen);
                } else {
                    // Si no se puede cargar, mostrar imagen por defecto
                    lblImagenSeleccionada.setText("Imagen no encontrada: " + rutaImagen);
                }
            } else {
                lblImagenSeleccionada.setText("Sin imagen");
            }
        } catch (Exception e) {
            lblImagenSeleccionada.setText("Error al cargar imagen");
        }
    }

    @FXML
    private void seleccionarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );

        File archivoSeleccionado = fileChooser.showOpenDialog(stage);
        if (archivoSeleccionado != null) {
            try {
                // Crear nombre único para la imagen
                String extension = obtenerExtension(archivoSeleccionado.getName());
                String nombreArchivo = "aloj_" + System.currentTimeMillis() + extension;

                // Copiar archivo a la carpeta de recursos
                Path destino = Paths.get("src/main/resources/images/alojamientos/" + nombreArchivo);
                Files.createDirectories(destino.getParent());
                Files.copy(archivoSeleccionado.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

                // Actualizar vista previa
                Image imagen = new Image(archivoSeleccionado.toURI().toString());
                imgVistaPrevia.setImage(imagen);
                lblImagenSeleccionada.setText(nombreArchivo);
                rutaImagenSeleccionada = nombreArchivo;

            } catch (IOException e) {
                mostrarAlerta("Error", "No se pudo copiar la imagen: " + e.getMessage());
            }
        }
    }

    @FXML
    private void agregarHabitacion() {
        try {
            String id = txtHabitacionId.getText().trim();
            double precio = Double.parseDouble(txtHabitacionPrecio.getText().trim());
            int capacidad = Integer.parseInt(txtHabitacionCapacidad.getText().trim());

            if (id.isEmpty()) {
                mostrarAlerta("Error", "El ID de la habitación no puede estar vacío");
                return;
            }

            if (alojamientoEditando instanceof Hotel) {
                Hotel hotel = (Hotel) alojamientoEditando;

                // Verificar que no exista una habitación con el mismo ID
                for (Habitacion hab : hotel.getHabitaciones()) {
                    if (hab.getId().equals(id)) {
                        mostrarAlerta("Error", "Ya existe una habitación con el ID: " + id);
                        return;
                    }
                }

                Habitacion nuevaHabitacion = new Habitacion(id, precio, capacidad);
                hotel.getHabitaciones().add(nuevaHabitacion);
                cargarHabitaciones();

                // Limpiar campos
                txtHabitacionId.clear();
                txtHabitacionPrecio.clear();
                txtHabitacionCapacidad.clear();
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor ingrese valores numéricos válidos para precio y capacidad");
        }
    }

    @FXML
    private void eliminarHabitacion() {
        Habitacion habitacionSeleccionada = lvHabitaciones.getSelectionModel().getSelectedItem();
        if (habitacionSeleccionada != null) {
            if (alojamientoEditando instanceof Hotel) {
                Hotel hotel = (Hotel) alojamientoEditando;
                hotel.getHabitaciones().remove(habitacionSeleccionada);
                cargarHabitaciones();
            }
        } else {
            mostrarAlerta("Advertencia", "Por favor seleccione una habitación para eliminar");
        }
    }

    @FXML
    private void guardarCambios() {
        try {
            // Validar campos obligatorios
            if (txtNombre.getText().trim().isEmpty()) {
                mostrarAlerta("Error", "El nombre es obligatorio");
                return;
            }

            // Actualizar datos básicos
            alojamientoEditando.setNombre(txtNombre.getText().trim());
            alojamientoEditando.setDescripcion(txtDescripcion.getText().trim());
            alojamientoEditando.setPrecioPorNocheBase(Double.parseDouble(txtPrecioBase.getText().trim()));
            alojamientoEditando.setHuespedesMaximos(Integer.parseInt(txtHuespedesMax.getText().trim()));
            alojamientoEditando.setCiudad(cbCiudad.getValue());

            // Actualizar imagen si se seleccionó una nueva
            if (rutaImagenSeleccionada != null) {
                alojamientoEditando.setImagen(rutaImagenSeleccionada);
            }

            // Actualizar costo de mantenimiento para Casa y Apartamento
            if (alojamientoEditando instanceof Casa && !txtCostoMantenimiento.getText().trim().isEmpty()) {
                ((Casa) alojamientoEditando).setCostoMantenimiento(Double.parseDouble(txtCostoMantenimiento.getText().trim()));
            } else if (alojamientoEditando instanceof Apartamento && !txtCostoMantenimiento.getText().trim().isEmpty()) {
                ((Apartamento) alojamientoEditando).setCostoMantenimiento(Double.parseDouble(txtCostoMantenimiento.getText().trim()));
            }

            // Guardar cambios en el sistema
            controladorPrincipal.getPlataformaServicio().actualizarAlojamiento(alojamientoEditando);

            // Actualizar la vista del admin
            if (adminController != null) {
                adminController.actualizarListaAlojamientos();
                adminController.actualizarPanelDetalles();
            }

            mostrarAlerta("Éxito", "Alojamiento actualizado correctamente");
            cerrarVentana();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Por favor ingrese valores numéricos válidos");
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo actualizar el alojamiento: " + e.getMessage());
        }
    }

    @FXML
    private void cancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        if (stage != null) {
            stage.close();
        }
    }

    private String obtenerExtension(String nombreArchivo) {
        int ultimoPunto = nombreArchivo.lastIndexOf('.');
        return ultimoPunto > 0 ? nombreArchivo.substring(ultimoPunto) : "";
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
