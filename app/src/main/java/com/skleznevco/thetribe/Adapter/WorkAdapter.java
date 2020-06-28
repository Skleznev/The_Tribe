package com.skleznevco.thetribe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.skleznevco.thetribe.Dialog.ChangeCountDialog;
import com.skleznevco.thetribe.GameRules;
import com.skleznevco.thetribe.R;
import com.skleznevco.thetribe.Resource;
import com.skleznevco.thetribe.ResourceInterface;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class WorkAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    ResourceInterface resourceInterface;
    Resource resource;

    public WorkAdapter(Context context, ResourceInterface resourceInterface) {
        this.context = context;
        this.resourceInterface = resourceInterface;
        resource = GameRules.getResource();
        lInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
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
        TextView text_name = view.findViewById(R.id.name);

        switch (position) {
            case 0:
                text_name.setText("Пища");
                break;
            case 1:
                text_name.setText("Камень");
                break;
            case 2:
                text_name.setText("Дерево");
                break;
            case 3:
                text_name.setText("Золото");
                break;
        }

        text_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeCountDialog changeCountDialog = new ChangeCountDialog(context, resourceInterface, text_count, position);
                changeCountDialog.setMaxSeek(String.valueOf(Integer.parseInt(resource.getHuman(Resource.ResourceType.WORKERS).getFree()) + resource.getItem(Resource.ResourceType.values()[position]).getCountWorkers()));
                changeCountDialog.setCurrentCount(resource.getItem(Resource.ResourceType.values()[position]).getCountWorkers());
                changeCountDialog.show();
            }
        });


        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (resource.getHuman(Resource.ResourceType.WORKERS).incrementBusy()) {
                    resource.getItem(Resource.ResourceType.values()[position]).incrementCountWorkers();
                    text_count.setText(convertToString(position));
                    resourceInterface.update();

                }
            }
        });

        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resource.getItem(Resource.ResourceType.values()[position]).getCountWorkers() != 0 && resource.getHuman(Resource.ResourceType.WORKERS).incrementFree()) {
                    resource.getItem(Resource.ResourceType.values()[position]).decrementCountWorkers();
                    resourceInterface.update();
                }
                text_count.setText(convertToString(position));
            }
        });
        return view;
    }

    private String convertToString(int position) {
        String s = "0";
        int count = resource.getItem(Resource.ResourceType.values()[position]).getCountWorkers();

        if (count < 10) {
            return s.concat(String.valueOf(count));
        }

        return String.valueOf(count);
    }

    private interface WorkInterface {
        void update(ResourceInterface resourceInterface);

        void setContWorkers(int count);
    }

}
