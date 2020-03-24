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
    private BuildAdapter buildAdapter;
    private TribeAdapter tribeAdapter;
    private int[] workersCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        workersCount = new int[4];
        gameRules = new GameRules(GameRules.Difficulty.EASY);
        resource = new Resource(gameRules.getGameDifficulty());
        resourceAdapter = new ResourceAdapter(this, resource);
        workAdapter= new WorkAdapter(this, workersCount, resourceAdapter, (Human) resource.getItem(Resource.ResourceType.WORKERS));
        tribeAdapter = new TribeAdapter(this);
        buildAdapter = new BuildAdapter(this);
        ListView listView = this.findViewById(R.id.list_resources);
        listView.setAdapter(resourceAdapter);

        GridView gridWork = this.findViewById(R.id.listCount);
        gridWork.setAdapter(workAdapter);
        gridWork.setNumColumns(workAdapter.getCount());

        ListView tribe = this.findViewById(R.id.list_tribes);
        tribe.setAdapter(tribeAdapter);

        GridView gridBuilds = this.findViewById(R.id.grid_builds);
        gridBuilds.setAdapter(buildAdapter);
        gridBuilds.setNumColumns(3);


    }

}
