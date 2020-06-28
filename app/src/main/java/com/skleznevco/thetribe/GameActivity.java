package com.skleznevco.thetribe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.skleznevco.thetribe.Adapter.BuildAdapter;
import com.skleznevco.thetribe.Adapter.ResourceAdapter;
import com.skleznevco.thetribe.Adapter.WarAdapter;
import com.skleznevco.thetribe.Adapter.WorkAdapter;
import com.skleznevco.thetribe.Dialog.ChangeCountDialog;

import java.util.Objects;


public class GameActivity extends AppCompatActivity {
    private GameRules gameRules;
    private ResourceAdapter resourceAdapter;
    private WorkAdapter workAdapter;
    private BuildAdapter buildAdapter;
    private WarAdapter warAdapter;
    private ResourceInterface resourceInterface;
    private ProgressBar progressBar;
    private Context context;
    private Button shield;

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
                buildAdapter.notifyDataSetChanged();
                workAdapter.update();

            }

            @Override
            public void setWorkers(int count) {
                GameRules.getResource().getHuman(Resource.ResourceType.WORKERS).addBusy(count);

            }

            @Override
            public void setMilitary(int count) {
                GameRules.getResource().getHuman(Resource.ResourceType.MILITARY).addBusy(count - Integer.parseInt(GameRules.getResource().getHuman(Resource.ResourceType.MILITARY).getBusy()));
            }

            @Override
            public double getLoyalty() {
                return progressBar.getProgress() * 0.01;
            }

            @Override
            public void resetSheild() {
                shield.setText("00");
            }

        };
        gameRules = new GameRules(gameDifficulty, resourceInterface);
        resourceAdapter = new ResourceAdapter(this);
        workAdapter = new WorkAdapter(this, resourceInterface);
        warAdapter = new WarAdapter(this);
        buildAdapter = new BuildAdapter(this);
        ListView listView = this.findViewById(R.id.list_resources);
        listView.setAdapter(resourceAdapter);

        GridView gridWork = this.findViewById(R.id.listCount);
        gridWork.setAdapter(workAdapter);
        gridWork.setNumColumns(workAdapter.getCount());

        GridView gridWar = this.findViewById(R.id.grid_war);
        gridWar.setAdapter(warAdapter);
        gridWar.setNumColumns(8);

        GridView gridBuilds = this.findViewById(R.id.grid_builds);
        gridBuilds.setAdapter(buildAdapter);
        gridBuilds.setNumColumns(4);

        progressBar = this.findViewById(R.id.progressBar);

        Button button_turn = findViewById(R.id.turn);
        button_turn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (GameRules.getResource().getBusyPopulation() / GameRules.getResource().getPopulation() <= GameRules.getloyalty() / 100) {
                    progressBar.setProgress(progressBar.getProgress() + 10);
                } else progressBar.setProgress(progressBar.getProgress() - 10);

                GameRules.makeTurn();


                ChanceDialog dialog = new ChanceDialog();
                dialog.show(getSupportFragmentManager(), "custom");
                warAdapter.addTurn();
                resourceAdapter.notifyDataSetChanged();
            }
        });
        shield = findViewById(R.id.shield);
        shield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeCountDialog changeCountDialog = new ChangeCountDialog(context, resourceInterface, shield, -1);
                changeCountDialog.setMaxSeek(String.valueOf(Integer.parseInt(GameRules.getResource().getHuman(Resource.ResourceType.MILITARY).getTotal())));
                changeCountDialog.setCurrentCount(Integer.parseInt(GameRules.getResource().getHuman(Resource.ResourceType.MILITARY).getBusy()));
                changeCountDialog.show();
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
