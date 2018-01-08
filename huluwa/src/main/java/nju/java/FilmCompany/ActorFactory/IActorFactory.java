package nju.java.FilmCompany.ActorFactory;

import nju.java.FilmCompany.Actor.Actor;
import nju.java.FilmCompany.Stage;

public interface IActorFactory {
    Actor getActor(int x, int y, Stage stage);
}
