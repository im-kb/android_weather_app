package com.example.ppsm_weather_budzik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ppsm_weather_budzik.classes.WeatherData;

public class MainActivity extends AppCompatActivity {

    private Button checkWeatherButton;
    private EditText cityNameInput;
    private TextView error;
    private WeatherData weather;
    private String cityName;
    private final String base = "http://api.openweathermap.org/";
    private final String apiid = "749561a315b14523a8f5f1ef95e45864";
    private final String units= "metric";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityNameInput = findViewById(R.id.cityNameInput);
        error = findViewById(R.id.errorTextView);
        getUsersLastCity();
    }
    private void getUsersLastCity(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences",MODE_PRIVATE);
        cityName = sharedPreferences.getString("CITY_KEY","");
        cityNameInput.setText(cityName);

    }
}


