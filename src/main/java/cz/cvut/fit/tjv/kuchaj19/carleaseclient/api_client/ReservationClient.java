package cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Reservation;

import java.util.Collection;
import java.util.Optional;

public interface ReservationClient extends CrudClient<Reservation> {
    void setFilteredClient (Optional<Long> user, Optional<Long> car);
    Collection<Reservation> readFiltered();
}
