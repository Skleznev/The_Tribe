package com.skleznevco.thetribe.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.skleznevco.thetribe.R;
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



    public ChangeCountDialog(@NonNull Context context, ResourceInterface resourceInterface) {
        super(context);
        this.target = target;
        this.resourceInterface = resourceInterface;
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
//            if (count.length()<2){
//                target.setText("0".concat(count));
//            }
//            else target.setText(count);
            resourceInterface.update();

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
