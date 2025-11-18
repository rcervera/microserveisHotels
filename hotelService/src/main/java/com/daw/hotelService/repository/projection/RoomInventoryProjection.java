package com.daw.hotelService.repository.projection;

public interface RoomInventoryProjection {
    Long getHotelId();
    Long getRoomTypeId();
    Long getTotalInventory();
}
