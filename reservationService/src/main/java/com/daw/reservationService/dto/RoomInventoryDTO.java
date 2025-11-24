package com.daw.reservationService.dto;

// NOVA CLASSE: RoomInventoryDTO.java
public class RoomInventoryDTO {

    private Long hotelId;
    private Long roomTypeId;
    private Integer totalInventory;
    
    // 1. Afegeix un constructor sense arguments (obligatori per a Jackson)
    public RoomInventoryDTO() {
    }

    // 2. Afegeix el constructor amb tots els arguments (opcional, però bona pràctica)
    public RoomInventoryDTO(Long hotelId, Long roomTypeId, Integer totalInventory) {
        this.hotelId = hotelId;
        this.roomTypeId = roomTypeId;
        this.totalInventory = totalInventory;
    }

    // 3. Afegeix els getters i setters
    // Els getters són estrictament necessaris perquè Feign/Jackson pugui MAPPEJAR el JSON.

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Integer getTotalInventory() {
        return totalInventory;
    }

    public void setTotalInventory(Integer totalInventory) {
        this.totalInventory = totalInventory;
    }
}
