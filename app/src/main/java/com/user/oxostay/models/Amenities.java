package com.user.oxostay.models;

public class Amenities {

    String amenetiesLabel,amenitiesImage,id;

    public Amenities(String amenetiesLabel, String amenitiesImage, String id) {
        this.amenetiesLabel = amenetiesLabel;
        this.amenitiesImage = amenitiesImage;
        this.id = id;
    }

    public Amenities() {
    }

    public String getAmenetiesLabel() {
        return amenetiesLabel;
    }

    public void setAmenetiesLabel(String amenetiesLabel) {
        this.amenetiesLabel = amenetiesLabel;
    }

    public String getAmenitiesImage() {
        return amenitiesImage;
    }

    public void setAmenitiesImage(String amenitiesImage) {
        this.amenitiesImage = amenitiesImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
