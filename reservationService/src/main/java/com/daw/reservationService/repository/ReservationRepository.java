package com.daw.reservationService.repository;

import com.daw.reservationService.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByHotelId(Long hotelId);

    List<Reservation> findByGuestId(Long guestId);

}
