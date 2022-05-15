import major_project.model.AppModel;
import major_project.model.PastebinHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PastebinHandlerTest {

    @Test
    public void testNullModel() {
        PastebinHandler ph = new PastebinHandler();
        assertNull(ph.generateOutputReport(null, "Report me"));
    }

    @Test
    public void testNullToReport() {
        PastebinHandler ph = new PastebinHandler();
        AppModel model = new AppModel(false, false);
        assertNull(ph.generateOutputReport(model, null));
    }

    @Test
    public void testNullBoth() {
        PastebinHandler ph = new PastebinHandler();
        assertNull(ph.generateOutputReport(null, null));
    }

    @Test
    public void mockedGenerateOutputReport() {
        PastebinHandler ph = mock(PastebinHandler.class);
        String pastebinLink = "definitely a pastebin string";
        AppModel model = new AppModel(false, false);
        when(ph.generateOutputReport(any(), anyString())).thenReturn(pastebinLink);
        assertEquals(ph.generateOutputReport(model, "definitely the content you want"), pastebinLink);

    }
}
