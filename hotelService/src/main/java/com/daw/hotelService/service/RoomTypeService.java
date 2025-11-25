package com.daw.hotelService.service;



import com.daw.hotelService.dto.RoomTypeDTO;
import com.daw.hotelService.model.RoomType;
import com.daw.hotelService.repository.RoomTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTypeService {

    private final RoomTypeRepository hotelRoomTypeRepository;

    public RoomTypeService(RoomTypeRepository hotelRoomTypeRepository) {
        this.hotelRoomTypeRepository = hotelRoomTypeRepository;

    }

    /* 
    public List<RoomTypeDTO> getRoomTypesByHotel(Long hotelId) {
        List<RoomType> list = hotelRoomTypeRepository.findByHotel_Id(hotelId);

        return list.stream()
                .map(rt -> new RoomTypeDTO(
                        rt.getId(),
                        rt.getDescription(),
                        rt.getMaxAdults(),
                        rt.getMaxChildren()
                ))
                .toList();
    } 
    */
}

