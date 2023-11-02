package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import app.JDBCConnection.CountryPopulationAbsoluteEntry;
import app.JDBCConnection.CountryPopulationEntry;
import app.JDBCConnection.CountryPopulationRelativeEntry;
import app.JDBCConnection.GeographicRegionTemperatureEntry;
import app.JDBCConnection.GeographicRegionTemperatureRelativeChangeEntry;
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
public class PageDeepDive2 implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/DeepDive2.html";

    @Override
    public void handle(Context context) throws Exception {

        JDBCConnection jdbc = new JDBCConnection();
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" +
                "<title>Subtask 2.2</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html
                + "<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>";

        html = html + "</head>";

        // fonts
        html = html + "<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Tsukimi Rounded'>";

        // Add the body
        html = html + "<body>";

        // website name/logo
        html += "<div class='name'>";
        html += "  <h1><a style='' href='/'>Climate Shifts</a></h1>";
        html += "</div>";

        html += "<div class='main'>";
        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html
                + """
                        <div class='topnav'>
                            <a href='/'>Homepage</a>
                            <a href='mission.html'>Our Mission</a>
                            <div class='dropdownNavbar'>
                                <a class='currentPage' class='dropbtn'>View Changes By <i class='fa fa-solid fa-angle-down'></i> </a>
                                <div class='dropdownNavbar-content'>
                                    <a href='ShallowGlanceC.html'>Temperature and population change by Country/World</a>
                                    <a href='ShallowGlanceT2.html'>Temperature change by City/State</a>
                                    <a href='DeepDive.html'>Temperature over extended periods</a>
                                    <a class='currentSelected' href='DeepDive2.html'>Time periods with similar temperature and/or population</a>
                                </div>
                            </div>
                        </div>
                        """;

        // Add header content block
        html = html + """
                    <div class='header' id='deepDive2Header'>
                        <h1>Deep Dive</h1>
                        <h2>Time periods with similar temperature and/or population</h2>
                    </div>
                """;

        // Add Div for page Content
        html = html + "<div class='grid-container-sg'>";
        html = html + "<div class='grid-item-sg-st1 item1-sg'>";

        html = html + "<div id='DeepDiveT2' class='box-dd-2 centered-box'>";
        html += """
                        <script>
                            function showCountry() {
                                document.getElementById('country').style.display='flex';
                                document.getElementById('state').style.display='none';
                                document.getElementById('city').style.display='none';
                                document.getElementById('bothOptionLabel').style.display='block';
                                document.getElementById('selectedType').style.display= 'flex';
                                document.getElementById('selectedTypeH4').style.display= 'block';
                                document.getElementById('DeepDiveT2').style.height= '510px';
                            }
                            function showState() {
                                document.getElementById('country').style.display='none';
                                document.getElementById('state').style.display='flex';
                                document.getElementById('city').style.display='none';
                                //document.getElementById('bothOptionLabel').style.display='none';
                                document.getElementById('selectedType').style.display= 'none';
                                document.getElementById('selectedTypeH4').style.display= 'none';
                                document.getElementById('DeepDiveT2').style.height= '422px';
                                document.getELementById('selectedType').value = 'temperature';
                            }
                            function showCity() {
                                document.getElementById('country').style.display='none';
                                document.getElementById('state').style.display='none';
                                document.getElementById('city').style.display='flex';
                                //document.getElementById('bothOptionLabel').style.display='none';
                                document.getElementById('selectedType').style.display= 'none';
                                document.getElementById('selectedTypeH4').style.display= 'none';
                                document.getElementById('DeepDiveT2').style.height= '422px';
                                document.getELementById('selectedType').value = 'temperature';
                            }
                        </script>
                """.formatted();

        html = html + "<form action='/DeepDive2.html' method='post'>";

        html = html + "<h4>Geographic Region</h4>";

        html = html
                + """
                        <div id="selectedGeographicRegion">
                                <input type="radio" name="selectedGeographicRegion" id="countryOption" value="country" checked>
                                <label for="countryOption" onclick="showCountry()">Country</label>
                                <input type="radio" name="selectedGeographicRegion" id="stateOption" value="state">
                                <label for="stateOption" onclick="showState()">State</label>
                                <input type="radio" name="selectedGeographicRegion" id="cityOption" value="city">
                                <label for="cityOption" onclick="showCity()">City</label>
                            </div>
                        """;

        html = html + "<div id= 'country' class='dropdown-container' style='display:flex;'>";

        html = html + "      <select id='countryDropdown' class='dropdown' name='countryDropdown'>";
        html += "      <option value='' disabled selected>Select your option</option>";
        html = getCountryOption(html, jdbc);
        html = html + "      </select>";
        html = html + "</div>";

        html = html + "<div id='state' class='dropdown-container' style='display:none;'>";

        html = html + "      <select id='stateDropdown' class='dropdown' name='stateDropdown'>";
        html += "      <option value='' disabled selected>Select your option</option>";
        html = getStateOption(html, jdbc);
        html = html + "      </select>";
        html = html + "</div>";

        html = html + "<div id='city' class='dropdown-container' style='display:none;'>";

        html = html + "      <select id='cityDropdown' class='dropdown' name='cityDropdown'>";
        html += "      <option value='' disabled selected>Select your option</option>";
        html = getCityOption(html, jdbc);
        html = html + "      </select>";
        html = html + "</div>";

        html = html + "<h4>Start Year</h4>";
        html = html + "<div class='dropdown-container'>";

        html = html + "      <select id='startYearDropdown' class='dropdown' name='startYearDropdown'>";
        html += "      <option value='' disabled selected>Select your option</option>";
        html = getStartYearOption(html, jdbc);
        html = html + "      </select>";
        html = html + "</div>";

        html = html + "<h4>Time Period</h4>";
        html = html + "<div class='dropdown-container'>";

        html = html + """
                <label id='timeLabel' for="timePeriod">Enter time period in years:
                <input type="number" id="timePeriod" name="timePeriod" min="1" max="264">
                </label>

                """;
        html = html + "</div>";

        html = html + "<h4 id='selectedTypeH4'>Select Similarity Type</h4>";
        html = html
                + """
                        <div id="selectedType" style='display:flex;'>
                                <input type="radio" name="selectedType" id="temperatureOption" value="temperature" checked>
                                <label for="temperatureOption" >Temperature</label>
                                <input type="radio" name="selectedType" id="populationOption" value="population">
                                <label for="populationOption">Population</label>

                                <input type="radio" name="selectedType" id="bothOption" value="both">
                                <label id="bothOptionLabel" for="bothOption">Both</label>

                            </div>
                        """;

        html = html + "<h4>Select Similarity Measure</h4>";
        html = html
                + """
                        <div id="selectedMeasure">
                                <input type="radio" name="selectedMeasure" id="relativeOption" value="relative" checked>
                                <label for="relativeOption">Relative Change</label>
                                <input type="radio" name="selectedMeasure" id="absoluteOption" value="absolute">
                                <label for="absoluteOption">Absolute Values</label>
                            </div>
                        """;

        html = html
                + "   <button type='submit' id='submitButton' class='submitbutton center' onclick='this.classList.add(\"active\")' formaction='/DeepDive2.html'>Submit</button>";

        html = html + "</form>";

        // returned data from the form
        String countrySelection = context.formParam("countryDropdown");
        String startYearSelection = context.formParam("startYearDropdown");
        String selectedGeographicRegion = context.formParam("selectedGeographicRegion");
        String selectedType = context.formParam("selectedType");
        String selectedMeasure = context.formParam("selectedMeasure");
        int endYearTimePeriod = 0;
        if (startYearSelection == null) {
            endYearTimePeriod = 0;
        } else {
            endYearTimePeriod = Integer.parseInt(context.formParam("startYearDropdown"))
                    + Integer.parseInt(context.formParam("timePeriod"));
        }
        String citySelection = context.formParam("cityDropdown");
        String stateSelection = context.formParam("stateDropdown");

        html = html + "</div>";
        html = html + "</div>";

        html = html + "<div class='grid-item-sg-st1 item2-sg'>";

        if (selectedGeographicRegion == null || startYearSelection == null || selectedType == null
                || selectedMeasure == null || endYearTimePeriod == 0) {
            html = html + "<h1>" + "Please select the options first" + "</h1>";
        } else if (endYearTimePeriod > 2013) {
            html = html + "<h1>" + "The entered Time period is too high" + "</h1>";
        } else {

            if (selectedGeographicRegion.equals("country")) {

                if (selectedMeasure.equals("absolute") && selectedType.equals("temperature")) {
                    html = html + "<h1>" + "Similarity by temperature for the " + selectedGeographicRegion + " of "
                            + countrySelection + "</h1>";

                    html = html + "<h4 class='measureType'>Absolute Change</h4>";

                    html = html + "<p>*If the table is empty, data isn't available.</p>";

                    html = html + """
                            <div class='tableClass'>
                                <table id='absoluteTemperatureCountry'>
                                <thead>
                                        <tr>
                                            <th>Year</th>
                                            <th>Country</th>
                                            <th>Average Temp</th>
                                            <th>Minimum Temp</th>
                                            <th>Maximum Temp</th>
                                            <th>Average Temp Difference</th>
                                            <th>Minimum Temp Difference</th>
                                            <th>Maximum Temp Difference</th>
                                        </tr>
                                </thead>
                                <tbody>
                                """;
                    html = html + getGeographicRegionTemperatureTable(jdbc, selectedGeographicRegion,
                            countrySelection, startYearSelection,
                            endYearTimePeriod);

                    html = html + "</tbody> </table> </div>";
                }

                else if (selectedMeasure.equals("relative") && selectedType.equals("temperature")) {
                    html = html + "<h1>" + "Similarity by temperature for the " + selectedGeographicRegion + " of "
                            + countrySelection + "</h1>";

                    html = html + "<h4 class='measureType'>Relative Change</h4>";

                    html = html + "<p>*If the table is empty, data isn't available.</p>";

                    html = html + """
                            <div class='tableClass'>
                                <table id='TemperatureCountryRelative'>
                                <thead>
                                        <tr>
                                            <th>Year</th>
                                            <th>Country</th>
                                            <th>Average Temp</th>
                                            <th>Minimum Temp</th>
                                            <th>Maximum Temp</th>
                                            <th>Average Temp Difference %</th>
                                            <th>Minimum Temp Difference %</th>
                                            <th>Maximum Temp Difference %</th>
                                        </tr>
                                </thead>
                                <tbody>
                                """;
                    html = html + getGeographicRegionTemperatureRelativeTable(jdbc, selectedGeographicRegion,
                            countrySelection, startYearSelection,
                            endYearTimePeriod);

                    html = html + "</tbody> </table> </div>";
                }

                else if (selectedMeasure.equals("absolute") && selectedType.equals("population")) {
                    html = html + "<h1>" + "Similarity by temperature for the " + selectedGeographicRegion + " of "
                            + countrySelection + "</h1>";

                    html = html + "<h4 class='measureType'>Absolute Change</h4>";

                    html = html + "<p>*If the table is empty, data isn't available.</p>";

                    html = html + """
                            <div class='tableClass'>
                                <table id='absolutePopulationCountry'>
                                <thead>
                                        <tr>
                                            <th>Year</th>
                                            <th>Country</th>
                                            <th>Population</th>
                                            <th>Population Difference</th>
                                        </tr>
                                </thead>
                                <tbody>
                                """;
                    html = html + getCountryPopulationAbsoluteTable(jdbc, countrySelection,
                            startYearSelection, endYearTimePeriod);

                    html = html + "</tbody> </table> </div>";
                }

                else if (selectedMeasure.equals("relative") && selectedType.equals("population")) {
                    html = html + "<h1>" + "Similarity by temperature for the " + selectedGeographicRegion + " of "
                            + countrySelection + "</h1>";

                    html = html + "<h4 class='measureType'>Relative Change</h4>";

                    html = html + "<p>*If the table is empty, data isn't available.</p>";

                    html = html + """
                            <div class='tableClass'>
                                <table id='relativePopulationCountry'>
                                <thead>
                                        <tr>
                                            <th>Year</th>
                                            <th>Country</th>
                                            <th>Population</th>
                                            <th>Population Difference %</th>
                                        </tr>
                                </thead>
                                <tbody>
                                """;
                    html = html + getCountryPopulationRelativeTable(jdbc, countrySelection,
                            startYearSelection, endYearTimePeriod);

                    html = html + "</tbody> </table> </div>";
                }

                else if (selectedMeasure.equals("relative") && selectedType.equals("both")) {
                    html = html + "<h1>" + "Similarity by both population & temperature for the "
                            + selectedGeographicRegion + " of "
                            + countrySelection + "</h1>";

                    html = html + "<h4 class='measureType'>Relative Change</h4>";

                    html = html + "<p>*If the table is empty, data isn't available.</p>";

                    html = html + """
                            <div class='tableClass'>
                                <table id='relativePopulationCountry'>
                                <thead>
                                        <tr>
                                            <th>Year</th>
                                            <th>Country</th>
                                            <th>Population</th>
                                            <th>Population Difference %</th>
                                        </tr>
                                </thead>
                                <tbody>
                                """;
                    html = html + getCountryPopulationRelativeTable(jdbc, countrySelection,
                            startYearSelection, endYearTimePeriod);

                    html = html + "</tbody> </table> </div>";

                    html = html + "<h1> </h1>";
                    html = html + "<p>*If the table is empty, data isn't available.</p>";
                    html = html + """
                            <div class='tableClass'>
                                <table id='TemperatureCountryRelative'>
                                <thead>
                                        <tr>
                                            <th>Year</th>
                                            <th>Country</th>
                                            <th>Average Temp</th>
                                            <th>Minimum Temp</th>
                                            <th>Maximum Temp</th>
                                            <th>Average Temp Difference %</th>
                                            <th>Minimum Temp Difference %</th>
                                            <th>Maximum Temp Difference %</th>
                                        </tr>
                                </thead>
                                <tbody>
                                """;
                    html = html + getGeographicRegionTemperatureRelativeTable(jdbc, selectedGeographicRegion,
                            countrySelection, startYearSelection,
                            endYearTimePeriod);

                    html = html + "</tbody> </table> </div>";
                }

                else if (selectedMeasure.equals("absolute") && selectedType.equals("both")) {
                    html = html + "<h1>" + "Similarity by both population & temperature for the "
                            + selectedGeographicRegion + " of "
                            + countrySelection + "</h1>";

                    html = html + "<h4 class='measureType'>Absolute Change</h4>";

                    html = html + "<p>*If the table is empty, data isn't available.</p>";

                    html = html + """
                            <div class='tableClass'>
                                <table id='absolutePopulationCountry'>
                                <thead>
                                        <tr>
                                            <th>Year</th>
                                            <th>Country</th>
                                            <th>Population</th>
                                            <th>Population Difference</th>
                                        </tr>
                                </thead>
                                <tbody>
                                """;
                    html = html + getCountryPopulationAbsoluteTable(jdbc, countrySelection,
                            startYearSelection, endYearTimePeriod);

                    html = html + "</tbody> </table> </div>";

                    html = html + "<h1> </h1>";
                    html = html + "<p>*If the table is empty, data isn't available.</p>";
                    html = html + """
                            <div class='tableClass'>
                                <table id='absoluteTemperatureCountry'>
                                <thead>
                                        <tr>
                                            <th>Year</th>
                                            <th>Country</th>
                                            <th>Average Temp</th>
                                            <th>Minimum Temp</th>
                                            <th>Maximum Temp</th>
                                            <th>Average Temp Difference</th>
                                            <th>Minimum Temp Difference</th>
                                            <th>Maximum Temp Difference</th>
                                        </tr>
                                </thead>
                                <tbody>
                                """;
                    html = html + getGeographicRegionTemperatureTable(jdbc, selectedGeographicRegion,
                            countrySelection, startYearSelection,
                            endYearTimePeriod);

                    html = html + "</tbody> </table> </div>";
                }

            } else if (selectedGeographicRegion.equals("state")) {

                if (selectedMeasure.equals("absolute") && selectedType.equals("temperature")) {
                    html = html + "<h1>" + "Similarity by temperature for the " + selectedGeographicRegion + " of "
                            + stateSelection + "</h1>";

                    html = html + "<h4 class='measureType'>Absolute Change</h4>";

                    html = html + "<p>*If the table is empty, data isn't available.</p>";

                    html = html + """
                            <div class='tableClass'>
                                <table id='absoluteTemperatureState'>
                                <thead>
                                        <tr>
                                            <th>Year</th>
                                            <th>State</th>
                                            <th>Average Temp</th>
                                            <th>Minimum Temp</th>
                                            <th>Maximum Temp</th>
                                            <th>Average Temp Difference</th>
                                            <th>Minimum Temp Difference</th>
                                            <th>Maximum Temp Difference</th>
                                        </tr>
                                </thead>
                                <tbody>
                                """;
                    html = html + getGeographicRegionTemperatureTable(jdbc, selectedGeographicRegion,
                            stateSelection, startYearSelection,
                            endYearTimePeriod);

                    html = html + "</tbody> </table> </div>";
                }

                else if (selectedMeasure.equals("relative") && selectedType.equals("temperature")) {
                    html = html + "<h1>" + "Similarity by temperature for the " + selectedGeographicRegion + " of "
                            + stateSelection + "</h1>";

                    html = html + "<h4 class='measureType'>Relative Change</h4>";

                    html = html + "<p>*If the table is empty, data isn't available.</p>";

                    html = html + """
                            <div class='tableClass'>
                                <table id='TemperatureStateRelative'>
                                <thead>
                                        <tr>
                                            <th>Year</th>
                                            <th>State</th>
                                            <th>Average Temp</th>
                                            <th>Minimum Temp</th>
                                            <th>Maximum Temp</th>
                                            <th>Average Temp Difference %</th>
                                            <th>Minimum Temp Difference %</th>
                                            <th>Maximum Temp Difference %</th>
                                        </tr>
                                </thead>
                                <tbody>
                                """;
                    html = html + getGeographicRegionTemperatureRelativeTable(jdbc, selectedGeographicRegion,
                            stateSelection, startYearSelection,
                            endYearTimePeriod);

                    html = html + "</tbody> </table> </div>";
                }

            } else if (selectedGeographicRegion.equals("city")) {

                if (selectedMeasure.equals("absolute") && selectedType.equals("temperature")) {
                    html = html + "<h1>" + "Similarity by temperature for the " + selectedGeographicRegion + " of "
                            + citySelection + "</h1>";

                    html = html + "<h4 class='measureType'>Absolute Change</h4>";

                    html = html + "<p>*If the table is empty, data isn't available.</p>";

                    html = html + """
                            <div class='tableClass'>
                                <table id='absoluteTemperatureCity'>
                                <thead>
                                        <tr>
                                            <th>Year</th>
                                            <th>City</th>
                                            <th>Average Temp</th>
                                            <th>Minimum Temp</th>
                                            <th>Maximum Temp</th>
                                            <th>Average Temp Difference</th>
                                            <th>Minimum Temp Difference</th>
                                            <th>Maximum Temp Difference</th>
                                        </tr>
                                </thead>
                                <tbody>
                                """;
                    html = html + getGeographicRegionTemperatureTable(jdbc, selectedGeographicRegion,
                            citySelection, startYearSelection,
                            endYearTimePeriod);

                    html = html + "</tbody> </table> </div>";
                }

                else if (selectedMeasure.equals("relative") && selectedType.equals("temperature")) {
                    html = html + "<h1>" + "Similarity by temperature for the " + selectedGeographicRegion + " of "
                            + citySelection + "</h1>";

                    html = html + "<h4 class='measureType'>Relative Change</h4>";

                    html = html + "<p>*If the table is empty, data isn't available.</p>";

                    html = html + """
                            <div class='tableClass'>
                                <table id='TemperatureCityRelative'>
                                <thead>
                                        <tr>
                                            <th>Year</th>
                                            <th>City</th>
                                            <th>Average Temp</th>
                                            <th>Minimum Temp</th>
                                            <th>Maximum Temp</th>
                                            <th>Average Temp Difference %</th>
                                            <th>Minimum Temp Difference %</th>
                                            <th>Maximum Temp Difference %</th>
                                        </tr>
                                </thead>
                                <tbody>
                                """;
                    html = html + getGeographicRegionTemperatureRelativeTable(jdbc, selectedGeographicRegion,
                            citySelection, startYearSelection,
                            endYearTimePeriod);

                    html = html + "</tbody> </table> </div>";
                }

            }

        }

        html = html + "</div>";
        html = html + "</div>"; // closes grid-container-sg

        // close div
        html = html + "</div>";
        html = html + "</div>";
        html = html + "</div>";

        // Footer
        html = html + """
                    <div class='footer'>
                        <p>&#169 2023 Climate Shifts. All rights reserved.</p>
                    </div>
                """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    private String getCountryOption(String html, JDBCConnection jdbc) {
        ArrayList<CountryPopulationEntry> countryPopulationEntries = jdbc.getCountryPopulation();
        ArrayList<String> countryNames = new ArrayList<>();

        for (CountryPopulationEntry countryPopulationEntry : countryPopulationEntries) {
            countryNames.add(countryPopulationEntry.getCountry());
        }

        Set<String> uniqueCountryNamesSet = new HashSet<>(countryNames);
        ArrayList<String> uniqueCountryNames = new ArrayList<>(uniqueCountryNamesSet);
        Collections.sort(uniqueCountryNames);

        for (String country : uniqueCountryNames) {
            html = html + "      <option>" + country + "</option>";
        }

        return html;

    }

    private String getCityOption(String html, JDBCConnection jdbc) {
        ArrayList<String> CityNameEntries = jdbc.getCityNames();
        ArrayList<String> cityNames = new ArrayList<>();

        for (String cityNameEntry : CityNameEntries) {
            cityNames.add(cityNameEntry);
        }

        Set<String> uniqueCityNamesSet = new HashSet<>(cityNames);
        ArrayList<String> uniqueCityNames = new ArrayList<>(uniqueCityNamesSet);
        Collections.sort(uniqueCityNames);

        for (String city : uniqueCityNames) {
            html = html + "      <option>" + city + "</option>";
        }

        return html;
    }

    private String getStateOption(String html, JDBCConnection jdbc) {
        ArrayList<String> stateNameEntries = jdbc.getStateNames();
        ArrayList<String> stateNames = new ArrayList<>();

        for (String stateNameEntry : stateNameEntries) {
            stateNames.add(stateNameEntry);
        }

        Set<String> uniqueStateNamesSet = new HashSet<>(stateNames);
        ArrayList<String> uniqueStateNames = new ArrayList<>(uniqueStateNamesSet);
        Collections.sort(uniqueStateNames);

        for (String state : uniqueStateNames) {
            html = html + "      <option>" + state + "</option>";
        }

        return html;
    }

    private String getStartYearOption(String html, JDBCConnection jdbc) {
        ArrayList<CountryPopulationEntry> countryPopulationEntries = jdbc.getCountryPopulation();
        ArrayList<Integer> years = new ArrayList<>();
        for (CountryPopulationEntry countryPopulationEntry : countryPopulationEntries) {
            years.add(countryPopulationEntry.getYear());
        }
        Set<Integer> uniqueYearsSet = new HashSet<>(years);
        ArrayList<Integer> uniqueYears = new ArrayList<>(uniqueYearsSet);

        for (int startYear : uniqueYears) {
            html = html + "      <option>" + startYear + "</option>";
        }
        return html;

    }

    // use this method getGeographicRegionTemperatureEntry() to get the data and
    // format it into a table
    private String getGeographicRegionTemperatureTable(JDBCConnection jdbc,
            String selectedGeographicRegion, String name, String startYearSelection, int endYearTimePeriod) {
        ArrayList<GeographicRegionTemperatureEntry> geographicRegionTemperatureEntries = jdbc
                .getGeographicRegionTemperatureEntry(selectedGeographicRegion, name,
                        Integer.parseInt(startYearSelection), endYearTimePeriod);

        ArrayList<Integer> years = new ArrayList<>();
        ArrayList<String> regionName = new ArrayList<>();
        ArrayList<Double> avgTemp = new ArrayList<>();
        ArrayList<Double> minTemp = new ArrayList<>();
        ArrayList<Double> maxTemp = new ArrayList<>();
        ArrayList<Double> avgTempDifference = new ArrayList<>();
        ArrayList<Double> MinDifference = new ArrayList<>();
        ArrayList<Double> MaxDifference = new ArrayList<>();

        for (GeographicRegionTemperatureEntry geographicRegionTemperatureEntry : geographicRegionTemperatureEntries) {
            years.add(geographicRegionTemperatureEntry.getYear());
            regionName.add(geographicRegionTemperatureEntry.getGeographicRegion());
            avgTemp.add(geographicRegionTemperatureEntry.getAverageTemperature());
            minTemp.add(geographicRegionTemperatureEntry.getMinTemperature());
            maxTemp.add(geographicRegionTemperatureEntry.getMaxTemperature());
            avgTempDifference.add(geographicRegionTemperatureEntry.getAVGdifference());
            MinDifference.add(geographicRegionTemperatureEntry.getMINdifference());
            MaxDifference.add(geographicRegionTemperatureEntry.getMAXdifference());

        }
        String html = "";

        for (int i = 0; i < years.size(); i++) {
            // get one element at a time from the arraylist
            String yearString = years.get(i).toString();
            String regionNameString = regionName.get(i);
            String avgTempString = avgTemp.get(i).toString();
            String minTempString = minTemp.get(i).toString();
            String maxTempString = maxTemp.get(i).toString();
            String avgTempDifferenceString = avgTempDifference.get(i).toString();
            String MinDifferenceString = MinDifference.get(i).toString();
            String MaxDifferenceString = MaxDifference.get(i).toString();

            html = html + """
                    <tr>
                        <td>""" + yearString + """
                        </td>
                    <td>""" + regionNameString + """
                                </td>
                    <td>""" + avgTempString + """
                                </td>
                    <td>""" + minTempString + """
                                </td>
                    <td>""" + maxTempString + """
                                </td>
                    <td>""" + avgTempDifferenceString + """
                                </td>
                    <td>""" + MinDifferenceString + """
                                </td>
                    <td>""" + MaxDifferenceString + """
                                </td>
                        </tr>
                    """;
        }
        return html;

    }

    private String getGeographicRegionTemperatureRelativeTable(JDBCConnection jdbc,
            String selectedGeographicRegion, String name, String startYearSelection, int endYearTimePeriod) {
        ArrayList<GeographicRegionTemperatureRelativeChangeEntry> geographicRegionTemperatureRelativeEntries = jdbc
                .getGeographicRegionTemperatureRelativeChangeEntry(selectedGeographicRegion, name,
                        Integer.parseInt(startYearSelection), endYearTimePeriod);

        ArrayList<Integer> years = new ArrayList<>();
        ArrayList<String> regionName = new ArrayList<>();
        ArrayList<Double> avgTemp = new ArrayList<>();
        ArrayList<Double> minTemp = new ArrayList<>();
        ArrayList<Double> maxTemp = new ArrayList<>();
        ArrayList<Double> avgTempDifference = new ArrayList<>();
        ArrayList<Double> MinDifference = new ArrayList<>();
        ArrayList<Double> MaxDifference = new ArrayList<>();

        for (GeographicRegionTemperatureRelativeChangeEntry GeographicRegionTemperatureRelativeChangeEntry : geographicRegionTemperatureRelativeEntries) {
            years.add(GeographicRegionTemperatureRelativeChangeEntry.getYear());
            regionName.add(GeographicRegionTemperatureRelativeChangeEntry.getGeographicRegion());
            avgTemp.add(GeographicRegionTemperatureRelativeChangeEntry.getAverageTemperature());
            minTemp.add(GeographicRegionTemperatureRelativeChangeEntry.getMinTemperature());
            maxTemp.add(GeographicRegionTemperatureRelativeChangeEntry.getMaxTemperature());
            avgTempDifference.add(GeographicRegionTemperatureRelativeChangeEntry.getAVGdifference());
            MinDifference.add(GeographicRegionTemperatureRelativeChangeEntry.getMINdifference());
            MaxDifference.add(GeographicRegionTemperatureRelativeChangeEntry.getMAXdifference());

        }
        String html = "";

        for (int i = 0; i < years.size(); i++) {
            // get one element at a time from the arraylist
            String yearString = years.get(i).toString();
            String regionNameString = regionName.get(i);
            String avgTempString = avgTemp.get(i).toString();
            String minTempString = minTemp.get(i).toString();
            String maxTempString = maxTemp.get(i).toString();
            String avgTempDifferenceString = avgTempDifference.get(i).toString();
            String MinDifferenceString = MinDifference.get(i).toString();
            String MaxDifferenceString = MaxDifference.get(i).toString();

            html = html + """
                    <tr>
                        <td>""" + yearString + """
                        </td>
                    <td>""" + regionNameString + """
                                </td>
                    <td>""" + avgTempString + """
                                </td>
                    <td>""" + minTempString + """
                                </td>
                    <td>""" + maxTempString + """
                                </td>
                    <td>""" + avgTempDifferenceString + """
                                </td>
                    <td>""" + MinDifferenceString + """
                                </td>
                    <td>""" + MaxDifferenceString + """
                                </td>
                        </tr>
                    """;
        }
        return html;

    }

    private String getCountryPopulationRelativeTable(JDBCConnection jdbc,
            String name, String startYearSelection, int endYearTimePeriod) {
        ArrayList<CountryPopulationRelativeEntry> CountryPopulationRelativeData = jdbc
                .getCountryPopulationRelativeEntry(name,
                        Integer.parseInt(startYearSelection), endYearTimePeriod);

        ArrayList<Integer> years = new ArrayList<>();
        ArrayList<String> countryName = new ArrayList<>();
        ArrayList<Long> population = new ArrayList<>();
        ArrayList<Double> populationDifference = new ArrayList<>();

        for (CountryPopulationRelativeEntry CountryPopulationRelativeEntry : CountryPopulationRelativeData) {
            years.add(CountryPopulationRelativeEntry.getYear());
            countryName.add(CountryPopulationRelativeEntry.getCountryName());
            population.add(CountryPopulationRelativeEntry.getPopulation());
            populationDifference.add(CountryPopulationRelativeEntry.getPopulationDifference());
        }
        String html = "";

        for (int i = 0; i < years.size(); i++) {
            // get one element at a time from the arraylist
            String yearString = years.get(i).toString();
            String countryNameString = countryName.get(i);
            String populationString = population.get(i).toString();
            String populationDifferenceString = populationDifference.get(i).toString();

            html = html + """
                    <tr>
                        <td>""" + yearString + """
                    </td>
                    <td>""" + countryNameString + """
                    </td>
                    <td>""" + populationString + """
                    </td>
                    <td>""" + populationDifferenceString + """
                        </td>
                        </tr>
                    """;
        }
        return html;

    }

    private String getCountryPopulationAbsoluteTable(JDBCConnection jdbc,
            String name, String startYearSelection, int endYearTimePeriod) {
        ArrayList<CountryPopulationAbsoluteEntry> CountryPopulationAbsoluteData = jdbc
                .getCountryPopulationAbsoluteEntry(name,
                        Integer.parseInt(startYearSelection), endYearTimePeriod);

        ArrayList<Integer> years = new ArrayList<>();
        ArrayList<String> countryName = new ArrayList<>();
        ArrayList<Long> population = new ArrayList<>();
        ArrayList<Long> populationDifference = new ArrayList<>();

        for (CountryPopulationAbsoluteEntry CountryPopulationAbsoluteEntry : CountryPopulationAbsoluteData) {
            years.add(CountryPopulationAbsoluteEntry.getYear());
            countryName.add(CountryPopulationAbsoluteEntry.getCountryName());
            population.add(CountryPopulationAbsoluteEntry.getPopulation());
            populationDifference.add(CountryPopulationAbsoluteEntry.getPopulationDifference());
        }
        String html = "";

        for (int i = 0; i < years.size(); i++) {
            // get one element at a time from the arraylist
            String yearString = years.get(i).toString();
            String countryNameString = countryName.get(i);
            String populationString = population.get(i).toString();
            String populationDifferenceString = populationDifference.get(i).toString();

            html = html + """
                    <tr>
                        <td>""" + yearString + """
                    </td>
                    <td>""" + countryNameString + """
                    </td>
                    <td>""" + populationString + """
                    </td>
                    <td>""" + populationDifferenceString + """
                        </td>
                        </tr>
                    """;
        }
        return html;

    }

}
