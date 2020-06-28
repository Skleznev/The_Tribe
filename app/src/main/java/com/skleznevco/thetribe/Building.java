package com.skleznevco.thetribe;

import java.util.Random;

public class Building {
    private int level = 0;
    private Payment costUpgrade;
    private Payment costService;
    private String name;
    private int img;

    Building(int level, boolean random, Builds.BuildingType type) {
        this.level = level;
        createBuilding(type);
        if (random) {
            Random rand = new Random();
            costService = new Payment(rand.nextInt(3)+1, rand.nextInt(4)+3, rand.nextInt(5)+2, rand.nextInt(3)+1);
            costUpgrade = new Payment(rand.nextInt(30)+10, rand.nextInt(40)+30, rand.nextInt(50)+20, rand.nextInt(30)+15);
        } else {
            costService = new Payment(4, 7, 8, 4);
            costUpgrade = new Payment(40, 70, 80, 45);
        }

    }

    private void createBuilding(Builds.BuildingType type) {
        switch (type) {
            case TOWN_HALL:
                name = "Ратуша";
                img = R.drawable.townhall;
                return;
            case BARRACKS:
                name = "Казарма";
                img = R.drawable.barracks;
                return;
            case JAIL:
                name = "Тюрьма";
                img = R.drawable.prison;
                return;
            case FAIR:
                name = "Рынок";
                img = R.drawable.fair;
                return;
            case CHURCH:
                name = "Храм";
                img = R.drawable.church;
                return;
            case TOWER:
                name = "Дозорная башня";
                img = R.drawable.tower;
                return;
            case POWER:
                name = "Мастерская";
                img = R.drawable.tools;
                return;
            case THEATRE:
                name = "Театр";
                img = R.drawable.theatre;
                return;
            case EMBASSY:
                name = "Посольство";
                img = R.drawable.people;
        }
    }

    Building(int level, Payment costUpgrade, Payment costService) {
        this.level = level;

    }

    public void levelUp() {
            GameRules.pay(getCostUpgrade(level));
            level++;
    }

    public int getLevel() {
        return level;
    }


    public Payment getCostService() {

        return costService;
    }

    public Payment getCostUpgrade(int coefficient) {
        return costUpgrade.multiply(coefficient + 1);
    }

    public String getName() {
        return name;
    }

    public int getImg() {
        return img;
    }
}

