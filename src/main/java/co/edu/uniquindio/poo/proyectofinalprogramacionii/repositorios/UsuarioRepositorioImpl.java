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
            return datos != null ? (List<Usuario>) datos : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void guardarDatos() {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_USUARIOS, usuarios);
        } catch (Exception e) {
        }
    }

    @Override
    public void guardar(Usuario usuario) {
        usuarios.add(usuario);
        guardarDatos();
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return usuarios.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getEmail().equals(usuario.getEmail())) {
                usuarios.set(i, usuario);
                guardarDatos();
                break;
            }
        }
    }

    @Override
    public void eliminar(String email) {
        usuarios.removeIf(u -> u.getEmail().equals(email));
        guardarDatos();
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }
}