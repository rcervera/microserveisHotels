package com.daw.reservationService.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "room_type_inventory")
public class RoomTypeInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // PK

    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "room_type_id")
    private Long roomTypeId;

    private LocalDate date;

    @Column(name = "total_inventory")
    private int totalInventory;

    @Column(name = "total_reserved")
    private int totalReserved;

    // Getters i Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTotalInventory() {
        return totalInventory;
    }

    public void setTotalInventory(int totalInventory) {
        this.totalInventory = totalInventory;
    }

    public int getTotalReserved() {
        return totalReserved;
    }

    public void setTotalReserved(int totalReserved) {
        this.totalReserved = totalReserved;
    }
}
