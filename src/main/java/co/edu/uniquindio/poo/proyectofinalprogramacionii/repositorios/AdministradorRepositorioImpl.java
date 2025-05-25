package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Administrador;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Constantes;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Persistencia;

import java.util.ArrayList;
import java.util.List;

public class AdministradorRepositorioImpl implements AdministradorRepositorio {
    private List<Administrador> administradores = new ArrayList<>();

    public AdministradorRepositorioImpl() {
        leerDatos();
    }

    @SuppressWarnings("unchecked")
    private void leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_ARCHIVO_ADMINISTRADORES);
            if (datos instanceof ArrayList) {
                administradores = (ArrayList<Administrador>) datos;
            }
        } catch (Exception e) {
            // Error al leer datos, se mantiene la lista vacía
            administradores = new ArrayList<>();
        }
    }    private void guardarDatos() {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_ARCHIVO_ADMINISTRADORES, administradores);
        } catch (Exception e) {
            // Error al guardar datos - en una implementación real se debería loggear
        }
    }

    @Override
    public void guardarAdministrador(Administrador admin) {
        administradores.add(admin);
        guardarDatos();
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