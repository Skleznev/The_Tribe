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

        if (random.nextInt(100) + 1 < GameRules.getLuckyChance() * 100) {
            switch (random.nextInt(6)) {
                case 0:
                    eventMessage = "Поселенцы вашего племени нашли клад";
                    break;
                case 1:
                    eventMessage = "К вашему племени присоединились мимо идущие путешественники";
                    break;
                case 2:
                    eventMessage = "К вашему племени присоединились сторонние военные";
                    break;
                case 3:
                    eventMessage = "Период дождей - урожай увеличился";
                    break;
                case 4:
                    eventMessage = "Поселенцы вашего племени нашли заброшенную каменоломню";
                    break;
                case 5:
                    eventMessage = "Поселенцы вашего племени нашли заброшенную лесопильню";
                    break;
            }
        } else {
            switch (random.nextInt(6)) {
                case 0:
                    eventMessage = "Период засухи - урожай погиб";
                    break;
                case 1:
                    eventMessage = "На вашем складе завелись крысы";
                    break;
                case 2:
                    eventMessage = "На вашей каменоломне камень упал на рабочих";
                    break;
                case 3:
                    eventMessage = "На вашей лесопильне произашел пожар";
                    break;
                case 4:
                    eventMessage = "Военные вашего племени дизертировали";
                    break;
                case 5:
                    eventMessage = "Воры украли казну";
                    break;
            }
        }

        return builder.setTitle("Диалоговое окно").setMessage(eventMessage).create();
    }
}
