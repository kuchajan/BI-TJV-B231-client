package cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Car;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CarClient extends CrudClient<Car> {
    void setFilteredClient (Optional<List<Long>> makeIds, Optional<List<Long>> featureIds, Optional<Long> minPrice, Optional<Long> maxPrice, Optional<Long> timeStart, Optional<Long> timeEnd);
    Collection<Car> readFiltered();
}
