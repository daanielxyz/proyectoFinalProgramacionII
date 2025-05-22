package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Oferta;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Usuario;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios.interfaces.IPlataformaServicio;

import java.time.LocalDateTime;
import java.util.List;

public class PlataformaServicio implements IPlataformaServicio {
    private final UsuarioServicio usuarioServicio;
    private final AlojamientoServicio alojamientoServicio;
    private final ReservaServicio reservaServicio;
    private final OfertaServicio ofertaServicio;
    private final EstadisticaServicio estadisticaServicio;

    public PlataformaServicio() {
        this.usuarioServicio = new UsuarioServicio(new UsuarioRepositorioImpl());
        this.alojamientoServicio = new AlojamientoServicio(new AlojamientoRepositorioImpl());
        this.reservaServicio = new ReservaServicio(new ReservaRepositorioImpl(), new BilleteraServicio(new BilleteraRepositorioImpl()));
        this.ofertaServicio = new OfertaServicio(new OfertaRepositorioImpl());
        this.estadisticaServicio = new EstadisticaServicio(new ReservaRepositorioImpl());
    }

    @Override
    public void registrarUsuario(Usuario usuario, String codigoActivacion) {
        usuarioServicio.registrarUsuario(usuario, codigoActivacion);
    }

    public List<Alojamiento> buscarAlojamientosAleatorios() {
        return alojamientoServicio.buscarAlojamientosAleatorios();
    }

    public void crearOferta(Oferta oferta) {
        ofertaServicio.crearOferta(oferta);
    }

    public List<Oferta> listarOfertasPorAlojamiento(String alojamientoNombre) {
        return ofertaServicio.listarOfertasPorAlojamiento(alojamientoNombre);
    }

    public void solicitarCambioContrasena(String email) {
        usuarioServicio.solicitarCambioContrasena(email);
    }

    public void cambiarContrasena(String email, String codigo, String nuevaContrasena) {
        usuarioServicio.cambiarContrasena(email, codigo, nuevaContrasena);
    }

    public List<Alojamiento> buscarAlojamientos(String nombre, String tipo, String ciudad, Double precioMin, Double precioMax) {
        return alojamientoServicio.buscarAlojamientos(nombre, tipo, ciudad, precioMin, precioMax);
    }

    public double calcularOcupacion(Alojamiento alojamiento, LocalDateTime inicio, LocalDateTime fin) {
        return estadisticaServicio.calcularOcupacion(alojamiento, inicio, fin);
    }

    public double calcularGanancias(Alojamiento alojamiento) {
        return estadisticaServicio.calcularGanancias(alojamiento);
    }

    public List<Alojamiento> listarAlojamientosPopularesPorCiudad(String ciudad) {
        return estadisticaServicio.listarAlojamientosPopularesPorCiudad(ciudad);
    }
}