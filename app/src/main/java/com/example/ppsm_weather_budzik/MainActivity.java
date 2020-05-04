package com.example.ppsm_weather_budzik;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ppsm_weather_budzik.classes.WeatherData;

import java.io.UnsupportedEncodingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText cityNameInput;
    private TextView error;
    private WeatherData weather;
    private String cityName;
    private final String base = "http://api.openweathermap.org/";
    private final String apiid = "749561a315b14523a8f5f1ef95e45864";
    private final String units = "metric";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityNameInput = findViewById(R.id.cityNameInput);
        error = findViewById(R.id.errorTextView);
        getUsersLastCity();
    }

    public void getWeatherData(View view) throws UnsupportedEncodingException {
        if (checkNetworkConnection()) {
            cityName = cityNameInput.getText().toString();
            intent = new Intent(this, WeatherActivity.class);
            intent.putExtra("CITY_NAME", cityName);
            System.out.println(cityName);
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/").
                    addConverterFactory(GsonConverterFactory.create()).build();

            JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);
            Call<WeatherData> call = jsonPlaceholderAPI.getMainWeatherClassCall(cityName + ",pl", apiid, units);
            call.enqueue(new Callback<WeatherData>() {
                @Override
                public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                    if (!response.isSuccessful()) {
                        System.out.println("Code: " + response.code());
                        error.setText("Błąd!");
                        return;
                    }
                    weather = response.body();
                    intent.putExtra("WEATHER_CLASS", weather);
                    startActivity(intent);
                    saveUsersLastCity(cityName);
                    error.setText("");
                }

                @Override
                public void onFailure(Call<WeatherData> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });
        } else {
            error.setText("Brak połączenia z internetem!");
        }
    }

    private void saveUsersLastCity(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CITY_KEY", name);
        editor.apply();
    }

    private void getUsersLastCity() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        cityName = sharedPreferences.getString("CITY_KEY", "");
        cityNameInput.setText(cityName);
    }

    public static Call<WeatherData> findData(String city) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/").
                addConverterFactory(GsonConverterFactory.create()).build();

        JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);
        Call<WeatherData> call = jsonPlaceholderAPI.getMainWeatherClassCall(city + ",pl", "749561a315b14523a8f5f1ef95e45864", "metric");
        return call;
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


