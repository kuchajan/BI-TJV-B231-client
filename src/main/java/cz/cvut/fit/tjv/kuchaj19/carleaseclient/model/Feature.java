package cz.cvut.fit.tjv.kuchaj19.carleaseclient.model;

public class Feature {
    private Long id;
    private String name;
    private Long priceIncrease;

    public Feature(Long id, String name, Long priceIncrease) {
        this.id = id;
        this.name = name;
        this.priceIncrease = priceIncrease;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPriceIncrease() {
        return priceIncrease;
    }

    public void setPriceIncrease(Long priceIncrease) {
        this.priceIncrease = priceIncrease;
    }
}
