package com.daw.hotelService.dto;

public class RoomDTO {

    private Long id;
    private String name;
    private String number;
    private int floor;
    private boolean isAvailable;

    // Dades aplanades del RoomType (per evitar N+1)
    private String roomTypeCode;
    private String roomTypeDescription;

    /**
     * Constructor especial per a consultes JPQL.
     * Permet a Spring Data omplir aquest DTO directament des de la base de dades.
     */
    public RoomDTO(Long id, String name, String number, int floor, boolean isAvailable, String roomTypeCode, String roomTypeDescription) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.floor = floor;
        this.isAvailable = isAvailable;
        this.roomTypeCode = roomTypeCode;
        this.roomTypeDescription = roomTypeDescription;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getNumber() { return number; }
    public int getFloor() { return floor; }
    public boolean isAvailable() { return isAvailable; }
    public String getRoomTypeCode() { return roomTypeCode; }
    public String getRoomTypeDescription() { return roomTypeDescription; }
}
