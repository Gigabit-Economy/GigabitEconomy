package com.mygdx.gigabiteconomy.screens.levels;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.screens.PlotScreen;

public class LevelOnePlotScreen extends PlotScreen {

    public LevelOnePlotScreen(GigabitEconomy director) {
        super(director);

        String title = "WELCOME TO THE GIG ECONOMY";
        String body =   "Hey there welcome to your first day as a delivery driver with 'Dangerous Packaging Company'. A delivery company that exclusively delivers" 
                        +" dangerous good.\n\n"
                        +"As an independent driver you will have the freedom to deliver parcels however you like... except we do keep track of you through our state"
                        +" of the art 'indepent worker organised rating knowledgable score system' or IWORKSS for short. It's pretty simple really deliver parcels and"
                        +" you get a point.\n\n"
                        +"For your first shift we thought we'd start you off in the lovely suburbs. Little picket fences, nice white brick houses, refreshing" 
                        +" semi-rural air. I can already imagine how much you'll love delivering round here... just a word of warning thoug... theres been a few reports"
                        +" through IWORKSS of a few... hostile residents... Still I'm sure you'll manage after all you have your trusty box cutter if anything gets out" 
                        +" of hand.\n\n"
                        +"P.S You didn't hear it from me but some of the stuff we deliver might be worth opening with [tab]";
        
        String nextLevelName = "LevelOneScreen";

        addTitle(title);
        addBody(body);
        addControls(true);
        addButton(nextLevelName);
    }
    
}

