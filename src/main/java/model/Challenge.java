package model;

import database.DatabaseConnection;

import java.sql.Connection;

public class Challenge {
    private String challenge_id;
    private String name;
    private String file;
    private String created_by;
    private int score;

    public Challenge(){

    }
    public Challenge(String challenge_id, String name, String file, String created_by, int score){
        this.challenge_id = challenge_id;
        this.name = name;
        this.file = file;
        this.created_by = created_by;
        this.score = score;

    }

    public String getChallenge_id() {
        return challenge_id;
    }

    public String getName() {
        return name;
    }

    public String getFile() {
        return file;
    }

    public String getCreated_by() {
        return created_by;
    }

    public int getScore() {
        return score;
    }

    public void assignChallengeId(Challenge challenge, Connection connect){

    }
}
