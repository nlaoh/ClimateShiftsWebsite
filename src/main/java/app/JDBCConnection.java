package app;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Database in Java.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    public static final String DATABASE = "jdbc:sqlite:database\\climate.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    // TODO: (Natchanon Laoharawee) Add your required methods here

    /**
     * Methods for @author Natchanon Laoharawee
     */
    // For creating personas
    /**
     * Persona class that stores name, filepath, and description as strings.
     * 
     * @author Natchanon Laoharawee
     */
    public class Persona {
        private String name;
        private String filepath;
        private String description;

        public Persona(String name, String filepath, String description) {
            this.name = name;
            this.filepath = filepath;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getFilePath() {
            return filepath;
        }

        public String getDescription() {
            return description;
        }
    }

    public ArrayList<Persona> getPersonas() {
        Connection connection = null;
        ArrayList<Persona> Personas = new ArrayList<Persona>();

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the first and last years
            String query = "SELECT * FROM Persona;";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String name = result.getString("name");
                String filepath = result.getString("Image_FilePath");
                String description = result.getString("description");

                Persona persona = new Persona(name, filepath, description);
                Personas.add(persona);

            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return Personas;
    }

    public ArrayList<String> getPersonaPaths() {
        Connection connection = null;
        ArrayList<String> PersonaPaths = new ArrayList<String>();

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the first and last years
            String query = "SELECT Image_FilePath FROM Persona;";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String filepath = result.getString("Image_FilePath");
                PersonaPaths.add(filepath);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return PersonaPaths;
    }

    public ArrayList<String> getPersonaPath() {
        Connection connection = null;
        ArrayList<String> PersonaPaths = new ArrayList<String>();

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the first and last years
            String query = "SELECT Image_FilePath FROM Persona;";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String filepath = result.getString("Image_FilePath");
                PersonaPaths.add(filepath);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return PersonaPaths;
    }

    public ArrayList<String> getPersonaNames() {
        Connection connection = null;
        ArrayList<String> list = new ArrayList<String>();

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the first and last years
            String query = "SELECT Name FROM Persona ORDER BY Image_FilePath;";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String name = result.getString("Name");
                list.add(name);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return list;
    }

    public ArrayList<String> getPersonaAttributesHeading(String searchWord) {
        Connection connection = null;
        ArrayList<String> list = new ArrayList<String>();

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the first and last years
            String query = "SELECT AttributeType FROM PersonaAttribute WHERE AttributeType = '" + searchWord
                    + "' ORDER BY ID;";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String personaAttribute = result.getString("AttributeType");
                list.add(personaAttribute);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return list;
    }

    public ArrayList<String> getPersonaAttributes(String searchWord) {
        Connection connection = null;
        ArrayList<String> list = new ArrayList<String>();

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the first and last years
            String query = "SELECT Description FROM PersonaAttribute WHERE AttributeType = '" + searchWord
                    + "' ORDER BY ID;";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String personaAttributeDesc = result.getString("Description");
                list.add(personaAttributeDesc);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return list;
    }

    public ArrayList<String> getPersonaAttributes(int personaNum, String searchWord) {
        Connection connection = null;
        ArrayList<String> list = new ArrayList<String>();

        try {
            connection = DriverManager.getConnection(DATABASE);
            // Find persona name using personanum
            ArrayList<String> personaNames = getPersonaNames();

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // Get the first and last years
            String query = "SELECT pa.Description FROM PersonaAttribute pa JOIN Persona p ON pa.PersonaName = p.Name WHERE pa.PersonaName = '"
                    + personaNames.get(personaNum - 1) + "' AND AttributeType = '" + searchWord + "'  ORDER BY ID;";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String attributeDesc = result.getString("Description");
                list.add(attributeDesc);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return list;
    }

    public ArrayList<String> getAuthorName() {
        Connection connection = null;
        ArrayList<String> list = new ArrayList<String>();

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the first and last years
            String query = "SELECT FirstName, LastName FROM Student ORDER BY FirstName;";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String fullName = result.getString("FirstName") + " " + result.getString("LastName");
                list.add(fullName);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return list;
    }

    public ArrayList<String> getStudentID() {
        Connection connection = null;
        ArrayList<String> list = new ArrayList<String>();

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the first and last years
            String query = "SELECT SID FROM Student ORDER BY FirstName;";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                list.add(result.getString("SID"));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return list;
    }

    public ArrayList<String> getAuthorEmail() {
        Connection connection = null;
        ArrayList<String> list = new ArrayList<String>();

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the first and last years
            String query = "SELECT EmailAddress FROM Student ORDER BY FirstName;";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                list.add(result.getString("EmailAddress"));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return list;
    }

    public ArrayList<String> getAuthorRole(String studentID) {
        Connection connection = null;
        ArrayList<String> list = new ArrayList<String>();

        try {
            connection = DriverManager.getConnection(DATABASE);
            // Find persona name using personanum
            // ArrayList<String> personaNames = getPersonaNames();

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            // Get the first and last years
            String query = "SELECT RoleTitle FROM StudentRole sr JOIN Student s ON s.SID = sr.SID WHERE sr.SID = '"
                    + studentID + "' ORDER BY RoleTitle;";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                String attributeDesc = result.getString("RoleTitle");
                list.add(attributeDesc);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return list;
    }

    public class ShallowGlanceT2Data {
        private String country;
        private String stateOrCityName;
        private int year;
        private double maxTemp;
        private double avgTemp;
        private double minTemp;

        public ShallowGlanceT2Data(String country, String stateOrCityName, int year, double maxTemp, double avgTemp,
                double minTemp) {
            this.country = country;
            this.stateOrCityName = stateOrCityName;
            this.year = year;
            this.maxTemp = maxTemp;
            this.avgTemp = avgTemp;
            this.minTemp = minTemp;

        }

        public String getCountry() {
            return country;
        }

        public String getStateOrCityName() {
            return stateOrCityName;
        }

        public int getYear() {
            return year;
        }

        public double getMaxTemp() {
            return maxTemp;
        }

        public double getAvgTemp() {
            return avgTemp;
        }

        public double getMinTemp() {
            return minTemp;
        }
    }

    // TODO: get data from database
    public ArrayList<ShallowGlanceT2Data> getShallowGlanceT2Data(String country, String stateOrCityInput, int year) {
        Connection connection = null;
        ArrayList<ShallowGlanceT2Data> data = new ArrayList<>();

        // parse year inputs to primitive int
        // int startYear = Integer.parseInt(startYearInput);
        // int endYear = Integer.parseInt(endYearInput);

        // parse stateOrCity variable for query
        String stateOrCity = "";
        String countryQry = "";
        String table = "";
        if (stateOrCityInput == null) {
            stateOrCity = "StateName";
            table = "StateTempObservation";
        } else if (stateOrCityInput.equals("states")) {
            stateOrCity = "StateName";
            table = "StateTempObservation";
        } else if (stateOrCityInput.equals("cities")) {
            stateOrCity = "CityName";
            table = "CityTempObservation";
        } else {
            stateOrCity = "ERROR: UNEXPECTED state/city INPUT";
        }

        // Query connection
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "SELECT CountryName, " + stateOrCity
                    + ", Year, AvgTemp, MaxTemp, MinTemp FROM " + table + " WHERE Year = " + year + " AND CountryName = '" + country + "' GROUP BY " + stateOrCity + " ORDER BY " + stateOrCity + " DESC, Year DESC;";
            ResultSet result = statement.executeQuery(query);
            // go through results
            while (result.next()) {
                ShallowGlanceT2Data currentRecord = new ShallowGlanceT2Data(result.getString("CountryName"),
                        result.getString(stateOrCity), result.getInt("Year"), result.getDouble("MaxTemp"), result.getDouble("AvgTemp"),
                        result.getDouble("MinTemp"));
                data.add(currentRecord);
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return data;
    }

    public ArrayList<Integer> getGlobalTemperatureYears() {
        Connection connection = null;
        ArrayList<Integer> data = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT DISTINCT(Year) FROM GlobalTempObservation;";
            ResultSet result = statement.executeQuery(query);
            // go through results
            while (result.next()) {
                Integer currentYear = Integer.parseInt(result.getString("Year"));
                data.add(currentYear);
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return data;
    }

    public ArrayList<Integer> getTotalYearsByCountryAndStateOrCity(String country, String stateOrCityInput) {
        Connection connection = null;
        ArrayList<Integer> data = new ArrayList<>();
        String table = "";
        if (stateOrCityInput == null) {

        } else if (stateOrCityInput.equals("states")) {
            table = "StateTempObservation";
        } else if (stateOrCityInput.equals("cities")) {
            table = "CityTempObservation";
        } else {
            System.out.println("ERROR: UNEXPECTED state/city INPUT");
        }

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT DISTINCT t.Year FROM CountryTempObservation c JOIN " + table
                    + " t ON c.Year = t.Year WHERE t.countryName = '" + country + "' ORDER BY t.Year ASC;";
            ResultSet result = statement.executeQuery(query);
            // go through results
            while (result.next()) {
                data.add(Integer.parseInt(result.getString("Year")));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return data;
    }

    public ArrayList<String> getTotalCountries() {
        Connection connection = null;
        ArrayList<String> data = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT DISTINCT CountryName FROM CountryTempObservation ORDER BY CountryName ASC;";
            ResultSet result = statement.executeQuery(query);
            // go through results
            while (result.next()) {
                data.add(result.getString("CountryName"));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return data;
    }

    public ArrayList<String> getTotalStates() {
        Connection connection = null;
        ArrayList<String> data = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT DISTINCT StateName FROM StateTempObservation ORDER BY StateName ASC;";
            ResultSet result = statement.executeQuery(query);
            // go through results
            while (result.next()) {
                data.add(result.getString("StateName"));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return data;
    }

    public ArrayList<String> getTotalCities() {
        Connection connection = null;
        ArrayList<String> data = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT DISTINCT CityName FROM CityTempObservation ORDER BY CityName ASC;";
            ResultSet result = statement.executeQuery(query);
            // go through results
            while (result.next()) {
                data.add(result.getString("CityName"));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return data;
    }

    public ArrayList<Integer> getTotalYears(String country, String state, String city) {
        Connection connection = null;
        ArrayList<Integer> data = new ArrayList<>();
        String table = "";
        String qry = "";
        if (country != null) {
            table = "CountryTempObservation";
            qry = "WHERE CountryName = '" + country + "'";
        } else if (state != null) {
            table = "StateTempObservation";
            qry = "WHERE StateName = '" + state + "'";
        } else if (city != null) {
            table = "CityTempObservation";
            qry = "WHERE CityName = '" + city + "'";
        } else {
            table = "GlobalTempObservation";
            qry = "";
        }
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT DISTINCT Year FROM " + table + " " + qry + " ORDER BY Year ASC;";
            System.out.println("Executing: " + query);
            ResultSet result = statement.executeQuery(query);
            // go through results
            while (result.next()) {
                data.add(Integer.parseInt(result.getString("Year")));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return data;
    }

    ArrayList<Double> getAvgTemp(String countryInput, String stateInput, String cityInput, String yearInput, String timePeriodInput) {
        Connection connection = null;
        ArrayList<Double> data = new ArrayList<>();
        String table = "";
        String qry = "";
        int yearInt;
        int timePeriodInt;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            try {
                yearInt = Integer.parseInt(yearInput);
                timePeriodInt = Integer.parseInt(timePeriodInput);
            } catch (Exception e) {
                yearInt = 0;
                timePeriodInt = 0;
            }
            if (countryInput != null && stateInput != null && cityInput != null) {
                table = "GlobalTempObservation";
                qry = "WHERE Year >= " + yearInt + " AND Year <= " + (yearInt + timePeriodInt);
            }
            else if (countryInput != null) {
                table = "CountryTempObservation";
                qry = "WHERE CountryName = '" + countryInput + "' AND Year >= " + yearInt + " AND Year <= " + (yearInt + timePeriodInt);
            } else if (stateInput != null) {
                table = "StateTempObservation";
                qry = "WHERE StateName = '" + stateInput + "' AND Year >= " + yearInt + " AND Year <= " + (yearInt + timePeriodInt);
            } else if (cityInput != null) {
                table = "CityTempObservation";
                qry = "WHERE CityName = '" + cityInput + "' AND Year >= " + yearInt + " AND Year <= " + (yearInt + timePeriodInt);
            } else {
                table = "GlobalTempObservation";
                qry = "WHERE Year >= " + yearInt + " AND Year <= " + (yearInt + timePeriodInt);
            }
            statement.setQueryTimeout(30);
            String query = "SELECT AvgTemp FROM " + table + " " + qry + " ORDER BY Year ASC;";
            System.out.println("Executing: " + query);
            ResultSet result = statement.executeQuery(query);
            //go through results
            while (result.next()) {
                data.add(Double.parseDouble(result.getString("AvgTemp")));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
            if (connection != null) {
                connection.close();
            }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return data;
    }

    public void insertUserInput(String regionInput, String countryInput, String stateInput, String cityInput, String yearInput, String timePeriodInput) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = """
                    INSERT INTO DeepDive1UserInput (regionInput, countryInput, stateInput, cityInput, yearInput, timePeriodInput) VALUES ('%s', '%s', '%s', '%s','%s', '%s');
                    """.formatted(regionInput, countryInput, stateInput, cityInput, yearInput, timePeriodInput);
            statement.executeQuery(query);
            statement.close();
        } catch (SQLException e) {
        System.err.println(e.getMessage());
        } finally {
        try {
        if (connection != null) {
        connection.close();
        }
        } catch (SQLException e) {
        System.err.println(e.getMessage());
        }
        }
    }

    public void deleteUserInput() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query1 = "UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME='DeepDive1UserInput';";
            String query2 = "DELETE FROM DeepDive1UserInput;";
            statement.executeUpdate(query1);
            System.out.println("Executing: ss" + query1);
            statement.executeUpdate(query2);
            System.out.println("Executing: " + query2);
            statement.close();
        } catch (SQLException e) {
        System.err.println(e.getMessage());
        } finally {
        try {
        if (connection != null) {
        connection.close();
        }
        } catch (SQLException e) {
        System.err.println(e.getMessage());
        }
        }
    }

    ArrayList<String> getUserInput(String sortInput) {
        Connection connection = null;
        ArrayList<String> data = new ArrayList<>();
        try {
            String sort = "";
            try {
                if (sortInput.equals("original")) {
                    sort = "ORDER BY ID DESC";
                }
                else if (sortInput.equals("temperature")) {
                    // sort = "ORDER BY Avg";
                }
            } catch (Exception e) {
                
            }
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "SELECT * FROM DeepDive1UserInput " + sort;
            System.out.println("Executing: " + query);
            ResultSet result = statement.executeQuery(query);
            //go through results
            while (result.next()) {
                data.add(result.getString("regionInput"));
                data.add(result.getString("countryInput"));
                data.add(result.getString("stateInput"));
                data.add(result.getString("cityInput"));
                data.add(result.getString("yearInput"));
                data.add(result.getString("timePeriodInput"));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
            if (connection != null) {
                connection.close();
            }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return data;
    }
    //TODO: Fix temperature change values in level 2
    ArrayList<Double> getAvgTemp(String countryInput, String stateOrCityInput, String startYearInput, String endYearInput) {
        Connection connection = null;
        ArrayList<Double> data = new ArrayList<>();
        String table = "";
        String qry = "";
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            //get years as primitive ints
            try {

                qry = "t.countryName = '" + countryInput + "' AND t.year >= " + startYearInput + " AND t.year <= " + endYearInput; 
            } catch (Exception e) {

            }
            //parse stateorcityinput
            try {
                if (stateOrCityInput == null) {

                } else if (stateOrCityInput.equals("states")) {
                    table = "StateTempObservation";
                } else if (stateOrCityInput.equals("cities")) {
                    table = "CityTempObservation";
                } else {
                    System.out.println("ERROR: UNEXPECTED state/city INPUT");
                }
            } catch (Exception e) {

            }
            statement.setQueryTimeout(30);
            // String query = "SELECT AvgTemp FROM " + table + " " + qry + " ORDER BY Year ASC;";
            String query = "SELECT DISTINCT t.Year FROM CountryTempObservation c JOIN " + table
                    + " t ON c.Year = t.Year WHERE " + qry + " ORDER BY t.Year ASC;";
            System.out.println("Executing: " + query);
            ResultSet result = statement.executeQuery(query);
            //go through results
            while (result.next()) {
                data.add(Double.parseDouble(result.getString("AvgTemp")));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
            if (connection != null) {
                connection.close();
            }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return data;
    }


    // Template jdbc connection
    // public variable Methodname (example example) {
    // Connection connection = null;
    // ArrayList<Object> data = new ArrayList<>();

    // try {
    // connection = DriverManager.getConnection(DATABASE);
    // Statement statement = connection.createStatement();
    // statement.setQueryTimeout(30);
    // String query = "";
    // ResultSet result = statement.executeQuery(query);
    // //go through results
    // while (result.next()) {
    // ShallowGlanceT2Data currentRecord = new ShallowGlanceT2Data(country,
    // stateOrCityInput, year, maxTemp, avgTemp, minTemp)
    // data.add(result.getString("EmailAddress"));
    // }

    // statement.close();
    // } catch (SQLException e) {
    // System.err.println(e.getMessage());
    // } finally {
    // try {
    // if (connection != null) {
    // connection.close();
    // }
    // } catch (SQLException e) {
    // System.err.println(e.getMessage());
    // }
    // }
    // return data;
    // }
    // TODO: (Russell Sheikh) Add your required methods here
    /**
     * Methods for @author Russell Sheikh
     */

    public long[] getFirstAndLastYearsPopulation() {
        long[] years = new long[4];
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the first and last years
            String query = "SELECT MIN(Year) AS first_year, MAX(Year) AS last_year FROM CountryTempObservation WHERE Population > 0";
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                years[0] = result.getLong("first_year");
                years[1] = result.getLong("last_year");
            }

            String query2 = "SELECT SUM(Population) AS totalPop FROM CountryTempObservation WHERE Year = " + years[0];
            ResultSet result2 = statement.executeQuery(query2);
            if (result2.next()) {
                years[2] = result2.getLong("totalPop");
            }

            String query3 = "SELECT SUM(Population) AS totalPop FROM CountryTempObservation WHERE Year = " + years[1];
            ResultSet result3 = statement.executeQuery(query3);
            if (result3.next()) {
                years[3] = result3.getLong("totalPop");
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return years;
    }

    public int totalNumOfYearsPopulation() {
        int totalYearsPopulation = 0;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the first and last years
            String query = "SELECT COUNT(DISTINCT Year) AS totalYears FROM CountryTempObservation WHERE Population > 0;";
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                totalYearsPopulation = result.getInt("totalYears");
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return totalYearsPopulation;
    }

    public double[] getFirstAndLastYearsTemperature() {
        double[] years = new double[4];
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the first and last years
            String query = "SELECT MIN(Year) AS first_year, MAX(Year) AS last_year FROM GlobalTempObservation;";
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                years[0] = result.getDouble("first_year");
                years[1] = result.getDouble("last_year");
            }

            String query2 = "SELECT avgTemp AS avgFirst FROM GlobalTempObservation WHERE Year = " + years[0];
            ResultSet result2 = statement.executeQuery(query2);
            if (result2.next()) {
                years[2] = result2.getDouble("avgFirst");
            }

            String query3 = "SELECT avgTemp AS avgLast FROM GlobalTempObservation WHERE Year = " + years[1];
            ResultSet result3 = statement.executeQuery(query3);
            if (result.next()) {
                years[3] = result3.getDouble("avgLast");
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return years;
    }

    public int totalNumOfYearsTemp() {
        int totalYearsTemp = 0;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the first and last years
            String query = "SELECT COUNT(DISTINCT Year) AS totalYears FROM GlobalTempObservation;";
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                totalYearsTemp = result.getInt("totalYears");
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return totalYearsTemp;
    }

    /*
     * Get the years and populations as a list of PopulationEntry objects
     */

    public class PopulationEntry {
        public long year;
        public long population;

        public PopulationEntry(long year, long population) {
            this.year = year;
            this.population = population;
        }
    }

    public List<PopulationEntry> getYearsAndPopulations() {
        List<PopulationEntry> populationData = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the years and populations
            String query = "SELECT Year, SUM(Population) AS TotalPopulation FROM CountryTempObservation WHERE Population > 0 GROUP BY Year;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                long year = result.getLong("Year");
                long population = result.getLong("TotalPopulation");

                populationData.add(new PopulationEntry(year, population));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return populationData;
    }

    /*
     * Get the years and temp as a list of TemperatureEntry objects
     */

    public class TemperatureEntry {
        public int year;
        public double temperature;

        public TemperatureEntry(int year, double temperature) {
            this.year = year;
            this.temperature = temperature;
        }
    }

    public List<TemperatureEntry> getYearsAndTemperature() {
        List<TemperatureEntry> TemperatureData = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the years and populations
            String query = "SELECT Year, avgTemp AS averageTemperature FROM GlobalTempObservation GROUP BY year ORDER BY year;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                int year = result.getInt("Year");
                Double temperature = result.getDouble("averageTemperature");

                TemperatureData.add(new TemperatureEntry(year, temperature));
            }

            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return TemperatureData;
    }

    // make a method that gets the counties and their population and year
    public class CountryPopulationEntry {
        public String country;
        public int year;

        public CountryPopulationEntry(String country, int year) {
            this.country = country;
            this.year = year;
        }

        public String getCountry() {
            return country;
        }

        public int getYear() {
            return year;
        }
    }

    // list of counties and their population and year
    public ArrayList<CountryPopulationEntry> getCountryPopulation() {
        ArrayList<CountryPopulationEntry> CountryPopulationData = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the years and populations
            String query = "SELECT CountryName, Population, Year FROM CountryTempObservation GROUP BY CountryName, Year;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String country = result.getString("CountryName");
                int year = result.getInt("Year");

                CountryPopulationData.add(new CountryPopulationEntry(country, year));
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return CountryPopulationData;
    }

    /* Gets the data for selected country's start and end year */
    public class selectedCountryPopulationEntry {
        public String country;
        public int year;
        long population;
        double averageTemperature;

        public selectedCountryPopulationEntry(String country, int year, long population, double averageTemperature) {
            this.country = country;
            this.year = year;
            this.population = population;
            this.averageTemperature = averageTemperature;

        }

        public String getCountry() {
            return country;
        }

        public int getYear() {
            return year;
        }

        public long getPopulation() {
            return population;
        }

        public double getAverageTemperature() {
            return averageTemperature;
        }
    }

    // list of counties and their population and year
    public ArrayList<selectedCountryPopulationEntry> getSelectedCountryPopulation(String country, int startYear,
            int endYear) {
        ArrayList<selectedCountryPopulationEntry> CountryPopulationData = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the years and populations
            String query = "SELECT CountryName, Year, Population, avgTemp FROM CountryTempObservation WHERE CountryName = '"
                    + country
                    + "' AND Year >= " + startYear + " AND Year <= " + endYear
                    + " GROUP BY CountryName, Year, Population;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String country1 = result.getString("CountryName");
                int year = result.getInt("Year");
                long population = result.getLong("Population");
                double averageTemperature = result.getDouble("avgTemp");

                CountryPopulationData
                        .add(new selectedCountryPopulationEntry(country1, year, population, averageTemperature));
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return CountryPopulationData;
    }

    public class getWorldPopulationEntry {
        public int year;
        public long population;
        public double averageTemperature;

        public getWorldPopulationEntry(int year, long population, double averageTemperature) {
            this.year = year;
            this.population = population;
            this.averageTemperature = averageTemperature;
        }

        public int getYear() {
            return year;
        }

        public long getPopulation() {
            return population;
        }

        public double getAverageTemperature() {
            return averageTemperature;
        }
    }

    public ArrayList<getWorldPopulationEntry> getWorldPopulation(int startYear, int endYear) {
        ArrayList<getWorldPopulationEntry> WorldPopulationData = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the years and populations
            String query = "SELECT c.Year, SUM(c.Population) AS TotalPopulation, ROUND(AVG(g.avgTemp), 3) AS AverageTemperature FROM CountryTempObservation AS c JOIN GlobalTempObservation AS g ON c.Year = g.Year WHERE c.Year >= "
                    + startYear + " AND c.Year <= " + endYear + " GROUP BY c.Year;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                int year = result.getInt("Year");
                long population = result.getLong("TotalPopulation");
                Double averageTemperature = result.getDouble("AverageTemperature");

                WorldPopulationData.add(new getWorldPopulationEntry(year, population, averageTemperature));

            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return WorldPopulationData;
    }

    // method that gets the city, temperature, year, avgTemp, minTemp, maxTemp
    public class CityTemperatureEntry {
        public String country;
        public String city;
        public int year;
        public double averageTemperature;
        public double minTemperature;
        public double maxTemperature;

        public CityTemperatureEntry(String country, String city, int year, double averageTemperature,
                double minTemperature,
                double maxTemperature) {
            this.country = country;
            this.city = city;
            this.year = year;
            this.averageTemperature = averageTemperature;
            this.minTemperature = minTemperature;
            this.maxTemperature = maxTemperature;
        }

        public String getCountry() {
            return country;
        }

        public String getCity() {
            return city;
        }

        public int getYear() {
            return year;
        }

        public double getAverageTemperature() {
            return averageTemperature;
        }

        public double getMinTemperature() {
            return minTemperature;
        }

        public double getMaxTemperature() {
            return maxTemperature;
        }
    }

    public ArrayList<CityTemperatureEntry> getCityTemperature(String country, String city, int startYear,
            int endYear) {
        ArrayList<CityTemperatureEntry> CityTemperatureData = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the years and populations
            String query = "SELECT CountryName, CityName, Year, avgTemp, minTemp, maxTemp FROM CityTempObservation WHERE CountryName = '"
                    + country + "' AND CityName = '" + city + "' AND Year >= " + startYear + " AND Year <= " + endYear
                    + " GROUP BY CountryName, CityName, Year, avgTemp, minTemp, maxTemp;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String country1 = result.getString("CountryName");
                String city1 = result.getString("CityName");
                int year = result.getInt("Year");
                double averageTemperature = result.getDouble("avgTemp");
                double minTemperature = result.getDouble("minTemp");
                double maxTemperature = result.getDouble("maxTemp");

                CityTemperatureData.add(new CityTemperatureEntry(country1, city1, year, averageTemperature,
                        minTemperature, maxTemperature));

            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return CityTemperatureData;
    }

    public ArrayList<String> getCityNames() {
        ArrayList<String> CityNames = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the years and populations
            String query = "SELECT DISTINCT CityName FROM CityTempObservation;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String city = result.getString("CityName");

                CityNames.add(city);
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return CityNames;
    }

    public ArrayList<String> getStateNames() {
        ArrayList<String> StateNames = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the years and populations
            String query = "SELECT DISTINCT StateName FROM StateTempObservation;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String state = result.getString("StateName");
                StateNames.add(state);
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return StateNames;
    }

    // method that gets the country-state-city, temperature, year, avgTemp, minTemp,
    // maxTemp
    public class GeographicRegionTemperatureEntry {
        public String geographicRegion;
        public int year;
        public double avgTemperature;
        public double minTemperature;
        public double maxTemperature;
        public double AVGdifference;
        public double MINdifference;
        public double MAXdifference;

        public GeographicRegionTemperatureEntry(String geographicRegion, int year, double averageTemperature,
                double minTemperature,
                double maxTemperature, double AVGdifference, double MINdifference, double MAXdifference) {
            this.geographicRegion = geographicRegion;
            this.year = year;
            this.avgTemperature = averageTemperature;
            this.minTemperature = minTemperature;
            this.maxTemperature = maxTemperature;
            this.AVGdifference = AVGdifference;
            this.MINdifference = MINdifference;
            this.MAXdifference = MAXdifference;
        }

        public String getGeographicRegion() {
            return geographicRegion;
        }

        public int getYear() {
            return year;
        }

        public double getAverageTemperature() {
            return avgTemperature;
        }

        public double getMinTemperature() {
            return minTemperature;
        }

        public double getMaxTemperature() {
            return maxTemperature;
        }

        public double getAVGdifference() {
            return AVGdifference;
        }

        public double getMINdifference() {
            return MINdifference;
        }

        public double getMAXdifference() {
            return MAXdifference;
        }
    }

    public ArrayList<GeographicRegionTemperatureEntry> getGeographicRegionTemperatureEntry(String geographicRegion,
            String regionName,
            int startYear,
            int endYear) {
        ArrayList<GeographicRegionTemperatureEntry> GeographicRegionTemperatureData = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String queryTableName = "";
            System.out.println(geographicRegion);
            if (geographicRegion.equals("country")) {
                queryTableName = "CountryTempObservation";
            } else if (geographicRegion.equals("state")) {
                queryTableName = "StateTempObservation";
            } else if (geographicRegion.equals("city")) {
                queryTableName = "CityTempObservation";
            }
            System.out.println(queryTableName);

            String columnName = "";
            if (geographicRegion.equals("country")) {
                columnName = "CountryName";
            } else if (geographicRegion.equals("state")) {
                columnName = "StateName";
            } else if (geographicRegion.equals("city")) {
                columnName = "CityName";
            }
            System.out.println(columnName);

            // Get the years and populations
            String query = "SELECT g.Year, g." + columnName
                    + ", g.AvgTemp,g.MinTemp, g.MaxTemp, ROUND(ABS(g.AvgTemp - selected.AvgTemp), 3) AS AVGdifference, ROUND(ABS(g.MinTemp - selected.MinTemp), 3) AS MINdifference, ROUND(ABS(g.MaxTemp - selected.MaxTemp), 3) AS MAXdifference, (ABS(g.AvgTemp - selected.AvgTemp) + ABS(g.MinTemp - selected.MinTemp) + ABS(g.MaxTemp - selected.MaxTemp)) AS score FROM "
                    + queryTableName + " as g JOIN ( SELECT AvgTemp,MinTemp, MaxTemp FROM " + queryTableName
                    + " WHERE Year = " + startYear + " AND " + columnName + " LIKE '" + regionName
                    + "' ) as selected WHERE (AVGdifference >= -5 AND AVGdifference <= 5) AND (MINdifference >= -5 AND MINdifference <= 5) AND (MAXdifference >= -5 AND MAXdifference <= 5) AND (g.year >= "
                    + startYear + " AND g.year <= " + endYear + " )  ORDER BY score ASC LIMIT 5;";
            System.out.println(query);
            ResultSet result = statement.executeQuery(query);
            System.out.println(result);

            while (result.next()) {
                String name = "";
                if (geographicRegion.equals("country")) {
                    name = result.getString("CountryName");
                } else if (geographicRegion.equals("state")) {
                    name = result.getString("StateName");
                } else if (geographicRegion.equals("city")) {
                    name = result.getString("CityName");
                }
                System.out.println(name);
                String selectedGeographicRegion = name;
                int year = result.getInt("Year");
                double averageTemperature = result.getDouble("AvgTemp");
                double minTemperature = result.getDouble("MinTemp");
                double maxTemperature = result.getDouble("MaxTemp");
                double AVGdifference = result.getDouble("AVGdifference");
                double MINdifference = result.getDouble("MINdifference");
                double MAXdifference = result.getDouble("MAXdifference");

                GeographicRegionTemperatureData
                        .add(new GeographicRegionTemperatureEntry(selectedGeographicRegion, year, averageTemperature,
                                minTemperature, maxTemperature, AVGdifference, MINdifference, MAXdifference));

            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return GeographicRegionTemperatureData;
    }

    // make a similar getGeographicRegionTemperatureEntry() but for relative change
    public class GeographicRegionTemperatureRelativeChangeEntry {
        public String geographicRegion;
        public int year;
        public double avgTemperature;
        public double minTemperature;
        public double maxTemperature;
        public double AVGdifference;
        public double MINdifference;
        public double MAXdifference;

        public GeographicRegionTemperatureRelativeChangeEntry(String geographicRegion, int year,
                double averageTemperature,
                double minTemperature,
                double maxTemperature, double AVGdifference, double MINdifference, double MAXdifference) {
            this.geographicRegion = geographicRegion;
            this.year = year;
            this.avgTemperature = averageTemperature;
            this.minTemperature = minTemperature;
            this.maxTemperature = maxTemperature;
            this.AVGdifference = AVGdifference;
            this.MINdifference = MINdifference;
            this.MAXdifference = MAXdifference;
        }

        public String getGeographicRegion() {
            return geographicRegion;
        }

        public int getYear() {
            return year;
        }

        public double getAverageTemperature() {
            return avgTemperature;
        }

        public double getMinTemperature() {
            return minTemperature;
        }

        public double getMaxTemperature() {
            return maxTemperature;
        }

        public double getAVGdifference() {
            return AVGdifference;
        }

        public double getMINdifference() {
            return MINdifference;
        }

        public double getMAXdifference() {
            return MAXdifference;
        }
    }

    public ArrayList<GeographicRegionTemperatureRelativeChangeEntry> getGeographicRegionTemperatureRelativeChangeEntry(
            String geographicRegion, String regionName, int startYear, int endYear) {
        ArrayList<GeographicRegionTemperatureRelativeChangeEntry> GeographicRegionTemperatureRelativeData = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String queryTableName = "";
            System.out.println(geographicRegion);
            if (geographicRegion.equals("country")) {
                queryTableName = "CountryTempObservation";
            } else if (geographicRegion.equals("state")) {
                queryTableName = "StateTempObservation";
            } else if (geographicRegion.equals("city")) {
                queryTableName = "CityTempObservation";
            }
            System.out.println(queryTableName);

            String columnName = "";
            if (geographicRegion.equals("country")) {
                columnName = "CountryName";
            } else if (geographicRegion.equals("state")) {
                columnName = "StateName";
            } else if (geographicRegion.equals("city")) {
                columnName = "CityName";
            }
            System.out.println(columnName);

            // Get the years and populations
            String query = "SELECT g.Year, g." + columnName
                    + ", g.AvgTemp, g.MinTemp, g.MaxTemp, ROUND(ABS(CAST (g.AvgTemp AS REAL) - CAST (selected.avgTemp AS REAL) ) / CAST (selected.avgTemp AS REAL) * 100, 1) AS AVGdifference, ROUND(ABS(CAST (g.MinTemp AS REAL) - CAST (selected.minTemp AS REAL) ) / CAST (selected.minTemp AS REAL) * 100, 1) AS MINdifference, ROUND(ABS(CAST (g.MaxTemp AS REAL) - CAST (selected.maxTemp AS REAL) ) / CAST (selected.maxTemp AS REAL) * 100, 1) AS MAXdifference, (ROUND(ABS(CAST (g.AvgTemp AS REAL) - CAST (selected.avgTemp AS REAL) ) / CAST (selected.avgTemp AS REAL) * 100, 1) + ROUND(ABS(CAST (g.MinTemp AS REAL) - CAST (selected.minTemp AS REAL) ) / CAST (selected.minTemp AS REAL) * 100, 1) + ROUND(ABS(CAST (g.MaxTemp AS REAL) - CAST (selected.maxTemp AS REAL) ) / CAST (selected.maxTemp AS REAL) * 100, 1) ) AS score FROM "
                    + queryTableName + " AS g JOIN ( SELECT AvgTemp, MinTemp, MaxTemp FROM " + queryTableName
                    + " WHERE Year = " + startYear + " AND " + columnName + " LIKE '" + regionName
                    + "' ) as selected WHERE (AVGdifference >= -5 AND AVGdifference <= 5) AND (MINdifference >= -5 AND MINdifference <= 5) AND (MAXdifference >= -5 AND MAXdifference <= 5) AND (g.year >= "
                    + startYear + " AND g.year <= " + endYear + " )  ORDER BY score ASC LIMIT 5";
            System.out.println(query);
            ResultSet result = statement.executeQuery(query);
            System.out.println(result);

            while (result.next()) {
                String name = "";
                if (geographicRegion.equals("country")) {
                    name = result.getString("CountryName");
                } else if (geographicRegion.equals("state")) {
                    name = result.getString("StateName");
                } else if (geographicRegion.equals("city")) {
                    name = result.getString("CityName");
                }
                System.out.println(name);
                String selectedGeographicRegion = name;
                int year = result.getInt("Year");
                double averageTemperature = result.getDouble("AvgTemp");
                double minTemperature = result.getDouble("MinTemp");
                double maxTemperature = result.getDouble("MaxTemp");
                double AVGdifference = result.getDouble("AVGdifference");
                double MINdifference = result.getDouble("MINdifference");
                double MAXdifference = result.getDouble("MAXdifference");

                GeographicRegionTemperatureRelativeData
                        .add(new GeographicRegionTemperatureRelativeChangeEntry(selectedGeographicRegion, year,
                                averageTemperature,
                                minTemperature, maxTemperature, AVGdifference, MINdifference, MAXdifference));

            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return GeographicRegionTemperatureRelativeData;
    }

    public class CountryPopulationRelativeEntry {
        public int year;
        public String countryName;
        public long population;
        public double populationDifference;

        public CountryPopulationRelativeEntry(int year, String countryName, long population,
                double populationDifference) {
            this.year = year;
            this.countryName = countryName;
            this.population = population;
            this.populationDifference = populationDifference;
        }

        public int getYear() {
            return year;
        }

        public String getCountryName() {
            return countryName;
        }

        public long getPopulation() {
            return population;
        }

        public double getPopulationDifference() {
            return populationDifference;
        }

    }

    public ArrayList<CountryPopulationRelativeEntry> getCountryPopulationRelativeEntry(String country,
            int startYear,
            int endYear) {
        ArrayList<CountryPopulationRelativeEntry> CountryPopulationRelativeData = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the years and populations
            String query = "SELECT c.Year, c.CountryName, c.Population, ROUND(ABS(CAST (c.population AS REAL) - CAST (selected.Population AS REAL) ) / CAST (selected.Population AS REAL) * 100, 1) AS PopulationDifference FROM CountryTempObservation as c JOIN ( SELECT Population FROM CountryTempObservation WHERE Year = "
                    + startYear + " AND CountryName LIKE '" + country
                    + "' ) as selected WHERE (c.population != 0) AND (c.year >= " + startYear + " AND c.year <= "
                    + endYear + " )  ORDER BY PopulationDifference ASC LIMIT 5;";
            ResultSet result = statement.executeQuery(query);
            System.out.println(query);

            while (result.next()) {
                int year = result.getInt("Year");
                String country1 = result.getString("CountryName");
                long population = result.getLong("Population");
                double populationDifference = result.getDouble("PopulationDifference");

                CountryPopulationRelativeData
                        .add(new CountryPopulationRelativeEntry(year, country1, population, populationDifference));
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return CountryPopulationRelativeData;
    }

    public class CountryPopulationAbsoluteEntry {
        public int year;
        public String countryName;
        public long population;
        public long populationDifference;

        public CountryPopulationAbsoluteEntry(int year, String countryName, long population,
                long populationDifference) {
            this.year = year;
            this.countryName = countryName;
            this.population = population;
            this.populationDifference = populationDifference;
        }

        public int getYear() {
            return year;
        }

        public String getCountryName() {
            return countryName;
        }

        public long getPopulation() {
            return population;
        }

        public long getPopulationDifference() {
            return populationDifference;
        }

    }

    public ArrayList<CountryPopulationAbsoluteEntry> getCountryPopulationAbsoluteEntry(String country,
            int startYear,
            int endYear) {
        ArrayList<CountryPopulationAbsoluteEntry> CountryPopulationAbsoluteData = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DATABASE);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // Get the years and populations
            String query = "SELECT c.Year, c.CountryName, c.Population, ROUND(ABS(c.population - selected.Population), 3) AS PopulationDifference FROM CountryTempObservation as c JOIN ( SELECT Population FROM CountryTempObservation WHERE Year = "
                    + startYear + " AND CountryName LIKE '" + country
                    + "' ) as selected WHERE (c.population != 0) AND (c.year >= " + startYear + " AND c.year <= "
                    + endYear + " )  ORDER BY PopulationDifference ASC LIMIT 5;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                int year = result.getInt("Year");
                String country1 = result.getString("CountryName");
                long population = result.getLong("Population");
                long populationDifference = result.getLong("PopulationDifference");

                CountryPopulationAbsoluteData
                        .add(new CountryPopulationAbsoluteEntry(year, country1, population, populationDifference));
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return CountryPopulationAbsoluteData;
    }

}
