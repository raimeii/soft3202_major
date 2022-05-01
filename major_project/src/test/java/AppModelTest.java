import major_project.model.AppModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        fixture = new AppModel(false, false);
        ArrayList<String> testList = fixture.searchMatchingTags("input that doesnt matter");
        assertEquals(testList.size(), 3);
    }

    @Test
    public void testOfflineResultWithTagSearch() {
        fixture = new AppModel(false, false);
        ArrayList<String> testList = fixture.getResultsWithTag("input that doesnt matter");
        assertEquals(testList.size(), 4);
    }

    @Test
    public void testOfflineGetContentURL() {
        fixture = new AppModel(false, false);
        assertEquals(fixture.getContentURL("anyt title that doesnt matter"), "https://youtu.be/dQw4w9WgXcQ");
    }

    @Test
    public void testOfflineHasTagResponseStored() {
        fixture = new AppModel(false, false);
        ArrayList<String> testList = fixture.searchMatchingTags("input that doesnt matter");
        assertFalse(fixture.hasTagResponseStored());
    }

    @Test
    public void testOnlineTagSearch() {
        fixture = mock(AppModel.class);
        when(fixture.searchMatchingTags(anyString())).thenReturn(new ArrayList<>(List.of("oneTag")));
        ArrayList<String> testList = fixture.searchMatchingTags("input that doesnt matter");
        assertEquals(testList.size(), 1);
    }

    @Test
    public void testOnlineResultWithTagSearch() {
        fixture = mock(AppModel.class);
        when(fixture.getResultsWithTag(anyString())).thenReturn(new ArrayList<>(List.of("oneResult", "twoResult")));
        ArrayList<String> testList = fixture.getResultsWithTag("input that doesnt matter");
        assertEquals(testList.size(), 2);
    }

    @Test
    public void testOnlineGetContentURL() {
        fixture = mock(AppModel.class);
        String pretend = "pretend this is a url";
        when(fixture.getContentURL(anyString())).thenReturn(pretend);
        assertEquals(fixture.getContentURL("anyt title that doesnt matter"), pretend);
    }
}
