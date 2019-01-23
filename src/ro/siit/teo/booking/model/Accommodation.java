package ro.siit.teo.booking.model;

import java.util.Objects;

public class Accommodation {

    private int id;
    private String type;
    private String bedType;
    private int maxGuests;
    private String description;

    public Accommodation(){

    }

    public Accommodation(String type, String bedType, int maxGuests, String description) {
        this.type = type;
        this.bedType = bedType;
        this.maxGuests = maxGuests;
        this.description = description;
    }

    public Accommodation(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accommodation that = (Accommodation) o;
        return id == that.id &&
                maxGuests == that.maxGuests &&
                Objects.equals(type, that.type) &&
                Objects.equals(bedType, that.bedType) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, bedType, maxGuests, description);
    }

    @Override
    public String toString() {
        return "Accommodation{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", bedType='" + bedType + '\'' +
                ", maxGuests=" + maxGuests +
                ", description='" + description + '\'' +
                '}';
    }
}
