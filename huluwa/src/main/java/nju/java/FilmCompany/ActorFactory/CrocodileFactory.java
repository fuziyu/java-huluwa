package nju.java.FilmCompany.ActorFactory;

import nju.java.FilmCompany.Actor.Actor;
import nju.java.FilmCompany.Actor.Crocodile;
import nju.java.FilmCompany.Stage;

public class CrocodileFactory implements IActorFactory {
    public Actor getActor(int x, int y, Stage stage) {
        Crocodile crocodile = new Crocodile(x, y, stage);
        return crocodile;
    }
}
