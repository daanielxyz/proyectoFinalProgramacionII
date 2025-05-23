package co.edu.uniquindio.poo.proyectofinalprogramacionii.utils;

public class EnvioEmail {
    public static void enviarNotificacion(String email, String asunto, String mensaje) {
        System.out.println("Notificación push simulada a " + email + ": " + asunto + " - " + mensaje);
    }
}