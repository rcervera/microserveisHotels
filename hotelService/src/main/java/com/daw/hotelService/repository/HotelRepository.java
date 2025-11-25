package com.daw.hotelService.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daw.hotelService.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    
    /**
     * Retorna una llista d'hotels que coincideixen amb una ubicació (location),
     * ignorant majúscules i minúscules.
     *
     * Exemple: findByLocation("barcelona")
     *
     */
    @Query("SELECT h FROM Hotel h WHERE LOWER(h.location) = LOWER(:location)")
    List<Hotel> findByLocation(String location);


    /**
     * Obté totes les ubicacions úniques (locations) on hi ha hotels.
     *
     * Ex: ["Madrid", "Barcelona", "Sevilla"]
     *
     * Retorna només el camp location
     */
    @Query("SELECT DISTINCT h.location FROM Hotel h")
    List<String> findDistinctLocations();


    /**
     * Retorna tots els hotels de manera paginada com ENTITATS completes.
     *
     */
    @Query("SELECT h FROM Hotel h")
    Page<Hotel> findAllHotels(Pageable pageable);


    /**
     * Igual que l'anterior, però aplicant un filtre de cerca.
     * Cerca per: name, address o location.
     *
     */
    @Query("SELECT h FROM Hotel h " +
           "WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(h.address) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(h.location) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Hotel> searchHotels(String keyword, Pageable pageable);
}
