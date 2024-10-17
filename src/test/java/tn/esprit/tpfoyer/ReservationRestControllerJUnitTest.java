package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.tpfoyer.control.ReservationRestController;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.service.IReservationService;

import java.util.Arrays;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
public class ReservationRestControllerJUnitTest {

    private MockMvc mockMvc;

    @Autowired
    private ReservationRestController reservationRestController;

    @Autowired
    private IReservationService reservationService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(reservationRestController).build();
    }

    @Test
    public void testGetAllReservations() throws Exception {
        mockMvc.perform(get("/reservation/retrieve-all-reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));  // Assuming there are 3 reservations
    }

    @Test
    public void testRetrieveReservationById() throws Exception {
        String reservationId = "res123";
        mockMvc.perform(get("/reservation/retrieve-reservation/" + reservationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value(reservationId));
    }

    @Test
    public void testRetrieveReservationByDateAndStatus() throws Exception {
        mockMvc.perform(get("/reservation/retrieve-reservation-date-status/{d}/{v}", "2024-09-05", true)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));  // Assuming 2 reservations match the criteria
    }

    @Test
    public void testAddReservation() throws Exception {
        Reservation reservation = new Reservation("res124", new Date(), true, null);

        mockMvc.perform(get("/reservation/add-reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"idReservation\": \"res124\", \"estValide\": true }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("res124"));
    }

    @Test
    public void testModifyReservation() throws Exception {
        Reservation reservation = new Reservation("res124", new Date(), false, null);

        mockMvc.perform(get("/reservation/modify-reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"idReservation\": \"res124\", \"estValide\": false }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estValide").value(false));
    }

    @Test
    public void testDeleteReservation() throws Exception {
        String reservationId = "res124";
        mockMvc.perform(get("/reservation/remove-reservation/" + reservationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
