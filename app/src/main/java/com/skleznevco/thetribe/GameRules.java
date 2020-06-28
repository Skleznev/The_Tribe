package com.skleznevco.thetribe;

import java.util.Random;

public class GameRules {
    private static boolean workDay;
    private static int workDayCD;
    private Difficulty gameDifficulty;
    static private Resource resource;
    static private Builds builds;
    static ResourceInterface resourceInterface;
    static int eventChance;
    static int turn;


    public GameRules(Difficulty gameDifficulty, ResourceInterface resourceInterface) {

        this.resourceInterface = resourceInterface;
        this.gameDifficulty = gameDifficulty;
        this.eventChance = 50;
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
            resource.getItem(type).setTotal(resource.getItem(type).getTotalInt() - payment.getItemCount(type));
        }

    }

    public static int getWorkDayCD() {
        return workDayCD;
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

    public static int getLuckyChance() {
        return 50 + builds.getBuilding(Builds.BuildingType.CHURCH).getLevel() * 15;
    }

    public static double getCoefLoyal(boolean good) {
        if (good) return resourceInterface.getLoyalty();
        return -1.0 + resourceInterface.getLoyalty();
    }

    public static double getloyalty() {
        if (workDay) return 100;
        return 70 + builds.getBuilding(Builds.BuildingType.THEATRE).getLevel() * 10;
    }

    public static void activateWorkDay() {
        workDay = true;
        workDayCD = 3;
    }

    public static int getCountEnemy() {
        return new Random().nextInt(5) * turn;
    }

    public static String typeToString(Resource.ResourceType type) {
        switch (type) {
            case FOOD:
                return "Пища";
            case STONE:
                return "Камень";
            case WOOD:
                return "Дерево";
            case GOLD:
                return "Золото";
            case WORKERS:
                return "Рабочие";
            case MILITARY:
                return "Военные";

        }
        return null;
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
                return coef * resource.getPopulation() + calculateService(type);
            }
            case GOLD: {
                int coef = 20;
                return coef * Integer.parseInt(resource.getHuman(Resource.ResourceType.MILITARY).getTotal()) + calculateService(type);
            }
            case STONE: {
                return calculateService(type);
            }
            case WOOD: {
                return calculateService(type);
            }
        }
        return 0;

    }

    private static int calculateService(Resource.ResourceType type) {
        int cost = 0;
        for (Builds.BuildingType buildingType : Builds.BuildingType.values()) {
            cost += builds.getBuilding(buildingType).getCostService().getItemCount(type);
        }
        return cost;
    }


    public static void makeTurn() {
        workDayCD -= 1;
        turn++;
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
        Payment cost = new Payment(0, 0, 0, count * 50);
        if (canPay(cost)) {
            pay(cost);
            resource.getHuman(type).addFree(count);
            if (type == Resource.ResourceType.MILITARY) {
                resource.getHuman(Resource.ResourceType.WORKERS).minusFree(count);
            }
        }
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
