package cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Feature;

import java.util.Collection;
import java.util.Optional;

public interface FeatureClient extends CrudClient<Feature> {
    void setFilteredClient (Optional<Long> carId, Optional<Boolean> inverse);
    Collection<Feature> readFiltered();
}
