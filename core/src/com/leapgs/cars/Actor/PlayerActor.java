package com.leapgs.cars.Actor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.leapgs.cars.Screens.GameplayScreen;

/**
 * Created by Leap-Pancho on 6/21/2017.
 */

public class PlayerActor extends Actor {

    GameplayScreen screen;
    Texture playerTexture;

    public PlayerActor(GameplayScreen screen , float x , float y) {
        this.screen = screen;
        playerTexture = new Texture("sprites/lightning.png");
        setSize(playerTexture.getWidth()*5,playerTexture.getHeight()*5);
        setPosition(x,y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(playerTexture,getX(),getY(),getWidth(),getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
