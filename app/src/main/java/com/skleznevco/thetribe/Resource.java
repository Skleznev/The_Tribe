package com.skleznevco.thetribe;

import com.skleznevco.thetribe.Models.Human;
import com.skleznevco.thetribe.Models.Item;

public class Resource {


    public double getBusyPopulation() {
        return Double.parseDouble(workers.getBusy()) + Integer.parseInt(military.getBusy());
    }

    public enum ResourceType {
        FOOD, STONE, WOOD, GOLD, WORKERS, MILITARY
    }


    private Item food;
    private Item stone;
    private Item wood;
    private Item gold;

    private Human workers;
    private Human military;


    public Resource(GameRules.Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                food = new Item(400);
                stone = new Item(100);
                wood = new Item(100);
                gold = new Item(200);
                workers = new Human(10);
                military = new Human(5);
                break;
            case MEDIUM:
                food = new Item(200);
                stone = new Item(50);
                wood = new Item(50);
                gold = new Item(100);
                workers = new Human(5);
                military = new Human(0);
                break;
            case HARD:
                food = new Item(100);
                stone = new Item(50);
                wood = new Item(50);
                gold = new Item(50);
                workers = new Human(2);
                military = new Human(0);
                break;
        }

    }


    public Item getItem(ResourceType type) {
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
        return null;
    }

    public Human getHuman(ResourceType type) {
        switch (type) {
            case WORKERS:
                return workers;
            case MILITARY:
                return military;
        }
        return null;
    }

    public int getPopulation() {
        return Integer.parseInt(workers.getTotal()) + Integer.parseInt(military.getTotal());
    }

}

