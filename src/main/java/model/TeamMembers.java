package model;

import java.util.List;
public class TeamMembers {
    private List<Person> members;
    private String name;

    public TeamMembers(List<Person> members, String name){
        this.members = members;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Person> getMembers() {
        return members;
    }
}


