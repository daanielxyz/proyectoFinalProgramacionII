package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Administrador;

public interface AdministradorRepositorio {
    void guardarAdministrador(Administrador admin);
    Administrador buscarAdministradorPorEmail(String email);
    boolean existeAdmin();
}