package com.daw.hotelService.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.daw.hotelService.dto.RoomDTO;
import com.daw.hotelService.model.Hotel;
import com.daw.hotelService.model.Room;
import com.daw.hotelService.repository.RoomRepository;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Page<RoomDTO> findByHotelAsDTO(Hotel hotel, Pageable pageable) {
        return roomRepository.findRoomsByHotelAsDTO(hotel, pageable);
    }

    /**
     * Crida el mètode del repositori que retorna un DTO
     */
    public Optional<RoomDTO> findByIdAndHotelIdAsDTO(Long roomId, Long hotelId) {
        return roomRepository.findRoomByIdAndHotelIdAsDTO(roomId, hotelId);
    }

    /**
     * Retorna una pàgina d'habitacions per a un hotel.
     */
    public Page<Room> findByHotel(Hotel hotel, Pageable pageable) {
        return roomRepository.findByHotel(hotel, pageable);
    }

    /**
     * retorna el 'Room' desat (necessari per al POST i PUT).
     */
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    
    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    /**
     * Mètode per esborrar.
     */
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }
}