package Test;

import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Alojamientos.Casa;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.modelo.Billetera.Billetera;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.repositorios.*;
import co.edu.uniquindio.poo.proyectofinalprogramacionii.servicios.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookYourStayTest {
    @Mock
    private UsuarioRepositorio usuarioRepositorio;
    @Mock
    private ReservaRepositorio reservaRepositorio;
    @Mock
    private BilleteraRepositorio billeteraRepositorio;
    @Mock
    private AlojamientoRepositorio alojamientoRepositorio;

    @InjectMocks
    private UsuarioServicio usuarioServicio;
    @InjectMocks
    private ReservaServicio reservaServicio;
    @InjectMocks
    private BilleteraServicio billeteraServicio;
    @InjectMocks
    private AlojamientoServicio alojamientoServicio;

    private Usuario usuario;
    private Alojamiento alojamiento;
    private Reserva reserva;
    private Billetera billetera;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = Usuario.builder()
                .email("test@example.com")
                .contrasena("password")
                .activo(true)
                .build();
        alojamiento = new Casa(100.0, 4, "Casa Test", new Ciudad("Bogotá"), "Descripción", "image.jpg",
                Arrays.asList("Wifi"), 20.0);
        billetera = Billetera.builder().saldo(1000.0).propietario(usuario).build();
        reserva = Reserva.builder()
                .id(UUID.randomUUID())
                .clienteHospedado(usuario)
                .alojamientoReservado(alojamiento)
                .fechaEntrada(LocalDateTime.now())
                .fechaSalida(LocalDateTime.now().plusDays(2))
                .numHuespedes(2)
                .build();
        usuario.setBilletera(billetera);
    }

    @Test
    void testRegistrarUsuario() {
        when(usuarioRepositorio.buscarPorEmail(anyString())).thenReturn(null);
        doNothing().when(usuarioRepositorio).guardar(any(Usuario.class));
        assertDoesNotThrow(() -> usuarioServicio.registrarUsuario(usuario, "123456"));
    }

    @Test
    void testActivarUsuario() {
        when(usuarioRepositorio.buscarPorEmail("test@example.com")).thenReturn(usuario);
        doNothing().when(usuarioRepositorio).actualizar(any(Usuario.class));
        assertDoesNotThrow(() -> usuarioServicio.activarUsuario("test@example.com", "123456"));
        assertTrue(usuario.isActivo());
    }

    @Test
    void testCrearAlojamiento() {
        when(alojamientoRepositorio.buscarPorNombre(anyString())).thenReturn(null);
        doNothing().when(alojamientoRepositorio).guardar(any(Alojamiento.class));
        assertDoesNotThrow(() -> alojamientoServicio.crearAlojamiento(alojamiento));
    }

    @Test
    void testRecargarBilletera() {
        when(billeteraRepositorio.buscarPorPropietario(anyString())).thenReturn(billetera);
        doNothing().when(billeteraRepositorio).actualizar(any(Billetera.class));
        billeteraServicio.recargarBilletera(billetera, 500.0);
        assertEquals(1500.0, billetera.getSaldo());
    }

    @Test
    void testPagarReserva() {
        when(billeteraRepositorio.buscarPorPropietario(anyString())).thenReturn(billetera);
        doNothing().when(billeteraRepositorio).actualizar(any(Billetera.class));
        assertDoesNotThrow(() -> billeteraServicio.pagarReserva(billetera, 200.0, reserva));
        assertEquals(800.0, billetera.getSaldo());
    }

    @Test
    void testRealizarReserva() {
        alojamiento.setReservasAlojamientoActivas(new ArrayList<>());
        when(billeteraRepositorio.buscarPorPropietario(anyString())).thenReturn(billetera);
        doNothing().when(reservaRepositorio).guardar(any(Reserva.class));
        doNothing().when(billeteraRepositorio).actualizar(any(Billetera.class));
        assertDoesNotThrow(() -> reservaServicio.realizarReserva(reserva, usuario, null));
    }

    @Test
    void testRealizarReservaSinDisponibilidad() {
        alojamiento.setReservasAlojamientoActivas(Arrays.asList(reserva));
        assertThrows(Exception.class, () -> reservaServicio.realizarReserva(reserva, usuario, null));
    }
}