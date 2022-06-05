package majorproject.model;

import majorproject.model.database.Database;
import majorproject.model.database.OfflineDatabaseImpl;
import majorproject.model.database.OnlineDatabaseImpl;
import majorproject.model.guardianhandler.GuardianHandler;
import majorproject.model.guardianhandler.OfflineGuardianHandlerImpl;
import majorproject.model.guardianhandler.OnlineGuardianHandlerImpl;
import majorproject.model.pastebinhandler.OfflinePastebinHandlerImpl;
import majorproject.model.pastebinhandler.PastebinHandler;
import majorproject.model.pastebinhandler.OnlinePastebinHandlerImpl;
import majorproject.model.resourcehandler.ResourceHandler;
import majorproject.model.resourcehandler.ResourceHandlerImpl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class AppModelImpl implements AppModel {
    /**
     * GuardianHandler in charge of API calls to the guardian API
     */
    private final GuardianHandler guardianHandler;

    /**
     * PastebinHandler in charge of API calls to pastebin API
     */
    private final PastebinHandler pastebinHandler;

    /**
     * Resource handler in charge of setting up the music resource
     */

    private final ResourceHandler resourceHandler = new ResourceHandlerImpl();

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
            database = new OnlineDatabaseImpl();
            database.createDatabase();
            database.setupDatabase();
            guardianHandler = new OnlineGuardianHandlerImpl(database);

        } else {
            database = new OfflineDatabaseImpl();
            guardianHandler = new OfflineGuardianHandlerImpl();
        }

        if (outputOnline) {
            pastebinHandler = new OnlinePastebinHandlerImpl();
        } else {
            pastebinHandler = new OfflinePastebinHandlerImpl();
        }
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
        try {
            return guardianHandler.getMatchingTags(input);
        } catch (InvalidParameterException e){
            return new ArrayList<>();
        }
    }
    //database search
    @Override
    public ArrayList<String> getResultsWithTagAPI(String tag) {
        try {
            return guardianHandler.getResultsWithTagAPI(tag);
        } catch (InvalidParameterException e) {
            return new ArrayList<>();
        }
    }
    @Override
    public ArrayList<String> getResultsWithTagDB(String tag) {
        try {
            return guardianHandler.getResultsWithTagDB(tag);
        } catch (InvalidParameterException e) {
            return new ArrayList<>();
        }
    }
    @Override
    public String getContentURL(String title) {
        try {
            return guardianHandler.getURL(title);
        } catch (InvalidParameterException e) {
            return "";
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
        if (tagMatches == null || resultMatches == null || currentTag == null) {
            return "";
        }
        try {
            return pastebinHandler.generateOutputReport(buildOutputReport());
        } catch (InvalidParameterException e)  {
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
        return resourceHandler.getMusicResource();
    }

    @Override
    public boolean isAudioPlaying() {
        return resourceHandler.isAudioPlaying();
    }

    @Override
    public void setAudioPlaying(boolean audioPlaying) {
        resourceHandler.setAudioPlaying(audioPlaying);
    }

    @Override
    public GuardianHandler getGuardianHandler() {
        return guardianHandler;
    }
}
