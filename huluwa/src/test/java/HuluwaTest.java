import nju.java.FilmCompany.Actor.Huluwa;
import nju.java.FilmCompany.FilmCompany;
import nju.java.FilmCompany.Stage;
import org.junit.Test;
import static org.junit.Assert.*;

public class HuluwaTest {
    @Test
    public void testIsNextPositionValid() {
        Stage stage = new Stage(new FilmCompany());
        int newX = 400, newY = 400;
        // set position 400,400 to be empty
        stage.setPositionState(newX, newY, Stage.POSITIONSTATE.EMPTY);
        assertEquals(new Huluwa(newX, newY,stage).isNextPositionValid(400,400), true);

        stage.setPositionState(newX, newY, Stage.POSITIONSTATE.GOODIN);
        assertEquals(new Huluwa(newX, newY,stage).isNextPositionValid(400,400), false);

        stage.setPositionState(newX, newY, Stage.POSITIONSTATE.BADIN);
        assertEquals(new Huluwa(newX, newY,stage).isNextPositionValid(400,400), true);
    }
}
