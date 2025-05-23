package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Habitacion.Habitacion;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios.interfaces.IPlataformaServicio;

import java.util.List;
import java.util.UUID;

public class PlataformaServicio implements IPlataformaServicio {
    private final UsuarioServicio usuarioServicio;
    private final ReservaServicio reservaServicio;
    private final AlojamientoServicio alojamientoServicio;
    private final BilleteraServicio billeteraServicio;
    private final OfertaServicio ofertaServicio;

    public PlataformaServicio() {
        this.usuarioServicio = new UsuarioServicio();
        this.reservaServicio = new ReservaServicio();
        this.alojamientoServicio = new AlojamientoServicio();
        this.billeteraServicio = new BilleteraServicio();
        this.ofertaServicio = new OfertaServicio();
    }

    @Override
    public void registrarUsuario(Usuario usuario) throws Exception {
        usuarioServicio.registrarUsuario(usuario);
    }

    @Override
    public void activarCuenta(String email, String codigo) throws Exception {
        usuarioServicio.activarCuenta(email, codigo);
    }

    @Override
    public Object iniciarSesion(String email, String contraseña) throws Exception {
        return usuarioServicio.iniciarSesion(email, contraseña);
    }

    @Override
    public void editarUsuario(Usuario usuario) throws Exception {
        usuarioServicio.editarUsuario(usuario);
    }

    @Override
    public void eliminarUsuario(String email) throws Exception {
        usuarioServicio.eliminarUsuario(email);
    }

    @Override
    public void solicitarCambioContraseña(String email) throws Exception {
        usuarioServicio.solicitarCambioContraseña(email);
    }

    @Override
    public void cambiarContraseña(String email, String codigo, String nuevaContraseña) throws Exception {
        usuarioServicio.cambiarContraseña(email, codigo, nuevaContraseña);
    }

    @Override
    public void recargarBilletera(Usuario usuario, double monto) throws Exception {
        billeteraServicio.recargarBilletera(usuario.getBilletera(), monto);
    }

    @Override
    public void crearReserva(Reserva reserva, Usuario usuario, Habitacion habitacion) throws Exception {
        reservaServicio.realizarReserva(reserva, usuario);
    }

    @Override
    public void cancelarReserva(Reserva reserva, Usuario usuario) throws Exception {
        reservaServicio.cancelarReserva(reserva, usuario);
    }

    @Override
    public List<Reserva> consultarReservasUsuario(Usuario usuario) throws Exception {
        return reservaServicio.consultarReservasPorUsuario(usuario);
    }

    @Override
    public List<Alojamiento> consultarAlojamientos(Ciudad ciudad) throws Exception {
        return alojamientoServicio.consultarAlojamientos(ciudad);
    }

    @Override
    public void gestionarAlojamiento(Alojamiento alojamiento, String accion, Object persona) throws Exception {
        if (!(persona instanceof Administrador)) {
            throw new Exception("Solo los administradores pueden gestionar alojamientos");
        }
        switch (accion.toUpperCase()) {
            case "CREAR":
                alojamientoServicio.crearAlojamiento(alojamiento);
                break;
            case "ACTUALIZAR":
                alojamientoServicio.actualizarAlojamiento(alojamiento);
                break;
            case "ELIMINAR":
                alojamientoServicio.eliminarAlojamiento(alojamiento);
                break;
            default:
                throw new Exception("Acción no válida: " + accion);
        }
    }

    @Override
    public void agregarReseña(Alojamiento alojamiento, String reseña, Usuario usuario) throws Exception {
        if (!haTenidoReserva(alojamiento, usuario)) {
            throw new Exception("Solo puedes reseñar alojamientos donde te hayas hospedado");
        }
        alojamiento.realizarReseña(reseña);
        alojamientoServicio.actualizarAlojamiento(alojamiento);
    }

    @Override
    public void agregarValoracion(Alojamiento alojamiento, int valoracion, Usuario usuario) throws Exception {
        if (!haTenidoReserva(alojamiento, usuario)) {
            throw new Exception("Solo puedes valorar alojamientos donde te hayas hospedado");
        }
        alojamiento.realizarValoracion(valoracion);
        alojamientoServicio.actualizarAlojamiento(alojamiento);
    }

    @Override
    public List<Alojamiento> buscarAlojamientosAleatorios(){
        return alojamientoServicio.buscarAlojamientosAleatorios();
    }

    private boolean haTenidoReserva(Alojamiento alojamiento, Usuario usuario) {
        return alojamiento.getReservasAlojamientoHistoricas().stream()
                .anyMatch(r -> r.getUsuario().equals(usuario));
    }

    @Override
    public List<Oferta> listarOfertasPorAlojamiento(String alojamientoNombre){
        return ofertaServicio.listarOfertasPorAlojamiento(alojamientoNombre);
    }
}