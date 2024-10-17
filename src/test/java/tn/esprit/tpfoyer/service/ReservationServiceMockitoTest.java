package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReservationServiceMockitoTest {

    @InjectMocks
    ReservationServiceImpl reservationService;

    @Mock
    ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllReservationsWithMockito() {
        // Arrange
        List<Reservation> mockedReservations = List.of(new Reservation(), new Reservation());
        when(reservationRepository.findAll()).thenReturn(mockedReservations);

        // Act
        List<Reservation> result = reservationService.retrieveAllReservations();

        // Assert
        assertEquals(mockedReservations, result);
        verify(reservationRepository, times(1)).findAll();
    }
}