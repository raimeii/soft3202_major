import majorproject.model.resourcehandler.ResourceHandler;
import majorproject.model.resourcehandler.ResourceHandlerImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceHandlerImplTest {

    @Test
    public void testGetMusicResource() {
        ResourceHandler rh = new ResourceHandlerImpl();
        assertNotNull(rh.getMusicResource());
    }


    @Test
    public void setAudioPlaying() {
        ResourceHandler rh = new ResourceHandlerImpl();
        assertTrue(rh.isAudioPlaying());
        rh.setAudioPlaying(false);
        assertFalse(rh.isAudioPlaying());
    }
}
