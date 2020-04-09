package com.skleznevco.thetribe;

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

        } else {
            costService = new Payment(1, 1, 1, 1);
            costUpgrade = new Payment(2, 2, 2, 2);
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
                name = "Склад";
                img = R.drawable.tools;
                return;
            case THEATRE:
                name = "Афмфитеатр";
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
        if (level < 3)
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

