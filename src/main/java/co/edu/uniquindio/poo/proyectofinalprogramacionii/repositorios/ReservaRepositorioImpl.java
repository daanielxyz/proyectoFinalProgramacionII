package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Reserva;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Usuario;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Constantes;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservaRepositorioImpl implements ReservaRepositorio {
    private List<Reserva> reservas;

    public ReservaRepositorioImpl() {
        this.reservas = leerDatos();
    }

    private List<Reserva> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_RESERVAS);
            @SuppressWarnings("unchecked")
            List<Reserva> lista = datos != null ? (List<Reserva>) datos : new ArrayList<>();
            return lista;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void guardarDatos() {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_RESERVAS, reservas);
        } catch (Exception e) {
            // Log error
        }
    }

    @Override
    public void guardar(Reserva reserva) {
        reservas.add(reserva);
        guardarDatos();
    }

    @Override
    public void eliminar(Reserva reserva) {
        reservas.remove(reserva);
        guardarDatos();
    }

    @Override
    public List<Reserva> listarPorUsuario(Usuario usuario) {
        return reservas.stream()
                .filter(r -> r.getUsuario().equals(usuario))
                .collect(Collectors.toList());
    }
}