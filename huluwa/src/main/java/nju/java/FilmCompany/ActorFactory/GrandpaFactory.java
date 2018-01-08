package nju.java.FilmCompany.ActorFactory;

import nju.java.FilmCompany.Actor.Actor;
import nju.java.FilmCompany.Actor.Grandpa;
import nju.java.FilmCompany.Stage;

public class GrandpaFactory implements IActorFactory {
    public Actor getActor(int x, int y, Stage stage) {
        Grandpa grandpa = new Grandpa(x, y, stage);
        return grandpa;
    }
}
