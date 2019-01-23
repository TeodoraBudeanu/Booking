package ro.siit.teo.booking.model;

import java.util.Objects;

public class AccommodationRoomRelation {

    public AccommodationRoomRelation() {
    }

    public AccommodationRoomRelation(int accommodationId, int roomFairId) {
        this.accommodationId = accommodationId;
        this.roomFairId = roomFairId;
    }

    private int id;
    private int accommodationId;
    private int roomFairId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccommodationId() {
        return accommodationId;
    }



    public void setAccommodationId(int accommodationId) {
        this.accommodationId = accommodationId;
    }

    public int getRoomFairId() {
        return roomFairId;
    }

    public void setRoomFairId(int roomFairId) {
        this.roomFairId = roomFairId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccommodationRoomRelation that = (AccommodationRoomRelation) o;
        return id == that.id &&
                accommodationId == that.accommodationId &&
                roomFairId == that.roomFairId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accommodationId, roomFairId);
    }

    @Override
    public String toString() {
        return "AccommodationRoomRelation{" +
                "id=" + id +
                ", accommodationId=" + accommodationId +
                ", roomFairId=" + roomFairId +
                '}';
    }
}
