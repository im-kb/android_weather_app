package com.example.ppsm_weather_budzik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ppsm_weather_budzik.classes.WeatherData;
import com.squareup.picasso.Picasso;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class WeatherActivity extends AppCompatActivity {

    private String iconUrl = "https://openweathermap.org/img/wn/";
    private String location;
    private TextView cityName, time, temperatureValue, pressureValue, humidityValue, windSpeed;
    private WeatherData weather;
    private ImageView icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);


        Intent intent = getIntent();
        location = intent.getStringExtra("CITY_NAME");
        weather = (WeatherData) intent.getSerializableExtra("WEATHER_CLASS");
        iconUrl = iconUrl + weather.getWeather()[0].getIcon() + "@2x.png";
        System.out.println(iconUrl);

        cityName = findViewById(R.id.cityName);
        time = findViewById(R.id.time);
        temperatureValue = findViewById(R.id.temperatureValue);
        pressureValue = findViewById(R.id.pressureValue);
        humidityValue = findViewById(R.id.humidityValue);
        icon = findViewById(R.id.weatherIcon);
        time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        windSpeed = findViewById(R.id.windSpeedLevel);

        Picasso.get().load(iconUrl).error(R.drawable.error).into(icon);
        cityName.setText(location);
        temperatureValue.setText(Double.toString(weather.getMain().getTemp()) + " °C");
        pressureValue.setText(Integer.toString(weather.getMain().getPressure()) + " hPa");
        humidityValue.setText(Integer.toString(weather.getMain().getHumidity()) + " %");
        windSpeed.setText(Double.toString(weather.getWind().getSpeed()) + " km/h");
    }

}

