package nju.java.FilmCompany;

import nju.java.FilmCompany.Actor.Actor;
import nju.java.FilmCompany.Actor.Huluwa;
import nju.java.FilmCompany.ActorFactory.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilmCompany {

    enum STATE {READY, ACTION, OVER, PLAYBACK}

    private STATE filmState;

    private FrameManager frameManager = new FrameManager();
    private Film film = null;
    private Stage stage = new Stage(this);

    // factories used to produce actors
    private HuluwaFactory huluwaFactory = new HuluwaFactory();
    private FrogFactory frogFactory = new FrogFactory();
    private ScorpionFactory scorpionFactory = new ScorpionFactory();
    private CrocodileFactory crocodileFactory = new CrocodileFactory();
    private GrandpaFactory grandpaFactory = new GrandpaFactory();

    // 7 * 10
    private String frame;

    private String state;

    public FilmCompany() {
        this.frame =
                "1.......f.\n" +
                        ".2.....f..\n" +
                        "..3...c...\n" +
                        "g..4.s..c.\n" +
                        "..5...c...\n" +
                        ".6.....f..\n" +
                        "7.......f.\n";
    }

    public void initStage() {

        ArrayList<Actor> actors = new ArrayList<Actor>();
        Background background = new Background(0, 0);

        int x = 0;
        int y = 0;

        Map<Character, Integer> map = new HashMap<Character, Integer>() {
            {
                put('1', 1);
                put('2', 2);
                put('3', 3);
                put('4', 4);
                put('5', 5);
                put('6', 6);
                put('7', 7);
            }
        };

        Actor actor;

        for (int i = 0; i < frame.length(); i++) {

            char item = frame.charAt(i);

            switch (item) {
                case '\n':
                    y += stage.getyOffset();
                    x = 0;
                    break;
                // huluwa
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    actor = huluwaFactory.getActor(x, y, stage);
                    actor.setAttackForce(2);
                    actor.setRole(Actor.ROLE.GOODGUY);
                    ((Huluwa) actor).setHuluwaCount(map.get(item));
                    actor.setImage("huluwa" + item + ".png");
                    actors.add(actor);
                    stage.setPositionState(x, y, Stage.POSITIONSTATE.GOODIN);
                    x += stage.getxOffset();
                    break;
                // scorpion
                case 's':
                    actor = scorpionFactory.getActor(x, y, stage);
                    actor.setAttackForce(3);
                    actor.setRole(Actor.ROLE.BADGUY);
                    actor.setImage("scorpion.png");
                    actors.add(actor);
                    stage.setPositionState(x, y, Stage.POSITIONSTATE.BADIN);
                    x += stage.getxOffset();
                    break;
                // frog
                case 'f':
                    actor = frogFactory.getActor(x, y, stage);
                    actor.setAttackForce(1);
                    actor.setRole(Actor.ROLE.BADGUY);
                    actor.setImage("frog.png");
                    actors.add(actor);
                    stage.setPositionState(x, y, Stage.POSITIONSTATE.BADIN);

                    x += stage.getxOffset();
                    break;
                // grandpa
                case 'g':
                    actor = grandpaFactory.getActor(x, y, stage);
                    actor.setAttackForce(3);
                    actor.setRole(Actor.ROLE.GOODGUY);
                    actor.setImage("grandpa.png");
                    actors.add(actor);
                    stage.setPositionState(x, y, Stage.POSITIONSTATE.GOODIN);

                    x += stage.getxOffset();
                    break;
                // crocodile
                case 'c':
                    actor = crocodileFactory.getActor(x, y, stage);
                    actor.setAttackForce(2);
                    actor.setRole(Actor.ROLE.BADGUY);
                    actor.setImage("crocodile.png");
                    actors.add(actor);
                    stage.setPositionState(x, y, Stage.POSITIONSTATE.BADIN);

                    x += stage.getxOffset();
                    break;
                case '.':
                    stage.setPositionState(x, y, Stage.POSITIONSTATE.EMPTY);
                    x += stage.getxOffset();
                    break;
            }
        }

        stage.setBackground(background);
        stage.setActors(actors);
    }

    public void changeStage() {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        Background background = new Background(0, 0);

        int x = 0;
        int y = 0;

        Actor actor;
        //System.out.println(frame.length() + " " + state.length());

        for (int i = 0; i < frame.length(); i++) {

            char item = frame.charAt(i);

            char state = this.state.charAt(i);

            switch (item) {
                case '\n':
                    y += stage.getyOffset();
                    x = 0;
                    break;
                // huluwa
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    actor = huluwaFactory.getActor(x, y, stage);

                    if (state == 'a') {
                        actor.setImage("huluwa" + item + ".png");
                    } else {
                        actor.setImage("deadhuluwa" + item + ".png");
                    }

                    actors.add(actor);
                    x += stage.getxOffset();
                    break;
                // scorpion
                case 's':
                    actor = scorpionFactory.getActor(x, y, stage);

                    if (state == 'a') {
                        actor.setImage("scorpion.png");
                    } else {
                        actor.setImage("deadscorpion.png");
                    }
                    actors.add(actor);
                    x += stage.getxOffset();
                    break;
                // frog
                case 'f':
                    actor = frogFactory.getActor(x, y, stage);
                    if (state == 'a') {
                        actor.setImage("frog.png");
                    } else {
                        actor.setImage("deadfrog.png");
                    }
                    actors.add(actor);
                    x += stage.getxOffset();
                    break;
                // pangolin
                case 'g':
                    actor = grandpaFactory.getActor(x, y, stage);
                    if (state == 'a') {
                        actor.setImage("grandpa.png");
                    } else {
                        actor.setImage("deadgrandpa.png");
                    }
                    actors.add(actor);
                    x += stage.getxOffset();
                    break;
                // crocodile
                case 'c':
                    actor = crocodileFactory.getActor(x, y, stage);
                    if (state == 'a') {
                        actor.setImage("crocodile.png");
                    } else {
                        actor.setImage("deadcrocodile.png");
                    }
                    actors.add(actor);
                    x += stage.getxOffset();
                    break;
                case '.':
                    x += stage.getxOffset();
                    break;
            }
        }

        stage.setBackground(background);
        stage.setActors(actors);
    }

    public void beforeSaving() {
        frameManager.beforeSaving();
    }

    public void beforeLoading(File file) {
        frameManager.beforeLoading(file);
    }

    public void afterSaving() {
        frameManager.afterSaving();
    }

    public void afterLoading() {
        frameManager.afterLoading();
    }

    public void saveFrame() {
        frameManager.saveFrame(stage.getElement(), stage.getxOffset(), stage.getyOffset(), stage.getBoardWidth(), stage.getBoardHeight());
    }

    public void playBack(File file) {
        filmState = STATE.PLAYBACK;
        beforeLoading(file);
        ArrayList<String>[] r = frameManager.loadFrame();

        ArrayList<String> frame = r[0];
        ArrayList<String> state = r[1];

        for (int i = 0; i < frame.size(); i++) {
            this.frame = frame.get(i);
            this.state = state.get(i);
            changeStage();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Sleep has been interrupted.");
            }
            stage.repaint();
        }
        afterLoading();
        filmState = STATE.OVER;
    }

    public void action() {
        filmState = STATE.ACTION;
        beforeSaving();
    }

    public void start() {
        filmState = STATE.READY;
        initStage();
        film = new Film(stage);
        film.setVisible(true);
    }

    public STATE getFilmState() {
        return filmState;
    }

    public void setFilmState(STATE filmState) {
        this.filmState = filmState;
    }
}
