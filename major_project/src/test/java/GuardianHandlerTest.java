import major_project.model.AppModel;
import major_project.model.GuardianHandler;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GuardianHandlerTest {

    @Test
    public void getMatchingTagsNull() {
        GuardianHandler gh = new GuardianHandler();

        assertNull(gh.getMatchingTags(null));
    }

    @Test
    public void getMatchingTagsMocked() {
        GuardianHandler gh = mock(GuardianHandler.class);
        when(gh.getMatchingTags(anyString())).thenReturn(new ArrayList<>(List.of("oneTag")));
        ArrayList<String> mockRes = gh.getMatchingTags("Zim zalabim");
        assertEquals(mockRes.size(), 1);
        assertEquals(mockRes.get(0), "oneTag");
    }
    @Test
    public void getResultsWithTagNull() {
        GuardianHandler gh = new GuardianHandler();
        assertNull(gh.getResultsWithTag(null));
    }

    @Test
    public void getResultsWithTagMocked() {
        GuardianHandler gh = mock(GuardianHandler.class);
        when(gh.getResultsWithTag(anyString())).thenReturn(new ArrayList<>(List.of("oneResult", "twoResult")));
        ArrayList<String> mockRes = gh.getResultsWithTag("custom keyboards are really cool!");
        assertEquals(mockRes.size(), 2);
        assertEquals(mockRes.get(1), "twoResult");
    }

    @Test
    public void getURLNull() {
        GuardianHandler gh = new GuardianHandler();
        assertNull(gh.getURL(null));
    }

    @Test
    public void getURLMocked() {
        GuardianHandler gh = mock(GuardianHandler.class);
        String url = "this is definitely a url";
        when(gh.getURL(anyString())).thenReturn(url);
        assertEquals(gh.getURL("rick astley rickroll hehe"), url);
    }

}
