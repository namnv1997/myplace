package com.nvn.myplace.map.geocoding;

import java.util.List;

/**
 * Created by n on 08/11/2017.
 */

public class GeocodingRoot {
    private List<Result> results;

    public String getStatus() {
        return status;
    }

    public GeocodingRoot setStatus(String status) {
        this.status = status;
        return this;
    }

    public String status;

    public List<Result> getResults() {
        return results;
    }

    public GeocodingRoot setResults(List<Result> results) {
        this.results = results;
        return this;
    }
}
