package com.leapgs.cars.InputProcessor;

import com.badlogic.gdx.input.GestureDetector;
import com.leapgs.cars.Screens.GameplayScreen;
import com.leapgs.cars.Screens.WelcomeScreen;

/**
 * Created by Leap-Pancho on 6/22/2017.
 */

public class GameplayInputProcessor extends GestureDetector.GestureAdapter {

    GameplayScreen screen;

    public GameplayInputProcessor(GameplayScreen screen)
    {
        this.screen=screen;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        screen.changePlayerLane();
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return true;
    }
}
