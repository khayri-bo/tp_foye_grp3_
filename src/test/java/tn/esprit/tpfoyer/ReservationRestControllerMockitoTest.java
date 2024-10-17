package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.control.ReservationRestController;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.service.IReservationService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationRestControllerMockitoTest {

    @InjectMocks
    private ReservationRestController reservationRestController;

    @Mock
    private IReservationService reservationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllReservations() {
        List<Reservation> reservations = Arrays.asList(new Reservation(), new Reservation());
        when(reservationService.retrieveAllReservations()).thenReturn(reservations);

        List<Reservation> result = reservationRestController.getReservations();

        assertEquals(2, result.size());
        verify(reservationService, times(1)).retrieveAllReservations();
    }

    @Test
    public void testRetrieveReservationById() {
        Reservation reservation = new Reservation("res123", new Date(), true, null);
        when(reservationService.retrieveReservation("res123")).thenReturn(reservation);

        Reservation result = reservationRestController.retrieveReservation("res123");

        assertNotNull(result);
        assertEquals("res123", result.getIdReservation());
        verify(reservationService, times(1)).retrieveReservation("res123");
    }

    @Test
    public void testRetrieveReservationByDateAndStatus() {
        Date date = new Date();
        when(reservationService.trouverResSelonDateEtStatus(date, true)).thenReturn(Arrays.asList(new Reservation()));

        List<Reservation> result = reservationRestController.retrieveReservationParDateEtStatus(date, true);

        assertEquals(1, result.size());
        verify(reservationService, times(1)).trouverResSelonDateEtStatus(date, true);
    }

    @Test
    public void testAddReservation() {
        Reservation reservation = new Reservation("res124", new Date(), true, null);
        when(reservationService.addReservation(reservation)).thenReturn(reservation);

        Reservation result = reservationRestController.addReservation(reservation);

        assertNotNull(result);
        assertEquals("res124", result.getIdReservation());
        verify(reservationService, times(1)).addReservation(reservation);
    }

    @Test
    public void testModifyReservation() {
        Reservation reservation = new Reservation("res124", new Date(), false, null);
        when(reservationService.modifyReservation(reservation)).thenReturn(reservation);

        Reservation result = reservationRestController.modifyReservation(reservation);

        assertNotNull(result);
        assertFalse(result.isEstValide());
        verify(reservationService, times(1)).modifyReservation(reservation);
    }

    @Test
    public void testDeleteReservation() {
        String reservationId = "res124";
        doNothing().when(reservationService).removeReservation(reservationId);

        reservationRestController.removeReservation(reservationId);

        verify(reservationService, times(1)).removeReservation(reservationId);
    }
}
