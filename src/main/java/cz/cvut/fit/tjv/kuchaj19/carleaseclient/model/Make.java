package cz.cvut.fit.tjv.kuchaj19.carleaseclient.model;

public class Make {
    Long id;
    String name;
    Long baseRentPrice;

    public Make(Long id, String name, Long baseRentPrice) {
        this.id = id;
        this.name = name;
        this.baseRentPrice = baseRentPrice;
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

    public Long getBaseRentPrice() {
        return baseRentPrice;
    }

    public void setBaseRentPrice(Long baseRentPrice) {
        this.baseRentPrice = baseRentPrice;
    }
}
