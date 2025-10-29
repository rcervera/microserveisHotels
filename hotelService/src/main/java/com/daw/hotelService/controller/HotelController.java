package com.daw.hotelService.controller;

import com.daw.hotelService.model.Hotel;
import com.daw.hotelService.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Controller
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;    }

   
    @GetMapping
public String listHotels(Model model,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "5") int size,
                         @RequestParam(defaultValue = "") String keyword) {

    Pageable pageable = PageRequest.of(page, size);
    Page<Hotel> hotelPage;

    if (keyword.isEmpty()) {
        hotelPage = hotelService.findAll(pageable);
    } else {
        hotelPage = hotelService.search(keyword, pageable);
    }

    model.addAttribute("hotels", hotelPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", hotelPage.getTotalPages());
    model.addAttribute("keyword", keyword);
    return "hotels/list";
}


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "hotels/create";
    }

    @PostMapping("/create")
    public String createHotel(@ModelAttribute Hotel hotel) {
        hotelService.save(hotel);
        return "redirect:/hotels";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Hotel hotel = hotelService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid hotel Id:" + id));
        model.addAttribute("hotel", hotel);
        return "hotels/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateHotel(@PathVariable Long id, @ModelAttribute Hotel hotel) {
        hotel.setId(id);
        hotelService.save(hotel);
        return "redirect:/hotels";
    }

    @GetMapping("/delete/{id}")
    public String deleteHotel(@PathVariable Long id) {
        hotelService.deleteById(id);
        return "redirect:/hotels";
    }
}
