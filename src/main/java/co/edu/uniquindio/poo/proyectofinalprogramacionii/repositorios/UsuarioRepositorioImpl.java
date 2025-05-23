package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorioImpl implements UsuarioRepositorio {
    private final List<Usuario> usuarios = new ArrayList<>();

    @Override
    public void guardarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        usuarios.removeIf(u -> u.getEmail().equals(usuario.getEmail()));
        usuarios.add(usuario);
    }

    @Override
    public void eliminarUsuario(String email) {
        usuarios.removeIf(u -> u.getEmail().equals(email));
    }

    @Override
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}