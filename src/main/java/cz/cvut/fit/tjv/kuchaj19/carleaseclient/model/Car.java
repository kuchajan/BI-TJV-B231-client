package cz.cvut.fit.tjv.kuchaj19.carleaseclient.model;

import java.util.Collection;

public class Car {
    Long id;
    String registrationPlate;
    Collection<Feature> features;
    Make make;
    Long price;

    public Car(Long id, String registrationPlate, Collection<Feature> features, Make make, Long price) {
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
    public Collection<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Collection<Feature> features) {
        this.features = features;
    }

    public Make getMake() {
        return make;
    }

    public void setMake(Make make) {
        this.make = make;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public boolean featureContained(Long featureId) {
        if(features == null || features.isEmpty() || featureId == null) {
            return false;
        }
        return features.stream().mapToLong(Feature::getId).anyMatch(featureId::equals);
    }
}
