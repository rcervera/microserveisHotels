package com.daw.hotelService.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daw.hotelService.dto.HotelSummaryDTO;
import com.daw.hotelService.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    // Pots afegir consultes personalitzades si vols
    // Exemple:
    // List<Hotel> findByCiutat(String ciutat);
    
    // Cerca per nom, adreça o ubicació (conté)
    Page<Hotel> findByNameContainingIgnoreCaseOrAddressContainingIgnoreCaseOrLocationContainingIgnoreCase(
        String name, String address, String location, Pageable pageable);

    /**
     * Busca tots els hotels i els retorna directament com a DTO.
     *  
     */
    @Query("SELECT new com.daw.hotelService.dto.HotelSummaryDTO(h.id, h.name, h.address, h.location) FROM Hotel h")
    Page<HotelSummaryDTO> findAllSummary(Pageable pageable);

    
    /**
     * El mateix però amb la cerca.
     */
    @Query("SELECT new com.daw.hotelService.dto.HotelSummaryDTO(h.id, h.name, h.address, h.location) FROM Hotel h " +
           "WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(h.address) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(h.location) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<HotelSummaryDTO> searchSummary(String keyword, Pageable pageable);
}

