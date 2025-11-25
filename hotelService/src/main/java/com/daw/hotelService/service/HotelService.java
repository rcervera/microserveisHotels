package com.daw.hotelService.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.daw.hotelService.dto.CityDTO;
import com.daw.hotelService.dto.HotelSummaryDTO;
import com.daw.hotelService.model.Hotel;
import com.daw.hotelService.repository.HotelRepository;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    // ---------------- CRUD BÀSIC ----------------

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel no trobat amb id: " + id));
    }

    public Hotel updateHotel(Long id, Hotel hotelDetails) {
    Hotel hotel = getHotelById(id);
    hotel.setName(hotelDetails.getName());
    hotel.setAddress(hotelDetails.getAddress());
    hotel.setLocation(hotelDetails.getLocation());
    return hotelRepository.save(hotel);
}


    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    // ---------------- CONSULTES AMB DTO ----------------

    public List<HotelSummaryDTO> findByLocation(String location) {
        return hotelRepository.findByLocation(location)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<String> findDistinctLocations() {
        return hotelRepository.findDistinctLocations();
    }

    public Page<HotelSummaryDTO> findAllHotels(Pageable pageable) {
        return hotelRepository.findAllHotels(pageable)
                .map(this::toDTO);
    }

    public Page<HotelSummaryDTO> searchHotels(String keyword, Pageable pageable) {
        return hotelRepository.searchHotels(keyword, pageable)
                .map(this::toDTO);
    }

    // Al HotelService
    public List<CityDTO> getAllCities() {
        return hotelRepository.findDistinctLocations()
                .stream()
                .map(city -> new CityDTO(city))
                .collect(Collectors.toList());
    }


    // ---------------- MÈTODE PRIVAT DE TRANSFORMACIÓ ----------------

    private HotelSummaryDTO toDTO(Hotel hotel) {
        return new HotelSummaryDTO(
                hotel.getId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getLocation()
        );
    }
}
