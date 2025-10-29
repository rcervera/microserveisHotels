package com.daw.hotelService.service;

import com.daw.hotelService.model.Hotel;
import com.daw.hotelService.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }   

    public Page<Hotel> findAll(Pageable pageable) {
        return hotelRepository.findAll(pageable);
    }

    public Page<Hotel> search(String keyword, Pageable pageable) {
    return hotelRepository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCaseOrLocationContainingIgnoreCase(
        keyword, keyword, keyword, pageable
    );
}


    public void save(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    public Optional<Hotel> findById(Long id) {
        return hotelRepository.findById(id);
    }

    public void deleteById(Long id) {
        hotelRepository.deleteById(id);
    }
}
