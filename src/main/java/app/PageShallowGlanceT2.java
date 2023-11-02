package app;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import app.JDBCConnection.CountryPopulationEntry;
import app.JDBCConnection.ShallowGlanceT2Data;
// import app.JDBCConnection.selectedCountryPopulationEntry;
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
public class PageShallowGlanceT2 implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/ShallowGlanceT2.html";

    @Override
    public void handle(Context context) throws Exception {

        JDBCConnection jdbc = new JDBCConnection();
        String stateOrCityInput = context.formParam("stateOrCity");
        String countryInput = context.formParam("country");
        String startYearInput = context.formParam("startYear");
        String endYearInput = context.formParam("endYear");

        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" +
                "<title>Population Shallow Glance</title>" +
                "<link rel='stylesheet' type='text/css' href='pagedeepdive.css' />" +
                "<script src='https://cdn.jsdelivr.net/npm/chart.js@4.3.0/dist/chart.umd.min.js'></script>" +
                "<script src='https://cdn.jsdelivr.net/npm/chart.js'></script>" +
                "<link rel='stylesheet' href='https://cdn.datatables.net/1.13.4/css/jquery.dataTables.css' />" +
                "<script src='https://code.jquery.com/jquery-3.5.1.js'></script>" +
                "<script src='https://cdn.datatables.net/1.13.4/js/jquery.dataTables.js'></script>" +
                "<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>";
        // fonts
        html = html + "<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Tsukimi Rounded'>";
        html += """
                <script> 

                    function formDisplay() {
                        let stateOrCity = "%s";
                        let country = "%s";
                        if (country == "null" || stateOrCity == "null") {
                            document.getElementById('geography').style.display='block';

                        }
                        else {
                            document.getElementById('year').style.display='block';
                            document.getElementById('stateOrCity').value="%s";
                            document.getElementById('country').value="%s";
                        }
                    }    
                    function resetDisplay() {
                        document.getElementById('stateOrCity').value="";
                        document.getElementById('country').value="";
                    }
                    
                    window.onload = function() {
                        formDisplay();
                    } 
                    
                </script>
                """.formatted(stateOrCityInput, countryInput, stateOrCityInput, countryInput);
                // """.formatted(countryInput);
        html += "</head>";


        // Add the body
        html = html + "<body>";

        // Title div
        html += "<div class='name'>";
        html += "  <h1><a style='' href='/'>Climate Shifts</a></h1>";
        html += "</div>";
        
        // Navbar div
        html = html
                + """
                        <div class='topnav'>
                            <a href='/'>Homepage</a>
                            <a href='mission.html'>Our Mission</a>
                            <div class='dropdown'>
                                <a class='currentPage'>View Changes By <i class='fa fa-solid fa-angle-down'></i></a>
                                <div class='dropdown-content'>
                                    <a href='ShallowGlanceC.html'>Temperature and population change by Country/World</a>
                                    <a class='selected' href='ShallowGlanceT2.html'>Temperature change by City/State</a>
                                    <a href='DeepDive.html'>Temperature over extended periods</a>
                                    <a href='DeepDive2.html'>Time periods with similar temperature and/or population</a>
                                </div>
                            </div>
                        </div>
                        """;
        html += "<div class='content'>";


        // Header div
        html = html + """
        <div id='header' style='background-image: url("/shallowglance2.svg"); background-size:cover; background-color:#d7fd9a; padding: 10px;'>
            <h1>Shallow Glance</h1>
            <h2>Temperature Change by City/State</h2>
        </div>
        """;
        
        // Grid div
        html = html + "<div class='grid'>";
        
        // Left side of grid
        html = html + "<div class='item1'>";
        html += "<h2>Choose your options</h2>";
        html = html + "<div class='box'>";
        
        // Drop down boxes
        html = html + "<form class='form' action=\"/ShallowGlanceT2.html\" method='post'>";

        // Determine if country and stateorcity selections are visible
        // html +="<span onload=\"formDisplay(document.getElementById('country'))\"></span>";
        html += "<div id='geography' style='display:none'>";
        // Choose state or city button
        html = html + "<h4>View State or City Data</h4>";
        html += """
                    <select class='dropdown' id='stateOrCity' name='stateOrCity'>
                        <option value='' disabled selected>Select your option</option>
                        <option value='states'>States</option>
                        <option value='cities'>Cities</option>
                    </select>
                """;

        html = html + "<h4>Select a country</h4>";
        html = html + "      <select id='country' class='dropdown' name='country'>";
        html += "               <option value='' disabled selected>Select your option</option>";
        html = getCountryOption(html, jdbc);
        html = html + "      </select>";
        html += "</div>";

        html += "<div id='year' style='display:none;'>";
        html = html + "<h4>Start Year</h4>";
        html = html + "      <select id='startYear' class='dropdown' name='startYear'>";
        html += "      <option value='' disabled selected>Select your option</option>";
        html = addTotalYearsByCountryAndStateOrCity(html, countryInput, stateOrCityInput);
        html = html + "      </select>";

        html = html + "<h4>End Year</h4>";

        html = html + "      <select id='endYear' class='dropdown' name='endYear'>";
        html += "      <option value='' disabled selected>Select your option</option>";
        html = addTotalYearsByCountryAndStateOrCity(html, countryInput, stateOrCityInput);
        html = html + "      </select>";
        html += "</div>";


        html += "<button type='submit' class='submit' formaction='/ShallowGlanceT2.html'>Submit</button>";
        html += "<button id='reset' class='reset' onclick='resetDisplay()'>Reset</button>";
        html = html + "</form>";

        html = html + "</div>";
        html = html + "</div>";

        html = html + "<div class='item2'>";
        // System status indicator
        if (countryInput == null && stateOrCityInput == null && (startYearInput == null || endYearInput == null)) {
            html += "<h1>Select states or cities and then select a country</h1>";
        } else if (countryInput != null && stateOrCityInput != null && (startYearInput == null || endYearInput == null)) {
            html += "<h1 id='emptySelectionError'>Select a time period</h1>";
            html += "<h1 id='noDataError' style='display:none'>Selected country and state or city view has no data, try pressing reset and using a different combination.</h1>";

        } else if (countryInput == null || startYearInput == null || endYearInput == null || stateOrCityInput == null) {
        } else if (Integer.parseInt(startYearInput) > Integer.parseInt(endYearInput)) {
            html += "<h1>Start year has to be lower than end year</h1>";
        } else {
            int startYear = Integer.parseInt(startYearInput);
            int endYear = Integer.parseInt(endYearInput);

            // get data from database and store as changedData array
            ArrayList<ShallowGlanceT2Data> startData = jdbc.getShallowGlanceT2Data(countryInput, stateOrCityInput,
                    startYear);
            ArrayList<ShallowGlanceT2Data> endData = jdbc.getShallowGlanceT2Data(countryInput, stateOrCityInput,
                    endYear);
            ArrayList<ShallowGlanceT2Data> changedData = new ArrayList<>();
            try {
                for (int i = 0; i < startData.size(); i++) {
                    ShallowGlanceT2Data currentData = jdbc.new ShallowGlanceT2Data(startData.get(i).getCountry(),
                            startData.get(i).getStateOrCityName(),
                            endData.get(i).getYear() - startData.get(i).getYear(),
                            (endData.get(i).getMaxTemp() - startData.get(i).getMaxTemp()) / (endYear - startYear),
                            (endData.get(i).getAvgTemp() - startData.get(i).getAvgTemp()) / (endYear - startYear),
                            (endData.get(i).getMinTemp() - startData.get(i).getMinTemp()) / (endYear - startYear));
                    changedData.add(currentData);
                }
            } catch (Exception e) {
                System.out.println("Error while creating table: startData length does not match endData length");
                html += "<h1> Error while creating table: Try using a different selection </h1>";
            }
            // If country does not exist, catch as error
            try {
                startData.get(0);
                // canvas = "<canvas id='selectedPopGraph' width='600' height='400'></canvas>";
                // TODO: create multi-axis graph
                // https://www.chartjs.org/docs/latest/getting-started/
                // https://www.chartjs.org/docs/latest/samples/line/multi-axis.html
                html = html
                        + "<h2>"
                        + countryInput
                        + " between "
                        + startYearInput + " and "
                        + endYearInput
                        + " as "
                        + stateOrCityInput
                        + "</h2>";

                // Table on the right of webpage
                String stateOrCityColumn = "";
                if (stateOrCityInput.equals("states")) {
                    stateOrCityColumn = "State";
                } else if (stateOrCityInput.equals("cities")) {
                    stateOrCityColumn = "City";
                }
                html += "<table style='border: 2px solid'>";
                html += "<tr>";
                html += "<td>";
                // html += "<div >";
                html += "<table class='header'>";
                // html += "<thead>";
                html += "<tr>";
                html += "<th>" + stateOrCityColumn + "</th>";
                html += "<th> Average Temperature Change </th>";
                html += "<th>" + stateOrCityColumn + "</th>";
                html += "<th> Maximum Temperature Change </th>";
                html += "<th>" + stateOrCityColumn + "</th>";
                html += "<th> Minimum Temperature Change </th>";
                html += "</tr>";
                // html += "</thead>";
                html += "</table>";
                
                html += "</td>";
                html += "</tr>";

                html += "<tr>";
                html += "<td>";
                // html += "<tbody>";
                // html += "<div style=\"width:800px; height:300px; overflow:auto;\">";
                html += "<div class='data'>";
                html += "<table class='data'>";

                // Sort AvgTemp data
                // ArrayList<ShallowGlanceT2Data> sortedData =
                // insertionsortAvgTemp(changedData);
                for (int i = 0; i < changedData.size(); i++) {
                    DecimalFormat roundedFormat = new DecimalFormat("0.00");
                    Double temp = 0.0;
                    html += "<tr>";
                    // Avg Temp Column
                    ArrayList<ShallowGlanceT2Data> sortedAvgTemp = insertionsortAvgTemp(changedData);
                    html += "<td>" + sortedAvgTemp.get(i).getStateOrCityName() + "</td>";
                    temp = sortedAvgTemp.get(i).getAvgTemp();
                    html += temp < 0 ? "<td>" + roundedFormat.format(temp) + "&#176;C</td>"
                            : "<td>+" + roundedFormat.format(temp) + "&#176;C</td>";
                    // Max Temp column
                    ArrayList<ShallowGlanceT2Data> sortedMaxTemp = insertionsortMaxTemp(changedData);
                    html += "<td>" + sortedMaxTemp.get(i).getStateOrCityName() + "</td>";
                    temp = sortedMaxTemp.get(i).getMaxTemp();
                    html += temp < 0 ? "<td>" + roundedFormat.format(temp) + "&#176;C</td>"
                            : "<td>+" + roundedFormat.format(temp) + "&#176;C</td>";
                    // Min Temp column
                    ArrayList<ShallowGlanceT2Data> sortedMinTemp = insertionsortMinTemp(changedData);
                    html += "<td>" + sortedMinTemp.get(i).getStateOrCityName() + "</td>";
                    temp = sortedMinTemp.get(i).getMinTemp();
                    html += temp < 0 ? "<td>" + roundedFormat.format(temp) + "&#176;C</td>"
                            : "<td>+" + roundedFormat.format(temp) + "&#176;C</td>";
                    // close table row
                    html += "</tr>";

                }
            } catch (Exception e) {
                html += "<h1>Selected query does not have data, try a different country or choose a different view</h1>";
            }
            html += "</table>";  
            html += "</div>";
            html += "</td>";
            html += "</tr>";
            // html += "</tbody>";
            // End of table
            html += """
                    </table>
                    """;
        }

        html = html + "</div>";

        html = html + "</div>";
        html = html + "</div>"; // closes grid-sg

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

    private static ArrayList<ShallowGlanceT2Data> insertionsortAvgTemp(ArrayList<ShallowGlanceT2Data> arrayList) {
        ArrayList<ShallowGlanceT2Data> list = arrayList;
        int i;
        int j;
        ShallowGlanceT2Data temp;
        for (i = 1; i < list.size(); ++i) {
            j = i;
            // Insert numbers[i] into sorted part
            // stopping once numbers[i] in correct position
            while (j > 0 && list.get(j).getAvgTemp() > list.get(j - 1).getAvgTemp()) { // sort in descending order
                // Swap numbers[j] and numbers[j - 1]
                temp = list.get(j);
                list.set(j, list.get(j - 1));
                list.set(j - 1, temp);
                // numbers.get(j) = numbers.[j - 1];
                // numbers[j - 1] = temp;
                --j;
            }
        }
        return list;
    }

    private static ArrayList<ShallowGlanceT2Data> insertionsortMaxTemp(ArrayList<ShallowGlanceT2Data> arrayList) {
        ArrayList<ShallowGlanceT2Data> list = arrayList;
        int i;
        int j;
        ShallowGlanceT2Data temp;
        for (i = 1; i < list.size(); ++i) {
            j = i;
            // Insert numbers[i] into sorted part
            // stopping once numbers[i] in correct position
            while (j > 0 && list.get(j).getMaxTemp() > list.get(j - 1).getMaxTemp()) { // sort in descending order
                // Swap numbers[j] and numbers[j - 1]
                temp = list.get(j);
                list.set(j, list.get(j - 1));
                list.set(j - 1, temp);
                // numbers.get(j) = numbers.[j - 1];
                // numbers[j - 1] = temp;
                --j;
            }
        }
        return list;
    }

    private static ArrayList<ShallowGlanceT2Data> insertionsortMinTemp(ArrayList<ShallowGlanceT2Data> arrayList) {
        ArrayList<ShallowGlanceT2Data> list = arrayList;
        int i;
        int j;
        ShallowGlanceT2Data temp;
        for (i = 1; i < list.size(); ++i) {
            j = i;
            // Insert numbers[i] into sorted part
            // stopping once numbers[i] in correct position
            while (j > 0 && list.get(j).getMinTemp() > list.get(j - 1).getMinTemp()) { // sort in descending order
                // Swap numbers[j] and numbers[j - 1]
                temp = list.get(j);
                list.set(j, list.get(j - 1));
                list.set(j - 1, temp);
                // numbers.get(j) = numbers.[j - 1];
                // numbers[j - 1] = temp;
                --j;
            }
        }
        return list;
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
            html = html + "      <option value='" + country + "'>" + country + "</option>";
        }

        return html;

    }

    private String addTotalYearsByCountryAndStateOrCity(String html, String country, String stateOrCityInput) {
        JDBCConnection jdbc = new JDBCConnection();
        ArrayList<Integer> years = jdbc.getTotalYearsByCountryAndStateOrCity(country, stateOrCityInput);
        try {
            years.get(0);
            for (Integer year : years) {
                html += "<option value='" + year + "'>" + year + "</option>";
            }
        } catch (Exception e) {
            html += "<option value='' disabled selected>Selected country and state or city view has no data, try pressing reset and using a different combination.</option>";
            if (country != null && stateOrCityInput != null) {
                html += """
                    <script>
                        window.onload = function () {
                            document.getElementById('noDataError').style.display='block';
                            document.getElementById('emptySelectionError').style.display='none';
                            document.getElementById('year').style.display='block';
                        }
                    </script>
                    """;
            }
        }

        return html;

    }
    //TODO: change temperature change data values
    // private static String addDataRow(String html, String category, String location, String countryInput, String stateInput, String cityInput, String yearInput, String timePeriodInput) {
    //     JDBCConnection jdbc = new JDBCConnection();
    //     DecimalFormat roundedFormat = new DecimalFormat("0.00");
    //     Map<String, Object> model = new HashMap<String, Object>();
    //     ArrayList<Double> data = jdbc.getAvgTemp(countryInput, stateInput, cityInput, yearInput, timePeriodInput);
    //     Double avgTemp = 0.0;
    //     Double changeAvgTemp = 0.0;
    //     Double totalAvgTemp = 0.0;
    //     String changeAvgTempString = "";
    //     int yearInt;
    //     int timePeriodInt;
    //     int i = 0;
    //     for (Double currentAvgTemp : data) {
    //         i++;
    //         totalAvgTemp += currentAvgTemp;
    //     }
    //     avgTemp = totalAvgTemp / i;
    //     try {
    //             yearInt = Integer.parseInt(yearInput);
    //             timePeriodInt = Integer.parseInt(timePeriodInput);
    //         } catch (Exception e) {
    //             yearInt = 0;
    //             timePeriodInt = 0;
    //         }
    //     try {
    //         // changeAvgTemp = (data.get(data.size() - 1) - data.get(0)) / (data.get(data.size() - 1) - data.get(0));
    //         //This is gradient (rate of change) formula
    //         changeAvgTemp = (data.get(data.size() - 1) - data.get(0)) / ((yearInt + timePeriodInt) - yearInt);
    //     } catch (Exception e) {

    //     }
    //     if (changeAvgTemp > 0) {
    //         changeAvgTempString = "<td>+" + roundedFormat.format(changeAvgTemp) + "&#176;C</td>";
    //     }
    //     else if (changeAvgTemp < 0) {
    //         changeAvgTempString = "<td>" + roundedFormat.format(changeAvgTemp) + "&#176;C</td>";
    //     }
    //     else {
    //         changeAvgTempString = "<td>" + roundedFormat.format(changeAvgTemp) + "&#176;C</td>";
    //     }
    //     html += "<tr>";
    //     html += "<td>" + category + "</td>";
    //     html += "<td>" + location + "</td>";
    //     html += "<td>" + yearInput + "</td>";
    //     html += "<td>" + timePeriodInput + "</td>";
    //     html += "<td>" + roundedFormat.format(avgTemp) + "&#176;C</td>";
    //     html += changeAvgTempString;
    //     html += "</tr>";
    //     return html;
    // }

}
