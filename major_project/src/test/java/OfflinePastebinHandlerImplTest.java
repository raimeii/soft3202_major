import major_project.model.PastebinHandler.OfflinePastebinHandlerImpl;
import major_project.model.PastebinHandler.PastebinHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OfflinePastebinHandlerImplTest {

    @Test
    public void testSimple() {
        PastebinHandler pbh = new OfflinePastebinHandlerImpl();
        assertEquals("", pbh.generateOutputReport("any String"));
    }

    @Test
    public void testNull() {
        PastebinHandler pbh = new OfflinePastebinHandlerImpl();
        assertEquals("", pbh.generateOutputReport(null));
    }
}
