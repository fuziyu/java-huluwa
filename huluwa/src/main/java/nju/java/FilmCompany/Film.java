package nju.java.FilmCompany;

import javax.swing.JFrame;


public final class Film extends JFrame {

    private final int OFFSET = 50;

    public Film(Stage stage) {
        InitUI(stage);
    }

    public void InitUI(Stage stage) {
        add(stage);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(stage.getBoardWidth(),
                stage.getBoardHeight());
        setLocationRelativeTo(null);
        setTitle("BattleField");
    }
}