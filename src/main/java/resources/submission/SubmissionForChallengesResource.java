package resources.submission;

import database.DatabaseConnection;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.UriInfo;
import model.SubmissionForChallenge;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Path("/submissionForChallenges")
public class SubmissionForChallengesResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;


    @Path("/challenge/{sessionKey}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SubmissionForChallenge> getSubmissionsForChallenges(@PathParam("sessionKey") int sessionKey) {
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT s.*, c.* FROM project_interactief.submission s, project_interactief.challenge c " +
                    "WHERE c.challenge_id = s.challenge AND c.challenge_id LIKE 'c%' AND s.team = " +
                    "(SELECT team FROM project_interactief.person WHERE sessionkey = " + sessionKey + ")";
            ResultSet resultSet = statement.executeQuery(query);
            List<SubmissionForChallenge> submissionsForChallenges = new ArrayList();
            while(resultSet.next()) {
                SubmissionForChallenge submissionForChallenge = new SubmissionForChallenge(resultSet.getString(9),
                        resultSet.getString(10),
                        resultSet.getString(11),
                        resultSet.getString(12),
                        resultSet.getInt(13),
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getBoolean(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8));
                submissionsForChallenges.add(submissionForChallenge);
            }
            return submissionsForChallenges;
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SubmissionForChallenge> getSubmissions() {
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT s.*, c.* FROM project_interactief.submission s, project_interactief.challenge c " +
                    "WHERE c.challenge_id = s.challenge";
            ResultSet resultSet = statement.executeQuery(query);
            List<SubmissionForChallenge> submissionsForChallenges = new ArrayList();
            while(resultSet.next()) {
                SubmissionForChallenge submissionForChallenge = new SubmissionForChallenge(resultSet.getString(9),
                        resultSet.getString(10),
                        resultSet.getString(11),
                        resultSet.getString(12),
                        resultSet.getInt(13),
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getBoolean(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8));
                submissionsForChallenges.add(submissionForChallenge);
            }
            return submissionsForChallenges;
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
    @Path("/crazy88/{sessionKey}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SubmissionForChallenge> getSubmissionForCrazy88(@PathParam("sessionKey") int sessionKey) {
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT s.*, c.* FROM project_interactief.submission s, project_interactief.challenge c " +
                    "WHERE c.challenge_id = s.challenge AND c.challenge_id LIKE 'z%' AND s.team = " +
                    "(SELECT team FROM project_interactief.person WHERE sessionkey = " + sessionKey + ")";
            ResultSet resultSet = statement.executeQuery(query);
            List<SubmissionForChallenge> submissionsForChallenges = new ArrayList();
            while(resultSet.next()) {
                SubmissionForChallenge submissionForChallenge = new SubmissionForChallenge(resultSet.getString(9),
                        resultSet.getString(10),
                        resultSet.getString(11),
                        resultSet.getString(12),
                        resultSet.getInt(13),
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getBoolean(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8));
                submissionsForChallenges.add(submissionForChallenge);
            }
            return submissionsForChallenges;
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    @Path("/puzzle/{sessionKey}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SubmissionForChallenge> getSubmissionForPuzzles(@PathParam("sessionKey") int sessionKey) {
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT s.*, c.* FROM project_interactief.submission s, project_interactief.challenge c " +
                    "WHERE c.challenge_id = s.challenge AND c.challenge_id LIKE 'p%' AND s.team = " +
                    "(SELECT team FROM project_interactief.person WHERE sessionkey = " + sessionKey + ")";
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            List<SubmissionForChallenge> submissionsForChallenges = new ArrayList();
            while(resultSet.next()) {
                SubmissionForChallenge submissionForChallenge = new SubmissionForChallenge(resultSet.getString(9),
                        resultSet.getString(10),
                        resultSet.getString(11),
                        resultSet.getString(12),
                        resultSet.getInt(13),
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getBoolean(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8));
                submissionsForChallenges.add(submissionForChallenge);
            }
            return submissionsForChallenges;
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }
}
