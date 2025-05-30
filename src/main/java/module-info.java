module co.edu.uniquindio.poo.proyectofinalprogramacionii {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.compiler;
    requires org.simplejavamail.core;
    requires org.simplejavamail;
    requires com.google.zxing.javase;
    requires com.google.zxing;
    requires jbcrypt;
    requires jakarta.activation;
    requires java.desktop;

    opens co.edu.uniquindio.poo.proyectofinalprogramacionii.controladores to javafx.fxml;
    opens co.edu.uniquindio.poo.proyectofinalprogramacionii to javafx.fxml;
    exports co.edu.uniquindio.poo.proyectofinalprogramacionii;
}