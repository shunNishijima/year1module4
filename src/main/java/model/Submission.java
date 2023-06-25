package model;

public class Submission {

    private int submission_id;
    private String file;
    private boolean is_graded;
    private int score;
    private String feedback;
    private String team;
    private String handed_in_by;
    private String graded_by;
    private String challenge;

    public Submission(){

    }

    public Submission(int submission_id, String file, boolean is_graded, int score, String feedback, String team, String handed_in_by, String graded_by, String challenge){
        this.submission_id = submission_id;
        this.file = file;
        this.is_graded = is_graded;
        this.score = score;
        this.feedback = feedback;
        this.team = team;
        this.handed_in_by = handed_in_by;
        this.graded_by = graded_by;
        this.challenge = challenge;
    }

    public int getSubmission_id(){
        return this.submission_id;
    }
    public String getFile(){
        return this.file;
    }
    public boolean getIs_graded(){
        return this.is_graded;
    }
    public int getScore(){
        return this.score;
    }
    public String getFeedback(){
        return this.feedback;
    }
    public String getTeam(){
        return this.team;
    }
    public String getHanded_in_by(){
        return this.handed_in_by;
    }
    public String getGraded_by(){
        return this.graded_by;
    }
    public String getChallenge(){
        return this.challenge;
    }


}
