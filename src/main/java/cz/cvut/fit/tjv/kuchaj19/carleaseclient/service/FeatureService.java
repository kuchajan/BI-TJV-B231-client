package cz.cvut.fit.tjv.kuchaj19.carleaseclient.service;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client.FeatureClient;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Feature;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Make;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class FeatureService {
    private final FeatureClient featureClient;

    public FeatureService(FeatureClient featureClient) {
        this.featureClient = featureClient;
    }

    public void setCurrentFeature(Long currentFeature) {
        featureClient.setCurrentClient(currentFeature);
    }

    public void setFilteredFeatures(Optional<Long> carId, Optional<Boolean> inverse) {
        featureClient.setFilteredClient(carId, inverse);
    }

    public Collection<Feature> readFiltered() {
        return featureClient.readFiltered();
    }
    public Optional<Feature> readOne() {
        return featureClient.getOne();
    }
    public Collection<Feature> readAll() {
        return featureClient.getAll();
    }
    public void create(Feature data) {
        featureClient.create(data);
    }
    public void update(Feature data) {
        featureClient.update(data);
    }

    public void delete() {
        featureClient.delete();
    }
}
