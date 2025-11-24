package com.daw.hotelService.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daw.hotelService.dto.RoomDTO;
import com.daw.hotelService.model.Hotel;
import com.daw.hotelService.model.Room;
import com.daw.hotelService.model.RoomType;
import com.daw.hotelService.repository.projection.RoomInventoryProjection;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // Exemple de mètode personalitzat:
    // List<Room> findByHotelId(Long hotelId);
    Page<Room> findByHotel(Hotel hotel, Pageable pageable);

    List<RoomType> findByHotelId(Long hotelId);
    
    @Query("SELECT new com.daw.hotelService.dto.RoomDTO(r.id, r.name, r.number, r.floor, r.isAvailable, rt.code, rt.description) " +
           "FROM Room r JOIN r.roomType rt " +
           "WHERE r.hotel = :hotel")
    Page<RoomDTO> findRoomsByHotelAsDTO(Hotel hotel, Pageable pageable);

    /**
     * NOU: Mètode per a una sola habitació (GET /.../rooms/{roomId})
     * Retorna un Optional<RoomDTO> i comprova la pertinença a l'hotel.
     */
    @Query("SELECT new com.daw.hotelService.dto.RoomDTO(r.id, r.name, r.number, r.floor, r.isAvailable, rt.code, rt.description) " +
           "FROM Room r JOIN r.roomType rt " +
           "WHERE r.id = :roomId AND r.hotel.id = :hotelId")
    Optional<RoomDTO> findRoomByIdAndHotelIdAsDTO(Long roomId, Long hotelId);


    @Query("""
        SELECT r.hotel.id AS hotelId,
               r.roomType.id AS roomTypeId,
               COUNT(r.id) AS totalInventory
        FROM Room r
        GROUP BY r.hotel.id, r.roomType.id
    """)
    List<RoomInventoryProjection> getInventoryByHotelAndRoomType();
}
