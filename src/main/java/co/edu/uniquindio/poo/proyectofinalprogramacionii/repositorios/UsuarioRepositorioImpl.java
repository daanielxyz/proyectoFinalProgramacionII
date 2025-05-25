package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Usuario;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Constantes;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Persistencia;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorioImpl implements UsuarioRepositorio {
    private List<Usuario> usuarios;

    public UsuarioRepositorioImpl() {
        this.usuarios = leerDatos();
    }

    private List<Usuario> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_USUARIOS);
            @SuppressWarnings("unchecked")
            List<Usuario> lista = datos != null ? (List<Usuario>) datos : new ArrayList<>();
            return lista;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void guardarDatos() {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_USUARIOS, usuarios);
        } catch (Exception e) {
            // Log error
        }
    }    @Override
    public void guardarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        guardarDatos();
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        usuarios.removeIf(u -> u.getEmail().equals(usuario.getEmail()));
        usuarios.add(usuario);
        guardarDatos();
    }

    @Override
    public void eliminarUsuario(String email) {
        usuarios.removeIf(u -> u.getEmail().equals(email));
        guardarDatos();
    }

    @Override
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}