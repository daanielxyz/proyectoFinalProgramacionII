package co.edu.uniquindio.poo.proyectofinalprogramacionii.utils;

import java.io.File;


public class DataManager {


    public static void inicializarDirectorios() {
        try {
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
                System.out.println("Directorio data creado exitosamente");
            }

            File imagesDir = new File("src/main/resources/images/alojamientos");
            if (!imagesDir.exists()) {
                imagesDir.mkdirs();
                System.out.println("Directorio de imágenes creado exitosamente");
            }

        } catch (Exception e) {
            System.err.println("Error al inicializar directorios: " + e.getMessage());
        }
    }

    public static void verificarArchivos() {
        String[] rutas = {
                Constantes.RUTA_USUARIOS,
                Constantes.RUTA_ALOJAMIENTOS,
                Constantes.RUTA_RESERVAS,
                Constantes.RUTA_BILLETERAS,
                Constantes.RUTA_CIUDADES,
                Constantes.RUTA_RESEÑAS,
                Constantes.RUTA_OFERTAS,
                Constantes.RUTA_ARCHIVO_ADMINISTRADORES
        };

        for (String ruta : rutas) {
            File archivo = new File(ruta);
            if (!archivo.exists()) {
                System.out.println("Archivo de datos no encontrado: " + ruta + " (se creará cuando sea necesario)");
            } else {
                System.out.println("Archivo de datos encontrado: " + ruta);
            }
        }
    }


    public static void cerrarAplicacion() {
        System.out.println("Cerrando aplicación y guardando datos finales...");
    }
}
