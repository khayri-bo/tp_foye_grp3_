package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReservationServiceImplTest {

    private ReservationServiceImpl reservationService;
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp() {
        reservationRepository = mock(ReservationRepository.class);
        reservationService = new ReservationServiceImpl(reservationRepository);
    }

    @Test
    public void testRetrieveAllReservations() {
        // Arrange
        List<Reservation> expectedReservations = List.of(new Reservation(), new Reservation()); // Mocked reservation list
        when(reservationRepository.findAll()).thenReturn(expectedReservations);

        // Act
        List<Reservation> actualReservations = reservationService.retrieveAllReservations();

        // Assert
        assertEquals(expectedReservations, actualReservations);
        verify(reservationRepository, times(1)).findAll();
    }
}