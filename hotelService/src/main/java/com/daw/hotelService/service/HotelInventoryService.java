package com.daw.hotelService.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.daw.hotelService.repository.RoomRepository;
import com.daw.hotelService.repository.projection.RoomInventoryProjection;

@Service
public class HotelInventoryService {

    private final RoomRepository roomRepository;

    public HotelInventoryService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomInventoryProjection> getInventory() {
        return roomRepository.getInventoryByHotelAndRoomType();
    }
}
