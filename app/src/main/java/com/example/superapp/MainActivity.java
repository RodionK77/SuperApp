package com.example.superapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String URL = "https://cbr.ru/";//"https://api.openweathermap.org/data/2.5/weather";
    private final String API_KEY = "b2066df8d7429d41fa027fb5f77d8540";
    DecimalFormat df = new DecimalFormat("#.##");
    TextView rates;
    private Thread secThread;
    private Runnable runnable;
    ImageView iv1, iv2, iv3;
    String str_rates = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv1 = findViewById(R.id.iv_qr1);
        iv2 = findViewById(R.id.iv_qr2);
        iv3 = findViewById(R.id.iv_qr3);
        //getWeather();
        getExchangeRate();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        rates = findViewById(R.id.tv_weather);

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/a.aaura"));
                startActivity(browserIntent);
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/k_rodion77"));
                startActivity(browserIntent);
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/id210928139"));
                startActivity(browserIntent);
            }
        });
    }

    public void getExchangeRate(){
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Parsing();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        secThread = new Thread(runnable);
        secThread.start();
    }

    public void Parsing() throws IOException {
        Document doc = Jsoup.connect(URL).get();
        String usd = doc.getElementsByClass("col-md-2 col-xs-9 _dollar").next().next().text();
        String eur = doc.getElementsByClass("col-md-2 col-xs-9 _euro").next().next().text();
        String date = doc.getElementsByClass("col-md-2 col-xs-7").next().next().text();
        str_rates += "Курсы валют на " + date + ":\n" + "USD: " + usd + "; EUR: " + eur;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rates.setText(str_rates);
            }
        });
    }

    /*public void getWeather(){
        String tempUrl = "";
        String city = "Moscow";
        String country = "Russia";
        tempUrl = URL + "?q=" + city + "," + country + "&appid=" + API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                String output = "";
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String description = jsonObjectWeather.getString("description");
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp") - 273.15;
                    double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                    float pressure = jsonObjectMain.getInt("pressure");
                    int humidity = jsonObjectMain.getInt("humidity");
                    JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                    String wind = jsonObjectWind.getString("speed");
                    JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                    String clouds = jsonObjectClouds.getString("all");
                    JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                    String countryName = jsonObjectSys.getString("country");
                    String cityName = jsonResponse.getString("name");
                    output += "Текущая погода в Москве " + "\n Температура: " + df.format(temp) + " °C" + "; Влажность: " + humidity + "%" + "; Скорость ветра: " + wind + "м/с";
                    weather.setText(output);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }*/

        public void onGames (View view){
            Intent intent = new Intent(getApplicationContext(), GamesActivity.class);
            startActivity(intent);
        }

        public void onComponents (View view){
            Intent intent = new Intent(getApplicationContext(), ComponentsActivity.class);
            startActivity(intent);
        }

        public void onAudio (View view){
            Intent intent = new Intent(getApplicationContext(), AudioActivity.class);
            startActivity(intent);
        }
}