import majorproject.model.pastebinhandler.PastebinHandler;
import majorproject.model.pastebinhandler.OnlinePastebinHandlerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class OnlinePastebinHandlerImplTest {


    @Test
    public void testNullToReport() {
        PastebinHandler ph = new OnlinePastebinHandlerImpl();
        InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> {
            ph.generateOutputReport(null);
        });
        assertEquals("Parameter is null.", thrown.getMessage());
    }


    @Test
    public void mockedGenerateOutputReport() {
        PastebinHandler ph = mock(OnlinePastebinHandlerImpl.class);
        String pastebinLink = "definitely a pastebin string";
        when(ph.generateOutputReport(anyString())).thenReturn(pastebinLink);
        assertEquals(ph.generateOutputReport("definitely the content you want"), pastebinLink);

    }
}
