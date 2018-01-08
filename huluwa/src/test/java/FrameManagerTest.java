import nju.java.FilmCompany.FrameManager;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class FrameManagerTest {
    @Test
    public void testLoadFrame() {
        FrameManager frameManager = new FrameManager();
        frameManager.beforeLoading(new File("frame.txt"));
        // r[0]: frame
        // r[1]: state
        ArrayList<String>[] r = frameManager.loadFrame();
        // the size of frame and state should be the same
        assert(r[0].size() == r[1].size());
    }
}
