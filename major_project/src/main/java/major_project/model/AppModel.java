package major_project.model;

import java.util.ArrayList;
import java.util.List;

public class AppModel {
    private final GuardianHandler guardianHandler = new GuardianHandler();

    private final PastebinHandler pastebinHandler = new PastebinHandler();

    private final boolean inputOnline;

    private final boolean outputOnline;

    private List<String> tagMatches;

    private List<String> resultMatches;

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
            return new ArrayList<>(List.of("testResult1" , "testResult2", "testResult3", "testResult4"));
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


    public String buildOutputReport() {
        StringBuilder sb = new StringBuilder();
        String tag = String.format("Tag: %s\n", currentTag);
        sb.append(tag);

        for (String res: resultMatches) {
            String n = String.format("%s\n", res);
            sb.append(n);
        }

        return sb.toString();
    }

    public String generateOutputReport() throws IllegalStateException {
        if (tagMatches == null || resultMatches == null || currentTag == null) {
            return null;
        }
        if (outputOnline) {
            return pastebinHandler.generateOutputReport(this, buildOutputReport());
        } else {
            return null;
        }
    }

    public List<String> getTagMatches() {
        return tagMatches;
    }

    public void setTagMatches(List<String> tagMatches) {
        this.tagMatches = tagMatches;
    }

    public List<String> getResultMatches() {
        return resultMatches;
    }

    public void setResultMatches(List<String> resultMatches) {
        this.resultMatches = resultMatches;
    }
}
