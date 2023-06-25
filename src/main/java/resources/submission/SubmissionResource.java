package resources.submission;

import database.DatabaseConnection;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SubmissionResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    String id;

    public SubmissionResource(UriInfo uriInfo, Request request, String id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Submission getSubmission(){
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM project_interactief.submission WHERE submission_id = '" + id +"'";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                return ( new Submission(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getBoolean(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)));
            }
            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return null;
    }

    @Path("/{person}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Submission getSubmissionFromChallengeAndPerson(@PathParam("person") int person){
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM project_interactief.submission WHERE challenge = '" + id +"'" +
                    "AND team = (" +
                    "SELECT team FROM project_interactief.person WHERE sessionkey = " + person + ")";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                return ( new Submission(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getBoolean(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)));
            }
            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return null;
    }

    @DELETE
    public void deleteSubmission(){
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "DELETE FROM project_interactief.submission WHERE submission_id = '" + id +"'";
            statement.executeQuery(query);
            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    @PUT
    public void updateSubmission(Submission submission){
        try {
            Connection connection = (new DatabaseConnection()).getConn();

            Statement statement1 = connection.createStatement();
            String id_query = "SELECT email FROM project_interactief.person WHERE sessionKey = '" + submission.getGraded_by() + "'";
            ResultSet resultSet = statement1.executeQuery(id_query);
            String email = "";
            if (resultSet.next()) {
                email = resultSet.getString(1);
            }

            Statement statement = connection.createStatement();
            String query = "UPDATE project_interactief.submission " +
                    "SET file = '" + submission.getFile() + "'," +
                    "is_graded = " + submission.getIs_graded() + "," +
                    "score = " + submission.getScore() + "," +
                    "feedback = '" + submission.getFeedback() + "'," +
                    "team = '" + submission.getTeam() + "'," +
                    "handed_in_by = '" + submission.getHanded_in_by() + "'," +
                    "graded_by = '" + email + "'," +
                    "challenge = '" + submission.getChallenge() + "'" +
                    "WHERE submission_id = '" + id +"'";
            statement.execute(query);
            Statement statement2 = connection.createStatement();
            String update_score_query = "UPDATE project_interactief.team t" +
                    " SET score = (" +
                    " SELECT SUM(s.score) FROM project_interactief.submission s " +
                    " WHERE s.team = '" + submission.getTeam() + "')" +
                    " WHERE t.name = '" + submission.getTeam() + "'";
            statement2.execute(update_score_query);
            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }
}

