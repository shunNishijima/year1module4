package resources.team;

import database.DatabaseConnection;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.UriInfo;
import model.Person;
import model.Team;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeamResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    String id;
    public TeamResource(UriInfo uriInfo, Request request, String id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Team getTeam(){
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM project_interactief.team WHERE name = '" + id + "'";
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

    @DELETE
    public void deleteTeam(){
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement2 = connection.createStatement();
            String team_members = "SELECT email FROM project_interactief.person WHERE team = '" + id + "'";
            ResultSet resultSet = statement2.executeQuery(team_members);
            while (resultSet.next()) {
                Statement statement3 = connection.createStatement();
                String query = "UPDATE project_interactief.submission " +
                        "SET handed_in_by = 'deleted'" +
                        "WHERE handed_in_by = '" + resultSet.getString(1) + "'";
                statement3.execute(query);

                Statement statement4 = connection.createStatement();
                String delete_query2 = "DELETE FROM project_interactief.person WHERE email = '" + resultSet.getString(1) +"'";
                statement4.execute(delete_query2);
            }
            Statement statement = connection.createStatement();
            String query = "DELETE FROM project_interactief.team WHERE name = '" + id + "'";
            System.out.println(query);
            statement.executeQuery(query);
            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }
}
