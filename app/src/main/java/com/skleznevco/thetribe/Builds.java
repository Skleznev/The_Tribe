package com.skleznevco.thetribe;

public class Builds {
    enum BuildingType {
        TOWN_HALL,
        BARRACKS,
        JAIL,
        FAIR,
        CHURCH,
        TOWER,
        POWER,
        THEATRE,
        EMBASSY
    }
    private Building townHall;
    private Building barracks;
    private Building jail;
    private Building fair;
    private Building church;
    private Building tower;
    private Building power;
    private Building theatre;
    private Building embassy;

    public Builds(GameRules.Difficulty difficulty){
        switch (difficulty){
            case EASY:

                break;
            case MEDIUM:

                break;
            case HARD:

                break;
        }
    }


    public Building getItem(BuildingType type) {
        switch (type){
            case TOWN_HALL: return townHall;
            case BARRACKS: return barracks;
            case JAIL: return jail;
            case FAIR: return fair;
            case CHURCH: return church;
            case TOWER: return tower;
            case POWER: return power;
            case THEATRE: return theatre;
            case EMBASSY: return embassy;
        }
        return null;
    }



}

class Building{
    private int level = 0;
    private int costUpgrade;
    private int costService;

    Building(int level, int costUpgrade, int costService){
        this.level = level;

    }

    public void levelUp(){
        if (level<3)
        level++;
    }

    public int getLevel() {
        return level;
    }


    public int getCostService() {
        return costService;
    }

    public int getCostUpgrade() {
        return costUpgrade;
    }

    public void setCostService(int costService) {
        this.costService = costService;
    }

    public void setCostUpgrade(int costUpgrade) {
        this.costUpgrade = costUpgrade;
    }

}
