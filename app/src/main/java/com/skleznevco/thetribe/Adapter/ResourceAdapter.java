package com.skleznevco.thetribe.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skleznevco.thetribe.GameRules;
import com.skleznevco.thetribe.Models.Human;
import com.skleznevco.thetribe.Models.Item;
import com.skleznevco.thetribe.R;
import com.skleznevco.thetribe.Resource;

public class ResourceAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    Resource resource;


    public ResourceAdapter(Context context) {
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        resource = GameRules.getResource();
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Object getItem(int type) {
        if (type < 4) {
            return resource.getItem(Resource.ResourceType.values()[type]);
        }

        return resource.getHuman(Resource.ResourceType.values()[type]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_items_resources, parent, false);
        }
        if (position == 0) {
            TextView income = view.findViewById(R.id.positive_count);
            income.setText("Доход");
            income.setTextColor(Color.parseColor("#FFFFFF"));
            income.setBackgroundColor(Color.parseColor("#1A5276"));

            TextView expense = view.findViewById(R.id.negative_count);
            expense.setText("Расход");
            expense.setTextColor(Color.parseColor("#FFFFFF"));
            expense.setBackgroundColor(Color.parseColor("#1A5276"));

            TextView total = view.findViewById(R.id.total_count);
            total.setText("Итого");
            total.setTextColor(Color.parseColor("#FFFFFF"));
            total.setBackgroundColor(Color.parseColor("#1A5276"));

            TextView resource = view.findViewById(R.id.resource_name);
            resource.setText("Ресурс");
            resource.setTextColor(Color.parseColor("#FFFFFF"));
            resource.setBackgroundColor(Color.parseColor("#1A5276"));

            return view;
        }

        Object line = getItem(position - 1);
        if (line instanceof Item) {
            TextView positiveText = view.findViewById(R.id.positive_count);
            positiveText.setText(((Item) line).getPositive());
            if (Resource.ResourceType.values()[position-1]==GameRules.getPowerType()) {
                positiveText.setTextColor((Color.parseColor("#117864")));
            }
                else {
                    positiveText.setTextColor(Color.parseColor("#1A5276"));
            }
            ((TextView) view.findViewById(R.id.negative_count)).setText(((Item) line).getNegative());
            ((TextView) view.findViewById(R.id.total_count)).setText(((Item) line).getTotal());
        } else {
            ((TextView) view.findViewById(R.id.positive_count)).setText(((Human) line).getFree());
            ((TextView) view.findViewById(R.id.negative_count)).setText(((Human) line).getBusy());
            ((TextView) view.findViewById(R.id.total_count)).setText(((Human) line).getTotal());
        }

        ((TextView) view.findViewById(R.id.resource_name)).setText(GameRules.typeToString((Resource.ResourceType.values()[position - 1])));

        return view;
    }

    public void update() {
        resource = GameRules.getResource();
        notifyDataSetChanged();
    }
}
