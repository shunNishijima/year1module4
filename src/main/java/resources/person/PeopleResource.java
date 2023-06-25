package resources.person;

import database.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.UriInfo;
import model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/people")
public class PeopleResource {

    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPeople() {
        try {
            Connection connection = (new DatabaseConnection()).getConn();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM project_interactief.Person";
            ResultSet resultSet = statement.executeQuery(query);
            List<Person> people = new ArrayList();
            while(resultSet.next()) {
                Person person = new Person(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3).toLowerCase(),
                        resultSet.getString(4),
                        resultSet.getBoolean(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getBoolean(8));
                people.add(person);
            }
            connection.close();
            return people;
        }
        catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addPerson(Person person) {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConn();
            Statement statement = connection.createStatement();
            List<String> columns = Arrays.asList("name", "password", "email", "mobile_number", "is_participant", "team", "allergies", "have_car");
            List<Object> values = Arrays.asList(person.getName(),
                    person.getPassword(),
                    person.getEmail().toLowerCase(),
                    person.getMobile_number(),
                    person.isIs_participant(), person.getTeam(),
                    person.getAllergies(),
                    person.isHave_car()
                    );
            String query = DatabaseConnection.insertInto("Person", columns, values);
            System.out.println(query);
            statement.executeQuery(query);
            connection.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Path("{id}")
    public PersonResource getPerson(@PathParam("id") String id) {
        return new PersonResource(uriInfo, request, id);
    }

}
