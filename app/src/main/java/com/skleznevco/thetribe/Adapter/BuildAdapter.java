package com.skleznevco.thetribe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.skleznevco.thetribe.Building;
import com.skleznevco.thetribe.Builds;
import com.skleznevco.thetribe.Dialog.BuildingDialog;
import com.skleznevco.thetribe.GameRules;
import com.skleznevco.thetribe.R;

public class BuildAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    Builds builds;
    ImageView imageBuild;


    public BuildAdapter(Context context){
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        builds = GameRules.getBuilds();
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Builds.BuildingType getItem(int position)
    {
        return Builds.BuildingType.values()[position];
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

        final Building building = builds.getBuilding(getItem(position));

        setLevel(view,building.getLevel());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildingDialog buildingDialog = new BuildingDialog(v.getContext(), getItem(position));
                buildingDialog.show();
            }
        });

        imageBuild = view.findViewById(R.id.imageBuild);
        imageBuild.setImageDrawable(context.getResources().getDrawable(building.getImg()));

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
