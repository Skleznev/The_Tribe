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
    }

    private Building townHall;
    private Building barracks;
    private Building jail;
    private Building fair;
    private Building church;
    private Building tower;
    private Building power;
    private Building theatre;

    public Builds(GameRules.Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                townHall = new Building(2, false, BuildingType.TOWN_HALL);
                barracks = new Building(1, false, BuildingType.BARRACKS);
                jail = new Building(1, false, BuildingType.JAIL);
                fair = new Building(1, false, BuildingType.FAIR);
                church = new Building(0, false, BuildingType.CHURCH);
                tower = new Building(1, false, BuildingType.TOWER);
                power = new Building(1, false, BuildingType.POWER);
                theatre = new Building(0, false, BuildingType.THEATRE);
                break;
            case MEDIUM:
                townHall = new Building(1, true, BuildingType.TOWN_HALL);
                barracks = new Building(0, true, BuildingType.BARRACKS);
                jail = new Building(0, true, BuildingType.JAIL);
                fair = new Building(1, true, BuildingType.FAIR);
                church = new Building(0, true, BuildingType.CHURCH);
                tower = new Building(1, true, BuildingType.TOWER);
                power = new Building(1, true, BuildingType.POWER);
                theatre = new Building(0, true, BuildingType.THEATRE);
                break;
            case HARD:
                townHall = new Building(1, false, BuildingType.TOWN_HALL);
                barracks = new Building(0, false, BuildingType.BARRACKS);
                jail = new Building(0, false, BuildingType.JAIL);
                fair = new Building(0, false, BuildingType.FAIR);
                church = new Building(0, false, BuildingType.CHURCH);
                tower = new Building(0, false, BuildingType.TOWER);
                power = new Building(0, false, BuildingType.POWER);
                theatre = new Building(0, false, BuildingType.THEATRE);
                break;
        }
    }


    public Building getBuilding(BuildingType type) {
        switch (type) {
            case TOWN_HALL:
                return townHall;
            case BARRACKS:
                return barracks;
            case JAIL:
                return jail;
            case FAIR:
                return fair;
            case CHURCH:
                return church;
            case TOWER:
                return tower;
            case POWER:
                return power;
            case THEATRE:
                return theatre;
        }
        return null;
    }


}

