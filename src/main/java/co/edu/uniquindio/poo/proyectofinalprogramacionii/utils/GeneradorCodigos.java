package co.edu.uniquindio.poo.proyectofinalprogramacionii.utils;

import java.util.Random;

public class GeneradorCodigos {

    private static final Random random = new Random();


    public static String generarCodigoActivacion() {
        int codigo = 100000 + random.nextInt(900000);
        return String.valueOf(codigo);
    }

    public static String generarCodigoCambioContrasena() {
        int codigo = 100000 + random.nextInt(900000);
        return String.valueOf(codigo);
    }
}
