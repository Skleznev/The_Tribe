package com.skleznevco.thetribe;

public class Resource {

    enum ResourceType{
        FOOD,STONE,WOOD,GOLD, WORKERS,MILITARY
    }


    private Item food;
    private Item stone;
    private Item wood;
    private Item gold;
    private Human workers;
    private Human military;


    public Resource(GameRules.Difficulty difficulty){
        switch (difficulty){
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
                stone = new Item(0);
                wood = new Item(0);
                gold = new Item(100);
                workers = new Human(5);
                military = new Human(0);
                break;
            case HARD:
                food = new Item(100);
                stone = new Item(0);
                wood = new Item(0);
                gold = new Item(0);
                workers = new Human(2);
                military = new Human(0);
                break;
        }

    }


    public Object getItem(ResourceType type) {
        switch (type){
            case FOOD: return food;
            case STONE:return stone;
            case WOOD: return wood;
            case GOLD: return gold;
            case WORKERS: return workers;
            case MILITARY: return military;
        }
        return null;
    }


}

class Item{
    private int positive;
    private int negative;
    private int total;

    Item(int total){
        this.total = total;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public String getNegative() {
        return String.valueOf(negative);
    }

    public String getPositive() {
        return String.valueOf(positive);
    }

    public String getTotal() {
        return String.valueOf(total);
    }

}

class Human{
    private int busy;
    private int free;
    private int total;

    public Human(int total){
        this.total = total;
        this.free = total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean incrementBusy() {
        if(busy < total){
            busy++;
            free--;
            return true;
        }
        return false;
    }

    public boolean incrementFree() {
        if (free < total){
            free++;
            busy--;
            return true;
        }
        return false;
    }

    public String getTotal() {
        return String.valueOf(total);
    }

    public String getBusy() {
        return String.valueOf(busy);
    }

    public String getFree() {
        return String.valueOf(free);
    }
}