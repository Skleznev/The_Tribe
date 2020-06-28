package com.skleznevco.thetribe;

public class GameRules {
    private Difficulty gameDifficulty;
    static private double luckyChance;
    static private Resource resource;
    static private Builds builds;
    static ResourceInterface resourceInterface;
    static Integer eventChance = 50;


    public GameRules(Difficulty gameDifficulty, ResourceInterface resourceInterface) {

        this.resourceInterface = resourceInterface;
        this.gameDifficulty = gameDifficulty;
        resource = new Resource(getGameDifficulty());
        builds = new Builds(getGameDifficulty());

        for (Resource.ResourceType type : Resource.ResourceType.values()) {
            if (type.ordinal() < 4) {
                resource.getItem(type).setPositive(calculatePositive(type));
                resource.getItem(type).setNegative(calculateNegative(type));
            }
        }
    }

    public static void pay(Payment payment) {

        for (Resource.ResourceType type : Resource.ResourceType.values()) {
            if (type == Resource.ResourceType.WORKERS) return;
            resource.getItem(type).setNegative(payment.getItemCount(type));
        }

    }

    public static double getFairCoef() {
        switch (builds.getBuilding(Builds.BuildingType.FAIR).getLevel()) {
            case 1:
                return 0.5;
            case 2:
                return 0.75;
            case 3:
                return 1;
        }
        return 0;
    }

    public static double getLuckyChance() {
        return luckyChance;
    }

    public static void updateLuckyChance() {
        GameRules.luckyChance = 0.5 + builds.getBuilding(Builds.BuildingType.CHURCH).getLevel() * 0.15;
    }

    public static double getCoefLoyal(boolean good) {
        if (good) return 1.0/2;
        return -1.0/2;
    }

    enum Difficulty {
        EASY, MEDIUM, HARD
    }

    public static boolean canPay(Payment payment) {
        for (Resource.ResourceType type : Resource.ResourceType.values()) {
            if (type == Resource.ResourceType.WORKERS) return true;
            if (payment.getItemCount(type) > resource.getItem(type).getTotalInt()) return false;
        }
        return true;
    }

    public void setGameDifficulty(Difficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;

    }

    public Difficulty getGameDifficulty() {
        return gameDifficulty;
    }

    static public Resource getResource() {
        for (Resource.ResourceType type : Resource.ResourceType.values()) {
            if (type.ordinal() < 4) {
                resource.getItem(type).setPositive(calculatePositive(type));
            }
        }

        return resource;
    }

    static public Builds getBuilds() {
        return builds;
    }

    private static int calculatePositive(Resource.ResourceType type) {
        int count = resource.getItem(type).getCountWorkers();

        int coef = 50;
        return count * coef;
    }

    private static int calculateNegative(Resource.ResourceType type) {
        switch (type) {
            case FOOD: {
                int coef = 20;
                return coef * resource.getPopulation();
            }
            case GOLD: {
                int coef = 20;
                return coef * Integer.parseInt(resource.getHuman(Resource.ResourceType.MILITARY).getTotal());
            }
        }
        return 0;

    }


    public static void makeTurn() {
        updateLuckyChance();

        for (Resource.ResourceType type : Resource.ResourceType.values()) {
            if (type.ordinal() < 4)
                resource.getItem(type).calculateTotal();
        }

        for (Resource.ResourceType type : Resource.ResourceType.values()) {
            if (type.ordinal() < 4) {
                resource.getItem(type).setPositive(calculatePositive(type));
                resource.getItem(type).setNegative(calculateNegative(type));
            }
        }
    }

    public static void addHuman(Resource.ResourceType type, int count) {
        resource.getHuman(type).addFree(count);
        if (type == Resource.ResourceType.MILITARY) {
            resource.getHuman(Resource.ResourceType.WORKERS).minusFree(count);
        }
        resource.getItem(Resource.ResourceType.GOLD).addNegative(count * 20);
        updateResourse();

    }

    public static void updateResourse() {
        resourceInterface.update();
    }

    public static Resource.ResourceType stringToType(String word) {
        if (word.equals("Gold")) return Resource.ResourceType.GOLD;
        if (word.equals("Food")) return Resource.ResourceType.FOOD;
        if (word.equals("Wood")) return Resource.ResourceType.WOOD;
        if (word.equals("Stone")) return Resource.ResourceType.STONE;
        return null;

    }


}
