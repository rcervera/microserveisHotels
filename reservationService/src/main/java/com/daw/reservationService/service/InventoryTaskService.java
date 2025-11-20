package com.daw.reservationService.service;


import  com.daw.reservationService.client.HotelServiceClient;
import  com.daw.reservationService.client.RoomInventoryProjection;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.util.List;

@Service
public class InventoryTaskService {

    private final HotelServiceClient hotelServiceClient;

    public InventoryTaskService(HotelServiceClient hotelServiceClient) {
        this.hotelServiceClient = hotelServiceClient;
    }

    // Executa quan arrenca l'aplicació
    @EventListener(ApplicationReadyEvent.class)
    public void runOnStartup() {
        //fetchRoomInventories();
    }

    // Executa cada 24 hores (24 * 60 * 60 * 1000 ms)
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void runDaily() {
        fetchRoomInventories();
    }

    private void fetchRoomInventories() {
        List<RoomInventoryProjection> inventories = hotelServiceClient.getAllRoomInventories();
        inventories.forEach(System.out::println); // processa o desa a BBDD aquí
    }
}
