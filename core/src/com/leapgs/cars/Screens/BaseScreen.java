package com.leapgs.cars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.leapgs.cars.Actor.ScreenOverlayActor;
import com.leapgs.cars.MainGame;

/**
 * Created by Leap-Pancho on 6/21/2017.
 */

public class BaseScreen implements Screen {

    public MainGame game;

    //Camera
    public OrthographicCamera camera;
    public Viewport viewport;

    Stage stage;

    ScreenOverlayActor overlayActor;

    public BaseScreen(MainGame mainGame)
    {
        this.game = mainGame;

        //Camera Settings
        camera=new OrthographicCamera();
        camera.setToOrtho(false, game.window.width, game.window.height);

        if(game.testing) viewport=new FitViewport(game.window.width*1.65f, game.window.height*1.65f, camera);
        else viewport=new FitViewport(game.window.width, game.window.height, camera); //Keep Screen Size

    }

    @Override
    public void show() {
        stage = new Stage(viewport);
        overlayActor = new ScreenOverlayActor(game);
        stage.addActor(overlayActor);
    }


    public void render(float delta) {
        Gdx.gl20.glClearColor(1.0f,1.0f,1.0f,1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.draw();


    }
    public void render(float delta,float r, float g, float b) {
        Gdx.gl20.glClearColor(r,g,b,1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void dispose() {
    }
}
