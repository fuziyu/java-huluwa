package nju.java.FilmCompany;

import nju.java.FilmCompany.Actor.Actor;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;

public class Stage extends JPanel {

    private final int xOffset = 80;
    private final int yOffset = 80;

    private final int w = 800;
    private final int h = 640;

    private Background background = null;
    private ArrayList<Actor> actors = null;
    private ArrayList element = null;

    private FilmCompany filmCompany;

    public enum POSITIONSTATE {EMPTY, GOODIN, BADIN, DEADIN}

    private POSITIONSTATE[][] positionState = new POSITIONSTATE[h / yOffset - 1][w / xOffset];

    private ArrayList<Thread> threads = new ArrayList<Thread>();

    public Stage(FilmCompany filmCompany) {
        addKeyListener(new TAdapter());
        setFocusable(true);
        this.filmCompany = filmCompany;
        for (int i = 0; i < positionState.length; i++) {
            for (int j = 0; j < positionState[i].length; j++) {
                positionState[i][j] = POSITIONSTATE.EMPTY;
            }
        }
    }

    public int getBoardWidth() {

        return this.w;
    }

    public int getBoardHeight() {

        return this.h;
    }

    public int getxOffset() {

        return xOffset;
    }

    public int getyOffset() {

        return yOffset;
    }

    public Actor findActor(int x, int y) {
        for (Actor actor : actors) {
            if (actor.x() == x && actor.y() == y) {
                return actor;
            }
        }
        return null;
    }

    public POSITIONSTATE getPositionState(int x, int y) {
        return positionState[y / yOffset][x / xOffset];
    }

    public ArrayList getElement() {
        return element;
    }

    public void setPositionState(int x, int y, POSITIONSTATE s) {
        positionState[y / yOffset][x / xOffset] = s;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
    }

    public void buildStage(Graphics g) {
        g.setColor(new Color(250, 240, 170));
        g.fillRect(0, 0, w, h);

        element = new ArrayList();

        element.add(background);

        element.addAll(actors);

        int numGoodGuy = 0;
        int numBadGuy = 0;

        for (int i = 0; i < element.size(); i++) {

            Thing2D item = (Thing2D) element.get(i);

            if (item.x() <= 0) item.setX(0);

            else if (item.x() >= w - xOffset)
                item.setX(w - xOffset);

            if (item.y() <= 0)
                item.setY(0);

            else if (item.y() >= h - yOffset)
                item.setY(h - yOffset);


            if (item instanceof Actor) {
                g.drawImage(item.getImage(), item.x(), item.y(), xOffset, yOffset, this);

                if (((Actor) item).getState() == Actor.STATE.ALIVE) {
                    if (((Actor) item).getRole() == Actor.ROLE.GOODGUY) {
                        numGoodGuy++;
                    } else {
                        numBadGuy++;
                    }
                }
            } else {
                g.drawImage(item.getImage(), item.x(), item.y(), w, h, this);
            }
        }
        if (filmCompany.getFilmState() == FilmCompany.STATE.ACTION) {
            if (numBadGuy == 0 || numGoodGuy == 0) {
                filmCompany.setFilmState(FilmCompany.STATE.OVER);
                filmCompany.saveFrame();
                filmCompany.afterSaving();
                for (Thread t : threads) {
                    t.interrupt();
                }
                repaint();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        buildStage(g);
        if (filmCompany.getFilmState() == FilmCompany.STATE.ACTION) {
            filmCompany.saveFrame();
        }
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            switch (key) {

                case KeyEvent.VK_SPACE:
                    if (filmCompany.getFilmState() == FilmCompany.STATE.READY) {
                        filmCompany.action();
                        for (Actor p : actors) {
                            Thread t = new Thread(p);
                            threads.add(t);
                            t.start();
                        }
                    }
                    break;
                case KeyEvent.VK_L:
                    if (filmCompany.getFilmState() == FilmCompany.STATE.READY || filmCompany.getFilmState() == FilmCompany.STATE.OVER) {
                        JFileChooser jfc = new JFileChooser();
                        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        jfc.showDialog(new JLabel(), "Select");
                        File file = jfc.getSelectedFile();
                        new Thread(() -> {
                            filmCompany.playBack(file);
                        }).start();
                    }
                    break;
            }

        }
    }
}