package co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios.interfaces;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Usuario;

public interface IPlataformaServicio {
    void registrarUsuario(Usuario usuario, String codigoActivacion);
}