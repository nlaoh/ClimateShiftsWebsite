package app;

import java.util.HashMap;
import java.util.Map;
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
public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

    // Name of the Thymeleaf HTML template page in the resources folder
    private static final String TEMPLATE = ("mission.html");

    @Override
    public void handle(Context context) throws Exception {
        //Create jdbc connection and model
        Map<String, Object> model = new HashMap<String, Object>();
        JDBCConnection jdbc = new JDBCConnection();

        final String personaPrefix = "persona";
        //Get persona attributes and put to hashmap
        mapPrefixSuffix(model, jdbc.getPersonaPaths(), personaPrefix, "FilePath");

        mapPrefixSuffix(model, jdbc.getPersonaNames(), personaPrefix ,"Name");

        //Attribute1 = Description
        final String Attribute1 = "Description";
        mapPrefixSuffix(model, jdbc.getPersonaAttributesHeading(Attribute1), personaPrefix, "Attribute1");
        mapPrefixSuffix(model, jdbc.getPersonaAttributes(Attribute1),personaPrefix, "Attribute1Desc");
        
        //🚨These attribute descriptions need loops as there is a one to many relationship with a single persona 
        //Attribute2 = Needs
        final String Attribute2 = "Need";
        mapPrefixSuffix(model, jdbc.getPersonaAttributesHeading(Attribute2), personaPrefix, "Attribute2");
        for (int i = 1; i <= jdbc.getPersonaNames().size(); i++) {
            mapSinglePrefixSuffix(model, jdbc.getPersonaAttributes(i, Attribute2), personaPrefix + Integer.toString(i), "Attribute2Desc");  
        }
        
        //Attribute3 = Goals
        final String Attribute3 = "Goal";
        mapPrefixSuffix(model, jdbc.getPersonaAttributesHeading(Attribute3), personaPrefix, "Attribute3");
        for (int i = 1; i <= jdbc.getPersonaNames().size(); i++) {
            mapSinglePrefixSuffix(model, jdbc.getPersonaAttributes(i, Attribute3), personaPrefix + Integer.toString(i), "Attribute3Desc");  
        }
        
        //Attribute4 = Skills & Experience
        final String Attribute4 = "Skills & Experience";
        mapPrefixSuffix(model, jdbc.getPersonaAttributesHeading(Attribute4), personaPrefix, "Attribute4");
        for (int i = 1; i <= jdbc.getPersonaNames().size(); i++) {
            mapSinglePrefixSuffix(model, jdbc.getPersonaAttributes(i, Attribute4), personaPrefix + Integer.toString(i), "Attribute4Desc");  
        }

        //Authors 
        mapPrefixSuffix(model, jdbc.getAuthorName(), "author", "Name");
        mapPrefixSuffix(model, jdbc.getStudentID(), "author", "StudentID");
        mapPrefixSuffix(model, jdbc.getAuthorEmail(), "author", "Email");
        for (int i = 1; i <= jdbc.getStudentID().size(); i++) {
            mapSinglePrefixSuffix(model, jdbc.getAuthorRole(jdbc.getStudentID().get(i - 1)), "author" + i, "Roles");  
        }

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage using Thymeleaf
        context.render(TEMPLATE, model);
    }
    /**
     * Maps an arraylist of strings using a prefix and suffix
     * @param model
     * @param list
     * @param prefix
     * @param suffix
     * @return
     */
    private Map<String, Object> mapPrefixSuffix(Map<String, Object> model, ArrayList<String> list, String prefix, String suffix ) {
        int i = 1;
        for (String currentListItem : list) {
            String reference = prefix + i + suffix;
            model.put(reference, currentListItem);
            i++;
        }
        return model;
    }
    private Map<String, Object> mapSinglePrefixSuffix(Map<String, Object> model, ArrayList<String> list, String prefix, String suffix ) {
        String reference = prefix + suffix; 
        model.put(reference, list);
        return model;
    }

}
