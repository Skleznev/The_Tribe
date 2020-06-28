package com.skleznevco.thetribe.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skleznevco.thetribe.GameRules;
import com.skleznevco.thetribe.R;

public class WarAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    private int turn;

    public WarAdapter(Context context) {
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        turn = 0;
    }

    @Override
    public int getCount() {
        return 48;
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
            view = lInflater.inflate(R.layout.war_items, parent, false);
        }
        TextView textCount = view.findViewById(R.id.war_count);
        TextView textColor = view.findViewById(R.id.war_color);

        if (position + 1 == turn) {

            textCount.setText(String.valueOf(GameRules.getCountEnemy()));
            textCount.setVisibility(View.VISIBLE);
            if(!GameRules.isWinBattle()) textColor.setBackgroundColor(Color.parseColor("#D72323"));
            textColor.setVisibility(View.VISIBLE);
        }

        if (position == turn && GameRules.isVision()) {

            textCount.setText(String.valueOf(GameRules.getCountEnemyNext()));
            textCount.setVisibility(View.VISIBLE);

        }


        return view;
    }

    public void addTurn() {
        turn++;
        notifyDataSetChanged();
    }
}
