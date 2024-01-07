package cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Reservation;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.*;

// extends CrudClientImpl<User> implements UserClient
@Component
public class ReservationClientImpl extends CrudClientImpl<Reservation> implements ReservationClient {
    private final RestClient reservationClient;
    private RestClient currentReservationClient;
    private RestClient filteredReservationClient;

    private final String baseUrl;

    ReservationClientImpl(@Value("${api.url}") String baseUrl) {
        this.baseUrl = baseUrl;
        reservationClient = RestClient.create(baseUrl + "/reservation");
    }

    @Override
    public void setCurrentClient(Long id) {
        currentReservationClient = RestClient.builder()
                .baseUrl(baseUrl + "/reservation/{id}")
                .defaultUriVariables(Map.of("id", id))
                .build();
    }

    @Override
    protected RestClient getClient() {
        return reservationClient;
    }

    @Override
    protected RestClient getCurrentClient() {
        return currentReservationClient;
    }

    @Override
    protected Class<Reservation> getTClass() {
        return Reservation.class;
    }

    @Override
    protected Class<Reservation[]> getAClass() {
        return Reservation[].class;
    }

    @Override
    public void setFilteredClient(Optional<Long> user, Optional<Long> car) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl + "/reservation");
        filteredReservationClient = RestClient.create(
                factory.builder()
                        .queryParamIfPresent("user", user)
                        .queryParamIfPresent("car", car)
                        .build().toString()
        );
    }

    public Collection<Reservation> readFiltered() {
        return Arrays.asList(
                Objects.requireNonNull(filteredReservationClient.get()
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntity(Reservation[].class)
                        .getBody())
        );
    }
}
