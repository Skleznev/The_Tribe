package com.skleznevco.thetribe.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;

import com.skleznevco.thetribe.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class BuildingDialog extends Dialog {
    public BuildingDialog(@NonNull Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.builds_dialog, null);


        BuildingDialogAdapter adapter = new BuildingDialogAdapter(context);

        GridView gridView = layout.findViewById(R.id.dialog_grid_builds);
        gridView.setAdapter(adapter);
        setContentView(layout);

        initialView();
    }

    private void initialView() {


    }
}
