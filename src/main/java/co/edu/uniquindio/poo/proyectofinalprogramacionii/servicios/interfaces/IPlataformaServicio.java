package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios.interfaces;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Habitacion.Habitacion;

import java.util.List;

public interface IPlataformaServicio {
    void registrarUsuario(Usuario usuario) throws Exception;
    void activarCuenta(String email, String codigo) throws Exception;
    Object iniciarSesion(String email, String contraseña) throws Exception;
    void editarUsuario(Usuario usuario) throws Exception;
    void eliminarUsuario(String email) throws Exception;
    void solicitarCambioContraseña(String email) throws Exception;
    void cambiarContraseña(String email, String codigo, String nuevaContraseña) throws Exception;
    void recargarBilletera(Usuario usuario, double monto) throws Exception;
    void crearReserva(Reserva reserva, Usuario usuario, Habitacion habitacion) throws Exception;
    void cancelarReserva(Reserva reserva, Usuario usuario) throws Exception;
    List<Reserva> consultarReservasUsuario(Usuario usuario) throws Exception;
    List<Alojamiento> consultarAlojamientos(Ciudad ciudad) throws Exception;
    void gestionarAlojamiento(Alojamiento alojamiento, String accion, Object persona) throws Exception;
    void agregarReseña(Alojamiento alojamiento, String reseña, Usuario usuario) throws Exception;
    void agregarValoracion(Alojamiento alojamiento, int valoracion, Usuario usuario) throws Exception;
    List<Alojamiento> buscarAlojamientosAleatorios();
    List<Oferta> listarOfertasPorAlojamiento(String alojamientoNombre);
}