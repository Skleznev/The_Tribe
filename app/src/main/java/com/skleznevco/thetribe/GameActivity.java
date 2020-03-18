package com.skleznevco.thetribe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

public class GameActivity extends AppCompatActivity {
    private GameRules gameRules;
    private Resource resource;
    private ResourceAdapter resourceAdapter;
    private WorkAdapter workAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameRules = new GameRules(GameRules.Difficulty.EASY);
        resource = new Resource(gameRules.getGameDifficulty());
        resourceAdapter = new ResourceAdapter(this, resource);
        workAdapter= new WorkAdapter(this);
        ListView listView = this.findViewById(R.id.list_resources);
        listView.setAdapter(resourceAdapter);

        GridView gridWork = this.findViewById(R.id.listCount);
        gridWork.setAdapter(workAdapter);
        gridWork.setNumColumns(workAdapter.getCount());


    }
}
