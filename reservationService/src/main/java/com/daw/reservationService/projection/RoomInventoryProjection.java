package com.daw.reservationService.projection;

public interface RoomInventoryProjection {

    Long getHotelId();

    Long getRoomTypeId();

    Integer getTotalInventory();
}

