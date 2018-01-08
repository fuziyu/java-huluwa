package nju.java.FilmCompany.ActorFactory;

import nju.java.FilmCompany.Actor.Actor;
import nju.java.FilmCompany.Actor.Frog;
import nju.java.FilmCompany.Stage;

public class FrogFactory implements IActorFactory {
    public Actor getActor(int x, int y, Stage stage) {
        Frog frog = new Frog(x, y, stage);
        return frog;
    }
}
