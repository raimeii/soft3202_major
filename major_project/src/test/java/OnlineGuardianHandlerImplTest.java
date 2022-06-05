import majorproject.model.guardianhandler.GuardianHandler;
import majorproject.model.guardianhandler.OnlineGuardianHandlerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OnlineGuardianHandlerImplTest {

    @Test
    public void getMatchingTagsNull() {
        GuardianHandler gh = new OnlineGuardianHandlerImpl(null);

        InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
            gh.getMatchingTags(null);
        });

        assertEquals("Tag parameter is null", thrown.getMessage());
    }

    @Test
    public void getMatchingTagsMocked() {
        GuardianHandler gh = mock(OnlineGuardianHandlerImpl.class);
        when(gh.getMatchingTags(anyString())).thenReturn(new ArrayList<>(List.of("oneTag")));
        ArrayList<String> mockRes = gh.getMatchingTags("Zim zalabim");
        assertEquals(mockRes.size(), 1);
        assertEquals(mockRes.get(0), "oneTag");
    }
    @Test
    public void getResultsWithTagAPINull() {
        GuardianHandler gh = new OnlineGuardianHandlerImpl(null);
        InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
            gh.getResultsWithTagAPI(null);
        });

        assertEquals("Tag parameter is null", thrown.getMessage());
    }

    @Test
    public void getResultsWithTagAPIMocked() {
        GuardianHandler gh = mock(OnlineGuardianHandlerImpl.class);
        when(gh.getResultsWithTagAPI(anyString())).thenReturn(new ArrayList<>(List.of("oneResult", "twoResult")));
        ArrayList<String> mockRes = gh.getResultsWithTagAPI("custom keyboards are really cool!");
        assertEquals(mockRes.size(), 2);
        assertEquals(mockRes.get(1), "twoResult");
    }

    @Test
    public void getResultsWithTagDBNull() {
        GuardianHandler gh = new OnlineGuardianHandlerImpl(null);
        InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
            gh.getResultsWithTagDB(null);
        });

        assertEquals("Tag parameter is null", thrown.getMessage());
    }

    @Test
    public void getResultsWithTagDBMocked() {
        GuardianHandler gh = mock(OnlineGuardianHandlerImpl.class);
        when(gh.getResultsWithTagAPI(anyString())).thenReturn(new ArrayList<>(List.of("oneResult", "twoResult", "threeResult", "4")));
        ArrayList<String> mockRes = gh.getResultsWithTagDB("custom keyboards are really cool! and really expensive :(");
        assertEquals(mockRes.size(), 2);
        assertEquals(mockRes.get(3), "4");
    }

    @Test
    public void getURLNull() {
        GuardianHandler gh = new OnlineGuardianHandlerImpl(null);

        InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
            gh.getURL(null);
        });

        assertEquals("Web title is null", thrown.getMessage());
    }

    @Test
    public void getURLMocked() {
        GuardianHandler gh = mock(OnlineGuardianHandlerImpl.class);
        String url = "this is definitely a url";
        when(gh.getURL(anyString())).thenReturn(url);
        assertEquals(gh.getURL("rick astley rickroll hehe"), url);
    }
}
