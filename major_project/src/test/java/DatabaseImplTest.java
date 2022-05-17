import major_project.model.Database;
import major_project.model.DatabaseImpl;
import major_project.model.ResultsPOJO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DatabaseImplTest {

    @Test
    public void mockCheckQueryTagExists() {
        Database db = mock(DatabaseImpl.class);
        when(db.queryCheckTagExists(anyString())).thenReturn(true);
        assertTrue(db.queryCheckTagExists("Any string"));
    }


    @Test
    public void mockRetrieveResults() {
        Database db = mock(DatabaseImpl.class);
        List<ResultsPOJO> resultsMock = new ArrayList<>();
        ResultsPOJO r1 = new ResultsPOJO("a", "b", "c", "d");
        ResultsPOJO r2 = new ResultsPOJO("e", "f", "g", "h");
        ResultsPOJO r3 = new ResultsPOJO("i", "j", "k", "l");
        resultsMock.add(r1);
        resultsMock.add(r2);
        resultsMock.add(r3);
        when(db.retrieveResults(anyString())).thenReturn(resultsMock);

        List<ResultsPOJO> mocked = db.retrieveResults("Any tag");
        assertEquals(3, mocked.size());
        assertEquals("a", mocked.get(0).getID());
        assertEquals("e", mocked.get(1).getID());
        assertEquals("i", mocked.get(2).getID());
    }

}
