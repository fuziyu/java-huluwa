import nju.java.FilmCompany.Actor.Huluwa;
import nju.java.FilmCompany.FilmCompany;
import nju.java.FilmCompany.Stage;
import org.junit.Test;
import static org.junit.Assert.*;

public class ActorTest {
    @Test
    public void testIsOutOfStage() {
        // x should be : 0 <= x <= 720
        // y should be : 0 <= y <= 480
        assertEquals(new Huluwa(0, 0, new Stage(new FilmCompany())).ifIsOutOfStage(720, 480), false);
        assertEquals(new Huluwa(0, 0, new Stage(new FilmCompany())).ifIsOutOfStage(900, 480), true);
        assertEquals(new Huluwa(0, 0, new Stage(new FilmCompany())).ifIsOutOfStage(400, 400), false);
        assertEquals(new Huluwa(0, 0, new Stage(new FilmCompany())).ifIsOutOfStage(-10, 100), true);
    }
}
