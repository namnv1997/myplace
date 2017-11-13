package com.nvn.myplace.data.model;

import android.text.TextUtils;

import org.w3c.dom.Text;

/**
 * Created by n on 05/11/2017.
 */

public class Place {

    private String placeID;
    private String categoryID;
    private byte[] placeImage;
    private String placeName;
    private String placeAddress;
    private String placeDescription;
    private Double placeLat;
    private Double placeLng;

    public Place(Builder builder) {
        this.placeID = builder.placeID;
        this.categoryID = builder.categoryID;
        this.placeImage = builder.placeImage;
        this.placeName = builder.placeName;
        this.placeAddress = builder.placeAddress;
        this.placeDescription = builder.placeDescription;
        this.placeLat = builder.placeLat;
        this.placeLng = builder.placeLng;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getPlaceID() {
        return placeID;
    }

    public byte[] getPlaceImage() {
        return placeImage;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public Double getPlaceLat() {
        return placeLat;
    }

    public Double getPlaceLng() {
        return placeLng;
    }


    public static class Builder {
        private String placeID;
        private String categoryID;
        private byte[] placeImage;
        private String placeName;
        private String placeAddress;
        private String placeDescription;
        private Double placeLat;
        private Double placeLng;

        public Builder setCategoryID(String categoryID) {
            this.categoryID = categoryID;
            return this;
        }

        public Builder setPlaceID(String placeID) {
            this.placeID = placeID;
            return this;
        }

        public Builder setPlaceImage(byte[] placeImage) {
            this.placeImage = placeImage;
            return this;
        }

        public Builder setPlaceName(String placeName) {
            this.placeName = placeName;
            return this;
        }

        public Builder setPlaceAddress(String placeAddress) {
            this.placeAddress = placeAddress;
            return this;
        }

        public Builder setPlaceDescription(String placeDescription) {
            this.placeDescription = placeDescription;
            return this;
        }

        public Builder setPlaceLat(Double placeLat) {
            this.placeLat = placeLat;
            return this;
        }

        public Builder setPlaceLng(Double placeLng) {
            this.placeLng = placeLng;
            return this;
        }

        public Place build() {
            return new Place(this);
        }
    }

    public static boolean validateInput(String placeName, String placeAddress, String placeDescription, String categoryID){
        return (TextUtils.isEmpty(placeName)
                || TextUtils.isEmpty(placeAddress)
                || TextUtils.isEmpty(placeDescription)
                || TextUtils.isEmpty(categoryID)) ? false : true;
    }
}
