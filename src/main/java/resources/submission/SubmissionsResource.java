package resources.submission;

import database.DatabaseConnection;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.UriInfo;
import model.Challenge;
import model.Person;
import model.Submission;
import resources.person.PersonResource;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/submissions")
public class SubmissionsResource {

    @Context
    UriInfo uriInfo;
    @Context
    Request request;


    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public List<Submission> getSubmissions(){
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM project_interactief.submission";
            ResultSet resultSet = statement.executeQuery(query);
            List<Submission> submissions = new ArrayList();
            while(resultSet.next()) {
                Submission submission = new Submission(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getBoolean(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9));
                submissions.add(submission);
            }
            return submissions;
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postSubmission(Submission submission) {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConn();
            Statement statement1 = connection.createStatement();
            String emailQuery = "SELECT email from project_interactief.person WHERE sessionkey = '" + submission.getHanded_in_by() + "'";
            System.out.println(emailQuery);

            ResultSet resultSet = statement1.executeQuery(emailQuery);
            String email = null;
            if(resultSet.next()) {
                email = resultSet.getString(1);
            }

            Statement statement3 = connection.createStatement();
            String id_query = "SELECT max(submission_id) FROM project_interactief.submission ";
            System.out.println(id_query);
            ResultSet resultSet1 = statement3.executeQuery(id_query);
            int id = 1000;
            if(resultSet1.next()) {
                id = resultSet1.getInt(1) + 1;
            }

            Statement statement4 = connection.createStatement();
            String team_query = "SELECT team FROM project_interactief.person WHERE email = '" + email + "'";
            System.out.println(team_query);
            ResultSet resultSet2 = statement4.executeQuery(team_query);
            String team = "";
            if(resultSet2.next()) {
                team = resultSet2.getString(1);
            }



            Statement statement2 = connection.createStatement();
            List<Object> values = Arrays.asList(id,
                    submission.getFile(),
                    submission.getIs_graded(),
                    submission.getScore(),
                    submission.getFeedback(),
                    team,
                    email,
                    "notgraded@hotmail.com",
                    submission.getChallenge());

            String query = DatabaseConnection.insertIntoAll("submission", values);
            System.out.println(query);
            statement2.executeQuery(query);
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }


    @Path("/graded")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Submission> getGradedSubmissions(){
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM project_interactief.submission WHERE is_graded = true";
            ResultSet resultSet = statement.executeQuery(query);
            List<Submission> submissions = new ArrayList();
            while(resultSet.next()) {
                Submission submission = new Submission(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getBoolean(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9));
                submissions.add(submission);
            }
            return submissions;
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    @Path("/ungraded")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Submission> getUngradedSubmissions(){
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM project_interactief.submission WHERE is_graded = false";
            ResultSet resultSet = statement.executeQuery(query);
            List<Submission> submissions = new ArrayList();
            while(resultSet.next()) {
                Submission submission = new Submission(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getBoolean(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9));
                submissions.add(submission);
            }
            return submissions;
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }

    }


    @Path("{id}")
    public SubmissionResource getSubmission(@PathParam("id") String id) {
        return new SubmissionResource(uriInfo, request, id);
    }
}
