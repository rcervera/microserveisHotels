package com.daw.hotelService.service;

import com.daw.hotelService.model.Hotel;
import com.daw.hotelService.model.Room;
import com.daw.hotelService.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Page<Room> findByHotel(Hotel hotel, Pageable pageable) {
        return roomRepository.findByHotel(hotel, pageable);
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public Room findById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }
}

