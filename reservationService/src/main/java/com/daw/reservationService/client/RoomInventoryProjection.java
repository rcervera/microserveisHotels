package com.daw.reservationService.client;

public record RoomInventoryProjection(
        Long roomId,
        String roomType,
        int availableRooms
) {}
