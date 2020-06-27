package com.skleznevco.thetribe.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.skleznevco.thetribe.Adapter.BuildingDialogAdapter;
import com.skleznevco.thetribe.Building;
import com.skleznevco.thetribe.Builds;
import com.skleznevco.thetribe.GameRules;
import com.skleznevco.thetribe.R;
import com.skleznevco.thetribe.Resource;

import java.lang.reflect.Array;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class BuildingDialog extends Dialog {
    @SuppressLint("ResourceType")
    public BuildingDialog(@NonNull Context context, Builds.BuildingType type) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        final Building building = GameRules.getBuilds().getBuilding(type);
        View layout = inflater.inflate(R.layout.builds_dialog, null);

        BuildingDialogAdapter adapter = new BuildingDialogAdapter(context, type);
        final TextView textView = layout.findViewById(R.id.build_name);
        textView.setText(building.getName());
        GridView gridView = layout.findViewById(R.id.dialog_grid_builds);
        gridView.setAdapter(adapter);

        Button button = layout.findViewById(R.id.upgrade);

            if (GameRules.canPay(building.getCostUpgrade(building.getLevel()+1)) && (building.getLevel()<GameRules.getBuilds().getBuilding(Builds.BuildingType.TOWN_HALL).getLevel()||building.getName().equals("Ратуша"))) {
                button.setBackgroundColor(Color.parseColor("#00FF00"));
            } else {
                button.setBackgroundColor(Color.parseColor("#FF0000"));
            }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GameRules.canPay(building.getCostUpgrade(building.getLevel()))&& (building.getLevel()<GameRules.getBuilds().getBuilding(Builds.BuildingType.TOWN_HALL).getLevel()||building.getName().equals("Ратуша")) ){
                    building.levelUp();
                    GameRules.updateResourse();
                    cancel();
                }
            }
        });

        LayoutInflater factory = LayoutInflater.from(layout.getContext());
        LinearLayout buildLayout = layout.findViewById(R.id.build_layout);

        switch (type){
            case FAIR:
                buildLayout.addView(factory.inflate(R.layout.fair_layout,null));
                final SeekBar FR_seekbar = buildLayout.findViewById(R.id.FR_seekbar);
                final TextView FR_text_left = buildLayout.findViewById(R.id.FR_text_left);
                final TextView FR_text_right= buildLayout.findViewById(R.id.FR_text_right);
                final Spinner FR_spinner_left = buildLayout.findViewById(R.id.FR_spinner_left);
                final Spinner FR_spinner_right = buildLayout.findViewById(R.id.FR_spinner_right);

                Button FR_button=buildLayout.findViewById(R.id.FR_button);
                if (GameRules.getBuilds().getBuilding(Builds.BuildingType.FAIR).getLevel()==0) FR_button.setEnabled(false);

                ArrayAdapter<String> FR_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, new String[]{"Food", "Stone", "Wood", "Gold"});
                FR_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                FR_spinner_left.setAdapter(FR_adapter);
                FR_spinner_left.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        FR_seekbar.setMax(GameRules.getResource().getItem(GameRules.stringToType(FR_spinner_left.getSelectedItem().toString())).getTotalInt());
                        FR_text_left.setText("0");
                        FR_text_right.setText("0");
                        FR_seekbar.setProgress(0);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                FR_spinner_right.setAdapter(FR_adapter);

                FR_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GameRules.getResource().getItem(GameRules.stringToType(FR_spinner_left.getSelectedItem().toString())).minusTotal(Integer.parseInt(FR_text_left.getText().toString()));
                        GameRules.getResource().getItem(GameRules.stringToType(FR_spinner_right.getSelectedItem().toString())).addTotal(Integer.parseInt(FR_text_right.getText().toString()));
                        GameRules.updateResourse();
                        cancel();
                    }
                });


               FR_seekbar.setMax(GameRules.getResource().getItem(GameRules.stringToType(FR_spinner_left.getSelectedItem().toString())).getTotalInt());
                FR_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                      FR_text_left.setText(String.valueOf(progress));
                      FR_text_right.setText(String.valueOf((int)(progress*GameRules.getFairCoef())));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                break;
            case JAIL:
                buildLayout.addView(factory.inflate(R.layout.jail_layout,null));
                break;
            case POWER:
                buildLayout.addView(factory.inflate(R.layout.power_layout,null));
                break;
            case TOWER:
                buildLayout.addView(factory.inflate(R.layout.tower_layout,null));
                break;
            case CHURCH:
                buildLayout.addView(factory.inflate(R.layout.church_layout,null));
                break;
            case THEATRE:
                buildLayout.addView(factory.inflate(R.layout.theatre_layout,null));
                break;
            case BARRACKS:
                buildLayout.addView(factory.inflate(R.layout.baracs_layout,null));
                final TextView BR_text = buildLayout.findViewById(R.id.BR_text);
                Button BR_button=buildLayout.findViewById(R.id.BR_button);
                SeekBar BR_seekbar = buildLayout.findViewById(R.id.BR_seekbar);
                BR_seekbar.setMax(Integer.parseInt(GameRules.getResource().getHuman(Resource.ResourceType.WORKERS).getFree()));

                BR_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        BR_text.setText(String.valueOf(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                BR_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GameRules.addHuman(Resource.ResourceType.MILITARY,Integer.parseInt(BR_text.getText().toString()));
                        cancel();
                    }
                });
                break;
            case TOWN_HALL:
                buildLayout.addView(factory.inflate(R.layout.town_hall_layout,null));
                final TextView TH_text = buildLayout.findViewById(R.id.TH_text);
                Button TH_button=buildLayout.findViewById(R.id.TH_button);
                SeekBar TH_seekbar = buildLayout.findViewById(R.id.TH_seekbar);
                TH_seekbar.setMax((GameRules.getResource().getItem(Resource.ResourceType.GOLD)
                        .getTotalInt()-Integer.parseInt(GameRules.getResource().getItem(Resource.ResourceType.GOLD).getNegative()))/20);
                TH_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        TH_text.setText(String.valueOf(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                TH_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GameRules.addHuman(Resource.ResourceType.WORKERS,Integer.parseInt(TH_text.getText().toString()));
                        cancel();
                    }
                });
                break;
        }




        setContentView(layout);

        initialView();
    }

    private void initialView() {


    }
}
