package com.nvn.myplace.map.geocoding;

/**
 * Created by n on 08/11/2017.
 */

public class Location {
    private double lat;
    private double lng;

    public double getLat() {
        return lat;
    }

    public Location(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Location setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public Location setLng(double lng) {
        this.lng = lng;
        return this;
    }
}
