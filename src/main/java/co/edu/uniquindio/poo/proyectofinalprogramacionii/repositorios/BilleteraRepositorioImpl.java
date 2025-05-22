package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera.Billetera;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Constantes;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.utils.Persistencia;
import java.util.ArrayList;
import java.util.List;

public class BilleteraRepositorioImpl implements BilleteraRepositorio {
    private List<Billetera> billeteras;

    public BilleteraRepositorioImpl() {
        this.billeteras = leerDatos();
    }

    private List<Billetera> leerDatos() {
        try {
            Object datos = Persistencia.deserializarObjeto(Constantes.RUTA_BILLETERAS);
            return datos != null ? (List<Billetera>) datos : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void guardarDatos() {
        try {
            Persistencia.serializarObjeto(Constantes.RUTA_BILLETERAS, billeteras);
        } catch (Exception e) {
        }
    }

    @Override
    public void guardar(Billetera billetera) {
        billeteras.add(billetera);
        guardarDatos();
    }

    @Override
    public Billetera buscarPorPropietario(String emailPropietario) {
        return billeteras.stream()
                .filter(b -> b.getPropietario().getEmail().equals(emailPropietario))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void actualizar(Billetera billetera) {
        for (int i = 0; i < billeteras.size(); i++) {
            if (billeteras.get(i).getPropietario().getEmail().equals(billetera.getPropietario().getEmail())) {
                billeteras.set(i, billetera);
                guardarDatos();
                break;
            }
        }
    }

    @Override
    public List<Billetera> listarTodas() {
        return new ArrayList<>(billeteras);
    }
}