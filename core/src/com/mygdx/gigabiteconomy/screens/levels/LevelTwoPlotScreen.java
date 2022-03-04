package com.mygdx.gigabiteconomy.screens.levels;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.screens.PlotScreen;

public class LevelTwoPlotScreen extends PlotScreen {

    public LevelTwoPlotScreen(GigabitEconomy director) {
        super(director);
        
        String title = "MOVING UP BY MOVING DOWN(TOWN)";
        String body =   "Great job with you first set of deliveries. Honestly I didn't know there was so many violent people in the suburbs!\n\n"
                        +"On the plus side your proficiency in delivering parcels under extreme conditions means that management feel you'd be"
                        +" perfect for a new delivery location - downtown. Now it is definitly more dangerous, and we won't be paying you more"
                        +" but think of all the experience you'll gain!\n\n"
                        +"Now you may have heard in the news about people downtown having slight abnormalites. Here at 'Dangerous Packaging Company'"
                        +" we don't discriminate and believe that everyone should have the joy and convenience of having dangerous objects delivered"
                        +" directly to your door. Even if there deranged, freakish looking rat people. So go out there and deliver!";

        String nextLevelName = "LevelTwoScreen";

        addTitle(title);
        addBody(body);
        addButton(nextLevelName);
    }
    
}
