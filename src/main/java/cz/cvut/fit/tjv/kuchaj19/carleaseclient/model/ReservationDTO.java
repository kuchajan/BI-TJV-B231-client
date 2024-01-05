package cz.cvut.fit.tjv.kuchaj19.carleaseclient.model;

public class ReservationDTO {
    Long id;
    Long timeStart;
    Long timeEnd;
    Long reservationMaker;
    Long carReserved;
    Long price;

    public ReservationDTO(Long id, Long timeStart, Long timeEnd, Long reservationMaker, Long carReserved, Long price) {
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

    public Long getReservationMaker() {
        return reservationMaker;
    }

    public void setReservationMaker(Long reservationMaker) {
        this.reservationMaker = reservationMaker;
    }

    public Long getCarReserved() {
        return carReserved;
    }

    public void setCarReserved(Long carReserved) {
        this.carReserved = carReserved;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
