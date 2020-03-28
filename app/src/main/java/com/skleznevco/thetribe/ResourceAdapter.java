package com.skleznevco.thetribe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ResourceAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    Resource resource;



    ResourceAdapter(Context context){
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        resource = GameRules.getResource();
    }

    @Override
    public int getCount() {
        return 6;
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
        Object line = getItem(position);
        if (line instanceof Item){
            ((TextView) view.findViewById(R.id.positive_count)).setText(((Item) line).getPositive());
            ((TextView) view.findViewById(R.id.negative_count)).setText(((Item) line).getNegative());
            ((TextView) view.findViewById(R.id.total_count)).setText(((Item) line).getTotal());
        }
        else{
            ((TextView) view.findViewById(R.id.positive_count)).setText(((Human)line).getFree());
            ((TextView) view.findViewById(R.id.negative_count)).setText(((Human)line).getBusy());
            ((TextView) view.findViewById(R.id.total_count)).setText(((Human)line).getTotal());
        }

        ((TextView) view.findViewById(R.id.resource_name)).setText(String.valueOf(Resource.ResourceType.values()[position]));

        return view;
    }

    public void update() {
        resource=GameRules.getResource();
        notifyDataSetChanged();
    }
}
