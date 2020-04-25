package com.example.ppsm_weather_budzik.classes;

import java.io.Serializable;

public class Main implements Serializable {
    private double temp;
    private int pressure;
    private int humidity;
    private double temp_min;
    private double temp_max;

    public double getTemp() {
        return temp;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getTemp_min() {
        return temp_min;
    }


    public double getTemp_max() {
        return temp_max;
    }
}
