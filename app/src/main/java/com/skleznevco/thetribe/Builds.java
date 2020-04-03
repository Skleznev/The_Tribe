package com.skleznevco.thetribe;

public class Builds {
    public enum BuildingType {
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
                townHall = new Building(2,false, "Ратуша");
                barracks = new Building(1 ,false, "Казарма");
                jail = new Building(1,false, "Тюрьма");
                fair = new Building(0,false, "Рынок");
                church = new Building(0, false, "Храм");
                tower = new Building (1,false, "Смотрительная башня");
                power = new Building(1,false, " Усиление");
                theatre = new Building(0, false, "Амфитеатр");
                embassy = new Building(0, false, "Посольство");
                break;
            case MEDIUM:

                break;
            case HARD:

                break;
        }
    }


    public Building getBuilding(BuildingType type) {
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

