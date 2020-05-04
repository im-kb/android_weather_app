package com.example.ppsm_weather_budzik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ppsm_weather_budzik.classes.WeatherData;
import com.squareup.picasso.Picasso;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    private String iconUrl = "https://openweathermap.org/img/wn/";
    private String location;
    private TextView cityName, time, temperatureValue, pressureValue, humidityValue, windSpeed;
    private WeatherData weatherData;
    private ImageView icon;
    SwipeRefreshLayout swipe;
    Toast errorToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Intent intent = getIntent();
        location = intent.getStringExtra("CITY_NAME");
        weatherData = (WeatherData) intent.getSerializableExtra("WEATHER_CLASS");
        iconUrl = iconUrl + weatherData.getWeather()[0].getIcon() + "@2x.png";
        System.out.println(iconUrl);

        cityName = findViewById(R.id.cityName);
        time = findViewById(R.id.time);
        temperatureValue = findViewById(R.id.temperatureValue);
        pressureValue = findViewById(R.id.pressureValue);
        humidityValue = findViewById(R.id.humidityValue);
        icon = findViewById(R.id.weatherIcon);
        windSpeed = findViewById(R.id.windSpeedLevel);
        swipe = findViewById(R.id.swipeRefresh);
        updateWeatherData(weatherData);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (checkNetworkConnection()) {
                    refreshData();
                    swipe.setRefreshing(false);
                    errorToast = Toast.makeText(getApplicationContext(), "Pobrano nowe informacje o pogodzie.", Toast.LENGTH_SHORT);
                    errorToast.setGravity(Gravity.TOP, 0, 200);
                    errorToast.show();
                } else {
                    errorToast = Toast.makeText(getApplicationContext(), "Brak połączenia z internetem!", Toast.LENGTH_SHORT);
                    errorToast.setGravity(Gravity.TOP, 0, 200);
                    errorToast.show();
                    swipe.setRefreshing(false);
                }
            }
        });

        Thread thread = new Thread() {
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if (checkNetworkConnection()) {
                                    refreshData();
                                    errorToast = Toast.makeText(getApplicationContext(), "Automatycznie pobrano nowe informacje o pogodzie.", Toast.LENGTH_SHORT);
                                    errorToast.setGravity(Gravity.TOP, 0, 200);
                                    errorToast.show();
                                } else {
                                    errorToast = Toast.makeText(getApplicationContext(), "Brak połączenia z internetem!", Toast.LENGTH_SHORT);
                                    errorToast.setGravity(Gravity.TOP, 0, 200);
                                    errorToast.show();
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    System.out.println("ERROR");
                }
            }
        };
        thread.start();
    }

    private void refreshData() {
        Call<WeatherData> call = MainActivity.findData((String) cityName.getText());
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                weatherData = response.body();
                updateWeatherData(weatherData);
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                System.out.println("error");
            }
        });
    }

    public void updateWeatherData(WeatherData weather) {
        Picasso.get().load(iconUrl).error(R.drawable.error).into(icon);
        cityName.setText(location);
        temperatureValue.setText(Double.toString(weather.getMain().getTemp()) + " °C");
        pressureValue.setText(Integer.toString(weather.getMain().getPressure()) + " hPa");
        humidityValue.setText(Integer.toString(weather.getMain().getHumidity()) + " %");
        windSpeed.setText(Double.toString(weather.getWind().getSpeed()) + " km/h");
        time.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

    }

    public boolean checkNetworkConnection() {
        boolean wifi = false;
        boolean mobile = false;
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mWifi.isConnected()) {
            wifi = true;
        }
        if (mMobile.isConnected()) {
            mobile = true;
        }
        return wifi || mobile;
    }
}