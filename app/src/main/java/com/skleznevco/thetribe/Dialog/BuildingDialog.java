package com.skleznevco.thetribe.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.skleznevco.thetribe.Adapter.BuildingDialogAdapter;
import com.skleznevco.thetribe.Building;
import com.skleznevco.thetribe.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class BuildingDialog extends Dialog {
    public BuildingDialog(@NonNull Context context, Building building) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.builds_dialog, null);

        BuildingDialogAdapter adapter = new BuildingDialogAdapter(context, building);
        TextView textView = layout.findViewById(R.id.build_name);
        textView.setText(building.getName());
        GridView gridView = layout.findViewById(R.id.dialog_grid_builds);
        gridView.setAdapter(adapter);
        setContentView(layout);

        initialView();
    }

    private void initialView() {


    }
}
