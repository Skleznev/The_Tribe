package com.skleznevco.thetribe;

import com.skleznevco.thetribe.Models.Payment;

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
    static public int delta;
    static public int prisonerTurn;
    static Resource.ResourceType powerType;
    static int countEnemy;
    static int countEnemyNext;
    static int prisonerCount;
    static boolean winBattle;

    public GameRules(Difficulty gameDifficulty, ResourceInterface resourceInterface) {
        turn = 0;
        this.resourceInterface = resourceInterface;
        this.gameDifficulty = gameDifficulty;
        this.eventChance = 50;
        countEnemy=0;
        countEnemyNext=generateCountEnemy(turn);
        resource = new Resource(getGameDifficulty());
        builds = new Builds(getGameDifficulty());

        for (Resource.ResourceType type : Resource.ResourceType.values()) {
            if (type.ordinal() < 4) {
                resource.getItem(type).setPositive(calculatePositive(type));
                resource.getItem(type).setNegative(calculateNegative(type));
            }
        }
    }

    public static int getCountEnemyNext() {
        return countEnemyNext;
    }

    private static int generateCountEnemy(int turn) {
        return (int) ((new Random().nextInt(50)+50)*0.01 * turn);
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
        return countEnemy;
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

    public static double getCoefPower(int level) {
        switch (level){
            case 1: return 1.5;
            case 2: return 1.75;
            case 3: return 2.0;
        }
        return 0;
    }

    public static void setPowerType(Resource.ResourceType type) {
        powerType=type;
    }

    public static Resource.ResourceType getPowerType() {
        return powerType;
    }

    public static boolean isVision() {
        return builds.getBuilding(Builds.BuildingType.TOWER).getLevel()==3;
    }

    public static int getPrisonerCount() {
        return prisonerCount;
    }
    static void addPrisoner(int count){
        prisonerCount+=count;
    }
    static void minusPrisoner(int count){
        prisonerCount-=count;
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
                if (type==powerType){
                    resource.getItem(type).setPositive((int)(calculatePositive(type)*getCoefPower(builds.getBuilding(Builds.BuildingType.POWER).getLevel())));
                }
                else resource.getItem(type).setPositive(calculatePositive(type));
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
                int coef;
                if (builds.getBuilding(Builds.BuildingType.BARRACKS).getLevel()==3) coef=0;
                else coef = 20;
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
        if (turn>=48) resourceInterface.theEnd();
        workDayCD -= 1;
        turn++;
        for (Resource.ResourceType type : Resource.ResourceType.values()) {
            if (type.ordinal() < 4){
                resource.getItem(type).calculateTotal();
                if (resource.getItem(type).getTotalInt()<0) resourceInterface.theEnd();
            }
        }

        for (Resource.ResourceType type : Resource.ResourceType.values()) {
            if (type.ordinal() < 4) {
                resource.getItem(type).setPositive(calculatePositive(type));
                resource.getItem(type).setNegative(calculateNegative(type));
            }
        }
        calculateBattle();
        countEnemy = countEnemyNext;
        countEnemyNext = generateCountEnemy(turn);
        workDay = false;
    }

    private static void calculateBattle() {
        delta = Integer.parseInt(resource.getHuman(Resource.ResourceType.MILITARY).getBusy())-countEnemyNext;
        if (delta >= 0) {
            double coef = 0;
            if (builds.getBuilding(Builds.BuildingType.JAIL).getLevel()==0) coef=0;
            if (builds.getBuilding(Builds.BuildingType.JAIL).getLevel()==1) coef=0.5;
            if (builds.getBuilding(Builds.BuildingType.JAIL).getLevel()==2) coef=0.75;
            if (builds.getBuilding(Builds.BuildingType.JAIL).getLevel()==3) coef=1;
            prisonerTurn=(int)(countEnemy*coef);
            addPrisoner(prisonerTurn);
            winBattle=true;
        }
        else{
            if(Integer.parseInt(resource.getHuman(Resource.ResourceType.MILITARY).getTotal())>-delta){
                resource.getHuman(Resource.ResourceType.MILITARY).setTotal(Integer.parseInt(resource.getHuman(Resource.ResourceType.MILITARY).getTotal())+delta);
                resourceInterface.resetSheild();
            }
            else{
                if(Integer.parseInt(resource.getHuman(Resource.ResourceType.WORKERS).getTotal())>-delta){
                    resource.getHuman(Resource.ResourceType.WORKERS).setTotal(Integer.parseInt(resource.getHuman(Resource.ResourceType.WORKERS).getTotal())+delta);
                    for(int i=0; i<4;i++)
                    resource.getItem(Resource.ResourceType.values()[i]).setCountWorkers(0);
                    resourceInterface.update();
                }
                else{
                    resourceInterface.theEnd();

                }
            }
            winBattle=false;
        }
        resourceInterface.update();
    }

    public static boolean isWinBattle() {
        return winBattle;
    }

    public static void addHuman(Resource.ResourceType type, int count) {
        if (type ==Resource.ResourceType.WOOD){
            Payment cost = new Payment(0, 0, 0, count * 20);
            if (canPay(cost)) {
                pay(cost);
                resource.getHuman(Resource.ResourceType.WORKERS).addFree(count);
                minusPrisoner(count);
            }
        }
        if (type == Resource.ResourceType.MILITARY) {
            int coef;
            if (builds.getBuilding(Builds.BuildingType.BARRACKS).getLevel() > 1) coef = 20;
            else coef = 50;
            Payment cost = new Payment(0, 0, 0, count * coef);
            if (canPay(cost)) {
                pay(cost);
                resource.getHuman(type).addFree(count);
                resource.getHuman(Resource.ResourceType.WORKERS).minusFree(count);
            }
        }
        if (type == Resource.ResourceType.WORKERS) {
            Payment cost = new Payment(0, 0, 0, count * 50);
            if (canPay(cost)) {
                pay(cost);
                resource.getHuman(type).addFree(count);
            }
        }
        updateResourse();

    }

    public static void updateResourse() {
        resourceInterface.update();
    }

    public static Resource.ResourceType stringToType(String word) {
        if (word.equals("Gold") || word.equals("Золото")) return Resource.ResourceType.GOLD;
        if (word.equals("Food") || word.equals("Пища")) return Resource.ResourceType.FOOD;
        if (word.equals("Wood") || word.equals("Дерево")) return Resource.ResourceType.WOOD;
        if (word.equals("Stone") || word.equals("Камень")) return Resource.ResourceType.STONE;
        return null;

    }


}
