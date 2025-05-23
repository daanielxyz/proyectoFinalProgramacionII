package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Administrador;

import java.util.ArrayList;
import java.util.List;

public class AdministradorRepositorioImpl implements AdministradorRepositorio {
    private final List<Administrador> administradores = new ArrayList<>();

    @Override
    public void guardarAdministrador(Administrador admin) {
        administradores.add(admin);
    }

    @Override
    public Administrador buscarAdministradorPorEmail(String email) {
        return administradores.stream()
                .filter(a -> a.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean existeAdmin() {
        return !administradores.isEmpty();
    }
}