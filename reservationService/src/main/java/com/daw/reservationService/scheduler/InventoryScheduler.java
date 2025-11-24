package com.daw.reservationService.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.daw.reservationService.service.InventorySyncService;

@Service
public class InventoryScheduler {

    private final InventorySyncService service;

    public InventoryScheduler(InventorySyncService service) {
        this.service = service;
    }

    // Executa al minut i després cada 24h
    @Scheduled(initialDelay = 60000, fixedRate = 86400000)
    public void runDaily() {
        service.syncInventoryCalendar();
    }
}
