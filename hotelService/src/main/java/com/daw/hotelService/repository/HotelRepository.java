package com.daw.hotelService.repository;


import com.daw.hotelService.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    // Pots afegir consultes personalitzades si vols
    // Exemple:
    // List<Hotel> findByCiutat(String ciutat);
    
    // Cerca per nom, adreça o ubicació (conté)
    Page<Hotel> findByNameContainingIgnoreCaseOrAddressContainingIgnoreCaseOrLocationContainingIgnoreCase(
        String name, String address, String location, Pageable pageable);
}

