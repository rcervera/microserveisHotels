package com.daw.hotelService.repository;


import java.util.List;

import com.daw.hotelService.model.RoomType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
    
     // List<RoomType> findByHotel_Id(Long hotelId);
}
