package database;

import model.Person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DatabaseConnection {
    Connection conn;


    public DatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            String host = "bronto.ewi.utwente.nl";
            String dbName = "dab_di22232b_119";
            String currentSchema = "?currentSchema=Project_InterActief";
            String password = "teaRFQ/FIDhXItFF";
            String url = "jdbc:postgresql://" + host + ":5432/" + dbName;
            conn = DriverManager.getConnection(url, dbName, password);
        }
        catch (ClassNotFoundException e) {
            System.out.println("Can't find class");
        }
        catch (SQLException e) {
            System.out.println("sql exception");
        }
    }

    public Connection getConn() {
        return conn;
    }


    public static String loopValues(String query, List<Object> values){
        for (int i = 0; i<values.size();i++){
            query = query+ values.get(i) + "','";
        }
        query = query.substring(0, query.length()-2) + ")";
        return query;
    }

    public static String insertInto(String table, List<String> columns, List<Object> values){
    String query = "INSERT INTO project_interactief."+table+"(";
    for (int i = 0; i < columns.size(); i++){
    query = query + columns.get(i)+", ";
    }
    query = query.substring(0,query.length()-2) + ") VALUES('";
    query = loopValues(query, values);

    return query;

    }


    public static String insertIntoAll(String table, List<Object> values){
        String query = "INSERT INTO project_interactief."+table+" VALUES('";
        query = loopValues(query, values);

        return query;
    }


    public static String loopTwoLists(String startString,List<String> l1, List<Object> l2, String connector){
        for (int i = 0; i < l1.size(); i++){
            startString = startString + l1.get(i) + " = '" + l2.get(i) + "'" + connector + " ";
        }
        return startString.substring(0, startString.length()-(connector.length()+1));

    }
    public static String update(String table, List<String> columns, List<Object> values, List<String> conditionColumns, List<Object> conditionValues){
        String query = "UPDATE project_interactief." +table+" SET " ;
        query = loopTwoLists(query,columns,values, ",");

        if (conditionColumns.size()>0){
            query = query + " WHERE ";
            query = loopTwoLists(query,conditionColumns,conditionValues, "AND");
        }

        return query;
    }


    public static void main(String[] args) {
        List<String> c = Arrays.asList("poopoo", "peepee");
        List<Object> v = Arrays.asList(true, 1);
        List<String> cc = Arrays.asList("moomoo", "caca");
        List<Object> cv = Arrays.asList(false, 3);
        System.out.println(update("table", c, v, cc, cv));
    }


}
