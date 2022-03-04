package com.mygdx.gigabiteconomy.screens.levels;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.screens.PlotScreen;

/**
 * Screen which shows before starting level 1
 */
public class LevelOnePlotScreen extends PlotScreen {
    private static final String LEVEL_NAME = "LevelOneScreen";

    /**
     * Create a new plot screen for level 2
     *
     * @param director the game's director class instance
     */
    public LevelOnePlotScreen(GigabitEconomy director) {
        super(director);

        String title = "WELCOME TO THE GIGABIT ECONOMY";
        String body =   "Hi! Welcome to your first day as a delivery driver with 'Dangerous Packaging Company' - your experts in delivering"
                        +" dangerous goods.\n\n"
                        +"As an independent driver you have the freedom to deliver parcels however you like... however, we do keep track of you through our state"
                        +" of the art 'independent worker organised rating knowledgeable score system' (or IWORKSS for short). It's pretty simple: deliver parcels and"
                        +" you get a point. Look after yourself and you'll get even more points.\n\n"
                        +"For your first shift we thought we'd start you off in the lovely suburbs. Little picket fences, nice white brick houses, refreshing"
                        +" semi-rural air. I can already imagine how much you'll love delivering round here. Just a word of warning though... there have been a few reports"
                        +" coming through on IWORKSS of a few... erm... \"hostile\" residents. I'm sure you'll manage though, and you have your trusty box cutter if anything gets out"
                        +" of hand.\n\n"
                        +"P.S. You didn't hear this from me but some of the stuff we deliver might be worth opening with [tab]";

        addTitle(title);
        addBody(body);
        addControls(true);
        addButton(LEVEL_NAME);
    }
    
}

