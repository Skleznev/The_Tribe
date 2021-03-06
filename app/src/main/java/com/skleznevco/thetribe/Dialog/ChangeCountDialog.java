package com.skleznevco.thetribe.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.skleznevco.thetribe.GameRules;
import com.skleznevco.thetribe.R;
import com.skleznevco.thetribe.Resource;
import com.skleznevco.thetribe.ResourceInterface;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ChangeCountDialog extends Dialog {

    TextView textView;
    TextView target;
    SeekBar seekBar;
    Button button_ok;
    Button button_cancel;
    String count;
    ResourceInterface resourceInterface;
    int position;



    public ChangeCountDialog(@NonNull Context context, ResourceInterface resourceInterface,TextView target, int position) {
        super(context);
        this.target = target;
        this.resourceInterface = resourceInterface;
        this.position = position;
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
            if (position==-1){
                resourceInterface.setMilitary(Integer.parseInt(count));
                resourceInterface.update();
            }
            else {
                resourceInterface.setWorkers(Integer.parseInt(count) - GameRules.getResource().getItem(Resource.ResourceType.values()[position]).getCountWorkers());
                GameRules.getResource().getItem(Resource.ResourceType.values()[position]).setCountWorkers(Integer.parseInt(count));
                resourceInterface.update();
            }
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
            if (count.length()<2){
                count="0".concat(count);
            }
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
    public void setCurrentCount(int count){seekBar.setProgress(count);}



}
