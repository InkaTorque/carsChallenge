package com.leapgs.cars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.leapgs.cars.Actor.StaticImageActor;
import com.leapgs.cars.InputProcessor.WelcomeScreenInputProcessor;
import com.leapgs.cars.MainGame;

/**
 * Created by Leap-Pancho on 6/21/2017.
 */

public class WelcomeScreen extends BaseScreen {

    String gameLogo="textImages/gameLogo.png";

    StaticImageActor logoActor,tapMessageActor;

    public WelcomeScreen(MainGame game) {
        super(game);
    }

    @Override
    public void render(float delta)
    {
        super.render(delta);
    }

    @Override
    public void show() {
        super.show();
        logoActor = new StaticImageActor(game,gameLogo,200,250,200,150);
        tapMessageActor = new StaticImageActor(game,"textImages/tapTex.png",200,150,300,75);
        stage.addActor(logoActor);
        stage.addActor(tapMessageActor);

        WelcomeScreenInputProcessor p =  new WelcomeScreenInputProcessor(this);
        Gdx.input.setInputProcessor(new GestureDetector(p));
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
    }
}