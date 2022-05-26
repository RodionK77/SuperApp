package com.example.superapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
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
    TextView rates, title;
    private Thread secThread;
    private Runnable runnable;
    ImageView iv1, iv2, iv3;
    String str_rates = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.tv_title);
        rates = findViewById(R.id.tv_weather);
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode  == Configuration.UI_MODE_NIGHT_YES){
            title.setTextColor(Color.parseColor("#dadada"));
            rates.setTextColor(Color.parseColor("#dadada"));
        }
        iv1 = findViewById(R.id.iv_qr1);
        iv2 = findViewById(R.id.iv_qr2);
        iv3 = findViewById(R.id.iv_qr3);
        getExchangeRate();
        /*ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }*/

        /**
         * Ниже представленны несоклько setOnClickListener-ов
         * Они нужны для обработки нажатий на QR-коды
         * При нажатии открывается браузер с ВК страничкой разработчика
         */
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

    /**
     * Ниже начинается парсинг сайта, для получения курса валют
     * Создаётся побочный поток для работы с сетью
     * Метод Parsing непосредственно парсит данные
     */
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

    // непосредственно парсинг
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

    // обрабатываем нажатия на кнопки
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