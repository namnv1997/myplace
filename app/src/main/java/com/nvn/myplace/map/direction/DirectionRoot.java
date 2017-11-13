package com.nvn.myplace.map.direction;

import java.util.List;


/**
 * Created by n on 12/11/2017.
 */

public class DirectionRoot {

    public List<Route> routes;

    public List<Route> getRoutes() {
        return routes;
    }

    public DirectionRoot setRoutes(List<Route> routes) {
        this.routes = routes;
        return this;
    }
}
