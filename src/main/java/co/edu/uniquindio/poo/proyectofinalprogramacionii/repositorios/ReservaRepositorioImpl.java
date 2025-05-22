package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Reserva;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Constantes;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Persistencia;
import java.util.ArrayList;
import java.util.List;

public class ReservaRepositorioImpl implements ReservaRepositorio {
    private List<Reserva> reservas;

    public ReservaRepositorioImpl() {
        this.reservas = leerDatos();
    }

    private List<Reserva> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_RESERVAS);
            return datos != null ? (List<Reserva>) datos : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void guardarDatos() {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_RESERVAS, reservas);
        } catch (Exception e) {
        }
    }

    @Override
    public void guardar(Reserva reserva) {
        reservas.add(reserva);
        guardarDatos();
    }

    @Override
    public Reserva buscarPorId(String id) {
        return reservas.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Reserva> listarTodas() {
        return new ArrayList<>(reservas);
    }
}