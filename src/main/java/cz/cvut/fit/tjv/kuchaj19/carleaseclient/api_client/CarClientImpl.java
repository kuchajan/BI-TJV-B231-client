package cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Car;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.*;

@Component
public class CarClientImpl extends CrudClientImpl<Car> implements CarClient {
    private final String baseUrl;
    private final RestClient carClient;
    private RestClient currentCarClient;
    private RestClient filteredCarClient;

    CarClientImpl(@Value("${api.url}") String baseUrl) {
        this.baseUrl = baseUrl;
        carClient = RestClient.create(baseUrl + "/car");
    }


    @Override
    public void setFilteredClient(Optional<List<Long>> makeIds, Optional<List<Long>> featureIds, Optional<Long> minPrice, Optional<Long> maxPrice, Optional<Long> timeStart, Optional<Long> timeEnd) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl + "/car");
        filteredCarClient = RestClient.create(
                factory.builder()
                        .queryParamIfPresent("makeIds", makeIds)
                        .queryParamIfPresent("featureIds",featureIds)
                        .queryParamIfPresent("minPrice",minPrice)
                        .queryParamIfPresent("maxPrice",maxPrice)
                        .queryParamIfPresent("timeStart",timeStart)
                        .queryParamIfPresent("timeEnd",timeEnd)
                        .build().toString()
        );
    }

    @Override
    public Collection<Car> readFiltered() {
        return Arrays.asList(
                Objects.requireNonNull(filteredCarClient.get()
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntity(Car[].class)
                        .getBody())
        );
    }

    @Override
    public void setCurrentClient(Long id) {
        currentCarClient = RestClient.builder()
                .baseUrl(baseUrl + "/car/{id}")
                .defaultUriVariables(Map.of("id", id))
                .build();
    }

    @Override
    protected RestClient getClient() {
        return carClient;
    }

    @Override
    protected RestClient getCurrentClient() {
        return currentCarClient;
    }

    @Override
    protected Class<Car> getTClass() {
        return Car.class;
    }

    @Override
    protected Class<Car[]> getAClass() {
        return Car[].class;
    }
}
