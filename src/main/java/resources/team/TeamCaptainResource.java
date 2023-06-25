package resources.team;

import database.*;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Path("/teamCaptain")
public class TeamCaptainResource {

    @Path("{id}/members")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getTeamMembers(@PathParam("id") String id){
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT  p2.name, p2.mobile_number, p2.email, p2.allergies, p2.have_car, p2.team " +
                    "FROM project_interactief.person p1, project_interactief.person p2 " +
                    "WHERE p1.sessionkey = '" + id + "' AND p2.team = p1.team";
            ResultSet resultSet = statement.executeQuery(query);
            List<Person> team = new ArrayList<>();
            while(resultSet.next()) {
                team.add(new Person(resultSet.getString(1),
                        null,
                        resultSet.getString(3),
                        resultSet.getString(2),
                        true,
                        resultSet.getString(6),
                        resultSet.getString(4),
                        resultSet.getBoolean(5)));
            }
            connection.close();
            return team;
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }


    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Team getTeam(@PathParam("id") String id){
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM project_interactief.team WHERE captain = '" + id + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                return(new Team(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)));
            }
            connection.close();
            return null;
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

}
