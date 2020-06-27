package com.skleznevco.thetribe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Random;

public class ChanceDialog extends DialogFragment {
    final private Random random = new Random();
    private String eventMessage;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (random.nextInt(100) + 1 < GameRules.getLuckyChance()) {
            eventMessage = "Хорошее событие";
        } else {
            eventMessage = "Плохое событие";
        }

        return builder.setTitle("Диалоговое окно").setMessage(eventMessage).create();
    }
}
