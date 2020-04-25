package com.example.ppsm_weather_budzik.classes;

import java.io.Serializable;

public class Wind implements Serializable {
    private double speed;
    private double deg;

    public double getSpeed() {
        return speed;
    }

    public double getDeg() {
        return deg;
    }
}
