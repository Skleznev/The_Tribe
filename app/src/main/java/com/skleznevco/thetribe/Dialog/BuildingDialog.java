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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.Random;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.skleznevco.thetribe.Resource.ResourceType.FOOD;
import static com.skleznevco.thetribe.Resource.ResourceType.GOLD;
import static com.skleznevco.thetribe.Resource.ResourceType.STONE;
import static com.skleznevco.thetribe.Resource.ResourceType.WOOD;

public class BuildingDialog extends Dialog {
    private Context context;


    @SuppressLint("ResourceType")
    public BuildingDialog(@NonNull Context context, Builds.BuildingType type) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        final Building building = GameRules.getBuilds().getBuilding(type);
        View layout = inflater.inflate(R.layout.builds_dialog, null);
        this.context = context;

        BuildingDialogAdapter adapter = new BuildingDialogAdapter(context, type);
        final TextView textView = layout.findViewById(R.id.build_name);
        textView.setText(building.getName());
        ListView listCost = layout.findViewById(R.id.list_cost);
        listCost.setAdapter(adapter);

        Button button = layout.findViewById(R.id.upgrade);

        if (GameRules.canPay(building.getCostUpgrade(building.getLevel())) && (building.getLevel()!=3) && (building.getLevel() < GameRules.getBuilds().getBuilding(Builds.BuildingType.TOWN_HALL).getLevel() || building.getName().equals("Ратуша"))) {
            button.setBackgroundColor(Color.parseColor("#117864"));
            button.setEnabled(true);

        } else {
            button.setBackgroundColor(Color.parseColor("#D72323"));
            button.setEnabled(false);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GameRules.canPay(building.getCostUpgrade(building.getLevel())) && (building.getLevel() < GameRules.getBuilds().getBuilding(Builds.BuildingType.TOWN_HALL).getLevel() || building.getName().equals("Ратуша"))) {
                    building.levelUp();
                    GameRules.updateResourse();
                    cancel();
                }
            }
        });

        LayoutInflater factory = LayoutInflater.from(layout.getContext());
        LinearLayout buildLayout = layout.findViewById(R.id.build_layout);

        TextView description = layout.findViewById(R.id.description);
        TextView level = layout.findViewById(R.id.level);

        if(building.getLevel() == 0){
            level.setText("Здание не построенно");
        }
        else if(building.getLevel() == 3){
            level.setText("Максимальный уровень");
        }
        else {
            level.setText("Уровень " + building.getLevel());
        }

        switch (type) {
            case FAIR:
                description.setText(context.getString(R.string.description_fair));
                buildLayout.addView(factory.inflate(R.layout.fair_layout, null));
                final SeekBar FR_seekbar = buildLayout.findViewById(R.id.FR_seekbar);
                final TextView FR_text_left = buildLayout.findViewById(R.id.FR_text_left);
                final TextView FR_text_right = buildLayout.findViewById(R.id.FR_text_right);
                final Spinner FR_spinner_left = buildLayout.findViewById(R.id.FR_spinner_left);
                final Spinner FR_spinner_right = buildLayout.findViewById(R.id.FR_spinner_right);

                Button FR_button = buildLayout.findViewById(R.id.FR_button);
                if (GameRules.getBuilds().getBuilding(Builds.BuildingType.FAIR).getLevel() == 0)
                    FR_button.setEnabled(false);

                ArrayAdapter<String> FR_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, new String[]{"Пища", "Камень", "Дерево", "Золото"});
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
                        FR_text_right.setText(String.valueOf((int) (progress * GameRules.getFairCoef())));
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
                description.setText(context.getString(R.string.description_jail));
                buildLayout.addView(factory.inflate(R.layout.jail_layout, null));
                final TextView JL_text = buildLayout.findViewById(R.id.JL_text);
                Button JL_button = buildLayout.findViewById(R.id.JL_button);
                SeekBar JL_seekbar = buildLayout.findViewById(R.id.JL_seekbar);
                JL_seekbar.setMax(GameRules.getPrisonerCount());

                if(building.getLevel()==0) {
                    JL_button.setEnabled(false);
                    JL_button.setBackgroundColor(Color.parseColor("#D72323"));
                }

                JL_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        JL_text.setText(String.valueOf(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                JL_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GameRules.addHuman(WOOD, Integer.parseInt(JL_text.getText().toString()));
                        cancel();
                    }
                });
                break;
            case POWER:
                description.setText(context.getString(R.string.description_power));
                buildLayout.addView(factory.inflate(R.layout.power_layout, null));
                Button powerButton = buildLayout.findViewById(R.id.power_button);
                final RadioGroup radioGroup = buildLayout.findViewById(R.id.radio);
                if(building.getLevel()>0){
                    powerButton.setText("Применить улучшение с множителем: " + String.valueOf(GameRules.getCoefPower(building.getLevel())) + "X");
                    powerButton.setBackgroundColor(Color.parseColor("#117864"));
                    powerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                switch (radioGroup.getCheckedRadioButtonId()){
                                    case R.id.food_button:
                                        GameRules.setPowerType(FOOD);
                                        break;
                                    case R.id.wood_button:
                                        GameRules.setPowerType(WOOD);
                                        break;
                                    case R.id.stone_button:
                                        GameRules.setPowerType(STONE);
                                        break;
                                    case R.id.gold_button:
                                        GameRules.setPowerType(GOLD);
                                }
                                GameRules.updateResourse();
                                cancel();
                        }
                    });
                }
                else {
                    powerButton.setText("Мастерская не построенна");
                    powerButton.setBackgroundColor(Color.parseColor("#D72323"));
                }
                break;
            case TOWER:
                description.setText(context.getString(R.string.description_tower));
                buildLayout.addView(factory.inflate(R.layout.tower_layout, null));
                final TextView TR_text = buildLayout.findViewById(R.id.TR_text);
                Button TR_button = buildLayout.findViewById(R.id.TR_button);
                final Random random = new Random();
                if (building.getLevel()==0) TR_text.setVisibility(View.GONE);
                if (building.getLevel()>=1) TR_text.setText("Количество нападающих не больше чем " + (random.nextInt(5)+GameRules.getCountEnemyNext()));
                if (building.getLevel()==2) TR_button.setEnabled(true);
                TR_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TR_text.setText("Количество нападающих: " + GameRules.getCountEnemyNext());
                    }
                });
                break;
            case CHURCH:
                description.setText(context.getString(R.string.description_church));
                buildLayout.addView(factory.inflate(R.layout.church_layout, null));
                TextView luckyText = buildLayout.findViewById(R.id.luckyText);
                luckyText.setText("Текущий множитель удачи: " + GameRules.getLuckyChance() + "%");
                break;
            case THEATRE:
                description.setText(context.getString(R.string.description_theatre));
                buildLayout.addView(factory.inflate(R.layout.theatre_layout, null));
                final TextView loyaltyText = buildLayout.findViewById(R.id.loyaltyText);
                loyaltyText.setText("Текущий множитель лояльности: " + GameRules.getloyalty() + "%");

                Button workButton= buildLayout.findViewById(R.id.workButton);
                if(building.getLevel()>1&&GameRules.getWorkDayCD()<1){
                    workButton.setBackgroundColor(Color.parseColor("#117864"));
                    workButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loyaltyText.setText("Текущий множитель лояльности: " + 100 + "%");
                            GameRules.activateWorkDay();
                        }
                    });
                }
                else workButton.setBackgroundColor(Color.parseColor("#D72323"));

                break;
            case BARRACKS:
                description.setText(context.getString(R.string.description_baracs));
                buildLayout.addView(factory.inflate(R.layout.baracs_layout, null));
                final TextView BR_text = buildLayout.findViewById(R.id.BR_text);
                Button BR_button = buildLayout.findViewById(R.id.BR_button);
                SeekBar BR_seekbar = buildLayout.findViewById(R.id.BR_seekbar);
                BR_seekbar.setMax(Integer.parseInt(GameRules.getResource().getHuman(Resource.ResourceType.WORKERS).getFree()));

                if(building.getLevel()==0) {
                    BR_button.setEnabled(false);
                    BR_button.setBackgroundColor(Color.parseColor("#D72323"));
                }

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
                        GameRules.addHuman(Resource.ResourceType.MILITARY, Integer.parseInt(BR_text.getText().toString()));
                        cancel();
                    }
                });
                break;
            case TOWN_HALL:
                description.setText(context.getString(R.string.description_townhall));
                buildLayout.addView(factory.inflate(R.layout.town_hall_layout, null));
                final TextView TH_text = buildLayout.findViewById(R.id.TH_text);
                Button TH_button = buildLayout.findViewById(R.id.TH_button);
                SeekBar TH_seekbar = buildLayout.findViewById(R.id.TH_seekbar);
                TH_seekbar.setMax((GameRules.getResource().getItem(Resource.ResourceType.GOLD)
                        .getTotalInt() - Integer.parseInt(GameRules.getResource().getItem(Resource.ResourceType.GOLD).getNegative())) / 50);
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
                        GameRules.addHuman(Resource.ResourceType.WORKERS, Integer.parseInt(TH_text.getText().toString()));
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

