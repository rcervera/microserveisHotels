package com.daw.hotelService.dto;

public class HotelSummaryDTO {

    private Long id;
    private String name;
    private String address;
    private String location;

    // Constructor important per a la transformació
    public HotelSummaryDTO(Long id, String name, String address, String location) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.location = location;
    }

    // Getters 
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getLocation() { return location; }
}