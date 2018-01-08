package nju.java.FilmCompany.ActorFactory;

import nju.java.FilmCompany.Actor.Actor;
import nju.java.FilmCompany.Actor.Scorpion;
import nju.java.FilmCompany.Stage;

public class ScorpionFactory implements IActorFactory {
    public Actor getActor(int x, int y, Stage stage) {
        Scorpion scorpion = new Scorpion(x, y, stage);
        return scorpion;
    }
}
