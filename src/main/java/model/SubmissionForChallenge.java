package model;

import java.sql.Connection;

public class SubmissionForChallenge {

    private String c_challenge_id;
    private String c_name;
    private String c_file;
    private String c_created_by;
    private int c_score;

    private int s_submission_id;
    private String s_file;
    private boolean s_is_graded;
    private int s_score;
    private String s_feedback;
    private String s_team;
    private String s_handed_in_by;
    private String s_graded_by;

    public SubmissionForChallenge(){

    }
    public SubmissionForChallenge(String c_challenge_id, String c_name, String c_file, String c_created_by, int c_score, int s_submission_id, String s_file, boolean s_is_graded, int s_score, String s_feedback, String s_team, String s_handed_in_by, String s_graded_by){
        this.c_challenge_id = c_challenge_id;
        this.c_name = c_name;
        this.c_file = c_file;
        this.c_created_by = c_created_by;
        this.c_score = c_score;
        this.s_submission_id = s_submission_id;
        this.s_file = s_file;
        this.s_is_graded = s_is_graded;
        this.s_score = s_score;
        this.s_feedback = s_feedback;
        this.s_team = s_team;
        this.s_handed_in_by = s_handed_in_by;
        this.s_graded_by = s_graded_by;
    }

    public String getc_Challenge_id() {
        return c_challenge_id;
    }

    public String getc_Name() {
        return c_name;
    }

    public String getc_File() {
        return c_file;
    }

    public String getc_Created_by() {
        return c_created_by;
    }

    public int getc_Score() {
        return c_score;
    }

    public int gets_Submission_id(){
        return this.s_submission_id;
    }
    public String gets_File(){
        return this.s_file;
    }
    public boolean gets_Is_graded(){
        return this.s_is_graded;
    }
    public int gets_Score(){
        return this.s_score;
    }
    public String gets_Feedback(){
        return this.s_feedback;
    }
    public String gets_Team(){
        return this.s_team;
    }
    public String gets_Handed_in_by(){
        return this.s_handed_in_by;
    }
    public String gets_Graded_by(){
        return this.s_graded_by;
    }

}
