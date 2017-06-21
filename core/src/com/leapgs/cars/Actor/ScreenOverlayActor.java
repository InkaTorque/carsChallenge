package com.leapgs.cars.Actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.leapgs.cars.MainGame;

/**
 * Created by Leap-Pancho on 6/21/2017.
 */

public class ScreenOverlayActor extends Actor {

    MainGame game;
    Texture overlayTex;

    public ScreenOverlayActor(MainGame game)
    {
        this.game = game;
        overlayTex = new Texture("watchOverlay.png");
        setX(game.window.x-200);
        setY(game.window.y-200);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(game.testing)
        {
            batch.draw(overlayTex, getX(), getY());
        }
    }
}