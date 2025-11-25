package com.daw.hotelService.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.daw.hotelService.dto.CityDTO;
import com.daw.hotelService.dto.HotelSummaryDTO;
import com.daw.hotelService.model.Hotel;
import com.daw.hotelService.service.HotelService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    // ---------------- GET HOTELS PAGINATS AMB OPCIÓ DE CERCA ----------------
    @GetMapping
    public Page<HotelSummaryDTO> listHotels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String keyword) {

        Pageable pageable = PageRequest.of(page, size);
        if (keyword.isEmpty()) {
            return hotelService.findAllHotels(pageable);
        } else {
            return hotelService.searchHotels(keyword, pageable);
        }
    }

    // ---------------- GET HOTEL PER ID ----------------
    @GetMapping("/{id}")
    public ResponseEntity<HotelSummaryDTO> getHotelById(@PathVariable Long id) {
        Hotel hotel = hotelService.getHotelById(id); // retorna entitat
        HotelSummaryDTO dto = new HotelSummaryDTO(
                hotel.getId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getLocation()
        );
        return ResponseEntity.ok(dto);
    }

    // ---------------- POST CREATE HOTEL ----------------
    @PostMapping
    public ResponseEntity<HotelSummaryDTO> createHotel(@RequestBody Hotel hotel) {
        Hotel savedHotel = hotelService.createHotel(hotel);
        HotelSummaryDTO dto = new HotelSummaryDTO(
                savedHotel.getId(),
                savedHotel.getName(),
                savedHotel.getAddress(),
                savedHotel.getLocation()
        );
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // ---------------- PUT UPDATE HOTEL ----------------
    @PutMapping("/{id}")
    public ResponseEntity<HotelSummaryDTO> updateHotel(@PathVariable Long id, @RequestBody Hotel hotel) {
        Hotel updatedHotel = hotelService.updateHotel(id, hotel);
        HotelSummaryDTO dto = new HotelSummaryDTO(
                updatedHotel.getId(),
                updatedHotel.getName(),
                updatedHotel.getAddress(),
                updatedHotel.getLocation()
        );
        return ResponseEntity.ok(dto);
    }

    // ---------------- DELETE HOTEL ----------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }

    // ---------------- EXCEPCIONS ----------------
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(IllegalArgumentException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Not Found");
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }

    // ---------------- GET CITIES ----------------
    @GetMapping("/cities")
    public List<CityDTO> getCities() {
        return hotelService.getAllCities();
    }

    // ---------------- GET HOTELS PER CIUTAT ----------------
    @GetMapping("/by-city/{city}")
    public List<HotelSummaryDTO> getHotelsByCity(@PathVariable String city) {
        return hotelService.findByLocation(city);
    }
}
