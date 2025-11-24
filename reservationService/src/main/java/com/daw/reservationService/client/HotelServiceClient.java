package com.daw.reservationService.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.daw.reservationService.dto.RoomInventoryDTO;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelServiceClient {

    @GetMapping("/api/hotels/inventory")
    List<RoomInventoryDTO> getAllRoomInventories();
}