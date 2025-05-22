package co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera.Billetera;
import java.util.List;

public interface BilleteraRepositorio {
    void guardar(Billetera billetera);
    Billetera buscarPorPropietario(String emailPropietario);
    void actualizar(Billetera billetera);
    List<Billetera> listarTodas();
}