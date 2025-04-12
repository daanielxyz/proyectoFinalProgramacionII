module co.edu.uniquindio.poo.proyectofinalprogramacionii {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.poo.proyectofinalprogramacionii to javafx.fxml;
    exports co.edu.uniquindio.poo.proyectofinalprogramacionii;
}