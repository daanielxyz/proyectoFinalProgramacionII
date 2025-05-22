package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Usuario;
import java.util.List;

public interface UsuarioRepositorio {
    void guardar(Usuario usuario);
    Usuario buscarPorEmail(String email);
    void actualizar(Usuario usuario);
    void eliminar(String email);
    List<Usuario> listarTodos();
}