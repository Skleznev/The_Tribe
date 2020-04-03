package com.skleznevco.thetribe.Dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.skleznevco.thetribe.GameRules;
import com.skleznevco.thetribe.R;
import com.skleznevco.thetribe.Resource;
import com.skleznevco.thetribe.ResourceAdapter;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class BuildingDialogAdapter extends BaseAdapter {

    Context context;
    LayoutInflater lInflater;

    BuildingDialogAdapter(Context context){
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_buid_dialog, parent, false);
        }


        return  view;
    }
}
