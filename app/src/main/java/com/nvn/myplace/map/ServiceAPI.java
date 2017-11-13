package com.nvn.myplace.map;


import com.google.android.gms.maps.model.LatLng;
import com.nvn.myplace.map.direction.DirectionRoot;
import com.nvn.myplace.map.geocoding.GeocodingRoot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by n on 08/11/2017.
 */

public interface ServiceAPI {

    @GET("geocode/json?&key=AIzaSyBHwOTj7JwRT0u4zhXeyGCe9lTALYKI2oE")
    Call<GeocodingRoot> getLocation(@Query("address") String address);

    @GET("directions/json?&key=AIzaSyBHwOTj7JwRT0u4zhXeyGCe9lTALYKI2oE")
    Call<DirectionRoot> getDirection(@Query("origin") String origin,
                                     @Query("destination") String destination);
}
