package com.skleznevco.thetribe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.skleznevco.thetribe.Adapter.BuildAdapter;
import com.skleznevco.thetribe.Adapter.ResourceAdapter;
import com.skleznevco.thetribe.Adapter.TribeAdapter;
import com.skleznevco.thetribe.Adapter.WorkAdapter;


public class GameActivity extends AppCompatActivity {
    private GameRules gameRules;
    private ResourceAdapter resourceAdapter;
    private WorkAdapter workAdapter;
    private BuildAdapter buildAdapter;
    private TribeAdapter tribeAdapter;
    private ResourceInterface resourceInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameRules = new GameRules(GameRules.Difficulty.EASY);
        resourceInterface = new ResourceInterface() {
            @Override
            public void update() {
                resourceAdapter.update();
            }

            @Override
            public void setWorkers() {

            }
        };

        resourceAdapter = new ResourceAdapter(this);
        workAdapter= new WorkAdapter(this, resourceInterface);
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
        gridBuilds.setNumColumns(4);

        Button button_turn = findViewById(R.id.turn);
        button_turn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                resourceAdapter.notifyDataSetChanged();
            }
        });


    }



}
