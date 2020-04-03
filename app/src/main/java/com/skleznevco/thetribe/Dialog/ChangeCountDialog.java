package com.skleznevco.thetribe.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.skleznevco.thetribe.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ChangeCountDialog extends Dialog {

    TextView textView;
    TextView target;
    SeekBar seekBar;
    Button button_ok;
    Button button_cancel;
    String count;



    public ChangeCountDialog(@NonNull Context context, TextView target) {
        super(context);
        this.target = target;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.fragment_defence_military, null);
        setContentView(layout);

        initialView();

    }

private void initialView(){
    seekBar =findViewById(R.id.seekBar);
    textView = findViewById(R.id.cout_seek);
    button_ok = findViewById(R.id.button_ok);
    button_cancel = findViewById(R.id.button_cancel);

    button_ok.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (count.length()<2){
                target.setText("0".concat(count));
            }
            else target.setText(count);


            cancel();
        }
    });

    button_cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cancel();
        }
    });

    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            count = String.valueOf(progress);
            textView.setText(count);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    });
}

    public void setMaxSeek(String max){
        seekBar.setMax(Integer.parseInt(max));
    }




}