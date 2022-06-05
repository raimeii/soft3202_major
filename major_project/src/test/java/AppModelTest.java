import majorproject.model.AppModel;
import majorproject.model.AppModelImpl;
import majorproject.model.guardianhandler.GuardianHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AppModelTest {

    private AppModel fixture;

    @AfterEach
    public void teardownFixture() {
        fixture = null;
    }

    @Test
    public void testOfflineTagSearch() {
        fixture = new AppModelImpl(false, false);
        ArrayList<String> testList = fixture.searchMatchingTags("input that doesnt matter");
        assertEquals(testList.size(), 3);
    }

    @Test
    public void testOfflineResultWithTagSearch() {
        fixture = new AppModelImpl(false, false);
        ArrayList<String> testList = fixture.getResultsWithTagAPI("input that doesnt matter");
        assertEquals(testList.size(), 4);
    }

    @Test
    public void testOfflineGetContentURL() {
        fixture = new AppModelImpl(false, false);
        assertEquals(fixture.getContentURL("anyt title that doesnt matter"), "https://youtu.be/dQw4w9WgXcQ");
    }

    @Test
    public void testOfflineHasTagResponseStored() {
        fixture = new AppModelImpl(false, false);
        ArrayList<String> testList = fixture.searchMatchingTags("input that doesnt matter");
        assertFalse(fixture.hasTagResponseStored());
        assertEquals(3, testList.size());
    }

    @Test
    public void testOnlineTagSearch() {
        fixture = mock(AppModelImpl.class);
        when(fixture.searchMatchingTags(anyString())).thenReturn(new ArrayList<>(List.of("oneTag")));
        ArrayList<String> testList = fixture.searchMatchingTags("input that doesnt matter");
        assertEquals(testList.size(), 1);
    }

    @Test
    public void testOnlineResultWithTagSearch() {
        fixture = mock(AppModelImpl.class);
        when(fixture.getResultsWithTagAPI(anyString())).thenReturn(new ArrayList<>(List.of("oneResult", "twoResult")));
        ArrayList<String> testList = fixture.getResultsWithTagAPI("input that doesnt matter");
        assertEquals(testList.size(), 2);
    }

    @Test
    public void testOnlineGetContentURL() {
        fixture = mock(AppModelImpl.class);
        String pretend = "pretend this is a url";
        when(fixture.getContentURL(anyString())).thenReturn(pretend);
        assertEquals(fixture.getContentURL("any title that doesnt matter"), pretend);
    }

    @Test
    public void testBuildOutputReportMocked() {
        fixture = mock(AppModelImpl.class);
        String pretend = "pretend this is the output report";
        when(fixture.buildOutputReport()).thenReturn(pretend);
        assertEquals(fixture.buildOutputReport(), pretend);
    }

    @Test
    public void testBuildOutputReport() {
        fixture = new AppModelImpl(false, false);
        fixture.setCurrentTag("University");
        List<String> testVals = Arrays.asList("USYD", "UTS", "UNSW", "MCQ", "WSU");
        fixture.setResultMatches(testVals);
        String generated = fixture.buildOutputReport();

        String expected = "Tag: University\nUSYD\nUTS\nUNSW\nMCQ\nWSU\n";

        assertEquals(generated, expected);

    }


    @Test
    public void generateOutputReportTestOffline() {
        fixture = new AppModelImpl(false, false);
        assertEquals("", fixture.generateOutputReport());
    }
    @Test
    public void generateOutputReportTestAllNullsOnline() {
        fixture = new AppModelImpl(true, true);
        fixture.setTagMatches(null);
        fixture.setResultMatches(null);
        fixture.setCurrentTag(null);
        assertEquals("", fixture.generateOutputReport());
    }

    /*all offline guardian handler logic is tested in any test that uses an offline API, so there is no test class for
        it specifically
    * */
    @Test
    public void callAPIWhenOffline() {
        fixture = new AppModelImpl(false, false);
        GuardianHandler gh = fixture.getGuardianHandler();

        ArrayList<String> resultsWithTagAPI = gh.getResultsWithTagAPI("some string");
        assertEquals(4, resultsWithTagAPI.size());

        ArrayList<String> resultsWithTagDB = fixture.getResultsWithTagDB("some tag");
        assertEquals(4, resultsWithTagDB.size());

        assertFalse(fixture.checkTagExistsInDatabase("doesnt matter"));
    }
}
