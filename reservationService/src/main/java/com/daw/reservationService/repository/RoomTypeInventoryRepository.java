package com.daw.reservationService.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daw.reservationService.model.RoomTypeInventory;

@Repository
public interface RoomTypeInventoryRepository extends JpaRepository<RoomTypeInventory, Long> {

    List<RoomTypeInventory> findByHotelIdAndRoomTypeId(Long hotelId, Long roomTypeId);

    RoomTypeInventory findByHotelIdAndRoomTypeIdAndDate(Long hotelId, Long roomTypeId, LocalDate date);

    @Query("SELECT MAX(r.date) FROM RoomTypeInventory r WHERE r.hotelId = :hotelId AND r.roomTypeId = :roomTypeId")
    LocalDate findLastDate(Long hotelId, Long roomTypeId);
}
