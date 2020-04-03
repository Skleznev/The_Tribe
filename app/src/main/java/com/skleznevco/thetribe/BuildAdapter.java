package com.skleznevco.thetribe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.skleznevco.thetribe.Dialog.BuildingDialog;
import com.skleznevco.thetribe.Dialog.ChangeCountDialog;

public class BuildAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;

    BuildAdapter(Context context){
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 9;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_items_builds, parent, false);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildingDialog buildingDialog = new BuildingDialog(v.getContext());
                buildingDialog.show();
            }
        });

        return view;
    }
}
