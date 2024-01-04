package cz.cvut.fit.tjv.kuchaj19.carleaseclient.model;

import java.util.Collection;
import java.util.HashSet;

public class CarDTO {
    Long id;
    String registrationPlate;
    Collection<Long> features;
    Long make;
    Long price;

    public CarDTO(Long id, String registrationPlate, Collection<Long> features, Long make, Long price) {
        this.id = id;
        this.registrationPlate = registrationPlate;
        this.features = features;
        this.make = make;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationPlate() {
        return registrationPlate;
    }

    public void setRegistrationPlate(String registrationPlate) {
        this.registrationPlate = registrationPlate;
    }

    public Collection<Long> getFeatures() {
        return features;
    }

    public void setFeatures(Collection<Long> features) {
        this.features = features;
    }

    public Long getMake() {
        return make;
    }

    public void setMake(Long make) {
        this.make = make;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Car toCar() {
        Collection<Feature> featureCollection = new HashSet<Feature>();
        if(features != null) {
            for (Long featureId :
                    features) {
                featureCollection.add(new Feature(featureId, null, null));
            }
        }
        if(make == null) {
            throw new IllegalArgumentException();
        }
        return new Car(id,registrationPlate,featureCollection,new Make(make, null, null), price);
    }
}