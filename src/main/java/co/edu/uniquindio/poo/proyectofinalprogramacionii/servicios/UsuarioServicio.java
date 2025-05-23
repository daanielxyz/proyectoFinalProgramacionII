package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.EnvioEmail;

import java.util.UUID;

public class UsuarioServicio {
    private final UsuarioRepositorioImpl usuarioRepositorio;
    private final AdministradorRepositorioImpl administradorRepositorio;
    private final BilleteraServicio billeteraServicio;

    public UsuarioServicio() {
        this.usuarioRepositorio = new UsuarioRepositorioImpl();
        this.administradorRepositorio = new AdministradorRepositorioImpl();
        this.billeteraServicio = new BilleteraServicio();
    }

    public void registrarUsuario(Usuario usuario) throws Exception {
        if (usuarioRepositorio.buscarUsuarioPorEmail(usuario.getEmail()) != null ||
                administradorRepositorio.buscarAdministradorPorEmail(usuario.getEmail()) != null) {
            throw new Exception("El email ya está registrado");
        }
        String codigoActivacion = UUID.randomUUID().toString();
        usuario.setCodigoActivacion(codigoActivacion);
        usuarioRepositorio.guardarUsuario(usuario);
        EnvioEmail.enviarNotificacion(usuario.getEmail(), "Activación de Cuenta",
                "Tu código de activación es: " + codigoActivacion);
    }

    public void registrarAdministrador(Administrador admin) throws Exception {
        if (administradorRepositorio.existeAdmin()) {
            throw new Exception("Ya existe un administrador");
        }
        if (usuarioRepositorio.buscarUsuarioPorEmail(admin.getEmail()) != null ||
                administradorRepositorio.buscarAdministradorPorEmail(admin.getEmail()) != null) {
            throw new Exception("El email ya está registrado");
        }
        administradorRepositorio.guardarAdministrador(admin);
    }

    public void activarCuenta(String email, String codigo) throws Exception {
        Usuario usuario = usuarioRepositorio.buscarUsuarioPorEmail(email);
        if (usuario == null) {
            throw new Exception("Usuario no encontrado");
        }
        if (!usuario.getCodigoActivacion().equals(codigo)) {
            throw new Exception("Código de activación incorrecto");
        }
        usuario.setCuentaActiva(true);
        usuarioRepositorio.actualizarUsuario(usuario);
    }

    public Object iniciarSesion(String email, String contraseña) throws Exception {
        Usuario usuario = usuarioRepositorio.buscarUsuarioPorEmail(email);
        if (usuario != null) {
            if (!usuario.getContraseña().equals(contraseña)) {
                throw new Exception("Credenciales incorrectas");
            }
            if (!usuario.isCuentaActiva()) {
                throw new Exception("La cuenta no está activada");
            }
            return usuario;
        }
        Administrador admin = administradorRepositorio.buscarAdministradorPorEmail(email);
        if (admin != null) {
            if (!admin.getContraseña().equals(contraseña)) {
                throw new Exception("Credenciales incorrectas");
            }
            return admin;
        }
        throw new Exception("Credenciales incorrectas");
    }

    public void editarUsuario(Usuario usuario) throws Exception {
        if (usuarioRepositorio.buscarUsuarioPorEmail(usuario.getEmail()) == null) {
            throw new Exception("Usuario no encontrado");
        }
        usuarioRepositorio.actualizarUsuario(usuario);
    }

    public void eliminarUsuario(String email) throws Exception {
        if (usuarioRepositorio.buscarUsuarioPorEmail(email) == null) {
            throw new Exception("Usuario no encontrado");
        }
        usuarioRepositorio.eliminarUsuario(email);
    }

    public void solicitarCambioContraseña(String email) throws Exception {
        Usuario usuario = usuarioRepositorio.buscarUsuarioPorEmail(email);
        Administrador admin = administradorRepositorio.buscarAdministradorPorEmail(email);
        if (usuario == null && admin == null) {
            throw new Exception("Usuario no encontrado");
        }
        String codigo = UUID.randomUUID().toString();
        if (usuario != null) {
            usuario.setCodigoActivacion(codigo);
            usuarioRepositorio.actualizarUsuario(usuario);
        } else {
            admin.setCodigoActivacion(codigo);
            administradorRepositorio.guardarAdministrador(admin);
        }
        EnvioEmail.enviarNotificacion(email, "Cambio de Contraseña",
                "Tu código para cambiar contraseña es: " + codigo);
    }

    public void cambiarContraseña(String email, String codigo, String nuevaContraseña) throws Exception {
        Usuario usuario = usuarioRepositorio.buscarUsuarioPorEmail(email);
        Administrador admin = administradorRepositorio.buscarAdministradorPorEmail(email);
        if (usuario == null && admin == null) {
            throw new Exception("Usuario no encontrado");
        }
        if (usuario != null) {
            if (!usuario.getCodigoActivacion().equals(codigo)) {
                throw new Exception("Código incorrecto");
            }
            usuario.setContraseña(nuevaContraseña);
            usuario.setCodigoActivacion(null);
            usuarioRepositorio.actualizarUsuario(usuario);
        } else {
            if (!admin.getCodigoActivacion().equals(codigo)) {
                throw new Exception("Código incorrecto");
            }
            admin.setContraseña(nuevaContraseña);
            admin.setCodigoActivacion(null);
            administradorRepositorio.guardarAdministrador(admin);
        }
    }
}