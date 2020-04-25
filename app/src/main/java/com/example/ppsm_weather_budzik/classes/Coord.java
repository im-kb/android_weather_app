package com.example.ppsm_weather_budzik.classes;

import java.io.Serializable;

public class Coord implements Serializable {
    private double lon;
    private double lat;

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }
}
