package nju.java.FilmCompany.ActorFactory;

import nju.java.FilmCompany.Actor.Actor;
import nju.java.FilmCompany.Actor.Huluwa;
import nju.java.FilmCompany.Stage;

public class HuluwaFactory implements IActorFactory {

    public Actor getActor(int x, int y, Stage stage) {
        Huluwa huluwa = new Huluwa(x, y, stage);
        return huluwa;
    }
}
