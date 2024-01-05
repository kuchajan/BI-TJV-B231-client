package cz.cvut.fit.tjv.kuchaj19.carleaseclient.service;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client.CarClient;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client.ReservationClient;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationClient reservationClient;


    public ReservationService(ReservationClient reservationClient) {
        this.reservationClient = reservationClient;
    }

    private Reservation convertFromDTO(ReservationDTO reservationDTO) {
        if(reservationDTO.getCarReserved() == null || reservationDTO.getReservationMaker() == null) {
            throw new IllegalArgumentException();
        }
        return new Reservation(reservationDTO.getId(),reservationDTO.getTimeStart(),reservationDTO.getTimeEnd(),
                new User(reservationDTO.getReservationMaker(), null, null, null),
                new Car(reservationDTO.getCarReserved(), null, null, null, null), reservationDTO.getPrice());
    }

    public void setCurrentReservation(Long id) {
        reservationClient.setCurrentClient(id);
    }

    public void setFilteredReservations(Optional<Long> user, Optional<Long> car) {
        reservationClient.setFilteredClient(user, car);
    }
    public Optional<Reservation> readOne() {
        return reservationClient.getOne();
    }
    public Collection<Reservation> readAll() {
        return reservationClient.getAll();
    }
    public Collection<Reservation> readFiltered() {
        return reservationClient.readFiltered();
    }
    public void create(ReservationDTO data) {
        reservationClient.create(convertFromDTO(data));
    }
    public void update(ReservationDTO data) {
        reservationClient.update(convertFromDTO(data));
    }

    public void delete() {
        reservationClient.delete();
    }
}
