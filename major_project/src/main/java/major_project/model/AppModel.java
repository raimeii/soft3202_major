package major_project.model;

import java.util.ArrayList;
import java.util.List;

public class AppModel {
    private final GuardianHandler guardianHandler;

    private final PastebinHandler pastebinHandler = new PastebinHandler();

    private final boolean inputOnline;

    private final boolean outputOnline;

    private List<String> tagMatches;

    private List<String> resultMatches;

    private boolean audioPlaying = true;

    private final Database database;



    public AppModel(boolean inputOnline, boolean outputOnline) {
        this.inputOnline = inputOnline;
        this.outputOnline = outputOnline;

        if (inputOnline) {
            database = new Database();
            database.createDatabase();
            database.setupDatabase();
        } else {
            database = null;
        }
        guardianHandler = new GuardianHandler(database);
    }

    private String currentTag = null;

    public void setCurrentTag(String tag) {
        this.currentTag = tag;
    }

    public String getCurrentTag() {
        return this.currentTag;
    }

    public boolean checkTagExistsInDatabase(String input) {
        if (inputOnline) {
            return database.queryCheckTagExists(input);
        } else {
            return false;
        }
    }

    //api search
    public ArrayList<String> searchMatchingTags(String input) {
        //basically return the list of ResultsPOJO's id tag, use web-title link
        if (inputOnline) {
            return guardianHandler.getMatchingTags(input);
        } else {
            return new ArrayList<>(List.of("testTag1" , "testTag2", "testTag3"));
        }
    }
    //database search

    public ArrayList<String> getResultsWithTagAPI(String tag) {
        //use q link
        if (inputOnline) {
            return guardianHandler.getResultsWithTagAPI(tag);
        } else {
            return new ArrayList<>(List.of("testResult1" , "testResult2", "testResult3", "testResult4"));
        }
    }

    public ArrayList<String> getResultsWithTagDB(String tag) {
        //use q link
        if (inputOnline) {
            return guardianHandler.getResultsWithTagDB(tag);
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

    public void clearDatabaseCache() {
        database.clearCache();
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

    public String getMusicResource() {
        return getClass().getClassLoader().getResource("amelia_watson_bgm.mp3").toString();
    }

    public boolean isAudioPlaying() {
        return audioPlaying;
    }

    public void setAudioPlaying(boolean audioPlaying) {
        this.audioPlaying = audioPlaying;
    }

}
