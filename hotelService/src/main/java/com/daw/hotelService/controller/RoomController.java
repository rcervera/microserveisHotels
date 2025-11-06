package com.daw.hotelService.controller;

import com.daw.hotelService.model.Hotel;
import com.daw.hotelService.model.Room;
import com.daw.hotelService.service.HotelService;
import com.daw.hotelService.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequestMapping(value = "/hotels/rooms")
@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelService hotelService;

    @GetMapping("/{hotelId}")
    public String listRooms(@PathVariable Long hotelId,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size,
                            Model model) {

        Hotel hotel = hotelService.findById(hotelId)
            .orElseThrow(() -> new RuntimeException("Hotel no trobat amb ID: " + hotelId));

        Page<Room> roomPage = roomService.findByHotel(hotel, PageRequest.of(page, size));

        model.addAttribute("hotel", hotel);
        model.addAttribute("rooms", roomPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", roomPage.getTotalPages());
        return "rooms/list";
    }
}
