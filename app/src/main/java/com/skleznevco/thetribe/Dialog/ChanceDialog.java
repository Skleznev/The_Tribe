package com.skleznevco.thetribe.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.skleznevco.thetribe.GameRules;
import com.skleznevco.thetribe.Resource;

import java.util.Random;

public class ChanceDialog extends DialogFragment {
    final private Random random = new Random();
    private String eventMessage;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (random.nextInt(101) < GameRules.getLuckyChance()) {
            switch (random.nextInt(6)) {
                case 0:
                    eventMessage = "Поселенцы вашего племени нашли клад";
                    eventMessage += itemEvent(Resource.ResourceType.GOLD, true);
                    break;
                case 1:
                    eventMessage = "К вашему племени присоединились мимо идущие путешественники";
                    eventMessage += humanEvent(Resource.ResourceType.WORKERS, true);
                    break;
                case 2:
                    eventMessage = "К вашему племени присоединились сторонние военные";
                    eventMessage += humanEvent(Resource.ResourceType.MILITARY, true);
                    break;
                case 3:
                    eventMessage = "Период дождей - урожай увеличился";
                    eventMessage += itemEvent(Resource.ResourceType.FOOD, true);
                    break;
                case 4:
                    eventMessage = "Поселенцы вашего племени нашли заброшенную каменоломню";
                    eventMessage += itemEvent(Resource.ResourceType.STONE, true);
                    break;
                case 5:
                    eventMessage = "Поселенцы вашего племени нашли заброшенную лесопильню";
                    eventMessage += itemEvent(Resource.ResourceType.WOOD, true);
                    break;
            }
        } else {
            switch (random.nextInt(6)) {
                case 0:
                    eventMessage = "Период засухи - урожай погиб";
                    eventMessage += itemEvent(Resource.ResourceType.FOOD, false);
                    break;
                case 1:
                    eventMessage = "Вашу каменоломню затопило";
                    eventMessage += itemEvent(Resource.ResourceType.STONE, false);
                    break;
                case 2:
                    eventMessage = "На вашей каменоломне камень упал на рабочих";
                    eventMessage += humanEvent(Resource.ResourceType.WORKERS, false);
                    break;
                case 3:
                    eventMessage = "На вашей лесопильне произашел пожар";
                    eventMessage += itemEvent(Resource.ResourceType.WOOD, false);
                    break;
                case 4:
                    eventMessage = "Военные вашего племени дизертировали";
                    eventMessage += humanEvent(Resource.ResourceType.MILITARY, false);
                    break;
                case 5:
                    eventMessage = "Воры украли казну";
                    eventMessage += itemEvent(Resource.ResourceType.GOLD, false);
                    break;
            }
        }
        eventMessage += "\nНа ваше население напало " + GameRules.getCountEnemy() + " врагов";
        if(GameRules.isWinBattle()) {
            eventMessage += "\nВы отбили атаку!";
            eventMessage += "\nВы пленили " + GameRules.prisonerTurn +" врагов";
        }
        else {
            eventMessage += "\nВы потерпели поражение!";
            eventMessage += "\nВаши потери составляют " + GameRules.delta + " поселенцев";
        }
        return builder.setTitle("Отчет по оканчанию хода").setMessage(eventMessage).create();
    }

    private String itemEvent(Resource.ResourceType type, boolean good) {
        int delta = (int) (GameRules.getResource().getItem(type).getTotalInt() * GameRules.getCoefLoyal(good));
        GameRules.getResource().getItem(type).setTotal(GameRules.getResource().getItem(type).getTotalInt() + delta);
        GameRules.updateResourse();
        if (good) return "\n +" + delta + " " + GameRules.typeToString(type);
        else return "\n " + delta + " " + GameRules.typeToString(type);

    }

    private String humanEvent(Resource.ResourceType type, boolean good) {
        int delta = (int) (Integer.parseInt(GameRules.getResource().getHuman(type).getFree()) * GameRules.getCoefLoyal(good));
        GameRules.getResource().getHuman(type).addFree(delta);
        GameRules.updateResourse();
        if (good) return "\n +" + delta + " " + GameRules.typeToString(type);
        else return "\n " + delta + " " + GameRules.typeToString(type);
    }
}
