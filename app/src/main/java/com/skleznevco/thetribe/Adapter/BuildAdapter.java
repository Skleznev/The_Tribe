package com.skleznevco.thetribe.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.skleznevco.thetribe.Building;
import com.skleznevco.thetribe.Builds;
import com.skleznevco.thetribe.Dialog.BuildingDialog;
import com.skleznevco.thetribe.GameRules;
import com.skleznevco.thetribe.R;

public class BuildAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    Builds builds;


    public BuildAdapter(Context context){
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        builds = GameRules.getBuilds();
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Building getItem(int position)
    {
        return builds.getBuilding(Builds.BuildingType.values()[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_items_builds, parent, false);
        }

        final Building building = getItem(position);

        setLevel(view,building.getLevel());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildingDialog buildingDialog = new BuildingDialog(v.getContext(), building);
                buildingDialog.show();
            }
        });

        return view;
    }

    private void setLevel(View view,int level) {
        switch (level) {
            case 3:
                (view.findViewById(R.id.level_3)).setVisibility(View.VISIBLE);
            case 2:
                (view.findViewById(R.id.level_2)).setVisibility(View.VISIBLE);
            case 1:
                (view.findViewById(R.id.level_1)).setVisibility(View.VISIBLE);
        }

    }
}
