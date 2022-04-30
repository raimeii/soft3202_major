package major_project.model;

import java.util.ArrayList;

public class AppModel {
    private final GuardianHandler guardianHandler = new GuardianHandler();

    private final PastebinHandler pastebinHandler = new PastebinHandler();

    private final boolean inputOnline;

    private final boolean outputOnline;

    public AppModel(boolean inputOnline, boolean outputOnline) {
        this.inputOnline = inputOnline;
        this.outputOnline = outputOnline;
    }

    private String currentTag = null;

    public void setCurrentTag(String tag) {
        this.currentTag = tag;
    }

    public String getCurrentTag() {
        return this.currentTag;
    }

    public ArrayList<String> searchMatchingTags(String input) {
        //basically return the list of ResultsPOJO's id tag
        return null;
    }

    public ArrayList<String> getResultsWithTag(String tag) {
        return null;
    }



    public String getContentURL(String title) {
        return "https://www.youtube.com/watch?v=hPzFwQyrD8A&ab_channel=PREP";
    }
}
