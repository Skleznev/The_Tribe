package com.skleznevco.thetribe.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.skleznevco.thetribe.Adapter.BuildingDialogAdapter;
import com.skleznevco.thetribe.Building;
import com.skleznevco.thetribe.Builds;
import com.skleznevco.thetribe.GameRules;
import com.skleznevco.thetribe.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class BuildingDialog extends Dialog {
    public BuildingDialog(@NonNull Context context, Builds.BuildingType type) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        final Building building = GameRules.getBuilds().getBuilding(type);
        View layout = inflater.inflate(R.layout.builds_dialog, null);

        BuildingDialogAdapter adapter = new BuildingDialogAdapter(context, type);
        TextView textView = layout.findViewById(R.id.build_name);
        textView.setText(building.getName());
        GridView gridView = layout.findViewById(R.id.dialog_grid_builds);
        gridView.setAdapter(adapter);

        Button button = layout.findViewById(R.id.upgrade);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GameRules.canPay(building.getCostUpgrade(building.getLevel())) ){
                    building.levelUp();
                }
            }
        });


        LinearLayout emptyLayout = layout.findViewById(R.id.empty_layout);
        emptyLayout.addView(new SeekBar(layout.getContext()));

        setContentView(layout);

        initialView();
    }

    private void initialView() {


    }
}
