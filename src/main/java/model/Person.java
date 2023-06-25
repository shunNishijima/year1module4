package model;

public class Person {

    private String name;
    private String password;
    private String email;
    private String mobile_number;
    private boolean is_participant;
    private String team;
    private String allergies;
    private boolean have_car;


    public Person(){}

    public Person(String name,String password, String email, String mobile_number, boolean is_participant, String team, String allergies, boolean have_car){
        this.name = name;
        this.password = password;
        this.email = email;
        this.mobile_number = mobile_number;
        this.is_participant = is_participant;
        this.team = team;
        this.allergies = allergies;
        this.have_car = have_car;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public boolean isIs_participant() {
        return is_participant;
    }

    public String getTeam() {
        return team;
    }

    public String getAllergies() {
        return allergies;
    }

    public boolean isHave_car() {
        return have_car;
    }
}
