package com.daw.hotelService.config;


import com.daw.hotelService.model.*;
import com.daw.hotelService.repository.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(
            HotelRepository hotelRepo,
            RoomRepository roomRepo,
            RoomTypeRepository roomTypeRepo
    ) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                // Esborrem dades prèvies
                roomRepo.deleteAll();
                hotelRepo.deleteAll();
                roomTypeRepo.deleteAll();

                // Tipus d’habitacions
                List<RoomType> types = new ArrayList<>();
                types.add(new RoomType("SINGLE", "Habitació individual amb un llit"));
                types.add(new RoomType("DOUBLE", "Habitació doble amb llit de matrimoni"));
                types.add(new RoomType("SUITE", "Suite de luxe amb terrassa i vistes"));
                roomTypeRepo.saveAll(types);

                // Llistes de dades realistes
                String[] nomsHotels = {
                    "Hotel Miramar", "Hotel Atlàntic", "Gran Hotel Central", 
                    "Costa Daurada Resort", "Hotel La Rambla", "Hotel Mediterrani", 
                    "Urban Stay", "Hotel del Port", "Hotel Montseny", "Skyline Suites"
                };

                String[] ciutats = {
                    "Barcelona", "Tarragona", "Girona", "Lleida", "València", "Saragossa"
                };

                String[] carrers = {
                    "Carrer Major", "Avinguda del Mar", "Passeig de Gràcia", 
                    "Rambla Nova", "Carrer del Port", "Carrer de la Pau"
                };

                Random random = new Random();

                // Crear 10 hotels
                for (String nom : nomsHotels) {
                    String ciutat = ciutats[random.nextInt(ciutats.length)];
                    String adreca = carrers[random.nextInt(carrers.length)] + ", " + (random.nextInt(200) + 1);

                    Hotel hotel = new Hotel(nom, adreca, ciutat);
                    hotelRepo.save(hotel);

                    // Crear entre 40 i 80 habitacions per hotel
                    int numRooms = 40 + random.nextInt(41);
                    for (int j = 1; j <= numRooms; j++) {
                        Room room = new Room();
                        room.setHotel(hotel);
                        room.setName("Habitació " + j);
                        room.setNumber(String.format("%03d", j));
                        room.setFloor((j - 1) / 10 + 1);
                        room.setIsAvailable(random.nextInt(100) < 80); // 80% disponibles
                        room.setRoomType(types.get(random.nextInt(types.size())));
                        roomRepo.save(room);
                    }
                }

                System.out.println("✅ Base de dades inicialitzada amb hotels i habitacions de prova.");
            }
        };
    }
}

