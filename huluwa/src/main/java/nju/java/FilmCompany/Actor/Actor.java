package nju.java.FilmCompany.Actor;

import nju.java.FilmCompany.Stage;
import nju.java.FilmCompany.Thing2D;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public abstract class Actor extends Thing2D implements Runnable {

    protected Stage stage;

    protected int[][] pace;

    protected int attackForce;

    public enum STATE {ALIVE, DEAD}

    protected STATE state;

    public enum ROLE {GOODGUY, BADGUY}

    protected ROLE role;

    public Actor(int x, int y, Stage stage) {
        super(x, y);
        this.stage = stage;
        state = STATE.ALIVE;
        pace = new int[][] {
                {-80, -80}, {0, -80}, {80, -80},
                {-80, 0}, {80, 0},
                {-80, 80}, {0, 80}, {80, 80}
        };
    }

    public final boolean ifIsOutOfStage(int x, int y) {
        return (x < 0) ||
                (x > this.stage.getBoardWidth() - this.stage.getxOffset()) ||
                (y < 0) ||
                (y > this.stage.getBoardHeight() - 2 * this.stage.getyOffset());
    }

    public abstract boolean isNextPositionValid(int newX, int newY);

    protected abstract void move(int x, int y);

    public void move() {

        LinkedList<Integer> direction = new LinkedList<Integer>();
        ArrayList<Integer> randomIndex = new ArrayList<Integer>();
        for(int i = 0;i < pace.length;i++)
            randomIndex.add(i);

        Collections.shuffle(randomIndex);

        for(int i : randomIndex) {
            int x = this.x() + pace[i][0];
            int y = this.y() + pace[i][1];
            if(isNextPositionValid(x,y)) {
                if(stage.getPositionState(x,y) != Stage.POSITIONSTATE.EMPTY) {
                    direction.addFirst(i);
                }
                else {
                    direction.addLast(i);
                }
            }
        }

        if(direction.size() == 0)
            return;

        int index = direction.getFirst();

        int newX = this.x() + pace[index][0];
        int newY = this.y() + pace[index][1];

        if (this.stage.getPositionState(newX, newY) == Stage.POSITIONSTATE.EMPTY) {
            if (this.role == ROLE.GOODGUY) {
                this.stage.setPositionState(newX, newY, Stage.POSITIONSTATE.GOODIN);
            } else {
                this.stage.setPositionState(newX, newY, Stage.POSITIONSTATE.BADIN);
            }
            this.stage.setPositionState(this.x(), this.y(), Stage.POSITIONSTATE.EMPTY);
            this.move(pace[index][0], pace[index][1]);
        } else {
            Actor enemy = this.stage.findActor(newX, newY);
            if (this.attackForce > enemy.getAttackForce()) {
                enemy.setState(STATE.DEAD);
                this.stage.setPositionState(newX, newY, Stage.POSITIONSTATE.DEADIN);
            } else if (this.attackForce < enemy.getAttackForce()){
                this.state = STATE.DEAD;
                this.stage.setPositionState(this.x(), this.y(), Stage.POSITIONSTATE.DEADIN);
            }
            else {
                Random random = new Random();
                if(random.nextInt(1) == 0) {
                    this.state = STATE.DEAD;
                    this.stage.setPositionState(this.x(), this.y(), Stage.POSITIONSTATE.DEADIN);
                }
                else {
                    enemy.setState(STATE.DEAD);
                    this.stage.setPositionState(newX, newY, Stage.POSITIONSTATE.DEADIN);
                }
            }
        }

    }

    public void setAttackForce(int attackForce) {
        this.attackForce = attackForce;
    }

    public int getAttackForce() {
        return attackForce;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public STATE getState() {
        return state;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public ROLE getRole() {
        return role;
    }

    public void setImage(String pic) {
        URL loc = this.getClass().getClassLoader().getResource(pic);
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
}

