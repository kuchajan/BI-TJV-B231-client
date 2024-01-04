package cz.cvut.fit.tjv.kuchaj19.carleaseclient.service;

import cz.cvut.fit.tjv.kuchaj19.carleaseclient.api_client.CarClient;
import cz.cvut.fit.tjv.kuchaj19.carleaseclient.model.Car;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarClient carClient;

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
    public void create(Car data) {
        carClient.create(data);
    }
    public void update(Car data) {
        carClient.update(data);
    }

    public void delete() {
        carClient.delete();
    }
}
