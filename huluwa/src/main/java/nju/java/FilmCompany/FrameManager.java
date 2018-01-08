package nju.java.FilmCompany;

import nju.java.FilmCompany.Actor.*;

import java.io.*;
import java.util.ArrayList;

public class FrameManager {

    private BufferedReader bufferedReader = null;
    private BufferedWriter bufferedWriter = null;

    public boolean beforeLoading(File file) {
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Opening file failed when loading frame.");
        }
        return false;
    }

    public boolean beforeSaving() {
        try {
            File file = new File("frame.txt");
            if (file.exists()) {
                file.delete();
            }
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Opening file failed when saving frame.");
        }
        return false;
    }

    public void afterLoading() {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Closing file failed after loading.");
            }
        }
    }

    public void afterSaving() {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Closing file failed after saving.");
            }
        }
    }

    private String generateScriptFromWorld(ArrayList world, int w, int h, int xSize, int ySize) {
        char[] frame = new char[xSize * ySize];
        char[] state = new char[xSize * ySize];
        char[] huluwaCount = {'1', '2', '3', '4', '5', '6', '7'};

        for (int i = 0; i < frame.length; i++) {
            frame[i] = '.';
            state[i] = '.';
        }

        for (Object element : world) {
            if (element instanceof Actor) {
                int x = ((Actor) element).x();
                int y = ((Actor) element).y();
                if (element instanceof Huluwa) {
                    int index = ((Huluwa) element).getHuluwaCount() - 1;
                    frame[y / h * ySize + x / w] = huluwaCount[index];
                    if (((Huluwa) element).getState() == Actor.STATE.ALIVE) {
                        state[y / h * ySize + x / w] = 'a';
                    } else {
                        state[y / h * ySize + x / w] = 'd';
                    }
                } else if (element instanceof Frog) {
                    frame[y / h * ySize + x / w] = 'f';
                    if (((Frog) element).getState() == Actor.STATE.ALIVE) {
                        state[y / h * ySize + x / w] = 'a';
                    } else {
                        state[y / h * ySize + x / w] = 'd';
                    }
                } else if (element instanceof Grandpa) {
                    frame[y / h * ySize + x / w] = 'g';
                    if (((Grandpa) element).getState() == Actor.STATE.ALIVE) {
                        state[y / h * ySize + x / w] = 'a';
                    } else {
                        state[y / h * ySize + x / w] = 'd';
                    }
                } else if (element instanceof Crocodile) {
                    frame[y / h * ySize + x / w] = 'c';
                    if (((Crocodile) element).getState() == Actor.STATE.ALIVE) {
                        state[y / h * ySize + x / w] = 'a';
                    } else {
                        state[y / h * ySize + x / w] = 'd';
                    }
                } else if (element instanceof Scorpion) {
                    frame[y / h * ySize + x / w] = 's';
                    if (((Scorpion) element).getState() == Actor.STATE.ALIVE) {
                        state[y / h * ySize + x / w] = 'a';
                    } else {
                        state[y / h * ySize + x / w] = 'd';
                    }
                }
            }
        }
        for (int i = 0; i < xSize; i++) {
            frame[i * ySize + ySize - 1] = '\n';
            state[i * ySize + ySize - 1] = '\n';
        }
        return new String(frame) + new String(state);
    }

    public void saveFrame(ArrayList world, int w, int h, int worldWidth, int worldHeight) {
        String string = generateScriptFromWorld(world, w, h, worldHeight / h - 1, worldWidth / w + 1);
        try {
            bufferedWriter.write(string);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Writing file failed when saving frame.");
        }

    }

    public ArrayList<String>[] loadFrame() {
        ArrayList<String> frame = new ArrayList<>();
        ArrayList<String> state = new ArrayList<>();
        try {
            String s;
            int n = 0;
            String tmp = "";
            while ((s = bufferedReader.readLine()) != null) {
                tmp = tmp + s + "\n";
                n++;
                if(n == 7) {
                    frame.add(tmp);
                    tmp = "";
                }
                else if(n == 14) {
                    state.add(tmp);
                    tmp = "";
                    n = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Reading file failed when loading frame.");
        }
        ArrayList<String>[] r = new ArrayList[]{frame, state};
        return r;
    }
}
