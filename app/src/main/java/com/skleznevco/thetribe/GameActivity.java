package com.skleznevco.thetribe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GameActivity extends AppCompatActivity {
    private GameRules gameRules;
    private Resource resource;
    private ResourceAdapter resourceAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameRules = new GameRules(GameRules.Difficulty.EASY);
        resource = new Resource(gameRules.getGameDifficulty());
        resourceAdapter = new ResourceAdapter(this, resource);

        ListView listView = this.findViewById(R.id.list_resources);
        listView.setAdapter(resourceAdapter);

    }
}
