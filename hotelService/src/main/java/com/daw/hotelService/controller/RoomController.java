package com.daw.hotelService.controller;

import com.daw.hotelService.dto.RoomDTO;
import com.daw.hotelService.model.Hotel;
import com.daw.hotelService.model.Room;
import com.daw.hotelService.service.HotelService;
import com.daw.hotelService.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
// Canviem la ruta base per a un recurs REST niat
@RequestMapping("/api/hotels/{hotelId}/rooms")
@RestController
public class RoomController {

    private final RoomService roomService;
    private final HotelService hotelService;

    // Injecció per constructor (pràctica recomanada)
    public RoomController(RoomService roomService, HotelService hotelService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
    }

    // Mètode privat per trobar l'hotel i gestionar el 404
    private Hotel findHotelById(Long hotelId) {
        return hotelService.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel no trobat amb ID: " + hotelId));
    }

    /**
     * CREATE - POST /api/hotels/{hotelId}/rooms
     * Crea una nova habitació per a un hotel específic.
     */
    @PostMapping
    public ResponseEntity<Room> createRoom(@PathVariable Long hotelId, @RequestBody Room room) {
        Hotel hotel = findHotelById(hotelId);
        room.setHotel(hotel); // Associem l'habitació amb l'hotel
        Room savedRoom = roomService.save(room);
        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }

    /**
     * READ (List) - GET /api/hotels/{hotelId}/rooms
     * Llista les habitacions d'un hotel específic (paginat).
     */
    @GetMapping
    public Page<RoomDTO> listRoomsByHotel(
            @PathVariable Long hotelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        
        Hotel hotel = findHotelById(hotelId); // Comprovem que l'hotel existeix
        Pageable pageable = PageRequest.of(page, size);
        return roomService.findByHotelAsDTO(hotel, pageable); // Cridem el mètode DTO
    }

    /**
     * READ (Single) - GET /api/hotels/{hotelId}/rooms/{roomId}
     * Obté una habitació específica per ID, assegurant que pertany a l'hotel.
     */
    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long hotelId, @PathVariable Long roomId) {
        // Cridem el nou mètode del servei que retorna un DTO
        RoomDTO roomDTO = roomService.findByIdAndHotelIdAsDTO(roomId, hotelId)
                .orElseThrow(() -> new RuntimeException("Habitació no trobada amb ID: " + roomId + " o no pertany a l'hotel " + hotelId));
        
        return ResponseEntity.ok(roomDTO);
    }

    /**
     * UPDATE - PUT /api/hotels/{hotelId}/rooms/{roomId}
     * Actualitza una habitació específica.
     */
    @PutMapping("/{roomId}")
    public ResponseEntity<Room> updateRoom(
            @PathVariable Long hotelId,
            @PathVariable Long roomId,
            @RequestBody Room roomDetails) {

        Hotel hotel = findHotelById(hotelId);
        findRoomAndCheckHotel(hotelId, roomId); // Assegura que l'habitació pertany a l'hotel

        roomDetails.setId(roomId); // Assegura que actualitzem l'ID correcte
        roomDetails.setHotel(hotel); // Re-associa amb l'hotel correcte
        Room updatedRoom = roomService.save(roomDetails);
        return ResponseEntity.ok(updatedRoom);
    }

    /**
     * DELETE - DELETE /api/hotels/{hotelId}/rooms/{roomId}
     * Esborra una habitació específica.
     */
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long hotelId, @PathVariable Long roomId) {
        Room room = findRoomAndCheckHotel(hotelId, roomId); // Assegura que existeix i pertany a l'hotel
        roomService.deleteById(room.getId());
        return ResponseEntity.noContent().build();
    }

    /**
     * Mètode privat per trobar l'habitació i verificar que pertany a l'hotel.
     */
    private Room findRoomAndCheckHotel(Long hotelId, Long roomId) {
        return roomService.findById(roomId)
            .map(room -> {
                if (!room.getHotel().getId().equals(hotelId)) {
                    throw new RuntimeException("L'habitació " + roomId + " no pertany a l'hotel " + hotelId);
                }
                return room;
            })
            .orElseThrow(() -> new RuntimeException("Habitació no trobada amb ID: " + roomId));
    }

    /**
     * Gestor d'excepcions local per a aquest controlador (retorna 404).
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(RuntimeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Not Found");
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }
}