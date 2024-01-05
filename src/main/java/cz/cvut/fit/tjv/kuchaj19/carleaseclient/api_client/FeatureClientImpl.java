package cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Feature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.*;

@Component
public class FeatureClientImpl extends CrudClientImpl<Feature> implements FeatureClient {
    private final String baseUrl;
    private final RestClient featureClient;
    private RestClient currentFeatureClient;
    private RestClient filteredFeatureClient;
    public FeatureClientImpl(@Value("${api.url}") String baseUrl) {
        this.baseUrl = baseUrl;
        featureClient = RestClient.create(baseUrl + "/feature");
    }
    @Override
    public void setCurrentClient(Long id) {
        currentFeatureClient = RestClient.builder()
                .baseUrl(baseUrl + "/feature/{id}")
                .defaultUriVariables(Map.of("id", id))
                .build();
    }

    @Override
    protected RestClient getClient() {
        return featureClient;
    }

    @Override
    protected RestClient getCurrentClient() {
        return currentFeatureClient;
    }

    @Override
    protected Class<Feature> getTClass() {
        return Feature.class;
    }

    @Override
    protected Class<Feature[]> getAClass() {
        return Feature[].class;
    }

    @Override
    public void setFilteredClient(Optional<Long> carId, Optional<Boolean> inverse) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl + "/feature");
        filteredFeatureClient = RestClient.create(
                factory.builder()
                        .queryParamIfPresent("carId", carId)
                        .queryParamIfPresent("inverse",inverse)
                        .build().toString()
        );
    }

    @Override
    public Collection<Feature> readFiltered() {
        return Arrays.asList(
                Objects.requireNonNull(filteredFeatureClient.get()
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntity(Feature[].class)
                        .getBody())
        );
    }
}
