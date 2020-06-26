package com.skleznevco.thetribe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.skleznevco.thetribe.Adapter.BuildAdapter;
import com.skleznevco.thetribe.Adapter.ResourceAdapter;
import com.skleznevco.thetribe.Adapter.TribeAdapter;
import com.skleznevco.thetribe.Adapter.WorkAdapter;

import java.util.Objects;


public class GameActivity extends AppCompatActivity {
    private GameRules gameRules;
    private ResourceAdapter resourceAdapter;
    private WorkAdapter workAdapter;
    private BuildAdapter buildAdapter;
    private TribeAdapter tribeAdapter;
    private ResourceInterface resourceInterface;
    private Context context;

    private GameRules.Difficulty gameDifficulty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        gameDifficulty = convertStringToDifficulty(Objects.requireNonNull(
                intent.getStringExtra("Difficulty")));


        context = this;
        resourceInterface = new ResourceInterface() {
            @Override
            public void update() {
                resourceAdapter.update();
            }

            @Override
            public void setWorkers(int count) {
                GameRules.getResource().getHuman(Resource.ResourceType.WORKERS).addBusy(count);

            }
        };
        gameRules = new GameRules(gameDifficulty, resourceInterface);
        resourceAdapter = new ResourceAdapter(this);
        workAdapter = new WorkAdapter(this, resourceInterface);
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

                GameRules.makeTurn();


                ChanceDialog dialog = new ChanceDialog();
                dialog.show(getSupportFragmentManager(), "custom");

                resourceAdapter.notifyDataSetChanged();
            }
        });


    }

    private GameRules.Difficulty convertStringToDifficulty(String s) {
        switch (s) {
            case "MEDIUM":
                return GameRules.Difficulty.MEDIUM;
            case "HARD":
                return GameRules.Difficulty.HARD;
            default:
                return GameRules.Difficulty.EASY;
        }
    }


}
