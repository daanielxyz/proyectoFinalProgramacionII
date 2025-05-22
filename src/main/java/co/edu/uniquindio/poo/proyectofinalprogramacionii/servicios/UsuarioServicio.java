package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera.Billetera;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Usuario;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.EnvioEmail;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UsuarioServicio {
    private final UsuarioRepositorio usuarioRepositorio;
    private final Map<String, String> codigosCambioContrasena = new HashMap<>();

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void registrarUsuario(Usuario usuario, String codigoActivacion) {
        if (usuarioRepositorio.buscarPorEmail(usuario.getEmail()) != null) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        usuario.setContrasena(BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt()));
        usuario.setActivo(false);
        usuario.setBilletera(Billetera.builder().saldo(0).propietario(usuario).build());
        usuarioRepositorio.guardar(usuario);
        EnvioEmail.enviarNotificacion(usuario.getEmail(), "Código de activación", "Tu código es: " + codigoActivacion);
    }

    public void activarUsuario(String email, String codigoActivacion) {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        usuario.setActivo(true);
        usuarioRepositorio.actualizar(usuario);
    }

    public Usuario iniciarSesion(String email, String contrasena) {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        if (usuario == null || !BCrypt.checkpw(contrasena, usuario.getContrasena())) {
            throw new IllegalArgumentException("Email o contraseña incorrectos");
        }
        if (!usuario.isActivo()) {
            throw new IllegalArgumentException("Cuenta no activada");
        }
        return usuario;
    }

    public void solicitarCambioContrasena(String email) {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        String codigo = UUID.randomUUID().toString().substring(0, 6);
        codigosCambioContrasena.put(email, codigo);
        EnvioEmail.enviarNotificacion(email, "Código de cambio de contraseña", "Tu código es: " + codigo);
    }

    public void cambiarContrasena(String email, String codigo, String nuevaContrasena) {
        String codigoAlmacenado = codigosCambioContrasena.get(email);
        if (codigoAlmacenado == null || !codigoAlmacenado.equals(codigo)) {
            throw new IllegalArgumentException("Código de verificación inválido");
        }
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        usuario.setContrasena(BCrypt.hashpw(nuevaContrasena, BCrypt.gensalt()));
        usuarioRepositorio.actualizar(usuario);
        codigosCambioContrasena.remove(email);
    }
}