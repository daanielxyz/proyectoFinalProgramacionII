package co.edu.uniquindio.poo.proyectofinalprogramacionii.controladores;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.ReservaRepositorioImpl;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios.EstadisticaServicio;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class EstadisticasController {
    @FXML
    private BarChart<String, Number> ocupacionChart, rentabilidadChart;
    @FXML
    private TextField ciudadField;

    private final ControladorPrincipal controladorPrincipal = ControladorPrincipal.getInstancia();
    private final EstadisticaServicio estadisticaServicio = new EstadisticaServicio(new ReservaRepositorioImpl());

    @FXML
    private void mostrarEstadisticas() {
        try {
            String ciudad = ciudadField.getText();
            LocalDateTime inicio = LocalDateTime.now().minusMonths(1);
            LocalDateTime fin = LocalDateTime.now();

            // Ocupación
            XYChart.Series<String, Number> ocupacionSeries = new XYChart.Series<>();
            ocupacionSeries.setName("Ocupación (%)");
            List<Alojamiento> alojamientos = controladorPrincipal.getPlataformaServicio().buscarAlojamientos(null, null, ciudad, null, null);
            for (Alojamiento alojamiento : alojamientos) {
                double ocupacion = estadisticaServicio.calcularOcupacion(alojamiento, inicio, fin);
                ocupacionSeries.getData().add(new XYChart.Data<>(alojamiento.getNombre(), ocupacion));
            }
            ocupacionChart.getData().clear();
            ocupacionChart.getData().add(ocupacionSeries);

            // Rentabilidad por tipo
            XYChart.Series<String, Number> rentabilidadSeries = new XYChart.Series<>();
            rentabilidadSeries.setName("Ganancias");
            Map<String, Double> rentabilidadPorTipo = estadisticaServicio.calcularRentabilidadPorTipo();
            rentabilidadPorTipo.forEach((tipo, ganancias) ->
                    rentabilidadSeries.getData().add(new XYChart.Data<>(tipo, ganancias)));
            rentabilidadChart.getData().clear();
            rentabilidadChart.getData().add(rentabilidadSeries);
        } catch (Exception e) {
            controladorPrincipal.crearAlerta("Error al mostrar estadísticas: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}