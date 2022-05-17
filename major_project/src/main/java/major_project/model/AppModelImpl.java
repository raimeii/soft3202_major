package major_project.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppModelImpl implements AppModel {
    /**
     * GuardianHandler in charge of API calls to the guardian API
     */
    private final GuardianHandler guardianHandler;

    /**
     * PastebinHandler in charge of API calls to pastebin API
     */
    private final PastebinHandler pastebinHandler = new PastebinHandlerImpl();

    /**
     * status of input api
     */
    private final boolean inputOnline;

    /**
     * status of output api
     */
    private final boolean outputOnline;

    /**
     * List of string representation of partial tag matches
     */
    private List<String> tagMatches;

    /**
     * List of string representation of result matches
     */
    private List<String> resultMatches;

    /**
     * state of audio (playing/paused)
     */
    private boolean audioPlaying = true;

    /**
     * Database handler to process SQL calls when caching
     */
    private final Database database;

    /**
     * Current tag that the model is working with
     */
    private String currentTag = null;



    public AppModelImpl(boolean inputOnline, boolean outputOnline) {
        this.inputOnline = inputOnline;
        this.outputOnline = outputOnline;

        if (inputOnline) {
            database = new DatabaseImpl();
            database.createDatabase();
            database.setupDatabase();
        } else {
            database = null;
        }
        guardianHandler = new GuardianHandlerImpl(database);
    }


    @Override
    public void setCurrentTag(String tag) {
        this.currentTag = tag;
    }

    @Override
    public String getCurrentTag() {
        return this.currentTag;
    }

    @Override
    public boolean checkTagExistsInDatabase(String input) {
        if (inputOnline) {
            return database.queryCheckTagExists(input);
        } else {
            return false;
        }
    }

    @Override
    public ArrayList<String> searchMatchingTags(String input) {
        //basically return the list of ResultsPOJO's id tag, use web-title link
        if (inputOnline) {
            try {
                return guardianHandler.getMatchingTags(input);
            } catch (InvalidParameterException e){
                return new ArrayList<>();
            }

        } else {
            return new ArrayList<>(List.of("testTag1" , "testTag2", "testTag3"));
        }
    }
    //database search
    @Override
    public ArrayList<String> getResultsWithTagAPI(String tag) {
        //use q link
        if (inputOnline) {
            try {
                return guardianHandler.getResultsWithTagAPI(tag);
            } catch (InvalidParameterException | IllegalStateException e) {
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>(List.of("testResult1" , "testResult2", "testResult3", "testResult4"));
        }
    }
    @Override
    public ArrayList<String> getResultsWithTagDB(String tag) {
        //use q link
        if (inputOnline) {
            try {
                return guardianHandler.getResultsWithTagDB(tag);
            } catch (InvalidParameterException | IllegalStateException e) {
                return new ArrayList<>();
            }

        } else {
            return new ArrayList<>(List.of("testResult1" , "testResult2", "testResult3", "testResult4"));
        }
    }
    @Override
    public String getContentURL(String title) {
        if (inputOnline) {
            try {
                return guardianHandler.getURL(title);
            } catch (InvalidParameterException e) {
                return "";
            }

        } else {
            return "https://youtu.be/dQw4w9WgXcQ";
        }
    }
    @Override
    public boolean hasTagResponseStored() {
        return guardianHandler.getCurrentTagResponse() != null;
    }

    @Override
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
    @Override
    public String generateOutputReport() {
        if (outputOnline) {
            if (tagMatches == null || resultMatches == null || currentTag == null) {
                return "";
            }
            try {
                return pastebinHandler.generateOutputReport(buildOutputReport());
            } catch (InvalidParameterException e)  {
                return "";
            }
        } else {
            return "";
        }
    }
    @Override
    public void clearDatabaseCache() {
        if (database != null) {
            database.clearCache();
        }
    }
    @Override
    public List<String> getTagMatches() {
        return tagMatches;
    }
    @Override
    public void setTagMatches(List<String> tagMatches) {
        this.tagMatches = tagMatches;
    }
    @Override
    public List<String> getResultMatches() {
        return resultMatches;
    }
    @Override
    public void setResultMatches(List<String> resultMatches) {
        this.resultMatches = resultMatches;
    }
    @Override
    public String getMusicResource() {
        try {
            return Objects.requireNonNull(getClass().getClassLoader().getResource("amelia_watson_bgm.mp3")).toString();
        } catch (NullPointerException e) {
            return null;
        }

    }
    @Override
    public boolean isAudioPlaying() {
        return audioPlaying;
    }
    @Override
    public void setAudioPlaying(boolean audioPlaying) {
        this.audioPlaying = audioPlaying;
    }
    @Override
    public GuardianHandler getGuardianHandler() {
        return guardianHandler;
    }
}
