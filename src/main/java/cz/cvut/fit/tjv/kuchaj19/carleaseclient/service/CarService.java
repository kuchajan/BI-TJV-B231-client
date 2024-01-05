package cz.cvut.fit.tjv.kuchaj19.carleaseclient.service;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client.CarClient;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Car;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.CarDTO;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Feature;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Make;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarClient carClient;
    private final Car convertFromDTO(CarDTO cardto) {
        Collection<Feature> featureCollection = new HashSet<Feature>();
        if(cardto.getFeatures() != null) {
            for (Long featureId :
                    cardto.getFeatures()) {
                featureCollection.add(new Feature(featureId, null, null));
            }
        }
        if(cardto.getMake() == null) {
            throw new IllegalArgumentException();
        }
        return new Car(cardto.getId(), cardto.getRegistrationPlate(), featureCollection,new Make(cardto.getMake(), null, null), cardto.getPrice());
    }

    public CarService(CarClient carClient) {
        this.carClient = carClient;
    }

    public void setCurrentCar(Long id) {
        carClient.setCurrentClient(id);
    }

    public void setFilteredCars(Optional<List<Long>> makeIds, Optional<List<Long>> featureIds, Optional<Long> minPrice, Optional<Long> maxPrice, Optional<Long> timeStart, Optional<Long> timeEnd) {
        carClient.setFilteredClient(makeIds, featureIds, minPrice, maxPrice, timeStart, timeEnd);
    }
    public Optional<Car> readOne() {
        return carClient.getOne();
    }
    public Collection<Car> readAll() {
        return carClient.getAll();
    }
    public Collection<Car> readFiltered() {
        return carClient.readFiltered();
    }
    public void create(CarDTO data) {
        carClient.create(convertFromDTO(data));
    }
    public void update(CarDTO data) {
        carClient.update(convertFromDTO(data));
    }

    public void delete() {
        carClient.delete();
    }
}
