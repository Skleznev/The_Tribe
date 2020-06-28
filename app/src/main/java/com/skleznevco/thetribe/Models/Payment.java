package com.skleznevco.thetribe.Models;

import androidx.annotation.NonNull;

import com.skleznevco.thetribe.Resource;

public class Payment {
    private int food;
    private int stone;
    private int wood;
    private int gold;

    public Payment(int food, int stone, int wood, int gold) {
        this.food = food;
        this.stone = stone;
        this.wood = wood;
        this.gold = gold;
    }

    @NonNull
    @Override
    public String toString() {
        String text = "Food: " + String.valueOf(food).concat(System.lineSeparator())
                + "Stone: " + String.valueOf(stone).concat(System.lineSeparator()) +
                "Wood: " + String.valueOf(wood).concat(System.lineSeparator()) +
                "Gold: " + String.valueOf(gold).concat(System.lineSeparator());


        return text;
    }


    public Payment multiply(int coef) {
        return new Payment(food * coef, stone * coef, wood * coef, gold * coef);
    }

    public int getItemCount(Resource.ResourceType type) {
        switch (type) {
            case FOOD:
                return food;
            case STONE:
                return stone;
            case WOOD:
                return wood;
            case GOLD:
                return gold;
        }
        return 0;
    }
}
