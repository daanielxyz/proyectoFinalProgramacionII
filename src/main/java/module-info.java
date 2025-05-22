module co.edu.uniquindio.poo.proyectofinalprogramacionii {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.compiler;
    requires org.simplejavamail.core;
    requires org.simplejavamail;


    opens co.edu.uniquindio.poo.proyectofinalprogramacionii to javafx.fxml;
    exports co.edu.uniquindio.poo.proyectofinalprogramacionii;
}