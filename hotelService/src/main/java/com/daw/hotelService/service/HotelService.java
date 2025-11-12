package com.daw.hotelService.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.daw.hotelService.dto.HotelSummaryDTO;
import com.daw.hotelService.model.Hotel;
import com.daw.hotelService.repository.HotelRepository;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    
    public Page<HotelSummaryDTO> findAll(Pageable pageable) {
        return hotelRepository.findAllSummary(pageable);
    }
    
    public Page<HotelSummaryDTO> search(String keyword, Pageable pageable) {
        return hotelRepository.searchSummary(keyword, pageable);
    }

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    // public Page<Hotel> findAll(Pageable pageable) {
    //     return hotelRepository.findAll(pageable);
    // }

    // public Page<Hotel> search(String keyword, Pageable pageable) {
    //     return hotelRepository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCaseOrLocationContainingIgnoreCase(
    //             keyword, keyword, keyword, pageable
    //     );
    // }

    /**
     * CANVIAT: Ara retorna l'hotel desat (necessari per al POST i PUT de l'API REST).
     */
    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Optional<Hotel> findById(Long id) {
        return hotelRepository.findById(id);
    }

    public void deleteById(Long id) {
        hotelRepository.deleteById(id);
    }
}