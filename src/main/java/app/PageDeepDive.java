package app;

import java.text.DecimalFormat;
import java.util.ArrayList;
import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageDeepDive implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/DeepDive.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        JDBCConnection jdbc = new JDBCConnection();
        //data manipulation inputs
        String resetTableInput = context.formParam("resetTable");
        String sortInput = context.formParam("sort");
        //user input data
        String regionInput = context.formParam("region");
        String countryInput = context.formParam("countryDropdown");
        String stateInput = context.formParam("stateDropdown");
        String cityInput = context.formParam("cityDropdown");
        String yearInput = context.formParam("yearDropdown");
        String timePeriodInput = context.formParam("timePeriodDropdown");
        
        String location = "";
            try {
                if (regionInput.equals("global")) {
                    location = "The World";
                }
                else if (regionInput.equals("countries")) {
                    location = countryInput;
                }
                else if (regionInput.equals("states")) {
                    location = stateInput;
                }
                else if (regionInput.equals("cities")) {
                    location = cityInput;
                }
            } catch (Exception e) {
            }
        
        System.out.println();
        System.out.println("New Page Loaded");
        System.out.println("Containing input: " + regionInput + countryInput + stateInput + cityInput + yearInput + timePeriodInput + resetTableInput + sortInput);
        
        String html = "";
        html += "<html>";
        // Metadata
        html += "   <head>" +
                "       <title>Subtask 2.2</title>";
        html += "       <link rel='stylesheet' type='text/css' href='pagedeepdive.css' />";
        html += "       <link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Tsukimi Rounded'>";
        html += "       <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>";
        // javascript methods
        html += """
                        <script>
                            function showGlobal() {
                                document.getElementById('country').style.display='none';
                                document.getElementById('state').style.display='none';
                                document.getElementById('city').style.display='none';
                            }
                            function showCountries() {
                                document.getElementById('country').style.display='block';
                                document.getElementById('state').style.display='none';
                                document.getElementById('city').style.display='none';
                            }
                            function showStates() {
                                document.getElementById('country').style.display='none';
                                document.getElementById('state').style.display='block';
                                document.getElementById('city').style.display='none';
                            }
                            function showCities() {
                                document.getElementById('country').style.display='none';
                                document.getElementById('state').style.display='none';
                                document.getElementById('city').style.display='block';
                            }

                            function formDisplay() {
                                let region = "%s";
                                let country = "%s";
                                let state = "%s";
                                let city = "%s";
                                let year = "%s";
                                let timePeriod = "%s";

                                //if timeperiod and year has been input, then reset query input
                                //   THIS NEEDS TO SAVE PREVIOUS ENTRIES
                                if (timePeriod !== "null" && year !== "null") {
                                    document.getElementById('next').style.display='none';
                                    document.getElementById('finalMsg').style.display='block';
                                    document.getElementById('reset').style.margin='auto';
                                    document.getElementById('reset').innerHTML ='Add new data'; 
                                    
                                }
                                //if timeperiod has input, then close timeperiod div and produce data
                                else if (timePeriod !== "null") {
                                    document.getElementById('timePeriod').style.display="none";
                                    document.getElementById('submit').style.display="none";
                                    document.getElementById('timePeriodDropdown').value=timePeriod;
                                    document.getElementById('yearDropdown').value=year;
                                    document.getElementById('countryDropdown').value=country;
                                    document.getElementById('stateDropdown').value=state;
                                    document.getElementById('cityDropdown').value=city;
                                }
                                //if year has input, then close year div and open timeperiod div
                                else if (year !== "null") {
                                    document.getElementById('year').style.display="none";
                                    document.getElementById('geography').style.display="none";
                                    document.getElementById('timePeriod').style.display="block";
                                    document.getElementById('timePeriodMsg').style.display="block";
                                    document.getElementById('yearDropdown').value=year;
                                    document.getElementById('countryDropdown').value=country;
                                    document.getElementById('stateDropdown').value=state;
                                    document.getElementById('cityDropdown').value=city;
                                    document.getElementById('next').style.display="none";
                                    document.getElementById('submit').style.display="block";
                                }
                                //if geographic region has input, close geographic region and open year div
                                else if (country !== "null" && (year == "null" || timePeriod == "null")) {
                                    document.getElementById('year').style.display="block";
                                    document.getElementById('yearMsg').style.display="block";
                                    document.getElementById('geography').style.display="none";
                                    document.getElementById('countryDropdown').value=country;
                                }
                                else if (state !== "null" && (year == "null" || timePeriod == "null")) {
                                    document.getElementById('year').style.display="block";
                                    document.getElementById('yearMsg').style.display="block";
                                    document.getElementById('geography').style.display="none";
                                    document.getElementById('stateDropdown').value=state;
                                }
                                else if (city !== "null" && (year == "null" || timePeriod == "null")) {
                                    document.getElementById('year').style.display="block";
                                    document.getElementById('yearMsg').style.display="block";
                                    document.getElementById('geography').style.display="none";
                                    document.getElementById('cityDropdown').value=city;
                                }
                                else if (region.localeCompare("global") == 0 && (year == "null" || timePeriod == "null")) {
                                    document.getElementById('year').style.display="block";
                                    document.getElementById('yearMsg').style.display="block";
                                    document.getElementById('geography').style.display="none";
                                    document.getElementById('countryDropdown').value='';
                                    document.getElementById('stateDropdown').value='';
                                    document.getElementById('cityDropdown').value='';
                                }
                                
                                else {
                                    document.getElementById('geography').style.display="block";
                                    document.getElementById('geographyMsg').style.display="block";

                                }
                                if (region.localeCompare("global") == 0) {
                                    document.getElementById('global').checked=true;
                                }
                                else if (region.localeCompare("countries") == 0) {
                                    document.getElementById('countries').checked=true;
                                }
                                else if (region.localeCompare("states") == 0) {
                                    document.getElementById('states').checked=true;
                                }
                                if (region.localeCompare("cities") == 0) {
                                    document.getElementById('cities').checked=true;
                                }
                                
                            }    
                            //Changes all current input values to null
                            function resetDisplay() {
                                document.getElementById('countryDropdown').value='';
                                document.getElementById('stateDropdown').value='';
                                document.getElementById('cityDropdown').value='';
                                document.getElementById('yearDropdown').value='';
                                //return null from radio buttons
                                for (let currentRegion of document.getElementsByName('region')) {
                                    currentRegion.checked=false;
                                }
                            }
                            function resetTable() {
                                document.getElementById('resetTable').checked=true;
                            }
                            function sortByOrder() {
                                document.getElementById('sortByOrder').checked=true;
                            }
                            function sortByTemperatureChange() {
                                document.getElementById('sortByTemperatureChange').checked=true;
                            }

                            window.onload = function () {
                                formDisplay();
                                alert(region + country + state + city + year + timePeriod);
                            }
                        </script>
                """.formatted(regionInput, countryInput, stateInput, cityInput, yearInput, timePeriodInput);
        html += "   </head>";

        // Body
        html += "   <body>";

        // Website name/logo
        html += "       <div class='name'>";
        html += "           <h1><a style='' href='/'>Climate Shifts</a></h1>";
        html += "       </div>";

        // Top Nav
        html = html
                + """
                        <div class='topnav'>
                            <a href='/'>Homepage</a>
                            <a href='mission.html'>Our Mission</a>
                            <div class='dropdown'>
                                <a class='currentPage'>View Changes By <i class='fa fa-solid fa-angle-down'></i></a>
                                <div class='dropdown-content'>
                                    <a href='ShallowGlanceC.html'>Temperature and population change by Country/World</a>
                                    <a href='ShallowGlanceT2.html'>Temperature change by City/State</a>
                                    <a class='selected' href='DeepDive.html'>Temperature over extended periods</a>
                                    <a href='DeepDive2.html'>Time periods with similar temperature and/or population</a>
                                </div>
                            </div>
                        </div>
                        """;

        // Content
        html += "<div class='content'>";

        // Header div
        html += """
                        <div id='header'>
                            <h1>Deep Dive</h1>
                            <h2>Changes in temperature over extended periods</h2>
                        </div>
                """;
        // Grid layout
        html += "       <div class='grid'>";
        // Item 1 (left column)
        html += "           <div class='item1'>";
        html += """
                                <h2 id='geographyMsg' style='display:none;'>Select a geographic region</h2>
                                <h2 id='yearMsg' style='display:none;'>Select a year to start from</h2>
                                <h2 id='timePeriodMsg' style='display:none;'>Select the time period for data (in years)</h2>
                                <h2 id='finalMsg' style='display:none;'>Click below to add more data!</h2>
                """;
        // Form input
        html += """
                                <div class='box'>
                                    <form class='form' action="/DeepDive.html" method='post'>
                                        <div id="geography" style="display:none;">
                                            <h4>Select a geographic region</h4>
                                            <div class='row'>
                                                <input type="radio" name="region" id="global" value='global'>
                                                <label for="global" onclick="showGlobal()">Global</label>
                                                <input type="radio" name="region" id="countries" value="countries">
                                                <label for="countries" onclick="showCountries()">Countries</label>
                                                <input type="radio" name="region" id="states" value="states">
                                                <label for="states" onclick="showStates()">States</label>
                                                <input type="radio" name="region" id="cities" value="cities">
                                                <label for="cities" onclick="showCities()">Cities</label>
                                            </div>
                                            <div id='country' style='display:none;'>
                                                <h4>Select a country</h4>
                                                <select class='dropdown' id='countryDropdown' name='countryDropdown'>
                                                    <option value='' disabled selected>Select your option</option>
                    """;
            html = addTotalCountries(html);
            html += """
                                                </select>
                                            </div>
                                            <div id='state' style='display:none;'>
                                                <h4>Select a state</h4>
                                                <select class='dropdown' id='stateDropdown' name='stateDropdown'>
                                                    <option value='' disabled selected>Select your option</option>
                    """;
            html = addTotalStates(html);
            html += """
                                                </select>
                                            </div>
                                            <div id='city' style='display:none;'>
                                                <h4>Select a city</h4>
                                                <select class='dropdown' id='cityDropdown' name='cityDropdown'>
                                                    <option value='' disabled selected>Select your option</option>
                    """;
            html = addTotalCities(html);
            html += """
                                                </select>
                                            </div>
                                        </div>
                                        <div id='year' style='display:none;'>
                                            <h3>Location chosen: %s</h3>
                                            <h4>Select the starting year</h4>
                                            <select class='dropdown' id='yearDropdown' name='yearDropdown'>
                                                <option value='' disabled selected>Select your option</option>
                    """.formatted(location);
        html = addTotalYears(html, countryInput, stateInput, cityInput);
        html += """
                                            </select>
                                        </div>
                                        <div id='timePeriod' style='display:none;'>
                                            <h3>Location chosen: %s</h3>
                                            <h3>Year chosen: %s</h3>
                                            <h4>Select time period (in years)</h4>
                                            <select class='dropdown' id='timePeriodDropdown' name='timePeriodDropdown'>
                                                <option value='' disabled selected>Select your option</option>
                """.formatted(location, yearInput);
        html = addTimePeriod(html, yearInput, countryInput, stateInput, cityInput);
        html += """
                                            </select>
                                        </div>
                                        <button id='next' class='submit' formaction='/DeepDive.html'>Next</button>
                                        <button id='submit' class='submit' style='display:none;' formaction='/DeepDive.html'>Submit</button>
                                        <button id='reset' class='reset' onclick='resetDisplay()'>Reset</button>
                                    </form>
                                </div>
                """;
        html += "           </div>";
        // Item 2 (right column)
        html += "           <div class='item2'>";
        html += """               

                """;
        html += "                   <h1>Your data entries</h1>";
                                    //Data table
        html += """
                                    <table style='border:2px solid;'>
                                        <tr>
                                            <td>
                                                <table class='header'>
                                                    <tr>
                """;
        html += "                                       <th>Category</th>";
        html += "                                       <th>Region name</th>";
        html += "                                       <th>Starting year</th>";
        html += "                                       <th>Time period (in years)</th>";
        html += "                                       <th>Average temperature</th>";
        html += "                                       <th>Change in average temperature</th>";
        html += """
                                                    <tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div class='data'>
                                                    <table class='data'>
                """;
        //insert userinput into database when user completes a query
        if (timePeriodInput != null && yearInput != null) {
            jdbc.insertUserInput(regionInput, countryInput, stateInput, cityInput, yearInput, timePeriodInput);
        }
        //deletes userInput in database if resettable is pressed
        try {
            if (resetTableInput.equals("reset")) {
                jdbc.deleteUserInput();
            }
        } catch (Exception e) {
            
        }
        //Get previous userinputs from database

        ArrayList<UserInput> userInputs = getUserInputs(jdbc, sortInput);
        for (UserInput userInput : userInputs) {
            html = addDataRow(html, userInput.getCategory(), userInput.getLocation(), userInput.getCountryInput(), userInput.getStateInput(), userInput.getCityInput(), userInput.getYearInput(), userInput.getTimePeriodInput());
        }

        html += """
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                    <div class='box' style="margin-top: 2%;">
                                        <form class='form' action="/DeepDive.html" method='post'>
                                            <input type="radio" id="resetTable" name="resetTable" value="reset" checked> 
                                            <input type="radio" id="sortByOrder" name="sort" value="original"> 
                                            <input type="radio" id="sortByTemperatureChange" name="sort" value="temperature"> 
                                            <input type="radio" id="tempRange" name="tempRange"> 
                                            <div class='row'>
                                                <button class='submit' onclick='sortByOrder()' onclick='resetDisplay()'>Original order</button>
                                                <button class='submit' onclick='sortByTemperatureChange()' onclick='resetDisplay()'>Sort by change in average temperature</button>
                                                <button class='reset' onclick='resetTable()' onclick='resetDisplay()'>Reset your entries</button>
                                            </div>
                                        </form>
                                    </div>
                """;
                                                // <button class='submit' onclick='resetTable()' onclick='resetDisplay()'>Filter by temperature change</button>
                                                // <button class='submit' onclick='resetTable()' onclick='resetDisplay()'>Filter countries by population</button>
        html += "           </div>";
        html += "       </div>";
        html += "</div>";

        // Footer
        html += "<div class='footer'>";
        html += "   <p>&#169 2023 Climate Shifts. All rights reserved.</p>";
        html += "</div>";

        // Finish the HTML webpage
        html += "</body>" + "</html>";

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    private static String addTotalCountries(String html) {
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<String> countries = jdbc.getTotalCountries();
        for (String country : countries) {
            html += "<option value='" + country + "'>" + country + "</option>";
        }
        return html;
    }
    private static String addTotalStates(String html) {
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<String> states = jdbc.getTotalStates();
        for (String state : states) {
            html += "<option value='" + state + "'>" + state + "</option>";
        }
        return html;
    }
    private static String addTotalCities(String html) {
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<String> cities = jdbc.getTotalCities();
        for (String city : cities) {
            html += "<option value='" + city + "'>" + city + "</option>";
        }
        return html;
    }

    private static String addTotalYears(String html, String country, String state, String city) {
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<Integer> years = jdbc.getTotalYears(country, state, city);
        try {   
            years.get(0);
            for (Integer year : years) {
                html += "<option value='" + year + "'>" + year + "</option>";
            }
        } catch (Exception e) {
            System.out.println("Error occurred at method addTotalYears(), PageDeepDive.java");
        }
        return html;
    }
    public static int linearSearch(ArrayList<Integer> arrayList, Integer key) { //Key should be the same data type as the element																																				// being searched for in the array.
        int i;   
        for (i = 0; i < arrayList.size(); ++i) {
            System.out.println("Searching: " + i + "th element");
            if (arrayList.get(i).equals(key)) {
                return i;
            }
        }
        return -1; /* not found */
    }
   
    private static String addTimePeriod(String html, String yearInput, String country, String state, String city) {
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<Integer> years = jdbc.getTotalYears(country, state, city);
        try {   
            years.get(0);
            Integer startYear = Integer.parseInt(yearInput);
            for (int i = linearSearch(years, Integer.parseInt(yearInput)) + 1; i < years.size(); i++) {
                html += "<option value='" + (years.get(i).intValue() - startYear.intValue()) + "'>" + (years.get(i).intValue() - startYear.intValue()) + "</option>";
            }
        } catch (Exception e) {
            System.out.println("Error occurred at method addTimePeriod(), PageDeepDive.java");
        }
        return html;
    }

    private static String addDataRow(String html, String category, String location, String countryInput, String stateInput, String cityInput, String yearInput, String timePeriodInput) {
        JDBCConnection jdbc = new JDBCConnection();
        DecimalFormat roundedFormat = new DecimalFormat("0.00");
        ArrayList<Double> data = jdbc.getAvgTemp(countryInput, stateInput, cityInput, yearInput, timePeriodInput);
        Double avgTemp = 0.0;
        Double changeAvgTemp = 0.0;
        Double totalAvgTemp = 0.0;
        String changeAvgTempString = "";
        int yearInt;
        int timePeriodInt;
        int i = 0;
        //Find average of dataset
        for (Double currentAvgTemp : data) {
            i++;
            totalAvgTemp += currentAvgTemp;
        }
        avgTemp = totalAvgTemp / i;
        try {
                yearInt = Integer.parseInt(yearInput);
                timePeriodInt = Integer.parseInt(timePeriodInput);
            } catch (Exception e) {
                yearInt = 0;
                timePeriodInt = 0;
            }
        try {
            //This is gradient (rate of change) formula
            changeAvgTemp = (data.get(data.size() - 1) - data.get(0)) / ((yearInt + timePeriodInt) - yearInt);
        } catch (Exception e) {

        }
        //Positive and negative prefix for average temp change values
        if (changeAvgTemp > 0) {
            changeAvgTempString = "<td>+" + roundedFormat.format(changeAvgTemp) + "&#176;C</td>";
        }
        else if (changeAvgTemp < 0) {
            changeAvgTempString = "<td>" + roundedFormat.format(changeAvgTemp) + "&#176;C</td>";
        }
        else {
            changeAvgTempString = "<td>" + roundedFormat.format(changeAvgTemp) + "&#176;C</td>";
        }
        //html string
        html += "<tr>";
        html += "<td>" + category + "</td>";
        html += "<td>" + location + "</td>";
        html += "<td>" + yearInput + "</td>";
        html += "<td>" + timePeriodInput + "</td>";
        html += "<td>" + roundedFormat.format(avgTemp) + "&#176;C</td>";
        html += changeAvgTempString;
        html += "</tr>";
        return html;
    }
    //class for storing userinput
    private static class UserInput {
        private String regionInput, countryInput, stateInput, cityInput, yearInput, timePeriodInput; 

        private UserInput(String regionInput, String countryInput, String stateInput, String cityInput, String yearInput, String timePeriodInput) {
            this.regionInput = regionInput;
            this.countryInput = countryInput;
            this.stateInput = stateInput;
            this.cityInput = cityInput;
            this.yearInput = yearInput;
            this.timePeriodInput = timePeriodInput;
        }

        //getter methods
        private String getCategory() {
            String category = "";
            try {
                if (regionInput.equals("global")) {
                    category = "Global";
                }
                else if (regionInput.equals("countries")) {
                    category = "Country";
                }
                else if (regionInput.equals("states")) {
                    category = "State";
                }
                else if (regionInput.equals("cities")) {
                    category = "City";
                }
            } catch (Exception e) {
            }
            return category;
        }
        private String getLocation() {
            String location = "";
            try {
                if (regionInput.equals("global")) {
                    location = "The World";
                }
                else if (regionInput.equals("countries")) {
                    location = countryInput;
                }
                else if (regionInput.equals("states")) {
                    location = stateInput;
                }
                else if (regionInput.equals("cities")) {
                    location = cityInput;
                }
            } catch (Exception e) {
            }
            return location;
        }
        private String getRegionInput() {
            return regionInput;
        }
        private String getCountryInput() {
            return countryInput;
        }
        private String getStateInput() {
            return stateInput;
        }
        private String getCityInput() {
            return cityInput;
        }
        private String getYearInput() {
            return yearInput;
        }
        private String getTimePeriodInput() {
            return timePeriodInput;
        }        
    }

    private static ArrayList<UserInput> getUserInputs(JDBCConnection jdbc, String sortInput) {
        ArrayList<String> data = jdbc.getUserInput(sortInput);
        ArrayList<UserInput> userInputs = new ArrayList<UserInput>();
        String regionInput, countryInput, stateInput, cityInput, yearInput, timePeriodInput; 
        regionInput = countryInput = stateInput = cityInput = yearInput = timePeriodInput = "";
        for (int i = 0; i < data.size(); i++) {
            int condition = i % 6;
            switch (condition) {
                case 0:
                    regionInput = data.get(i);
                    break;
                case 1:
                    countryInput = data.get(i);
                    break;
                case 2:
                    stateInput = data.get(i);
                    break;
                case 3:
                    cityInput = data.get(i);
                    break;
                case 4:
                    yearInput = data.get(i);
                    break;
                case 5:
                    timePeriodInput = data.get(i);
                    break;
                }
            if (i != 0 && i % 6 == 5) {
                UserInput userInput = new UserInput(regionInput, countryInput, stateInput, cityInput, yearInput, timePeriodInput);
                userInputs.add(userInput);
            }
            
        }
        return userInputs;
    }
}
