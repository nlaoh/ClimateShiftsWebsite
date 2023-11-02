package helper;

import java.io.File;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.nio.charset.StandardCharsets;

/**
 * Stand-alone Java file for processing the database CSV files.
 * <p>
 * You can run this file using the "Run" or "Debug" options
 * from within VSCode. This won't conflict with the web server.
 * <p>
 * This program opens a CSV file from the Climate Change Awareness data set
 * and uses JDBC to load up data into the database.
 * <p>
 * To use this program you will need to change:
 * 1. The input file location
 * 2. The output file location
 * <p>
 * This assumes that the CSV files are the the **database** folder.
 * <p>
 * WARNING: This code may take quite a while to run as there will be a lot
 * of SQL insert statements!
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au

 */
public class ClimateProcessCSV {

   /*MODIFY these to load/store to/from the correct locations
   🚨🚨🚨CHECK THIS BEFORE RUNNING🚨🚨🚨
   */

   private static final String DATABASE = "jdbc:sqlite:database/climate.db";
   private static final String CSV_FILE = "database/GlobalYearlyLandTempByCity.csv";
   /*
    *🚨🚨
    */

   public static void main (String[] args) {
      // Load up the Date table
      // This only needs to be done once - uncomment this to reload the Date table
      // loadYears();

      // Load the Country Temperature Observations
      // loadCountryTempObservation();

      // Load the Global Temperature Observations
      // loadGlobalTempObservation();

      // Insert personas
      // loadPersonas();

      // Load City Temperature Observations
      loadCityTempObservation();

      // Load State Temperature Observations
      // loadStateTempObservation();
   }

   
   public static void loadYears() {
      // JDBC Database Object
      Connection connection = null;

      // Like JDBCConnection, we need some error handling.
      try {
         connection = DriverManager.getConnection(DATABASE);

         for (int i = 1750; i != 2024; ++i) {
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();

            // Create Insert Statement
            String query = "INSERT into Date VALUES ("
                           + i
                           + ")";

            // Execute the INSERT
            System.out.println("Executing: " + query);
            statement.execute(query);
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private static void loadCountryTempObservation() {
      // JDBC Database Object
      Connection connection = null;

      // We need some error handling.
      try {
         // Open A CSV File to process, one line at a time
         // CHANGE THIS to process a different file
         Scanner lineScanner = new Scanner(new File(CSV_FILE));

         // Read the first line of "headings"
         String header = lineScanner.nextLine();
         System.out.println("Heading row" + header + "\n");

         // Setup JDBC
         // Connect to JDBC database
         connection = DriverManager.getConnection(DATABASE);

         // Read each line of the CSV
         int row = 1;
         while (lineScanner.hasNext()) {
            // Always get scan by line
            String line = lineScanner.nextLine();
            
            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner = new Scanner(line);
            rowScanner.useDelimiter(",");

            // Get all of the columns in order
            String year = rowScanner.next();
            String avgTemp = rowScanner.next();
            String minTemp = rowScanner.next();
            String maxTemp = rowScanner.next();
            String rawCountryName = rowScanner.next();

            // Previously left as 0, now grabs population from mergeddata.csv
            String population = rowScanner.next();
            
            // Set a default country code
            String countryCode = "ZZZZ";

            // Some error handling
            if (avgTemp.equals("")) {
               avgTemp = "0";
            }
            if (minTemp.equals("")) {
               minTemp = "0";
            }
            if (maxTemp.equals("")) {
               maxTemp = "0";
            }

            // Convert any Latin1 encoded country names to UTF-8
            String countryName = new String(rawCountryName.getBytes("ISO-8859-1"), "UTF-8");
            
            // We now need to look-up the country code from the name
            Statement statement = connection.createStatement();
            String query = "SELECT * from Country WHERE CountryName = \"" + countryName + "\"";
            System.out.println("Looking up: " + query);
            ResultSet results = statement.executeQuery(query);
            if(results.next()) {
               countryCode = results.getString("CountryCode");
            }

            // Now we can insert the entry into the CountryTempObservation table
            // Prepare a new SQL Query & Set a timeout
            statement = connection.createStatement();

            // Create Insert Statement
            query = "INSERT into CountryTempObservation VALUES ("
                     + year + ","
                     + "\"" + countryCode + "\"" + ","
                     + "\"" + countryName + "\"" + ","
                     + avgTemp + ","
                     + minTemp + ","
                     + maxTemp + ","
                     + population
                     + ")";

            // Execute the INSERT
            System.out.println("Executing: " + query);
            statement.execute(query);
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private static void loadGlobalTempObservation() {
      // JDBC Database Object
      Connection connection = null;

      // Like JDBCConnection, we need some error handling.
      try {
         // Setup JDBC
         // Connect to JDBC database
         connection = DriverManager.getConnection(DATABASE);

         // Open A CSV File to process, one line at a time
         // CHANGE THIS to process a different file
         Scanner lineScanner = new Scanner(new File(CSV_FILE));

         // Read the first line of "headings"
         String header = lineScanner.nextLine();
         System.out.println("Heading row" + header + "\n");
         
         // Read each line of the CSV
         int row = 1;
         while (lineScanner.hasNext()) {
            // Always get scan by line
            String line = lineScanner.nextLine();

            // Create a new scanner for this line to delimit by commas (,)
            Scanner rowScanner = new Scanner(line);
            rowScanner.useDelimiter(",");

            // Get all of the columns in order
            
            // Year and temperature parsing
            String Year = rowScanner.next();
            System.out.println(Year);
            String AverageTemperature = rowScanner.next();
            // System.out.println(AverageTemperature); -------- Print messages not needed?
            String MinimumTemperature = rowScanner.next();
            // System.out.println(MinimumTemperature);
            String MaximumTemperature = rowScanner.next();
            // System.out.println(MaximumTemperature);
            
            // LandOceanTemperature rows parsing
            String LandOceanAverageTemperature = "";
            String LandOceanMinimumTemperature = "";
            String LandOceanMaximumTemperature = "";

            if (rowScanner.hasNext()) {
               LandOceanAverageTemperature = rowScanner.next();
            }
            if (rowScanner.hasNext()) {
               LandOceanMinimumTemperature = rowScanner.next();
            }
            if (rowScanner.hasNext()) {
               LandOceanMaximumTemperature = rowScanner.next();
            }
            
            // Temperature rows parsing?
            if (AverageTemperature.equals("")) {
               AverageTemperature = "0";
            }
            if (MinimumTemperature.equals("")) {
               MinimumTemperature = "0";
            }
            if (MaximumTemperature.equals("")) {
               MaximumTemperature = "0";
            }
            if (LandOceanAverageTemperature.equals("")) {
               LandOceanAverageTemperature = "0";
            }
            if (LandOceanMinimumTemperature.equals("")) {
               LandOceanMinimumTemperature = "0";
            }
            if (LandOceanMaximumTemperature.equals("")) {
               LandOceanMaximumTemperature = "0";
            }

            // Now we can insert the entry into the CountryTempObservation table
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            String query = ""; 

            // Create Insert Statement
            query = "INSERT into GlobalTempObservation (Year,AvgTemp,MinTemp,MaxTemp,LandOceanAvgTemp,LandOceanMinTemp,LandOceanMaxTemp) VALUES ("
                  + Year + ","
                  + AverageTemperature + ","
                  + MinimumTemperature + ","
                  + MaximumTemperature + ","
                  + LandOceanAverageTemperature + ","
                  + LandOceanMinimumTemperature + ","
                  + LandOceanMaximumTemperature + ")";


            // Execute the INSERT
            System.out.println("Executing: " + query);
            statement.execute(query);
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private static void insertPersona(String Persona, String Attribute, String Description) {
      // JDBC Database Object
      Connection connection = null;
      
      // Like JDBCConnection, we need some error handling.
      try {
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            
            // Create Insert Statement
            String query = "INSERT INTO PersonaAttribute (PersonaName, AttributeType, Description) VALUES (\"" + Persona + "\", " + "\"" + Attribute + "\"," + "\"" + Description + "\");";
            
            // Execute the INSERT
            System.out.println("Executing: " + query);
            statement.execute(query);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      private static void loadPersonas() {
         String P1 = "Linus Sebastian", P2 = "Elliot Wellick", P3 = "Academic Andy";
         final String A1 = "Description", A2 = "Need", A3 = "Goal", A4 = "Skills & Experience";
         //Persona 1 attributes
         insertPersona(P1, A1, "Linus, a 27-year-old environmental activist, is dedicated to reducing climate change. He holds a degree in environmental science and has been employed by a non-profit that conducts research into climate change.");
         insertPersona(P1, A2, "To find trends and patterns in temperature data.");
         insertPersona(P1, A2, "Linus needs a simple tool that he can use to rapidly and properly assess the data.");
         insertPersona(P1, A2, "He is interested in using graphs and visuals to assist in spreading awareness of the importance of combating climate change while he works as an environmental activist.");
         insertPersona(P1, A3, "Linus's goal is to use his knowledge and abilities to improve the environment.");
         insertPersona(P1, A3, "He wants to take part in a movement that promotes action to combat climate change and increases public awareness of it.");
         insertPersona(P1, A3, "He needs a simple tool that will enable him to precisely and quickly assess temperature data.");
         insertPersona(P1, A4, "Linus has a degree in Environmental Science and some experience working in climate change research.");
         insertPersona(P1, A4, "He is comfortable working with data and has some experience with data analysis tools.");
         insertPersona(P1, A4, "However, he is not an expert in this area and would benefit from a tool that is easy to use and requires minimal technical expertise.");
         //Persona 2 attributes
         insertPersona(P2, A1, "Elliot Wellick is a 48-year-old high school science teacher with a strong interest in climate change. He has been teaching for over a decade and is always looking for new ways to engage his students in learning about science and the environment using graphs.");
         insertPersona(P2, A2, "Access to up-to-date and accurate information on climate change and its impacts on the environment.");
         insertPersona(P2, A2, "Clear and concise educational resources that can be easily integrated into his lesson plans.");
         insertPersona(P2, A2, "Tools and visual aids that can help him explain concepts in a way that is easy for his students to understand like graphs.");
         insertPersona(P2, A3, "To educate his students about the science of climate change and its potential impacts on their lives.");
         insertPersona(P2, A3, "To find new and innovative ways to teach his students about climate change.");
         insertPersona(P2, A4, "He is a qualified teacher with tertiary education.");
         insertPersona(P2, A4, "Familiar with a range of educational technology tools and comfortable incorporating them into lesson plans.");
         //Persona 3 attributes
         insertPersona(P3, A1, "Andy is a young adult higher-education student that has a deep interest in self-improvement and learning new things.");
         insertPersona(P3, A2, "Accurate information.");
         insertPersona(P3, A2, "A good level of detail in data.");
         insertPersona(P3, A2, "References to avoid plagiarism and verify the credibility.");
         insertPersona(P3, A3, "To read meaningful and useful information.");
         insertPersona(P3, A3, "To see clear diagrams/data visualisations.");
         insertPersona(P3, A4, "Good reading comprehension.");
         insertPersona(P3, A4, "Experienced with technology, uses an LMS, eCommerce sites and various social media sites");
      }
      private static void loadCityTempObservation() {
         // JDBC Database Object
         Connection connection = null;
   
         // We need some error handling.
         try {
            // Open A CSV File to process, one line at a time
            // CHANGE THIS to process a different file
            Scanner lineScanner = new Scanner(new File(CSV_FILE), StandardCharsets.UTF_8);
   
            // Read the first line of "headings"
            String header = lineScanner.nextLine();
            System.out.println("Heading row" + header + "\n");
   
            // Setup JDBC
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);
   
            // Read each line of the CSV
            while (lineScanner.hasNext()) {
               // Always get scan by line
               String line = lineScanner.nextLine();
               
               // Create a new scanner for this line to delimit by commas (,)
               Scanner rowScanner = new Scanner(line);
               rowScanner.useDelimiter(",");
   
               // Get all of the columns in order
               String year = rowScanner.next();
               String avgTemp = rowScanner.next();
               String minTemp = rowScanner.next();
               String maxTemp = rowScanner.next();
               String rawCityName = rowScanner.next();
               //Raw, because countryname is refined later
               String rawCountryName = rowScanner.next();
               String latitude = rowScanner.next();
               String longitude = rowScanner.next();
               
   
               // Some error handling
               if (avgTemp.equals("")) {
                  avgTemp = "0";
               }
               if (minTemp.equals("")) {
                  minTemp = "0";
               }
               if (maxTemp.equals("")) {
                  maxTemp = "0";
               }
   
               // Convert any Latin1 encoded country names to UTF-8
               String countryName = new String(rawCountryName.getBytes("ISO-8859-1"), "UTF-8");
               String cityName = new String(rawCityName.getBytes("ISO-8859-1"), "UTF-8");
   
   
               // Now we can insert the entry into the CityTempObservation table
               // Prepare a new SQL Query & Set a timeout
               Statement statement = connection.createStatement();
   
               // Create Insert Statement
               String query = "INSERT into CityTempObservation VALUES ("
                        + year + ","
                        + "\"" + countryName + "\"" + ","
                        + "\"" + cityName + "\"" + ","
                        + "\"" + latitude + "\"" + ","
                        + "\"" + longitude + "\"" + ","
                        + avgTemp + ","
                        + minTemp + ","
                        + maxTemp
                        + ")";
   
               // Execute the INSERT
               System.out.println("Executing: " + query);
               statement.execute(query);
            }
   
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      private static void loadStateTempObservation() {
         // JDBC Database Object
         Connection connection = null;
   
         // We need some error handling.
         try {
            // Open A CSV File to process, one line at a time
            // CHANGE THIS to process a different file
            Scanner lineScanner = new Scanner(new File(CSV_FILE));
   
            // Read the first line of "headings"
            String header = lineScanner.nextLine();
            System.out.println("Heading row" + header + "\n");
   
            // Setup JDBC
            // Connect to JDBC database
            connection = DriverManager.getConnection(DATABASE);
   
            // Read each line of the CSV
            while (lineScanner.hasNext()) {
               // Always get scan by line
               String line = lineScanner.nextLine();
               
               // Create a new scanner for this line to delimit by commas (,)
               Scanner rowScanner = new Scanner(line);
               rowScanner.useDelimiter(",");
   
               // Get all of the columns in order
               String year = rowScanner.next();
               String avgTemp = rowScanner.next();
               String minTemp = rowScanner.next();
               String maxTemp = rowScanner.next();
               String rawStateName = rowScanner.next();
               String rawCountryName =rowScanner.next();
   
               // Some error handling
               if (avgTemp.equals("")) {
                  avgTemp = "0";
               }
               if (minTemp.equals("")) {
                  minTemp = "0";
               }
               if (maxTemp.equals("")) {
                  maxTemp = "0";
               }
   
               // Convert any Latin1 encoded country names to UTF-8
               String countryName = new String(rawCountryName.getBytes("ISO-8859-1"), "UTF-8");
               String stateName = new String(rawStateName.getBytes("ISO-8859-1"), "UTF-8");

               
               // Create Insert Statement
               Statement statement = connection.createStatement();
               String query = "INSERT into StateTempObservation VALUES ("
                        + year + ","
                        + "\"" + countryName + "\"" + ","
                        + "\"" + stateName + "\"" + ","
                        + avgTemp + ","
                        + minTemp + ","
                        + maxTemp
                        + ")";
   
               // Execute the INSERT
               System.out.println("Executing: " + query);
               statement.execute(query);
            }
   
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }
   