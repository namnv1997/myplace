package com.nvn.myplace.map.geocoding;

/**
 * Created by n on 08/11/2017.
 */

public class Result {

    private Geometry geometry;

    public Geometry getGeometry() {
        return geometry;
    }

    public Result setGeometry(Geometry geometry) {
        this.geometry = geometry;
        return this;
    }
}
