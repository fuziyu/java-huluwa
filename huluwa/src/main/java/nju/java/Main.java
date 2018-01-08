package nju.java;

import nju.java.FilmCompany.FilmCompany;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        FilmCompany huluwa = new FilmCompany();
        File file = new File("frame.txt");
        //huluwa.playBack(file);
        huluwa.start();
    }
}
