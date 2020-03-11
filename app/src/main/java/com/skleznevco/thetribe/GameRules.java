package com.skleznevco.thetribe;

public class GameRules {

    enum Difficulty{
        EASY, MEDIUM, HARD
    }
    private Difficulty gameDifficulty;


    public GameRules(Difficulty gameDifficulty ){
    this.gameDifficulty = gameDifficulty;
    }

    public void setGameDifficulty(Difficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

    public Difficulty getGameDifficulty() {
        return gameDifficulty;
    }
}
