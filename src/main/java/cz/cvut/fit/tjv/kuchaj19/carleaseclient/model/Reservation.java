package cz.cvut.fit.tjv.kuchaj19.carleaseclient.model;

public class Reservation {
    Long id;
    Long timeStart;
    Long timeEnd;
    User reservationMaker;
    Car carReserved;
    Long price;

    public Reservation(Long id, Long timeStart, Long timeEnd, User reservationMaker, Car carReserved, Long price) {
        this.id = id;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.reservationMaker = reservationMaker;
        this.carReserved = carReserved;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Long timeStart) {
        this.timeStart = timeStart;
    }

    public Long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public User getReservationMaker() {
        return reservationMaker;
    }

    public void setReservationMaker(User reservationMaker) {
        this.reservationMaker = reservationMaker;
    }

    public Car getCarReserved() {
        return carReserved;
    }

    public void setCarReserved(Car carReserved) {
        this.carReserved = carReserved;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
