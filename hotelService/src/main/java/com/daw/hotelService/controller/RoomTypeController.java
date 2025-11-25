package com.daw.hotelService.controller;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.hotelService.service.RoomTypeService;

@RestController
@RequestMapping("/api/room-types")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    public RoomTypeController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    //@GetMapping("/hotel/{hotelId}")
    //public List<RoomTypeDTO> getRoomTypesByHotel(@PathVariable Long hotelId) {
    //    return roomTypeService.getRoomTypesByHotel(hotelId);
    //}
}
