package com.skleznevco.thetribe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class WorkAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    ResourceAdapter resourceAdapter;
    Resource resource;

    WorkAdapter(Context context, ResourceAdapter resourceAdapter){
        this.context = context;
        this.resourceAdapter = resourceAdapter;
        resource = GameRules.getResource();
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_items_count, parent, false);
        }

        Button btn_up = view.findViewById(R.id.count_up);
        Button btn_down = view.findViewById(R.id.count_down);
        final TextView text_count = view.findViewById(R.id.count);

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (resource.getHuman(Resource.ResourceType.WORKERS).incrementBusy()) {
                    resource.getItem(Resource.ResourceType.values()[position]).incrementCountWorkers();
                    text_count.setText(convertToString(position));
                    resourceAdapter.update();
                }
            }
        });

        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resource.getItem(Resource.ResourceType.values()[position]).getCountWorkers() != 0 &&
                        resource.getHuman(Resource.ResourceType.WORKERS).incrementFree()){
                    resource.getItem(Resource.ResourceType.values()[position]).decrementCountWorkers();
                    resourceAdapter.update();
                }
                text_count.setText(convertToString(position));
            }
        });
        return view;
    }

    private String convertToString(int position){
        String s = "0";
        int count = resource.getItem(Resource.ResourceType.values()[position]).getCountWorkers();

        if (count < 10){
            return s.concat(String.valueOf(count));
        }

        return String.valueOf(count);
    }
}
