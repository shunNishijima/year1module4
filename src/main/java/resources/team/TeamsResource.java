package resources.team;

import database.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import model.*;

import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Path("/teams")
public class TeamsResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Team> getTeams(){
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM project_interactief.team";
            ResultSet resultSet = statement.executeQuery(query);
            List<Team> teams = new ArrayList();
            while(resultSet.next()) {
                Team team = new Team(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3));
                teams.add(team);
            }
            connection.close();
            return teams;
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postTeam(Team team){
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConn();
            Statement statement = connection.createStatement();
            String query = "INSERT INTO project_interactief.team(name, captain, score)" +
                    "VALUES('" + team.getName() + "','"
                    + team.getCaptain() + "',"
                    + team.getScore() + ")";
            statement.executeQuery(query);
            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    @GET
    @Path("/members")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TeamMembers> getTeamsMembers(){
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT t.name as teamname, p.name, p.mobile_number, p.email, p.allergies, p.have_car " +
                    "FROM project_interactief.team t, project_interactief.person p " +
                    "WHERE p.team = t.name";
            ResultSet resultSet = statement.executeQuery(query);
            List<TeamMembers> teamsMembers = new ArrayList();
            while(resultSet.next()) {
                String teamName = resultSet.getString(1);
                TeamMembers teamMembers = notContainsMember(teamsMembers, teamName);
                if (teamMembers == null) {
                    List<Person> members = new ArrayList<>();
                    members.add(new Person(resultSet.getString(2),
                            null,
                            resultSet.getString(4),
                            resultSet.getString(3),
                            true,
                            teamName,
                            resultSet.getString(5),
                            resultSet.getBoolean(6)));
                    teamMembers = new TeamMembers(members, teamName);
                    teamsMembers.add(teamMembers);
                }
               else {
                   teamMembers.getMembers().add(new Person(resultSet.getString(2),
                           null,
                           resultSet.getString(4),
                           resultSet.getString(3),
                           true,
                           teamName,
                           resultSet.getString(5),
                           resultSet.getBoolean(6)));
                }
            }
            connection.close();
            return teamsMembers;
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public TeamMembers notContainsMember(List<TeamMembers> teamsMembers, String teamName){
        for (TeamMembers teamMembers : teamsMembers) {
            if (teamMembers.getName().equals(teamName)) {
                return teamMembers;
            }
        }
        return null;
    }

    @Path("{id}")
    public TeamResource getTeam(@PathParam("id") String id) {
        return new TeamResource(uriInfo, request, id);
    }
}
