package com.mygdx.gigabiteconomy.screens;

import com.mygdx.gigabiteconomy.GigabitEconomy;

public class LevelRatKingPlotScreen extends PlotScreen {

    public LevelRatKingPlotScreen(GigabitEconomy director) {
        super(director);
        
        String title = "DON'T RATUS RATUS OUT";
        String body =   "So we've recently been losing lots of parcels. IWORKSS has been putting it down to"
                        +" delivery drivers, but it seems like it's been caused by a rat. A big old rat. As"
                        +" an indepent driver mangament think its only right we give you the oppotunity to"
                        +" solve this issue for us. Just to clarify 'Dangerous Packaging Company' has nothing"
                        +"to do with people looking like rats or have no prior knowledge to rats taking our"
                        +"parcels - if that even is happening.";
        
        String nextLevelName = "LevelThreeScreen";

        addTitle(title);
        addBody(body);
        addButton(nextLevelName);
    }
    
}
