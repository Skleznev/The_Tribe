package com.skleznevco.thetribe;

public class GameRules {



    enum Difficulty{
        EASY, MEDIUM, HARD
    }
    private Difficulty gameDifficulty;
    static private Resource resource;



    public GameRules(Difficulty gameDifficulty ){
    this.gameDifficulty = gameDifficulty;
        resource = new Resource(getGameDifficulty());
    }

    public void setGameDifficulty(Difficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;

    }

    public Difficulty getGameDifficulty() {
        return gameDifficulty;
    }
    static public Resource getResource(){
        for(Resource.ResourceType type : Resource.ResourceType.values()){
            if(type.ordinal()<4) {
                resource.getItem(type).setPositive(calculatePositive(type));
                resource.getItem(type).setNegative(calculateNegative(type));
            }
        }

        return resource;
    }

    private static int calculatePositive(Resource.ResourceType type) {
        int count = resource.getItem(type).getCountWorkers();

        int coef = 50;
        return  count* coef;
    }

    private static int calculateNegative(Resource.ResourceType type) {
        switch (type){
            case FOOD:{
                int coef = 20;
                return coef*resource.getPopulation();
            }
            case GOLD:{
                int coef = 20;
                return coef *Integer.parseInt(resource.getHuman(Resource.ResourceType.MILITARY).getTotal());
            }
        }
        return 0;

    }


    public static void makeTurn() {

        for(Resource.ResourceType type : Resource.ResourceType.values()){
            if(type.ordinal()<4)
                resource.getItem(type).calculateTotal();
        }
    }

}
