package co.edu.uniquindio.poo.proyectofinalprogramacionii.utils;


import com.google.zxing.WriterException;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import java.io.File;
import java.io.IOException;


public class EnvioEmail {

    private static final String FROM_EMAIL = "proyectoclinicauq@gmail.com";
    private static final String FROM_NAME = "BookYourStay";

    public static void enviarNotificacion(String destinatario, String asunto, String mensaje) throws Exception {

        if (destinatario == null || destinatario.trim().isEmpty() || !destinatario.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new Exception("La dirección de correo del destinatario no es válida.");
        }


        Email email = EmailBuilder.startingBlank()
                .from(FROM_NAME, FROM_EMAIL)
                .to(destinatario)
                .withSubject(asunto)
                .withPlainText(mensaje)
                .buildEmail();


        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, FROM_EMAIL, "gstw yxsp mowl zsce")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer()) {


            mailer.sendMail(email);
        } catch (Exception e) {
            throw new Exception("Error al enviar el correo: " + e.getMessage(), e);
        }
    }

    public static void enviarEmailConQR(String destinatario, String asunto, String mensaje, String qrData, String qrPath) throws Exception {

        if (destinatario == null || destinatario.trim().isEmpty() || !destinatario.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new Exception("La dirección de correo del destinatario no es válida.");
        }


        if (qrData == null || qrData.trim().isEmpty()) {
            throw new Exception("Los datos para el código QR no pueden estar vacíos.");
        }


        File qrFile = new File(qrPath);
        try {
            GeneradorQR.generarQR(qrData, qrPath);
        } catch (WriterException | IOException e) {
            throw new Exception("Error al generar el código QR: " + e.getMessage(), e);
        }


        DataSource source = new FileDataSource(qrFile);

        Email email = EmailBuilder.startingBlank()
                .from(FROM_NAME, FROM_EMAIL)
                .to(destinatario)
                .withSubject(asunto)
                .withPlainText(mensaje)
                .withAttachment("Código QR.png", source)
                .buildEmail();

        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, FROM_EMAIL, "gstw yxsp mowl zsce")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer()) {
            mailer.sendMail(email);
        } catch (Exception e) {
            throw new Exception("Error al enviar el correo con el código QR: " + e.getMessage(), e);
        } finally {
            if (qrFile.exists()) {
                qrFile.delete();
            }
        }
    }
}
