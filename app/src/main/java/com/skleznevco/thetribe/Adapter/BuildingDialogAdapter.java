package com.skleznevco.thetribe.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skleznevco.thetribe.Building;
import com.skleznevco.thetribe.Builds;
import com.skleznevco.thetribe.GameRules;
import com.skleznevco.thetribe.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class BuildingDialogAdapter extends BaseAdapter {

    Context context;
    LayoutInflater lInflater;
    Building building;

    public BuildingDialogAdapter(Context context, Builds.BuildingType type){
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        this.building = GameRules.getBuilds().getBuilding(type);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_buid_dialog, parent, false);
        }
        TextView textView = view.findViewById(R.id.build_cost);
        TextView textLevel = view.findViewById(R.id.build_level);
        textView.setText(building.getCostUpgrade(position).toString());
        if (position<building.getLevel()){
            textLevel.setBackgroundColor(Color.parseColor("#00FFFF"));
        }

        return  view;
    }
}
