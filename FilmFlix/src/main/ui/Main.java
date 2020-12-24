package ui;

import javax.swing.*;

// launches the FilmFlix application
public class Main {
    public static void main(String[] args) {
        JFrame frame = new FilmFlixApp("FilmFlix");
        frame.setVisible(true);
        frame.setSize(800, 600);
        frame.setResizable(false);
    }
}