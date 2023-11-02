package app;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

/**
 * Main Application Class.
 * <p>
 * Running this class as regular java application will start the
 * Javalin HTTP Server and our web application.
 *
 * @author Timothy Wiley, 2023. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class App {

    public static final int JAVALIN_PORT = 7001;
    public static final String CSS_DIR = "css/";
    public static final String IMAGES_DIR = "images/";

    public static void main(String[] args) {
        // Create our HTTP server and listen in port 7000
        Javalin app = Javalin.create(config -> {
            config.registerPlugin(new RouteOverviewPlugin("/help/routes"));

            // Uncomment this if you have files in the CSS Directory
            config.addStaticFiles(CSS_DIR);

            // Uncomment this if you have files in the Images Directory
            config.addStaticFiles(IMAGES_DIR);
        }).start(JAVALIN_PORT);

        // Configure Web Routes
        configureRoutes(app);
    }

    public static void configureRoutes(Javalin app) {
        // All webpages are listed here as GET pages
        app.get(PageIndex.URL, new PageIndex());
        app.get(PageMission.URL, new PageMission());
        app.get(PageShallowGlanceC.URL, new PageShallowGlanceC());
        app.get(PageShallowGlanceW.URL, new PageShallowGlanceW());
        app.get(PageShallowGlanceT2.URL, new PageShallowGlanceT2());
        app.get(PageDeepDive.URL, new PageDeepDive());
        app.get(PageDeepDive2.URL, new PageDeepDive2());

        // Add / uncomment POST commands for any pages that need web form POSTS
        app.post(PageIndex.URL, new PageIndex());
        app.post(PageShallowGlanceC.URL, new PageShallowGlanceC());
        app.post(PageShallowGlanceW.URL, new PageShallowGlanceW());
        app.post(PageShallowGlanceT2.URL, new PageShallowGlanceT2());
        app.post(PageDeepDive.URL, new PageDeepDive());
        app.post(PageDeepDive2.URL, new PageDeepDive2());

    }

}
