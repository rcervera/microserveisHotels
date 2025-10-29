package com.daw.hotelService.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daw.hotelService.model.Hotel;
import com.daw.hotelService.model.Room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // Exemple de mètode personalitzat:
    // List<Room> findByHotelId(Long hotelId);
    Page<Room> findByHotel(Hotel hotel, Pageable pageable);
}
