package app;

import java.util.ArrayList;
import java.util.List;
import app.JDBCConnection.PopulationEntry;
import app.JDBCConnection.TemperatureEntry;

import java.text.DecimalFormat;

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
public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {

        JDBCConnection jdbc = new JDBCConnection();

        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" +
                "<title>Homepage</title>";

        // Add some CSS (external file)
        html = html + "<head>" +
                "<title>Homepage</title>" +
                "<script src='https://cdn.jsdelivr.net/npm/chart.js@4.3.0/dist/chart.umd.min.js'></script>" +
                "<link rel='stylesheet' type='text/css' href='common.css' />" +
                "<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>"
                +
                "</head>";

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
        html = html + """
                <div class='topnav'>
                    <a class='currentPage' href='/'>Homepage</a>
                    <a href='mission.html'>Our Mission</a>
                    <div class='dropdownNavbar'>
                        <a class='dropbtn'>View Changes By <i class='fa fa-solid fa-angle-down'></i></a>
                        <div class='dropdownNavbar-content'>
                            <a href='ShallowGlanceC.html'>Temperature and population change by Country/World</a>
                            <a href='ShallowGlanceT2.html'>Temperature change by City/State</a>
                            <a href='DeepDive.html'>Temperature over extended periods</a>
                            <a href='DeepDive2.html'>Time periods with similar temperature and/or population</a>
                        </div>
                    </div>
                </div>
                """;

        // Add header content block
        html = html + """
                    <div class='titlebar'>
                        <div id='welcomeHeader' class='header'>
                            <h1>Welcome to Climate Shifts!</h1>
                            <h2>Explore climate change data using the navigation bar at the top of the page!</h2>
                        </div>
                    </div>
                """;

        html = html + "<div class='grid-container'>";
        // box 1
        html = html + "<div class='box box1'>";
        html = html + "<h2>Global Population</h2>";

        // getting the first and last year of the data
        long[] yearPop = jdbc.getFirstAndLastYearsPopulation();
        long firstYearPop = yearPop[0];
        long lastYearPop = yearPop[1];
        long firstYearPopData = yearPop[2];
        long lastYearPopData = yearPop[3];

        // Formatting number using DecimalFormat class
        DecimalFormat commaSeparatedFormat = new DecimalFormat("#,###");
        String formattedFirstYearPopData = commaSeparatedFormat.format(firstYearPopData);
        String formattedLastYearPopData = commaSeparatedFormat.format(lastYearPopData);

        // box 1
        html = html + "<p>Year Range: " + firstYearPop + " - " + lastYearPop + "</p>";
        html = html + "<p>Data available for " + jdbc.totalNumOfYearsPopulation() + " Years</p>";
        html = html + "<p>Population in " + firstYearPop + ": " + formattedFirstYearPopData + "</p>";
        html = html + "<p>Population in " + lastYearPop + ": " + formattedLastYearPopData + "</p>";
        html = html + "</div>";

        // getting the first and last year of the data
        double[] yearTemp = jdbc.getFirstAndLastYearsTemperature();
        double firstYearTemp = yearTemp[0];
        double lastYearTemp = yearTemp[1];
        double firstYearTempData = yearTemp[2];
        double lastYearTempData = yearTemp[3];

        // Round average temp data to two decimal places
        DecimalFormat roundedFormat = new DecimalFormat("0.00");
        String roundedFirstYearTempData = roundedFormat.format(firstYearTempData);
        String roundedLastYearTempData = roundedFormat.format(lastYearTempData);

        // box 2
        html = html + "<div class='box box2'>";
        html = html + "<h2>Global Temperature</h2>";
        html = html + "<p>Year Range: " + Math.round(firstYearTemp) + " - " + Math.round(lastYearTemp) + "</p>";
        html = html + "<p>Data available for " + jdbc.totalNumOfYearsTemp() + " Years</p>";
        html = html + "<p>Temperature in " + Math.round(firstYearTemp) + ": " + roundedFirstYearTempData + " &#8451 "
                + "</p>";
        html = html + "<p>Temperature in " + Math.round(lastYearTemp) + ": " + roundedLastYearTempData + " &#8451 "
                + "</p>";
        html = html + "</div>";

        List<PopulationEntry> populationData = jdbc.getYearsAndPopulations();
        String popGraph = generatePopulationGraph(populationData);

        List<TemperatureEntry> TemperatureData = jdbc.getYearsAndTemperature();
        String tempGraph = generateTemperatureGraph(TemperatureData);

        // JavaScript code to show and hide the graphs
        String script = """
                    <script>
                    function showPopGraph() {
                        document.getElementById("popGraph").style.display = "block";
                        document.getElementById("tempGraph").style.display = "none";
                        document.getElementById("graphHeading").innerText = "Global Population Graph";

                        // Adds the active-button class to the population graph button
                        document.getElementById("popGraphButton").classList.add("active-button");
                        // Removes the active-button class from the temperature graph button
                        document.getElementById("tempGraphButton").classList.remove("active-button");
                    }
                    function showTempGraph() {
                        document.getElementById("popGraph").style.display = "none";
                        document.getElementById("tempGraph").style.display = "block";
                        document.getElementById("graphHeading").innerText = "Global Temperature Graph";

                        // Adds the active-button class to the temperature graph button
                        document.getElementById("tempGraphButton").classList.add("active-button");
                        // Removes the active-button class from the population graph button
                        document.getElementById("popGraphButton").classList.remove("active-button");
                    }
                    </script>
                """;

        html = html + script;

        // Graph container
        html = html + "<div class='graph'>";

        html = html + "<div class='heading-container'>";
        html = html + "<h2 id='graphHeading'>Global Population Graph</h2>";
        html = html + "</div>";

        // Population graph
        html = html + "<canvas id='popGraph' width='600' height='400'></canvas>";

        // Temperature graph
        html = html + "<canvas id='tempGraph' width='600' height='400' style='display: none;'></canvas>";

        html = html + popGraph; // Add the population graph script
        html = html + tempGraph; // Add the temperature graph script

        // Buttons to switch between graphs
        html = html + "<div class='buttons-container'>";
        html = html
                + "<div class='button'><button class='active-button' id='popGraphButton' onclick='showPopGraph()'>Population Graph</button></div>";
        html = html
                + "<div class='button'><button id='tempGraphButton' onclick='showTempGraph()'>Temperature Graph</button></div>";
        html = html + "</div>";
        html = html + "</div>";

        // closes grid container div
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

    public String generatePopulationGraph(List<PopulationEntry> populationData) {
        List<Long> years = new ArrayList<>();
        List<Long> populations = new ArrayList<>();

        for (PopulationEntry entry : populationData) {
            years.add(entry.year);
            populations.add(entry.population);
        }

        // Convert the data arrays to JavaScript arrays
        String jsYearsArray = years.toString();
        String jsPopulationsArray = populations.toString();

        // JavaScript code to generate the chart using Chart.js
        String popGraph = """
                    <script>
                    var ctx = document.getElementById('popGraph').getContext('2d');
                    var myChart = new Chart(ctx, {
                    type: 'line',
                    data: {
                        labels: %s,
                        datasets: [{
                        label: 'Population (billions)',
                        data: %s.map(value => value / 1000000000), // Divide population values by 1 billion
                        backgroundColor: 'rgba(0, 123, 255, 0.5)',
                        borderColor: 'rgba(0, 123, 255, 1)',
                        borderWidth: 2
                        }]
                    },
                    options: {
                        plugins: {
                            legend: {
                                display: false // Hide the legend (population button)
                            }
                        },
                        scales: {
                            y: {
                                beginAtZero: false,
                                // Y-axis label
                                title: {
                                    display: true,
                                    text: 'Population (billions)',
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
                    </script>
                """.formatted(jsYearsArray, jsPopulationsArray);

        return popGraph;
    }

    public String generateTemperatureGraph(List<TemperatureEntry> temperatureData) {
        List<Integer> years = new ArrayList<>();
        List<Double> temperatures = new ArrayList<>();

        for (TemperatureEntry entry : temperatureData) {
            years.add(entry.year);
            temperatures.add(entry.temperature);
        }

        // Convert the data arrays to JavaScript arrays
        String jsYearsArray = years.toString();
        String jsTemperatureArray = temperatures.toString();

        // JavaScript code to generate the chart using Chart.js
        String tempGraph = """
                    <script>
                    var ctx = document.getElementById('tempGraph').getContext('2d');
                    var myChart = new Chart(ctx, {
                    type: 'line',
                    data: {
                        labels: %s,
                        datasets: [{
                        label: 'Temperature (Celsius)',
                        data: %s,
                        backgroundColor: 'rgba(255, 0, 0, 0.5)',
                        borderColor: 'rgba(255, 0, 0, 0.5)',
                        borderWidth: 2
                        }]
                    },
                    options: {
                        plugins: {
                            legend: {
                                display: false // Hide the legend (population button)
                            }
                        },
                        scales: {
                            y: {
                                beginAtZero: false,
                                // Y-axis label
                                title: {
                                    display: true,
                                    text: 'Temperature (Celsius)',
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
                    </script>
                """.formatted(jsYearsArray, jsTemperatureArray);

        return tempGraph;
    }
}
