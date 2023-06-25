package resources.person;

import database.DatabaseConnection;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    String id;
    public PersonResource(UriInfo uriInfo, Request request, String id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPerson(){
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM project_interactief.Person WHERE email = '" + id +"'";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                return (new Person(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3).toLowerCase(),
                        resultSet.getString(4),
                        resultSet.getBoolean(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getBoolean(8)));
            }
            connection.close();
            return null;
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    //we dont actually delete a person but we change the name to DELETEDPERSON
    @DELETE
    public void deletePerson(){
        try {
            String email = id;
            Connection connection = (new DatabaseConnection()).getConn();

            Statement statement2 = connection.createStatement();
            String query = "UPDATE project_interactief.submission " +
                    "SET handed_in_by = 'deleted'" +
                    "WHERE handed_in_by = '" + email + "'";
            statement2.execute(query);

            Statement statement3 = connection.createStatement();
            String delete_query = "DELETE FROM project_interactief.person WHERE email = '" + id +"'";
            statement3.execute(delete_query);
            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    @PUT
    public void updatePerson(Person person) {try {
        Connection connection = (new DatabaseConnection()).getConn();
        Statement statement = connection.createStatement();
        String query = "UPDATE project_interactief.person " +
                "SET name = '" + person.getName() + "'," +
                "password = '" + person.getPassword() + "'," +
                "email = '" + person.getEmail() + "'," +
                "mobile_number = '" + person.getMobile_number() + "'," +
                "is_participant = " + person.isIs_participant() + "," +
                "team = '" + person.getTeam() + "'," +
                "allergies = '" + person.getAllergies() + "'," +
                "have_car = " + person.isHave_car() +
                " WHERE email = '" + id +"'";
        statement.executeQuery(query);
        connection.close();
    }
    catch (SQLException e) {
        System.out.println(e);
    }

    }

    @Path("{sessionid}")
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    public void postSessionID(@PathParam("sessionid") String sessionid) {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConn();
            Statement statement = connection.createStatement();
            String query = "update project_interactief.person " +
                    "set sessionkey = '" + sessionid + "' " +
                    "where person.email = '" + id + "'";
            statement.executeQuery(query);
            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }
}
