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
import com.daw.hotelService.dto.HotelBasicDTO;
import com.daw.hotelService.dto.HotelSummaryDTO;
import com.daw.hotelService.model.Hotel;
import com.daw.hotelService.service.HotelService;

@CrossOrigin("*")
// Canviem la ruta base per seguir les convencions d'API REST
@RequestMapping("/api/hotels")
// Canviem @Controller per @RestController
@RestController
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    /**
     * GET /api/hotels
     * Retorna una pàgina d'hotels (amb cerca opcional per 'keyword').
     */
   @GetMapping
    public Page<HotelSummaryDTO> listHotels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String keyword) {

        Pageable pageable = PageRequest.of(page, size);
        if (keyword.isEmpty()) {
            return hotelService.findAll(pageable); // retorna el DTO
        } else {
            return hotelService.search(keyword, pageable); // retorna el DTO
        }
    }

    /**
     * GET /api/hotels/{id}
     * Retorna un sol hotel pel seu ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        Hotel hotel = hotelService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hotel Id:" + id));
        return ResponseEntity.ok(hotel);
    }

    /**
     * POST /api/hotels
     * Crea un nou hotel rebent un JSON al body.
     */
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        Hotel savedHotel = hotelService.save(hotel);
        // Retorna un 201 CREATED amb l'objecte creat
        return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
    }

    /**
     * PUT /api/hotels/{id}
     * Actualitza un hotel existent rebent un JSON al body.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long id, @RequestBody Hotel hotel) {
        // Assegurem que l'ID de la URL és el que es desa
        hotel.setId(id);
        Hotel updatedHotel = hotelService.save(hotel);
        return ResponseEntity.ok(updatedHotel);
    }

    /**
     * DELETE /api/hotels/{id}
     * Esborra un hotel pel seu ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteById(id);
        // Retorna un 204 NO CONTENT, indicant èxit sense cos de resposta
        return ResponseEntity.noContent().build();
    }

    /**
     * Gestor d'excepcions local per a aquest controlador.
     * Si 'findById' llança IllegalArgumentException, es captura aquí
     * i es retorna un 404 Not Found amb un missatge d'error en JSON.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(IllegalArgumentException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Not Found");
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }

     @GetMapping("/cities")
    public List<CityDTO> getCities() {
        return hotelService.getAllCities();
    }

    @GetMapping("/by-city/{city}")
    public List<HotelBasicDTO> getHotelsByCity(@PathVariable String city) {
        return hotelService.getHotelsByLocation(city);
    }
}