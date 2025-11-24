package com.daw.hotelService.controller;



import com.daw.hotelService.dto.RoomTypeDTO;
import com.daw.hotelService.service.RoomTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-types")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    public RoomTypeController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    @GetMapping("/hotel/{hotelId}")
    public List<RoomTypeDTO> getRoomTypesByHotel(@PathVariable Long hotelId) {
        return roomTypeService.getRoomTypesByHotel(hotelId);
    }
}
