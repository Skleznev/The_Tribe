package com.skleznevco.thetribe;

public class Building{
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

