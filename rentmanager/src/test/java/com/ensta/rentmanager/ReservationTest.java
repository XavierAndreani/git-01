package com.ensta.rentmanager;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
public class ReservationTest {
    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationDao reservationDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll_should_fail_when_dao_throws_exception() throws DaoException {
        // When
        when(this.reservationDao.findAll()).thenThrow(DaoException.class);
        // Then
        assertThrows(ServiceException.class, () -> reservationService.findAll());
    }
    @Test
    public void create_should_succeed_with_valid_data() throws ServiceException, DaoException {
        // Given
        Reservation reservation = new Reservation(1, 3, 4, LocalDate.now(), LocalDate.now().plusDays(6));
        when(reservationDao.create(reservation)).thenReturn(1L);
        // When
        long reservationId = reservationService.create(reservation);
        // Then
        assertEquals(1L, reservationId);
    }
    @Test
    public void create_should_fail_when_end_date_before_start_date() {
        // Given
        Reservation reservation = new Reservation(2,1, 1, LocalDate.now().plusDays(4), LocalDate.now());
        // When & Then
        assertThrows(ServiceException.class, () -> reservationService.create(reservation));
    }
    @Test
    public void create_should_fail_when_reservation_duration_exceeds_7_days() {
        // Given
        Reservation reservation = new Reservation(2,5, 1, LocalDate.now(), LocalDate.now().plusDays(8));
        // When & Then
        assertThrows(ServiceException.class, () -> reservationService.create(reservation));
    }
}
