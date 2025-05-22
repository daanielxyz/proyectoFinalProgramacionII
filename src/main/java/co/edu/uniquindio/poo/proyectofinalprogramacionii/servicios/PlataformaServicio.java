package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamiento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Usuario;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios.interfaces.IPlataformaServicio;

import java.util.List;

public class PlataformaServicio implements IPlataformaServicio {
    private final UsuarioServicio usuarioServicio;
    private final AlojamientoServicio alojamientoServicio;
    private final ReservaServicio reservaServicio;

    public PlataformaServicio() {
        this.usuarioServicio = new UsuarioServicio(new UsuarioRepositorioImpl());
        this.alojamientoServicio = new AlojamientoServicio(new AlojamientoRepositorioImpl());
        this.reservaServicio = new ReservaServicio(new ReservaRepositorioImpl(), new BilleteraServicio(new BilleteraRepositorioImpl()));
    }

    @Override
    public void registrarUsuario(Usuario usuario, String codigoActivacion) {
        usuarioServicio.registrarUsuario(usuario, codigoActivacion);
    }

    public List<Alojamiento> buscarAlojamientosAleatorios() {
        return alojamientoServicio.buscarAlojamientosAleatorios();
    }
}