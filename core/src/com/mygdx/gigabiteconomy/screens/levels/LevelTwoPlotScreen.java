package com.mygdx.gigabiteconomy.screens.levels;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.screens.PlotScreen;

/**
 * Screen which shows before starting level 2
 */
public class LevelTwoPlotScreen extends PlotScreen {
    private static final String LEVEL_NAME = "LevelTwoScreen";

    /**
     * Create a new plot screen for level 2
     *
     * @param director the game's director class instance
     */
    public LevelTwoPlotScreen(GigabitEconomy director) {
        super(director);
        
        String title = "MOVING UP BY MOVING DOWN(TOWN)";
        String body =   "Great job with you first set of deliveries. I honestly didn't know there were so many violent people in the suburbs!\n\n"
                        +"On the plus side your proficiency in delivering parcels under extreme conditions means that management feel you'd be"
                        +" perfect for a new delivery location - downtown. I must warn you that now it's definitely more dangerous, and we won't"
                        +" be paying you more regrettably; but just think of all the experience you'll gain!\n\n"
                        +"Now you may have heard in the news about people downtown having slight abnormalities round here."
                        +" Here at 'Dangerous Packaging Company' we don't discriminate and believe that everyone should have the joy and convenience of having dangerous objects delivered"
                        +" directly to their door. Even if they're deranged, freakish looking rat people. So get out there and deliver!";

        addTitle(title);
        addBody(body);
        addButton(LEVEL_NAME);
    }
}
