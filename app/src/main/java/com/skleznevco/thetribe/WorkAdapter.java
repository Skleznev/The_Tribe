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
    int[] workersCount;
    ResourceAdapter resourceAdapter;
    Human worker;

    WorkAdapter(Context context, int[] workersCount, ResourceAdapter resourceAdapter, Human worker){
        this.context = context;
        this.workersCount = workersCount;
        this.resourceAdapter = resourceAdapter;
        this.worker = worker;
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

                if (worker.incrementBusy()) {
                    workersCount[position]++;
                    text_count.setText(convertToString(position));
                    resourceAdapter.notifyDataSetChanged();
                }
            }
        });

        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workersCount[position] != 0 && worker.incrementFree()){
                    workersCount[position]--;
                    resourceAdapter.notifyDataSetChanged();
                }
                text_count.setText(convertToString(position));
            }
        });
        return view;
    }

    private String convertToString(int position){
        String s = "0";
        int count = workersCount[position];

        if (count < 10){
            return s.concat(String.valueOf(count));
        }

        return String.valueOf(count);
    }
}
