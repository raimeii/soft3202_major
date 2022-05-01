package major_project.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        //basically return the list of ResultsPOJO's id tag, use web-title link
        if (inputOnline) {
            return guardianHandler.getMatchingTags(input);
        } else {
            return new ArrayList<>(List.of("testTag1" , "testTag2", "testTag3"));
        }
    }

    public ArrayList<String> getResultsWithTag(String tag) {
        //use q link
        if (inputOnline) {
            return guardianHandler.getResultsWithTag(tag);
        } else {
            return new ArrayList<>(List.of("testResult1" , "testResult2", "testResult3"));
        }
    }

    public String getContentURL(String title) {
        if (inputOnline) {
            return guardianHandler.getURL(title);
        } else {
            return "https://youtu.be/dQw4w9WgXcQ";
        }
    }

    public boolean hasTagResponseStored() {
        return guardianHandler.getCurrentTagResponse() != null;
    }

    public String generateOutputReport() {
        if (outputOnline) {
            return PastebinHandler.generateOutputReport(this);
        } else {
            return null;
        }
    }
}
