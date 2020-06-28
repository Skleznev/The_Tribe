package com.skleznevco.thetribe.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skleznevco.thetribe.Building;
import com.skleznevco.thetribe.Builds;
import com.skleznevco.thetribe.GameRules;
import com.skleznevco.thetribe.Item;
import com.skleznevco.thetribe.R;
import com.skleznevco.thetribe.Resource;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class BuildingDialogAdapter extends BaseAdapter {

    Context context;
    LayoutInflater lInflater;
    Building building;

    public BuildingDialogAdapter(Context context, Builds.BuildingType type) {
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        this.building = GameRules.getBuilds().getBuilding(type);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return GameRules.getResource().getItem(Resource.ResourceType.values()[position]);
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
            view = lInflater.inflate(R.layout.list_items_resources, parent, false);
        }
        Object line = getItem(position);
        if (line instanceof Item) {
            ((TextView) view.findViewById(R.id.positive_count)).setText(GameRules.typeToString((Resource.ResourceType.values()[position])));
            ((TextView) view.findViewById(R.id.negative_count)).setText(String.valueOf(building.getCostUpgrade(building.getLevel()).getItemCount(Resource.ResourceType.values()[position])));
            TextView total = view.findViewById(R.id.total_count);
            total.setText(GameRules.getResource().getItem(Resource.ResourceType.values()[position]).getTotal());

            if (Integer.parseInt(GameRules.getResource().getItem(Resource.ResourceType.values()[position]).getTotal()) < building.getCostUpgrade(building.getLevel()).getItemCount(Resource.ResourceType.values()[position])){
                total.setTextColor(Color.parseColor("#D72323"));
            }
            else {
                total.setTextColor(Color.parseColor("#117864"));
            }
        }
        ((ViewManager) (view.findViewById(R.id.resource_name)).getParent()).removeView((view.findViewById(R.id.resource_name)));

        return view;
    }
}
