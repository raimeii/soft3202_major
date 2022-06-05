package majorproject.model.guardianhandler;

import majorproject.model.pojos.GuardianPOJO;

import java.util.ArrayList;
import java.util.List;

/**
 * Offline implementation of the GuardianHandler interface, only returns test lists and a link to a predetermined
 * youtube video
 */
public class OfflineGuardianHandlerImpl implements GuardianHandler{

    @Override
    public ArrayList<String> getMatchingTags(String tag) {
        return new ArrayList<>(List.of("testTag1" , "testTag2", "testTag3"));
    }

    @Override
    public ArrayList<String> getResultsWithTagAPI(String tag) {
        return new ArrayList<>(List.of("testResult1" , "testResult2", "testResult3", "testResult4"));
    }

    @Override
    public ArrayList<String> getResultsWithTagDB(String tag) {
        return new ArrayList<>(List.of("testResult1" , "testResult2", "testResult3", "testResult4"));
    }

    @Override
    public String getURL(String title) {
        return "https://youtu.be/dQw4w9WgXcQ";
    }

    @Override
    public GuardianPOJO getCurrentTagResponse() {
        return null;
    }
}