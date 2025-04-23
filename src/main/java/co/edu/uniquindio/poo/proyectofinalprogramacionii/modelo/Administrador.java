package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Apartamento;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Casa;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Hotel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class Administrador {
    //ATRIBUTOS
        @NonNull private String id;
        @NonNull private String nombre;
        @NonNull private String telefono;
        @NonNull private String correo;
        @NonNull private String contraseña;

    //METODOS DEL ADMINISTRADOR
        //VALIDAR ALOJAMIENTOS
            public void validarApartamento(Apartamento apartamento) throws Exception{
                if(Plataforma.getInstancia().getListaAlojamientos().stream().filter(alojamiento -> alojamiento instanceof Apartamento).map(alojamiento -> (Apartamento) alojamiento).anyMatch(a -> a.getNombre().equals(apartamento.getNombre()))){
                    throw new Exception("El apartamento ya existe en el sistema");
                } else if(apartamento.getPrecioPorNoche()<0){
                    throw new Exception("El precio por noche del apartamento tiene que ser mayor a 0");
                } else if(apartamento.getHuespedesMaximos()<0){
                    throw new Exception("La cantidad de huespedes maximos tiene que ser mayor a 0");
                } else if(apartamento.getCostoAseoYMantenimiento()<0){
                    throw new Exception("El costo de aseo y mantenimiento tiene que ser mayor a 0");
                }
            }

            public void validarCasa(Casa casa) throws Exception{
                if(Plataforma.getInstancia().getListaAlojamientos().stream().filter(alojamiento -> alojamiento instanceof Casa).map(alojamiento -> (Casa) alojamiento).anyMatch(c -> c.getNombre().equals(casa.getNombre()))){
                    throw new Exception("La casa ya existe en el sistema");
                } else if(casa.getPrecioPorNoche()<0){
                    throw new Exception("El precio por noche del apartamento tiene que ser mayor a 0");
                } else if(casa.getHuespedesMaximos()<0){
                    throw new Exception("La cantidad de huespedes maximos tiene que ser mayor a 0");
                } else if(casa.getCostoAseoYMantenimiento()<0){
                    throw new Exception("El costo de aseo y mantenimiento tiene que ser mayor a 0");
                }
            }

            public void validarHotel(Hotel hotel) throws Exception{
                if(Plataforma.getInstancia().getListaAlojamientos().stream().filter(alojamiento -> alojamiento instanceof Hotel).map(alojamiento -> (Hotel) alojamiento).anyMatch(h -> h.getNombre().equals(hotel.getNombre()))){
                    throw new Exception("El hotel ya existe en el sistema");
                } else if(hotel.getPrecioPorNoche()<0){
                    throw new Exception("El precio por noche del apartamento tiene que ser mayor a 0");
                } else if(hotel.getHuespedesMaximos()<0) {
                    throw new Exception("La cantidad de huespedes maximos tiene que ser mayor a 0");
                }
            }

        //GESTIONAR ALOJAMIENTOS
            public void crearApartamento(String nombre, Ciudad ciudad, String descripcion, String imagen, double precioPorNoche, int huespedesMaximos, List<String> serviciosDisponibles, double costoAseoYMantenimiento) throws Exception{
                Apartamento nuevoApartamento = new Apartamento(nombre, ciudad, descripcion, imagen, precioPorNoche, huespedesMaximos, serviciosDisponibles, costoAseoYMantenimiento);
                validarApartamento(nuevoApartamento);
                Plataforma.getInstancia().getListaAlojamientos().add(nuevoApartamento);
            }


}
