package com.example.superapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComponentsActivity extends AppCompatActivity {
    private final String URL = "https://4pda.to/tag/pc/";
    private RecyclerView gameList;
    private RecycleAdapter recycleAdapter;
    private Thread secThread;
    private Runnable runnable;
    List<GameCard> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_components);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }

    private void init(){
        gameList = (RecyclerView) findViewById(R.id.rv_components);
        list = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        gameList.setLayoutManager(layoutManager);
        gameList.setHasFixedSize(true);
        recycleAdapter = new RecycleAdapter(list);
        gameList.setAdapter(recycleAdapter);
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
        Document doc  = Jsoup.connect(URL).get();
        Elements titles = doc.getElementsByAttributeValue("itemprop", "url");
        Elements dates = doc.getElementsByClass("date");
        Elements desc = doc.getElementsByAttributeValue("itemprop", "description");
        Elements images = doc.getElementsByClass("j3deSFAjz0 j3deSFAjz0");
        for(int i = 0; i < titles.size();i++){
            GameCard game = new GameCard(titles.get(i).attr("title"), desc.get(i).text(), dates.get(i).text(), images.get(i).child(0).absUrl("src"));
            list.add(game);
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recycleAdapter.notifyDataSetChanged();
            }
        });
        /*System.out.println(titles.get(0).attr("title"));
        System.out.println(dates.get(0).text());
        System.out.println(desc.get(0).text());
        System.out.println(images.get(0).child(0).absUrl("src"));*/
    }
}

