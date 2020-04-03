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
                townHall = new Building(2,false);
                barracks = new Building(1 ,false);
                jail = new Building(1,false);
                fair = new Building(0,false);
                church = new Building(0, false);
                tower = new Building (1,false);
                power = new Building(1,false);
                theatre = new Building(0, false);
                embassy = new Building(0, false);
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
    private Payment costUpgrade;
    private Payment costService;

    Building(int level, boolean random){
        this.level = level;
        if (random){

        }
        else {
            costService = new Payment(1,1,1,1);
            costUpgrade = new Payment(2,2,2,2);
        }

    }

    Building(int level, Payment costUpgrade, Payment costService){
        this.level = level;

    }

    public void levelUp(){
        if (level<3)
        level++;
    }

    public int getLevel() {
        return level;
    }


    public Payment getCostService() {
        return costService;
    }

    public Payment getCostUpgrade() {
        return costUpgrade;
    }

}
