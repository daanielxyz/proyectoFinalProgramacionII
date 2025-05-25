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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class AdminController {
    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private final Administrador admin = Administrador.getInstancia();
    private Stage stage;    @FXML
    private TextField txtNombre, txtDescripcion, txtPrecioBase, txtHuespedesMax, txtCostoMantenimiento, txtHabitacionId, txtHabitacionPrecio, txtHabitacionCapacidad, txtServicios;
    @FXML
    private ComboBox<Ciudad> cbCiudad;
    @FXML
    private ComboBox<String> cbTipoAlojamiento;
    @FXML
    private ListView<Alojamiento> lvAlojamientos;

    @FXML
    private ImageView imgAlojamiento;    @FXML
    private Label lblNombreDetalle, lblTipoDetalle, lblCiudadDetalle, lblDescripcionDetalle,
            lblHuespedesDetalle, lblPrecioDetalle, lblMantenimientoDetalle, lblServiciosDetalle,
            lblHabitacionesTitulo, lblEstadisticas, lblImagenSeleccionada;
    @FXML
    private ListView<Habitacion> lvHabitaciones;


    private String rutaImagenSeleccionada = null;

    public AdminController(Administrador admin) {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }    @FXML
    private void initialize() {
        cbCiudad.setItems(FXCollections.observableArrayList(Ciudad.values()));
        cbTipoAlojamiento.setItems(FXCollections.observableArrayList("Casa", "Apartamento", "Hotel"));
        lvAlojamientos.setItems(FXCollections.observableArrayList(controladorPrincipal.getPlataformaServicio().listarAlojamientos()));

        lvAlojamientos.setCellFactory(listView -> new ListCell<Alojamiento>() {
            @Override
            protected void updateItem(Alojamiento item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });

        lvHabitaciones.setCellFactory(listView -> new ListCell<Habitacion>() {
            @Override
            protected void updateItem(Habitacion item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });

        actualizarAlojamientos();
        controladorPrincipal.getPlataformaServicio().asignarAdministrador(admin);

        // Inicializar panel de detalles vacío
        limpiarPanelDetalles();
    }    @FXML
    private void crearAlojamiento() {
        try {
            String nombre = txtNombre.getText();
            Ciudad ciudad = cbCiudad.getValue();
            String descripcion = txtDescripcion.getText();
            double precioBase = Double.parseDouble(txtPrecioBase.getText());
            int huespedesMax = Integer.parseInt(txtHuespedesMax.getText());
            String servicios = txtServicios.getText();
            String tipo = cbTipoAlojamiento.getValue();

            Alojamiento alojamiento;
            if (tipo.equals("Casa") || tipo.equals("Apartamento")) {
                double costoMantenimiento = Double.parseDouble(txtCostoMantenimiento.getText());
                alojamiento = tipo.equals("Casa")
                        ? new Casa(nombre, ciudad, descripcion, null, precioBase, huespedesMax, servicios, costoMantenimiento)
                        : new Apartamento(nombre, ciudad, descripcion, null, precioBase, huespedesMax, servicios, costoMantenimiento);
            } else {
                alojamiento = new Hotel(nombre, ciudad, descripcion, null, precioBase, huespedesMax, servicios);
            }// Copiar imagen si se ha seleccionado una
            if (rutaImagenSeleccionada != null) {
                copiarImagenAlojamiento(alojamiento, rutaImagenSeleccionada);
            }

            controladorPrincipal.getPlataformaServicio().gestionarAlojamiento(alojamiento, "CREAR", admin);
            new Alert(Alert.AlertType.INFORMATION, "Alojamiento creado exitosamente").show();
            limpiarCampos();
            limpiarPanelDetalles();
            actualizarAlojamientos();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void actualizarAlojamiento() {
        Alojamiento alojamiento = lvAlojamientos.getSelectionModel().getSelectedItem();
        if (alojamiento == null) {
            new Alert(Alert.AlertType.WARNING, "Selecciona un alojamiento").show();
            return;
        }
        try {
            alojamiento.setNombre(txtNombre.getText());
            alojamiento.setCiudad(cbCiudad.getValue());
            alojamiento.setDescripcion(txtDescripcion.getText());
            alojamiento.setPrecioPorNocheBase(Double.parseDouble(txtPrecioBase.getText()));
            alojamiento.setHuespedesMaximos(Integer.parseInt(txtHuespedesMax.getText()));
            alojamiento.setServicios(txtServicios.getText());
            if (alojamiento instanceof Casa casa) {
                casa.setCostoMantenimiento(Double.parseDouble(txtCostoMantenimiento.getText()));
            } else if (alojamiento instanceof Apartamento apartamento) {
                apartamento.setCostoMantenimiento(Double.parseDouble(txtCostoMantenimiento.getText()));
            }

            // Copiar nueva imagen si se ha seleccionado una
            if (rutaImagenSeleccionada != null) {
                copiarImagenAlojamiento(alojamiento, rutaImagenSeleccionada);
            }

            controladorPrincipal.getPlataformaServicio().gestionarAlojamiento(alojamiento, "ACTUALIZAR", admin);
            new Alert(Alert.AlertType.INFORMATION, "Alojamiento actualizado exitosamente").show();
            limpiarCampos();
            limpiarPanelDetalles();
            actualizarAlojamientos();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }@FXML
    private void eliminarAlojamiento() {
        Alojamiento alojamiento = lvAlojamientos.getSelectionModel().getSelectedItem();
        if (alojamiento == null) {
            new Alert(Alert.AlertType.WARNING, "Selecciona un alojamiento").show();
            return;
        }

        // Primera confirmación
        Alert confirmacion1 = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion1.setTitle("Confirmar Eliminación");
        confirmacion1.setHeaderText("¿Estás seguro de eliminar este alojamiento?");
        confirmacion1.setContentText("Alojamiento: " + alojamiento.getNombre() +
                "\nCiudad: " + alojamiento.getCiudad() +
                "\nTipo: " + alojamiento.getClass().getSimpleName());

        if (confirmacion1.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        // Segunda confirmación
        Alert confirmacion2 = new Alert(Alert.AlertType.WARNING);
        confirmacion2.setTitle("Confirmación Final");
        confirmacion2.setHeaderText("ATENCIÓN: Esta acción no se puede deshacer");
        confirmacion2.setContentText("¿Realmente deseas eliminar \"" + alojamiento.getNombre() + "\"?\n\n" +
                "Se perderán todos los datos asociados al alojamiento.");
        confirmacion2.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        if (confirmacion2.showAndWait().orElse(ButtonType.NO) != ButtonType.YES) {
            return;
        }

        try {
            controladorPrincipal.getPlataformaServicio().gestionarAlojamiento(alojamiento, "ELIMINAR", admin);
            new Alert(Alert.AlertType.INFORMATION, "Alojamiento eliminado exitosamente").show();
            limpiarCampos();
            limpiarPanelDetalles();
            actualizarAlojamientos();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void agregarHabitacion() {
        Alojamiento alojamiento = lvAlojamientos.getSelectionModel().getSelectedItem();
        if (!(alojamiento instanceof Hotel hotel)) {
            new Alert(Alert.AlertType.WARNING, "Selecciona un hotel").show();
            return;
        }
        try {
            Habitacion habitacion = new Habitacion(
                    txtHabitacionId.getText(),
                    Double.parseDouble(txtHabitacionPrecio.getText()),
                    Integer.parseInt(txtHabitacionCapacidad.getText())
            );
            hotel.agregarHabitacion(habitacion);
            controladorPrincipal.getPlataformaServicio().gestionarAlojamiento(alojamiento, "ACTUALIZAR", admin);
            new Alert(Alert.AlertType.INFORMATION, "Habitación añadida").show();
            limpiarCampos();
            actualizarAlojamientos();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }    @FXML
    private void seleccionarAlojamiento() {
        Alojamiento alojamiento = lvAlojamientos.getSelectionModel().getSelectedItem();
        if (alojamiento != null) {
            // Llenar los campos del formulario
            txtNombre.setText(alojamiento.getNombre());
            cbCiudad.setValue(alojamiento.getCiudad());
            txtDescripcion.setText(alojamiento.getDescripcion());
            txtPrecioBase.setText(String.valueOf(alojamiento.getPrecioPorNocheBase()));
            txtHuespedesMax.setText(String.valueOf(alojamiento.getHuespedesMaximos()));
            txtServicios.setText(alojamiento.getServicios());
            if (alojamiento instanceof Casa casa) {
                cbTipoAlojamiento.setValue("Casa");
                txtCostoMantenimiento.setText(String.valueOf(casa.getCostoMantenimiento()));
            } else if (alojamiento instanceof Apartamento apartamento) {
                cbTipoAlojamiento.setValue("Apartamento");
                txtCostoMantenimiento.setText(String.valueOf(apartamento.getCostoMantenimiento()));
            } else {
                cbTipoAlojamiento.setValue("Hotel");
                txtCostoMantenimiento.setText("");
            }

            // Actualizar el panel de detalles
            actualizarPanelDetalles(alojamiento);
        }
    }

    private void actualizarAlojamientos() {
        try {
            List<Alojamiento> alojamientos = controladorPrincipal.getPlataformaServicio().consultarAlojamientos(null);
            lvAlojamientos.setItems(FXCollections.observableArrayList(alojamientos));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }    private void limpiarCampos() {
        txtNombre.clear();
        cbCiudad.setValue(null);
        txtDescripcion.clear();
        txtPrecioBase.clear();
        txtHuespedesMax.clear();
        txtServicios.clear();
        txtCostoMantenimiento.clear();
        txtHabitacionId.clear();
        txtHabitacionPrecio.clear();
        txtHabitacionCapacidad.clear();
        cbTipoAlojamiento.setValue(null);
        rutaImagenSeleccionada = null;
        lblImagenSeleccionada.setText("Sin imagen seleccionada");
        lblImagenSeleccionada.setStyle("-fx-text-fill: gray;");
    }

    @FXML
    private void seleccionarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen del Alojamiento");

        // Configurar filtros de extensión
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter(
                "Archivos de imagen", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"
        );
        fileChooser.getExtensionFilters().add(imageFilter);

        // Mostrar el diálogo de selección de archivos
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            rutaImagenSeleccionada = selectedFile.getAbsolutePath();
            lblImagenSeleccionada.setText(selectedFile.getName());
            lblImagenSeleccionada.setStyle("-fx-text-fill: green;");

            // Mostrar vista previa en el panel de detalles si está visible
            try {
                Image previewImage = new Image(selectedFile.toURI().toString());
                imgAlojamiento.setImage(previewImage);
            } catch (Exception e) {
                new Alert(Alert.AlertType.WARNING, "No se pudo cargar la vista previa de la imagen").show();
            }
        }
    }    private void actualizarPanelDetalles(Alojamiento alojamiento) {
        System.out.println("Actualizando panel de detalles para: " + alojamiento.getNombre());

        // Actualizar imagen
        cargarImagenAlojamiento(alojamiento);

        // Información general
        lblNombreDetalle.setText("Nombre: " + alojamiento.getNombre());
        lblCiudadDetalle.setText("Ciudad: " + alojamiento.getCiudad());
        lblDescripcionDetalle.setText("Descripción: " + alojamiento.getDescripcion());

        // Determinar tipo y configurar etiqueta
        String tipo = "";
        if (alojamiento instanceof Casa) {
            tipo = "Casa";
        } else if (alojamiento instanceof Apartamento) {
            tipo = "Apartamento";
        } else if (alojamiento instanceof Hotel) {
            tipo = "Hotel";
        }
        lblTipoDetalle.setText("Tipo: " + tipo);        // Capacidad y precios
        lblHuespedesDetalle.setText("Huéspedes Máximos: " + alojamiento.getHuespedesMaximos());
        lblPrecioDetalle.setText("Precio por Noche: $" + String.format("%.2f", alojamiento.getPrecioPorNocheBase()));

        // Servicios
        String servicios = alojamiento.getServicios();
        if (servicios != null && !servicios.trim().isEmpty()) {
            lblServiciosDetalle.setText("Servicios: " + servicios);
            lblServiciosDetalle.setVisible(true);
        } else {
            lblServiciosDetalle.setText("Servicios: No especificados");
            lblServiciosDetalle.setVisible(true);
        }

        // Costo de mantenimiento para Casa y Apartamento
        if (alojamiento instanceof Casa casa) {
            lblMantenimientoDetalle.setText("Costo Mantenimiento: $" + String.format("%.2f", casa.getCostoMantenimiento()));
            lblMantenimientoDetalle.setVisible(true);
        } else if (alojamiento instanceof Apartamento apartamento) {
            lblMantenimientoDetalle.setText("Costo Mantenimiento: $" + String.format("%.2f", apartamento.getCostoMantenimiento()));
            lblMantenimientoDetalle.setVisible(true);
        } else {
            lblMantenimientoDetalle.setVisible(false);
        }

        // Habitaciones para hoteles
        if (alojamiento instanceof Hotel hotel) {
            lblHabitacionesTitulo.setVisible(true);
            lvHabitaciones.setVisible(true);
            if (hotel.getHabitaciones() != null) {
                lvHabitaciones.setItems(FXCollections.observableArrayList(hotel.getHabitaciones()));
            } else {
                lvHabitaciones.setItems(FXCollections.observableArrayList());
            }
        } else {
            lblHabitacionesTitulo.setVisible(false);
            lvHabitaciones.setVisible(false);
        }

        // Estadísticas básicas (se puede expandir más adelante)
        int reservasCount = 0; // Aquí podrías obtener el número real de reservas
        double promedioReseñas = 0.0; // Aquí podrías calcular el promedio real de reseñas
        lblEstadisticas.setText("Reservas: " + reservasCount + " | Promedio Reseñas: " + String.format("%.1f", promedioReseñas));
    }    private void cargarImagenAlojamiento(Alojamiento alojamiento) {
        try {
            Image imagen = null;

            // Crear nombre de archivo basado en el nombre del alojamiento
            String nombreAlojamiento = alojamiento.getNombre().toLowerCase()
                    .replaceAll("\\s+", "_")  // Reemplazar espacios con guiones bajos
                    .replaceAll("[^a-zA-Z0-9_]", ""); // Remover caracteres especiales

            // Intentar cargar imagen específica con diferentes extensiones
            String[] extensiones = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};

            for (String extension : extensiones) {
                String rutaImagen = "/images/alojamientos/" + nombreAlojamiento + extension;
                try {
                    imagen = new Image(getClass().getResourceAsStream(rutaImagen));
                    if (imagen != null && !imagen.isError()) {
                        System.out.println("Imagen cargada exitosamente: " + rutaImagen);
                        break; // Imagen encontrada, salir del bucle
                    }
                } catch (Exception e) {
                    // Continuar con la siguiente extensión
                }
            }

            // Si no se encontró imagen específica, usar imagen por defecto según el tipo
            if (imagen == null || imagen.isError()) {
                String tipoImagen = "";
                if (alojamiento instanceof Casa) {
                    tipoImagen = "/images/casa_default.jpg";
                } else if (alojamiento instanceof Apartamento) {
                    tipoImagen = "/images/apartamento_default.jpg";
                } else if (alojamiento instanceof Hotel) {
                    tipoImagen = "/images/hotel_default.jpg";
                } else {
                    tipoImagen = "/images/alojamiento_default.jpg";
                }

                try {
                    imagen = new Image(getClass().getResourceAsStream(tipoImagen));
                    System.out.println("Usando imagen por defecto: " + tipoImagen);
                } catch (Exception e) {
                    System.err.println("Error cargando imagen por defecto: " + tipoImagen);
                }

                if (imagen == null || imagen.isError()) {
                    try {
                        imagen = new Image(getClass().getResourceAsStream("/images/alojamiento_default.jpg"));
                        System.out.println("Usando imagen genérica: /images/alojamiento_default.jpg");
                    } catch (Exception e) {
                        System.err.println("Error cargando imagen genérica");
                    }
                }
            }

            // Establecer la imagen en el ImageView
            if (imagen != null && !imagen.isError()) {
                imgAlojamiento.setImage(imagen);
                System.out.println("Imagen establecida correctamente en ImageView");
            } else {
                imgAlojamiento.setImage(null);
                System.err.println("No se pudo cargar ninguna imagen para el alojamiento: " + alojamiento.getNombre());
            }

        } catch (Exception e) {
            System.err.println("Error general cargando imagen: " + e.getMessage());
            imgAlojamiento.setImage(null);
        }
    }    private void limpiarPanelDetalles() {
        imgAlojamiento.setImage(null);
        lblNombreDetalle.setText("Nombre: ");
        lblTipoDetalle.setText("Tipo: ");
        lblCiudadDetalle.setText("Ciudad: ");
        lblDescripcionDetalle.setText("Descripción: ");
        lblHuespedesDetalle.setText("Huéspedes Máximos: ");
        lblPrecioDetalle.setText("Precio por Noche: ");
        lblServiciosDetalle.setText("Servicios: ");
        lblServiciosDetalle.setVisible(false);
        lblMantenimientoDetalle.setText("Costo Mantenimiento: ");
        lblMantenimientoDetalle.setVisible(false);
        lblHabitacionesTitulo.setVisible(false);
        lvHabitaciones.setVisible(false);
        lvHabitaciones.setItems(FXCollections.observableArrayList());
        lblEstadisticas.setText("Selecciona un alojamiento para ver los detalles");
    }

    /**
     * Copia la imagen seleccionada a la carpeta de recursos del proyecto
     */
    private void copiarImagenAlojamiento(Alojamiento alojamiento, String rutaImagenOrigen) {
        try {
            // Crear el directorio de destino si no existe
            Path directorioDestino = Paths.get("src/main/resources/images/alojamientos");
            if (!Files.exists(directorioDestino)) {
                Files.createDirectories(directorioDestino);
            }

            // Crear el nombre del archivo basado en el nombre del alojamiento
            String nombreAlojamiento = alojamiento.getNombre().toLowerCase()
                    .replaceAll("\\s+", "_")  // Reemplazar espacios con guiones bajos
                    .replaceAll("[^a-zA-Z0-9_]", ""); // Remover caracteres especiales

            // Obtener la extensión del archivo original
            File archivoOrigen = new File(rutaImagenOrigen);
            String nombreArchivoOrigen = archivoOrigen.getName();
            String extension = nombreArchivoOrigen.substring(nombreArchivoOrigen.lastIndexOf("."));

            // Crear el path de destino
            Path archivoDestino = directorioDestino.resolve(nombreAlojamiento + extension);

            // Copiar el archivo
            Files.copy(Paths.get(rutaImagenOrigen), archivoDestino, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Imagen copiada exitosamente a: " + archivoDestino.toString());

        } catch (IOException e) {
            System.err.println("Error al copiar la imagen: " + e.getMessage());
            new Alert(Alert.AlertType.WARNING,
                    "No se pudo copiar la imagen, pero el alojamiento fue creado exitosamente.").show();
        }
    }    @FXML
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

    // Métodos públicos para ser llamados desde EditarAlojamientoController
    public void actualizarListaAlojamientos() {
        actualizarAlojamientos();
    }

    public void actualizarPanelDetalles() {
        Alojamiento alojamientoSeleccionado = lvAlojamientos.getSelectionModel().getSelectedItem();
        if (alojamientoSeleccionado != null) {
            actualizarPanelDetalles(alojamientoSeleccionado);
        } else {
            limpiarPanelDetalles();
        }
    }
}