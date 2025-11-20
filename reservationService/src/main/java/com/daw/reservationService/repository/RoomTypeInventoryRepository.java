package com.daw.reservationService.repository;


import com.daw.reservationService.model.RoomTypeInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomTypeInventoryRepository extends JpaRepository<RoomTypeInventory, Long> {

    List<RoomTypeInventory> findByHotelIdAndRoomTypeId(Long hotelId, Long roomTypeId);

    RoomTypeInventory findByHotelIdAndRoomTypeIdAndDate(Long hotelId, Long roomTypeId, LocalDate date);

}
