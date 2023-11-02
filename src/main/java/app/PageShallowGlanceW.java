package app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import app.JDBCConnection.CountryPopulationEntry;
import app.JDBCConnection.getWorldPopulationEntry;
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
public class PageShallowGlanceW implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/ShallowGlanceW.html";

    @Override
    public void handle(Context context) throws Exception {

        JDBCConnection jdbc = new JDBCConnection();

        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" +
                "<title>Population Shallow Glance</title>" +
                "<link rel='stylesheet' type='text/css' href='common.css' />" +
                "<script src='https://cdn.jsdelivr.net/npm/chart.js@4.3.0/dist/chart.umd.min.js'></script>" +
                "<link rel='stylesheet' href='https://cdn.datatables.net/1.13.4/css/jquery.dataTables.css' />" +
                "<script src='https://code.jquery.com/jquery-3.5.1.js'></script>" +
                "<script src='https://cdn.datatables.net/1.13.4/js/jquery.dataTables.js'></script>" +
                "<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>"
                +
                "</head>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // fonts
        html = html + "<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Tsukimi Rounded'>";

        // Add the body
        html = html + "<body>";

        html = html + """
                <script>

                $(document).ready(function() {
                    $('#worldTable').DataTable();
                });



                function showGraph() {
                    var selectedWorldGraph = document.getElementById("worldPopGraph");
                    var radioButtons = document.getElementById("dataOptions");

                    selectedWorldGraph.style.display = "block";
                    selectedWorldGraph.style.height = "400px";
                    selectedWorldGraph.style.width = "600px";
                    worldTable_wrapper.style.display = "none";

                    graphView.classList.add("active-button");
                    tableView.classList.remove("active-button");

                    radioButtons.style.display = "block";

                }
                function showTable() {
                    var selectedWorldGraph = document.getElementById("worldPopGraph");

                    var radioButtons = document.getElementById("dataOptions");

                    selectedWorldGraph.style.display = "none";
                    worldTable_wrapper.style.display = "flex";

                    graphView.classList.remove("active-button");
                    tableView.classList.add("active-button");

                    radioButtons.style.display = "none";
                }

                </script>
                """;

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
                                <a class='currentPage' class='dropbtn'>View Changes By <i class='fa fa-solid fa-angle-down'></i></a>
                                <div class='dropdownNavbar-content'>
                                    <a class='currentSelected' href='ShallowGlanceC.html'>Temperature and population change by Country/World</a>
                                    <a href='ShallowGlanceT2.html'>Temperature change by City/State</a>
                                    <a href='DeepDive.html'>Temperature over extended periods</a>
                                    <a href='DeepDive2.html'>Time periods with similar temperature and/or population</a>
                                </div>
                            </div>
                        </div>
                        """;

        // Add header content block
        html = html + """
                    <div class='header' id='shallowGlanceHeader'>
                        <h1>Shallow Glance</h1>
                        <h2>World Temperature and Population Data</h2>
                    </div>
                """;

        // Add Div for page Content
        html = html + "<div class='grid-container-sg'>";
        html = html + "<div class='grid-item-sg-st1 item1-sg'>";

        html = html + "<h3>Please select a view</h3>";

        html = html + "<div class='viewButtonsId'>";
        html = html + "<a href='http://localhost:7001/ShallowGlanceC.html'>Country</a>";
        html = html + "<a id='currentView' href='http://localhost:7001/ShallowGlanceW.html'>World</a>";
        html = html + "</div>";

        html = html + "<div class='box-sg-world'>";
        html = html + "<form action='/ShallowGlanceW.html' method='post'>";

        html = html + "<h4>Start Year</h4>";
        html = html + "<div class='dropdown-container'>";

        html = html + "      <select id='startYearDropdown' class='dropdown' name='startYearDropdown'>";
        html += "      <option value='' disabled selected>Select your option</option>";
        html = getStartYearOption(html, jdbc);
        html = html + "      </select>";
        html = html + "</div>";
        html = html + "<h4>End Year</h4>";
        html = html + "<div class='dropdown-container'>";

        html = html + "      <select id='endYearDropdown' class='dropdown' name='endYearDropdown'>";
        html += "      <option value='' disabled selected>Select your option</option>";
        html = getEndYearOption(html, jdbc);
        html = html + "      </select>";
        html = html + "</div>";

        html = html
                + "   <button type='submit' id='submitButton' class='submitbutton center' onclick='this.classList.add(\"active\")' formaction='/ShallowGlanceW.html'>Submit</button>";
        html = html + "</form>";
        html = html + "</div>";
        html = html + "</div>";

        String startYearSelection = context.formParam("startYearDropdown");
        String endYearSelection = context.formParam("endYearDropdown");

        String canvas = "";
        String graph = "";
        String table = "";

        if (startYearSelection == null || endYearSelection == null) {
            graph = "<h1 id='worldError'>Select all the options first</h1>";
        } else if (Integer.parseInt(startYearSelection) > Integer.parseInt(endYearSelection)) {
            graph = "<h1 id='worldError'>Start year has to be lower than end year</h1>";
        }

        else {
            int startYear = 0;
            int endYear = 0;
            startYear = Integer.parseInt(startYearSelection);
            endYear = Integer.parseInt(endYearSelection);

            canvas = "<canvas id='worldPopGraph' width='600' height='400'></canvas>";
            graph = generateSelectedWordGraph(startYear, endYear);
            table = generateSelectedWorldTable(jdbc, startYear, endYear);
        }

        html = html + "<div class='grid-item-sg-st1 item2-sg'>";
        html = html + "<div class='viewTableOrGraph'>";
        html = html + "<h3>Please select a view</h3>";
        html = html + "<div id=viewTableOrGraphButtonsId>";
        html = html + "<button id='graphView' class='active-button' onclick='showGraph()'>Graph</button>";
        html = html + "<button id='tableView' onclick='showTable()'>Table</button>";
        html = html + "</div>";
        html = html + "</div>";

        String graphHeading = "";
        String graphHeadingAvailable = "";
        if (startYearSelection == null || endYearSelection == null) {
            graphHeading = "";
        } else if (Integer.parseInt(startYearSelection) > Integer.parseInt(endYearSelection)) {
            graphHeading = "";
        } else {
            graphHeading = "<div class='selectedPopulationTitle'><h3 id = 'selectedPopulationTitle'>"
                    + "World between "
                    + startYearSelection + " and "
                    + endYearSelection
                    + "</h3></div>";
            graphHeadingAvailable = "<div class='selectedPopulationTitle'><h7 id = 'selectedPopulationTitle'>"
                    + "*if empty, no data available"
                    + "</h7></div>";
        }

        html = html + graphHeading;
        html = html + graphHeadingAvailable;
        html = html + canvas;
        html = html + graph;
        html = html + table;

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

    private String getEndYearOption(String html, JDBCConnection jdbc) {
        ArrayList<CountryPopulationEntry> countryPopulationEntries = jdbc.getCountryPopulation();
        ArrayList<Integer> years = new ArrayList<>();
        for (CountryPopulationEntry countryPopulationEntry : countryPopulationEntries) {
            years.add(countryPopulationEntry.getYear());
        }
        Set<Integer> uniqueYearsSet = new HashSet<>(years);
        ArrayList<Integer> uniqueYears = new ArrayList<>(uniqueYearsSet);

        for (int endYear : uniqueYears) {
            html = html + "      <option>" + endYear + "</option>";
        }
        return html;

    }

    private String populationToStringWithNAN(ArrayList<Long> population) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (Long pop : population) {
            if (pop != null) {
                sb.append(pop);
            } else {
                sb.append("'N/A'");
            }

            sb.append(",");
        }

        if (population.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        sb.append("]");

        return sb.toString();
    }

    private String avgTempToStringWithNAN(ArrayList<Double> avgTemp) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (Double pop : avgTemp) {
            if (pop != null) {
                sb.append(pop);
            } else {
                sb.append("'N/A'");
            }

            sb.append(",");
        }

        if (avgTemp.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        sb.append("]");

        return sb.toString();
    }

    public String generateSelectedWordGraph(int startYear, int endYear) {
        JDBCConnection jdbc = new JDBCConnection();

        ArrayList<getWorldPopulationEntry> WorldPopulationEntry = jdbc
                .getWorldPopulation(startYear, endYear);

        ArrayList<Double> avgTemp = new ArrayList<>();
        ArrayList<Long> population = new ArrayList<>();
        ArrayList<Integer> years = new ArrayList<>();

        for (getWorldPopulationEntry getWorldPopulationEntry : WorldPopulationEntry) {
            int year = getWorldPopulationEntry.getYear();
            long pop = getWorldPopulationEntry.getPopulation();
            double temp = getWorldPopulationEntry.getAverageTemperature();

            if (pop != 0) {
                years.add(year);
                population.add(pop);
                avgTemp.add(temp);
            } else if (temp == 0 && pop == 0) {
                years.add(year);
                population.add(null);
                avgTemp.add(null);
            } else if (temp != 0 && pop == 0) {
                years.add(year);
                population.add(null);
                avgTemp.add(temp);
            }

            else {
                years.add(year);
                population.add(pop);
                avgTemp.add(temp);
            }
        }

        String jsAvgTempArray = avgTempToStringWithNAN(avgTemp);
        String jsPopulationsArray = populationToStringWithNAN(population);
        String jsYearArray = years.toString();

        String selectedPopGraph = """
                    <div id="dataOptions">
                        <input type="radio" name="dataOption" id="populationOption" value="population" checked>
                        <label for="populationOption">Population</label>
                        <input type="radio" name="dataOption" id="temperatureOption" value="temperature">
                        <label for="temperatureOption">Average Temperature</label>
                    </div>

                    <script>
                    var ctx = document.getElementById('worldPopGraph').getContext('2d');
                    var myChart = new Chart(ctx, {
                        type: 'line',
                        data: {
                            labels: %s,
                            datasets: [{
                                label: 'Population (billion)',
                                data: %s.map(value => value / 1000000000),
                                backgroundColor: 'rgba(0, 123, 255, 0.5)',
                                borderColor: 'rgba(0, 123, 255, 1)',
                                borderWidth: 2,
                                hidden: false
                            },
                            {
                                label: 'Average Temperature (C)',
                                data: %s,
                                backgroundColor: 'rgba(255, 0, 0, 0.5)',
                                borderColor: 'rgba(255, 0, 0, 1)',
                                borderWidth: 2,
                                hidden: true
                            }]
                        },
                        options: {
                            plugins: {
                                legend: {
                                    display: false,
                                    labels: {
                                        usePointStyle: true
                                    }
                                }
                            },
                            scales: {
                                y: {
                                    beginAtZero: false,
                                    // Y-axis label
                                    title: {
                                        display: true,
                                        text: 'Population (billion)',
                                        fontSize: 20
                                    }
                                },
                                x: {
                                    // X-axis label
                                    title: {
                                        display: true,
                                        text: 'Year',
                                        fontSize: 20
                                    }
                                }
                            }
                        }
                    });

                    var dataOptionInputs = document.getElementsByName('dataOption');
                    dataOptionInputs.forEach(function(input) {
                        input.addEventListener('change', function() {
                            var selectedValue = this.value;
                            if (selectedValue === 'population') {
                                myChart.data.datasets[0].hidden = false;
                                myChart.data.datasets[1].hidden = true;
                                myChart.options.scales.y.title.text = 'Population (billion)';
                            } else if (selectedValue === 'temperature') {
                                myChart.data.datasets[0].hidden = true;
                                myChart.data.datasets[1].hidden = false;
                                myChart.options.scales.y.title.text = 'Average Temperature (C)';
                            }
                            myChart.update();
                        });
                    });
                    </script>
                """.formatted(jsYearArray, jsPopulationsArray, jsAvgTempArray);

        return selectedPopGraph;
    }

    public String generateSelectedWorldTable(JDBCConnection jdbc, int startYear,
            int endYear) {

        ArrayList<getWorldPopulationEntry> WorldPopulationEntry = jdbc
                .getWorldPopulation(startYear, endYear);

        ArrayList<Integer> years = new ArrayList<>();
        ArrayList<Long> population = new ArrayList<>();
        ArrayList<Double> averageTemp = new ArrayList<>();
        for (getWorldPopulationEntry getWorldPopulationEntry : WorldPopulationEntry) {
            years.add(getWorldPopulationEntry.getYear());
            population.add(getWorldPopulationEntry.getPopulation());
            averageTemp.add(getWorldPopulationEntry.getAverageTemperature());

        }

        String html2 = "";
        html2 = html2 + """
                <div class='worldTableDiv'>
                    <table id='worldTable' class='display'>
                    <thead>
                            <tr>
                                <th>Year</th>
                                <th>Population</th>
                                <th>Average Temperature</th>
                            </tr>
                    </thead>
                    <tbody>
                    """;

        for (int i = 0; i < years.size(); i++) {
            // get one element at a time from the arraylist
            String yearString = years.get(i).toString();
            String populationString = population.get(i).toString();
            String averageTempString = averageTemp.get(i).toString();

            if (populationString.equals("0")) {
                populationString = "N/A";
            }

            if (averageTempString.equals("0.0")) {
                averageTempString = "N/A";
            }

            html2 = html2 + """
                    <tr>
                        <td>""" + yearString + """
                        </td>
                    <td>""" + populationString + """
                                </td>
                    <td>""" + averageTempString + """
                        </tr>
                    """;
        }
        html2 = html2 + "</tbody> </table> </div>";

        return html2;
    }

}
