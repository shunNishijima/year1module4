package resources.challenge;

import database.DatabaseConnection;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.UriInfo;
import model.Challenge;
import model.Team;
import resources.person.PersonResource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Path("/crazy88")
public class Crazy88sResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Challenge> getCrazy88(){
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM project_interactief.challenge" +
                    " WHERE challenge_id LIKE 'z%'";
            ResultSet resultSet = statement.executeQuery(query);
            List<Challenge> challenges = new ArrayList();
            while(resultSet.next()) {
                Challenge challenge = new Challenge(resultSet.getString(5),
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4));
                challenges.add(challenge);
            }
            connection.close();
            return challenges;
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    public void postCrazy88(Challenge challenge){
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConn();
            Statement statement1 = connection.createStatement();
            String id_query = "SELECT email FROM project_interactief.person WHERE sessionKey = '" + challenge.getCreated_by() + "'";
            ResultSet resultSet = statement1.executeQuery(id_query);

            Statement statement2 = connection.createStatement();
            String z_id_query = "SELECT max(challenge_id) FROM project_interactief.challenge " +
                    "WHERE challenge_id LIKE 'z%'";
            ResultSet resultSet2 = statement2.executeQuery(z_id_query);
            String challenge_id;
            if (resultSet2.next()) {
                challenge_id = "z" + (Integer.parseInt(resultSet2.getString(1).substring(1, resultSet2.getString(1).length())) + 1);
            }
            else {
                challenge_id = "z0";
            }

            if (resultSet.next()) {
                String email = resultSet.getString(1);
                Statement statement = connection.createStatement();
                List<Object> values = Arrays.asList(challenge.getName(),challenge.getFile(),email,challenge.getScore(),challenge_id);
                String query = databaseConnection.insertIntoAll("challenge",values);
                System.out.println(query);
                statement.executeQuery(query);
            }
            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    @GET
    @Path("/unfinished/{sessionKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Challenge> getUnfinishedChallenges(@PathParam("sessionKey") int sessionkey) {
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT DISTINCT c.* FROM project_interactief.challenge c, project_interactief.submission s" +
                    " WHERE c.challenge_id LIKE 'z%' AND c.challenge_id NOT IN (" +
                    " SELECT DISTINCT s1.challenge FROM project_interactief.challenge c1, project_interactief.submission s1 WHERE s1.team= ( " +
                    " SELECT team FROM project_interactief.person" +
                    " WHERE sessionkey = " + sessionkey + "))";
            ResultSet resultSet = statement.executeQuery(query);
            List<Challenge> challenges = new ArrayList();
            while(resultSet.next()) {
                Challenge challenge = new Challenge(resultSet.getString(5),
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4));
                challenges.add(challenge);
            }
            connection.close();
            return challenges;
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }

    }

    @Path("{id}")
    public Crazy88Resource getCrazy88(@PathParam("id") String id) {
        return new Crazy88Resource(uriInfo, request, id);
    }
}
