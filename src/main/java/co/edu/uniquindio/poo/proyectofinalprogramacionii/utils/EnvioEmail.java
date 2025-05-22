package co.edu.uniquindio.poo.proyectofinalprogramacionii.utils;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import javax.activation.FileDataSource;
import java.io.IOException;
import java.util.Properties;

public class EnvioEmail {
    private static final Properties config = new Properties();

    static {
        try {
            config.load(EnvioEmail.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Error cargando configuración de correo", e);
        }
    }

    public static void enviarNotificacion(String destinatario, String asunto, String mensaje, String qrPath) {
        EmailBuilder emailBuilder = EmailBuilder.startingBlank()
                .from(config.getProperty("email.username"))
                .to(destinatario)
                .withSubject(asunto)
                .withPlainText(mensaje);

        if (qrPath != null) {
            emailBuilder.withAttachment("FacturaQR.png", new FileDataSource(qrPath));
        }

        Email email = emailBuilder.buildEmail();

        try (Mailer mailer = MailerBuilder
                .withSMTPServer(
                        config.getProperty("email.smtp.host"),
                        Integer.parseInt(config.getProperty("email.smtp.port")),
                        config.getProperty("email.username"),
                        config.getProperty("email.password"))
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {
            mailer.sendMail(email);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error enviando correo: " + e.getMessage());
        }
    }

    public static void enviarNotificacion(String destinatario, String asunto, String mensaje) {
        enviarNotificacion(destinatario, asunto, mensaje, null);
    }
}