package co.edu.uniquindio.poo.proyectofinalprogramacionii.utils;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

public class EnvioEmail {
    private static final String EMAIL = "proyectoclinicauq@gmail.com";
    private static final String CONTRASENA = "dwhz vggn teek llfc";

    public static void enviarNotificacion(String destinatario, String asunto, String mensaje) {
        Email email = EmailBuilder.startingBlank()
                .from(EMAIL)
                .to(destinatario)
                .withSubject(asunto)
                .withPlainText(mensaje)
                .buildEmail();

        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, EMAIL, CONTRASENA)
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {
            mailer.sendMail(email);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error enviando correo: " + e.getMessage());
        }
    }
}