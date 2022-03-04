package com.mygdx.gigabiteconomy.screens.levels;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.screens.PlotScreen;

/**
 * Screen which shows before starting level 3
 */
public class LevelThreePlotScreen extends PlotScreen {
    private static final String LEVEL_NAME = "LevelThreeScreen";

    /**
     * Create a new plot screen for level 3
     *
     * @param director the game's director class instance
     */
    public LevelThreePlotScreen(GigabitEconomy director) {
        super(director);
        
        String title = "DON'T RAT-US OUT";
        String body =   "So we've recently been losing lots of parcels. IWORKSS has been putting it down to"
                        +" delivery drivers, but it seems like it's been caused by some extra-freakish rat. A big old rat."
                        +" As our star driver, management think its only right we give you the opportunity to"
                        +" get this issue resolved for us... if you catch my drift. I must stress that 'Dangerous Packaging Company'"
                        +" has no involvement with people looking like rats or have no prior knowledge to rats taking our"
                        +" parcels. This conversation didn't happen.. okay?\n\n"
                        + "Your aim now is to defeat the 'Rat King', by defeating his fort and then finally the monster Rat King himself.";

        addTitle(title);
        addBody(body);
        addButton(LEVEL_NAME);
    }
}
