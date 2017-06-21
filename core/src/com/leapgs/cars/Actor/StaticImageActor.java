package com.leapgs.cars.Actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.leapgs.cars.MainGame;

/**
 * Created by Leap-Pancho on 6/21/2017.
 */

public class StaticImageActor extends Actor {

    MainGame game;
    Texture texture;

    public StaticImageActor(MainGame game,String name, float X , float Y, float width,float height)
    {
        this.game = game;
        texture = new Texture(name);
        //coordinates relative to the center of the image
        setSize(width,height);
        setPosition(X-getWidth()/2,Y - getHeight()/2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(),getWidth(),getHeight());
    }
}
