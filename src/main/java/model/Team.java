package model;

public class Team {
    String name;
    String captain;
    int score;

    public Team(){}
    public Team(String name, String captain, int score) {
        this.name = name;
        this.captain = captain;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getCaptain() {
        return captain;
    }

    public int getScore() {
        return score;
    }
}

