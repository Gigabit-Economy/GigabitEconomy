package com.mygdx.gigabiteconomy.screens;

import java.io.File; 
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class ScoreSystem {

    private int score;
    private File f;

    public ScoreSystem() {
        
        score = 0;

        try {
            f = new File("ScoreSheet.txt");
            if (f.createNewFile()) {
                System.out.println("File created: " + f.getName());
              } else {
                System.out.println("File already exists."); }
        } catch (Exception e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }

    public int alterScore(int addAmount) {
        
        score += addAmount;
        return score;
    
    }

    public void submitScore() {
        
        String stringScore = Integer.toString(score);
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("ScoreSheet.txt", true));
            writer.append(stringScore);
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }

    }

    public List<String> scoreboard() {
        List<String> scoreboard = new ArrayList<String>();
        
        try {
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                scoreboard.add(s.nextLine());
            }
        } catch (Exception e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }

        return scoreboard;
    }


}
