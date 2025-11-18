package com.daw.hotelService.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.hotelService.repository.projection.RoomInventoryProjection;
import com.daw.hotelService.service.HotelInventoryService;

@RestController
@RequestMapping("/api/hotels")
public class HotelInventoryController {

    private final HotelInventoryService inventoryService;

    public HotelInventoryController(HotelInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/inventory")
    public List<RoomInventoryProjection> getInventory() {
        return inventoryService.getInventory();
    }
}

