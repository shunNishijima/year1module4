package resources.challenge;

import database.DatabaseConnection;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.UriInfo;
import model.Challenge;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PuzzleResource {
        @Context
        UriInfo uriInfo;
        @Context
        Request request;
        String id;

        public PuzzleResource(UriInfo uriInfo, Request request, String id) {
            this.uriInfo = uriInfo;
            this.request = request;
            this.id = id;
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Challenge getPuzzle(){
            try {
                Connection connection = (new DatabaseConnection()).getConn();
                Statement statement = connection.createStatement();
                String query = "SELECT * FROM project_interactief.challenge WHERE challenge_id = '" + id +"'";
                ResultSet resultSet = statement.executeQuery(query);
                if(resultSet.next()) {
                    return (new Challenge(resultSet.getString(5),
                            resultSet.getString(1),
                            resultSet.getString(2).toLowerCase(),
                            resultSet.getString(3),
                            resultSet.getInt(4)));

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
        public void deletePuzzle(){
            try {
                Connection connection = (new DatabaseConnection()).getConn();
                Statement statement = connection.createStatement();
                String query = "DELETE FROM project_interactief.challenge WHERE challenge_id = '" + id +"'";
                statement.executeQuery(query);
                connection.close();
            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }


        @PUT
        @Consumes(MediaType.APPLICATION_JSON)
        public void updatePuzzle(Challenge challenge){
            try {
                Connection connection = (new DatabaseConnection()).getConn();
                Statement statement = connection.createStatement();
                String query = "UPDATE project_interactief.challenge " +
                        "SET name = '" + challenge.getName() + "'," +
                        "file = '" + challenge.getFile() + "'," +
                        "created_by = '" + challenge.getCreated_by() + "'," +
                        "score = " + challenge.getScore() + "" +
                        "WHERE challenge_id = '" + id +"'";
                statement.executeQuery(query);
                connection.close();
            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }
}
