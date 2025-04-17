package co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera.Billetera;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera.Transaccion;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
public class Plataforma {
    //ATRIBUTOS
    @NonNull
    private String nombre;
    private ArrayList<Cliente> listaClientes = new ArrayList<>();
    private ArrayList<Transaccion> listaTransacciones = new ArrayList<>();
    private ArrayList<Billetera> listaBilleteras = new ArrayList<>();
    private ArrayList<Alojamiento> listaAlojamientos = new ArrayList<>();
    private ArrayList<Reserva> listaReservas = new ArrayList<>();

    //SINGLETON
    public static Plataforma INSTANCIA;
    public static Plataforma getInstancia(){
        if(INSTANCIA == null){
            INSTANCIA = new Plataforma("BookYourStay");
        }
        return INSTANCIA;
    }

    //METODOS DE LA PLATAFORMA
        //VALIDAR CLIENTE
        public void validarCliente(Cliente cliente) throws Exception{
            if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
                throw new Exception("El nombre no puede estar vacío.");
            } else if (cliente.getId() == null || cliente.getId().trim().isEmpty()) {
                throw new Exception("La identificación no puede estar vacía.");
            } else if (cliente.getCorreo() == null || cliente.getCorreo().trim().isEmpty()) {
                throw new Exception("El correo no puede estar vacío.");
            } else if (cliente.getContraseña() == null || cliente.getContraseña().trim().isEmpty()) {
                throw new Exception("La contraseña no puede estar vacía.");
            }  else if (!cliente.getId().matches("\\d+")) {
                throw new Exception("El ID no puede contener caracteres.");
            } else if (listaClientes.stream().anyMatch(u -> u.getId().equals(cliente.getId()))){
                throw new Exception("Ya existe un usuario registrado con este ID.");
            } else if (listaClientes.stream().anyMatch(u -> u.getTelefono().equals(cliente.getTelefono()))) {
                throw new Exception("Ya existe un usuario registrado con este ID.");
            } else if (!cliente.getCorreo().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                throw new Exception("Ingrese un correo válido.");
            }
        }

        //EDITAR DATOS DEL CLIENTE
            public void editarDatosCliente(Cliente clienteActual, Cliente datosNuevos) throws Exception{
                validarCliente(datosNuevos);
                for (Cliente cliente : listaClientes) {
                    if(clienteActual.getId().equals(cliente.getId())) {
                        clienteActual.setNombre(datosNuevos.getNombre());
                        clienteActual.setId(datosNuevos.getId());
                        clienteActual.setContraseña(datosNuevos.getContraseña());
                        clienteActual.setCorreo(datosNuevos.getCorreo());
                        clienteActual.setTelefono(datosNuevos.getTelefono());
                        break;
                    }
                }
            }

        //REGISTRAR CLIENTE
            public void registrarCliente(Cliente cliente) throws Exception{
                validarCliente(cliente);
                listaClientes.add(cliente);
                Billetera billetera = new Billetera(0, cliente);
                listaBilleteras.add(billetera);
            }

        //BUSCAR CLIENTE
            public Cliente buscarCliente(String id) throws Exception {
                for (Cliente c : listaClientes) {
                    if (c.getId().equals(id)) {
                        return c;
                    }
                }
                throw new Exception("No se encontro un cliente con el ID.");
            }

        //ELIMINAR CLIENTE
            public void eliminarCliente(Cliente cliente){
                //LISTA GENERAL DE RESERVAS EN PLATAFORMA, LISTA INDIVIDUAL PARA CLIENTE
                //METODOS DE REALIZARRESERVA A CLIENTE
                
                listaClientes.remove(cliente);
            }


        //INICIAR SESION
            public void iniciarSesion(Cliente cliente) throws Exception {
                for (Cliente c : listaClientes) {
                    if (c.getId().equals(cliente.getId()) && c.getContraseña().equals(cliente.getContraseña())) {
                        return;
                    }
                }
                throw new Exception("ID/Contraseña incorrectos.");
            }


        //BUSCAR ALOJAMIENTO POR NOMBRE
            public List<Alojamiento> alojamientosPorNombre(String nombre) throws Exception {
                List<Alojamiento> alojamientosFiltrados  = listaAlojamientos.stream().filter(a -> a.getNombre().equalsIgnoreCase(nombre)).collect(Collectors.toList());
                if(alojamientosFiltrados.isEmpty()){
                    throw new Exception("No hay alojamientos con el nombre indicado");
                }
                return alojamientosFiltrados;
            }

        // BUSCAR ALOJAMIENTO POR TIPO DE ALOJAMIENTO
            public List<Alojamiento> alojamientosPorTipo(Class <? extends Alojamiento> tipo) throws Exception {
                List<Alojamiento> alojamientosFiltrados = listaAlojamientos.stream().filter(tipo::isInstance).toList();
                if(alojamientosFiltrados.isEmpty()){
                    throw new Exception("No hay alojamientos de este tipo disponibles");
                }
                return alojamientosFiltrados;
            }

        //BUSCAR ALOJAMIENTO POR CIUDAD
            public List<Alojamiento> alojamientosPorCiudad(Ciudad ciudad) throws Exception {
                List<Alojamiento> alojamientosFiltrados = listaAlojamientos.stream().filter(a -> a.getCiudad().equals(ciudad)).toList();
                if(alojamientosFiltrados.isEmpty()){
                    throw new Exception("No hay alojamientos de este ciudad");
                }
                return alojamientosFiltrados;
            }


        //BUSCAR ALOJAMIENTO POR RANGO DE PRECIO
            public List<Alojamiento> alojamientosPorRangoPrecio(double precioMinimo, double precioMaximo) throws Exception {
                List<Alojamiento> alojamientosFiltrados = listaAlojamientos.stream().filter(a -> a.getPrecioPorNocheTotal() < precioMaximo && a.getPrecioPorNocheTotal() > precioMinimo).toList();
                if(alojamientosFiltrados.isEmpty()){
                    throw new Exception("No hay alojamientos entre este rango de precio");
                }
                return alojamientosFiltrados;
            }

        //VALIDAR RESERVA
            public void validarReserva(Reserva reserva, Billetera billetera) throws Exception {
                if(reserva.getAlojamientoReservado().isEstaHospedado()){
                    throw new Exception("El alojamiento ya esta hospedado");
                } else if(reserva.getFechaEntrada().isAfter(reserva.getFechaSalida())){
                    throw new Exception("La fecha de entrada esta seleccionada para después de la de salida");
                } else if(reserva.getFechaSalida().isBefore(reserva.getFechaEntrada())){
                    throw new Exception("La fecha de salida esta seleccionada para antes de la fecha de entrada");
                } else if(reserva.getNumHuespedes() > reserva.getAlojamientoReservado().getHuespedesMaximos()){
                    throw new Exception("La reserva excede la cantidad maxima de huéspedes permitidos por el alojamiento");
                } else if (billetera.getSaldo() < reserva.getAlojamientoReservado().getPrecioPorNocheTotal() * Reserva.calcularNochesDeReserva(reserva.getFechaEntrada(), reserva.getFechaSalida())){
                    throw new Exception("No hay saldo suficiente para completar la reserva");
                }
            }

        //REALIZAR RESERVA
            public Reserva realizarReserva(Cliente clienteHospedado, Alojamiento alojamientoReservado, LocalDateTime fechaEntrada, LocalDateTime fechaSalida, int numHuespedes) throws Exception {
                Reserva reserva = new Reserva(clienteHospedado, alojamientoReservado, fechaEntrada, fechaSalida, numHuespedes);
                validarReserva(reserva, obtenerBilleteraDeUnCliente(clienteHospedado));
                alojamientoReservado.setEstaHospedado(true);
                listaReservas.add(reserva);
                return reserva;
            }

        //CANCELAR RESERVA
            public void cancelarReserva(Reserva reserva){
                listaReservas.remove(reserva);
                reserva.getAlojamientoReservado().setEstaHospedado(false);
            }

        //LISTAR RESERVAS
            public List<Reserva> listarReservasCliente(Cliente cliente) throws Exception {
                List<Reserva> reservasDelCliente = listaReservas.stream().filter(r -> r.getClienteHospedado().equals(cliente)).toList();
                if(reservasDelCliente.isEmpty()){
                    throw new Exception("No tienes alojamientos reservados por ahora");
                }
                return reservasDelCliente;
            }

        //RECARGAR BILLETERA
            public void recargarBilletera(Cliente cliente, double cantidad) throws Exception {
                Billetera billeteraRecargar = obtenerBilleteraDeUnCliente(cliente);
                billeteraRecargar.setSaldo(billeteraRecargar.getSaldo() + cantidad);
            }

        //OBTENER BILLETERA DE UN USUARIO
        public Billetera obtenerBilleteraDeUnCliente(Cliente cliente) throws Exception {
            return listaBilleteras.stream()
                    .filter(b -> b.getPropietario().equals(cliente))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No se encontró una billetera asociada al cliente"));
        }













            //ENCONTRAR USUARIO POR ID Y CONTRASEÑA
        public Cliente clientePorIdYContraseña(String id, String contraseña) throws Exception{
            for(int i=0; i<listaClientes.size(); i++) {
                if (listaClientes.get(i).getId().equals(id) && listaClientes.get(i).getContraseña().equals(contraseña)) {
                    return listaClientes.get(i);
                }
            }
            throw new Exception("No existe un cliente con ese id y esa contraseña");
        }

        //OBTENER BILLETERA POR NÚMERO DE CUENTA
        public Billetera obtenerBilleteraNumCuenta(String numCuenta) {
            return listaBilleteras.stream()
                    .filter(b -> b.getNumTarjeta().equals(numCuenta))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No se encontró una billetera asociada a ese numero de cuenta"));
        }
        //VALIDAR SI UN USUARIO ESTA EN LA LISTA
        public boolean clienteExiste(Cliente cliente) {
            return listaClientes.stream().anyMatch(c -> c.equals(cliente));
        }
        //VALIDAR SI UNA BILLETERA ESTA EN LA LISTA
        public boolean billeteraExiste(Billetera billetera) {
            return listaBilleteras.stream().anyMatch(b -> b.equals(billetera));
        }
}
