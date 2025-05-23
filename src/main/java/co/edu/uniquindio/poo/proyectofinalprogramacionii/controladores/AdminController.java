package co.edu.uniquindio.poo.proyectofinalprogramacionii.controladores;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Apartamento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Casa;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Habitacion.Habitacion;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Hotel;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios.interfaces.IPlataformaServicio;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class AdminController {
    private final IPlataformaServicio plataformaServicio;
    private final Administrador admin;

    @FXML
    private TextField txtNombre, txtDescripcion, txtPrecioBase, txtHuespedesMax, txtCostoMantenimiento, txtHabitacionId, txtHabitacionPrecio, txtHabitacionCapacidad;
    @FXML
    private ComboBox<Ciudad> cbCiudad;
    @FXML
    private ComboBox<String> cbTipoAlojamiento;
    @FXML
    private ListView<Alojamiento> lvAlojamientos;

    public AdminController(IPlataformaServicio plataformaServicio, Administrador admin) {
        this.plataformaServicio = plataformaServicio;
        this.admin = admin;
    }

    @FXML
    private void initialize() {
        cbCiudad.setItems(FXCollections.observableArrayList(Ciudad.values()));
        cbTipoAlojamiento.setItems(FXCollections.observableArrayList("Casa", "Apartamento", "Hotel"));
        actualizarAlojamientos();
    }

    @FXML
    private void crearAlojamiento() {
        try {
            String nombre = txtNombre.getText();
            Ciudad ciudad = cbCiudad.getValue();
            String descripcion = txtDescripcion.getText();
            double precioBase = Double.parseDouble(txtPrecioBase.getText());
            int huespedesMax = Integer.parseInt(txtHuespedesMax.getText());
            String tipo = cbTipoAlojamiento.getValue();

            Alojamiento alojamiento;
            if (tipo.equals("Casa") || tipo.equals("Apartamento")) {
                double costoMantenimiento = Double.parseDouble(txtCostoMantenimiento.getText());
                alojamiento = tipo.equals("Casa")
                        ? new Casa(nombre, ciudad, descripcion, null, precioBase, huespedesMax, costoMantenimiento)
                        : new Apartamento(nombre, ciudad, descripcion, null, precioBase, huespedesMax, costoMantenimiento);
            } else {
                alojamiento = new Hotel(nombre, ciudad, descripcion, null, precioBase, huespedesMax);
            }

            plataformaServicio.gestionarAlojamiento(alojamiento, "CREAR", admin);
            new Alert(Alert.AlertType.INFORMATION, "Alojamiento creado").show();
            limpiarCampos();
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
            if (alojamiento instanceof Casa casa) {
                casa.setCostoMantenimiento(Double.parseDouble(txtCostoMantenimiento.getText()));
            } else if (alojamiento instanceof Apartamento apartamento) {
                apartamento.setCostoMantenimiento(Double.parseDouble(txtCostoMantenimiento.getText()));
            }
            plataformaServicio.gestionarAlojamiento(alojamiento, "ACTUALIZAR", admin);
            new Alert(Alert.AlertType.INFORMATION, "Alojamiento actualizado").show();
            limpiarCampos();
            actualizarAlojamientos();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void eliminarAlojamiento() {
        Alojamiento alojamiento = lvAlojamientos.getSelectionModel().getSelectedItem();
        if (alojamiento == null) {
            new Alert(Alert.AlertType.WARNING, "Selecciona un alojamiento").show();
            return;
        }
        try {
            plataformaServicio.gestionarAlojamiento(alojamiento, "ELIMINAR", admin);
            new Alert(Alert.AlertType.INFORMATION, "Alojamiento eliminado").show();
            limpiarCampos();
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
            plataformaServicio.gestionarAlojamiento(alojamiento, "ACTUALIZAR", admin);
            new Alert(Alert.AlertType.INFORMATION, "Habitación añadida").show();
            limpiarCampos();
            actualizarAlojamientos();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void seleccionarAlojamiento() {
        Alojamiento alojamiento = lvAlojamientos.getSelectionModel().getSelectedItem();
        if (alojamiento != null) {
            txtNombre.setText(alojamiento.getNombre());
            cbCiudad.setValue(alojamiento.getCiudad());
            txtDescripcion.setText(alojamiento.getDescripcion());
            txtPrecioBase.setText(String.valueOf(alojamiento.getPrecioPorNocheBase()));
            txtHuespedesMax.setText(String.valueOf(alojamiento.getHuespedesMaximos()));
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
        }
    }

    private void actualizarAlojamientos() {
        try {
            List<Alojamiento> alojamientos = plataformaServicio.consultarAlojamientos(null);
            lvAlojamientos.setItems(FXCollections.observableArrayList(alojamientos));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        cbCiudad.setValue(null);
        txtDescripcion.clear();
        txtPrecioBase.clear();
        txtHuespedesMax.clear();
        txtCostoMantenimiento.clear();
        txtHabitacionId.clear();
        txtHabitacionPrecio.clear();
        txtHabitacionCapacidad.clear();
        cbTipoAlojamiento.setValue(null);
    }
}