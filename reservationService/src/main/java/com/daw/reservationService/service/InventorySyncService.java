package com.daw.reservationService.service;


import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.daw.reservationService.client.HotelServiceClient;
import com.daw.reservationService.model.RoomTypeInventory;
import com.daw.reservationService.dto.RoomInventoryDTO;
import com.daw.reservationService.repository.RoomTypeInventoryRepository;

@Service
public class InventorySyncService {

    private final RoomTypeInventoryRepository repository;
    private final HotelServiceClient hotelServiceClient;

    private static final Logger logger = LoggerFactory.getLogger(InventorySyncService.class);

    public InventorySyncService(RoomTypeInventoryRepository repository,
                                HotelServiceClient hotelServiceClient) {
        this.repository = repository;
        this.hotelServiceClient = hotelServiceClient;
    }

    public void syncInventoryCalendar() {

        logger.info("Iniciant sincronització d'inventaris...");

        List<RoomInventoryDTO> inventories = hotelServiceClient.getAllRoomInventories();
        LocalDate today = LocalDate.now();

        inventories.forEach(inv -> {

            Long hotelId = inv.getHotelId();
            Long roomTypeId = inv.getRoomTypeId();
            int totalInv = inv.getTotalInventory();

            LocalDate lastDate = repository.findLastDate(hotelId, roomTypeId);

            // 1️⃣ Primer cop → 30 dies
            if (lastDate == null) {
                logger.info("Creant 30 dies inicials per hotel {} roomType {}", hotelId, roomTypeId);

                for (int i = 0; i < 30; i++) {
                    RoomTypeInventory r = new RoomTypeInventory();
                    r.setHotelId(hotelId);
                    r.setRoomTypeId(roomTypeId);
                    r.setDate(today.plusDays(i));
                    r.setTotalInventory(totalInv);
                    r.setTotalReserved(0);

                    repository.save(r);
                }

            } else {
                // 2️⃣ Afegir només el següent dia
                LocalDate nextDate = lastDate.plusDays(1);

                if (nextDate.isAfter(today.plusDays(29))) {
                    logger.info("Afegint dia {} per hotel {} roomType {}", nextDate, hotelId, roomTypeId);

                    RoomTypeInventory r = new RoomTypeInventory();
                    r.setHotelId(hotelId);
                    r.setRoomTypeId(roomTypeId);
                    r.setDate(nextDate);
                    r.setTotalInventory(totalInv);
                    r.setTotalReserved(0);

                    repository.save(r);
                } else {
                    logger.info("Ja hi ha dates suficients fins {}", lastDate);
                }
            }
        });

        logger.info("Sincronització finalitzada.");
    }
}
