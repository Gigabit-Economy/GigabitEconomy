package com.mygdx.gigabiteconomy.screens.levels;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.screens.PlotScreen;

public class LevelOnePlotScreen extends PlotScreen {
    private static final String LEVEL_NAME = "LevelOneScreen";

    public LevelOnePlotScreen(GigabitEconomy director) {
        super(director);

        String title = "WELCOME TO THE GIGABIT ECONOMY";
        String body =   "Hi! Welcome to your first day as a delivery driver with 'Dangerous Packaging Company' - your experts in delivering"
                        +" dangerous goods.\n\n"
                        +"As an independent driver you have the freedom to deliver parcels however you like... we do keep track of you through our state"
                        +" of the art 'independent worker organised rating knowledgeable score system' (or IWORKSS for short). "
                        + "It's pretty simple: deliver parcels and get points. "
                        +"Look after yourself and you'll get even more points.\n\n"
                        +"For your first shift we thought we'd start you off in the lovely suburbs. Easy first job."
                        +"Just a word of warning though... there have been a few reports"
                        +" coming through on IWORKSS of a few... erm... \"hostile\" residents who have not received their parcels.\n\n\nBe warned...";

        addTitle(title);
        addBody(body);
        addControls(true);
        addButton(LEVEL_NAME);
    }
    
}

