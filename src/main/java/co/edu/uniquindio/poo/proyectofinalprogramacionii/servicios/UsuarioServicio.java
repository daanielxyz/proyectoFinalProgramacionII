package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera.Billetera;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Usuario;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.UsuarioRepositorio;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.EnvioEmail;

public class UsuarioServicio {
    private final UsuarioRepositorio usuarioRepositorio;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void registrarUsuario(Usuario usuario, String codigoActivacion) {
        if (usuarioRepositorio.buscarPorEmail(usuario.getEmail()) != null) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
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
        if (usuario == null || !usuario.getContrasena().equals(contrasena)) {
            throw new IllegalArgumentException("Email o contraseña incorrectos");
        }
        if (!usuario.isActivo()) {
            throw new IllegalArgumentException("Cuenta no activada");
        }
        return usuario;
    }
}