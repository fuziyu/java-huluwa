package nju.java.FilmCompany.Actor;

import nju.java.FilmCompany.Stage;

import java.util.Random;

public class Grandpa extends Actor {

    public Grandpa(int x, int y, Stage stage) {
        super(x, y, stage);
    }

    @Override
    public boolean isNextPositionValid(int newX, int newY) {
        if (ifIsOutOfStage(newX, newY)) {
            return false;
        }
        if (stage.getPositionState(newX, newY) == Stage.POSITIONSTATE.EMPTY)
            return true;

        else
            return stage.getPositionState(newX, newY) == Stage.POSITIONSTATE.BADIN;
    }

    protected void move(int x, int y) {
        int nx = this.x() + x;
        int ny = this.y() + y;
        this.setX(nx);
        this.setY(ny);
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this.stage) {
                    if (this.state == STATE.ALIVE) {
                        this.move();
                    }
                    else {
                        this.setImage("deadgrandpa.png");
                    }
                }


                Thread.sleep(500);
                this.stage.repaint();

            }
        } catch (Exception e) {

        }
    }
}

