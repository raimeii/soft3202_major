import major_project.model.GuardianHandler;
import major_project.model.GuardianHandlerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GuardianHandlerImplTest {

    @Test
    public void getMatchingTagsNull() {
        GuardianHandler gh = new GuardianHandlerImpl(null);

        InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
            gh.getMatchingTags(null);
        });

        assertEquals("Tag parameter is null", thrown.getMessage());
    }

    @Test
    public void getMatchingTagsMocked() {
        GuardianHandler gh = mock(GuardianHandlerImpl.class);
        when(gh.getMatchingTags(anyString())).thenReturn(new ArrayList<>(List.of("oneTag")));
        ArrayList<String> mockRes = gh.getMatchingTags("Zim zalabim");
        assertEquals(mockRes.size(), 1);
        assertEquals(mockRes.get(0), "oneTag");
    }
    @Test
    public void getResultsWithTagNull() {
        GuardianHandler gh = new GuardianHandlerImpl(null);
        InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
            gh.getResultsWithTagAPI(null);
        });

        assertEquals("Tag parameter is null", thrown.getMessage());
    }

    @Test
    public void getResultsWithTagMocked() {
        GuardianHandler gh = mock(GuardianHandlerImpl.class);
        when(gh.getResultsWithTagAPI(anyString())).thenReturn(new ArrayList<>(List.of("oneResult", "twoResult")));
        ArrayList<String> mockRes = gh.getResultsWithTagAPI("custom keyboards are really cool!");
        assertEquals(mockRes.size(), 2);
        assertEquals(mockRes.get(1), "twoResult");
    }

    @Test
    public void getURLNull() {
        GuardianHandler gh = new GuardianHandlerImpl(null);

        InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
            gh.getURL(null);
        });

        assertEquals("Web title is null", thrown.getMessage());
    }

    @Test
    public void getURLMocked() {
        GuardianHandler gh = mock(GuardianHandlerImpl.class);
        String url = "this is definitely a url";
        when(gh.getURL(anyString())).thenReturn(url);
        assertEquals(gh.getURL("rick astley rickroll hehe"), url);
    }




    //to test null database, need GuardianHandler getter from app model fixture

}
