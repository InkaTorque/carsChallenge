package com.leapgs.cars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.leapgs.cars.Actor.EnemyActor;
import com.leapgs.cars.Actor.PlayerActor;
import com.leapgs.cars.Actor.RoadActor;
import com.leapgs.cars.MainGame;
import com.leapgs.cars.Model.LevelData;

import java.util.Random;

/**
 * Created by Leap-Pancho on 6/21/2017.
 */

public class GameplayScreen extends BaseScreen {

    private float currentPoints , currentSpawnWaitTime,currentSpawnStep,currentSlotNumbers,currentPassedCars,spawnTimer,lapTime,lapTimer;
    private boolean won,waitedTime,carsLeft;
    private int currentLevel,spawnedCounter;
    private PlayerActor player;
    private LevelData currentLevelData;

    private Group carsGroup,backgroundGroup;

    public GameplayScreen(MainGame mainGame, int level) {
        super(mainGame);
        currentLevel = level;

        won = false;

        carsGroup = new Group();
        backgroundGroup = new Group();

        setUpLevelData(currentLevel);
    }

    private void setUpLevelData(int currentLevel) {
        switch (currentLevel)
        {
            case 1: currentLevelData = new LevelData(60,2.5F,10);
            case 2: currentLevelData = new LevelData(60,2.5F,10);
            case 3: currentLevelData = new LevelData(60,2.5F,10);
            case 4: currentLevelData = new LevelData(60,2.5F,10);
            case 5: currentLevelData = new LevelData(60,2.5F,10);
        }
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);

        RoadActor rd = new RoadActor(this,0,0,currentLevel);
        player = new PlayerActor(this,75,25);

        backgroundGroup.addActor(rd);
        carsGroup.addActor(player);

        stage.addActor(backgroundGroup);
        stage.addActor(carsGroup);

        //stage.setDebugAll(true);
        setUpCurrentLevel();

    }

    private void addLabelToStage(Label label, float x , float y , float width , float height , Color color) {
        label.setAlignment(Align.center);
        label.setColor(color);
        label.setSize(width,height);
        label.setPosition(x,y);
        stage.addActor(label);
    }

    private void setUpCurrentLevel() {
        currentPassedCars=0;
        currentSlotNumbers = currentLevelData.enemyNumber+2;
        currentSpawnStep = currentLevelData.timeStep;
        currentSpawnWaitTime = currentLevelData.spawnWaitTime;
        lapTime = currentLevelData.totalLapTime;
        waitedTime = false;
        carsLeft = true;
        spawnTimer=0;
        lapTimer = lapTime;
    }



    @Override
    public void render(float delta) {
        super.render(delta);

        lapTimer = lapTimer - delta;
        if(lapTimer<=0)
        {
            endGame();
        }

        if(carsLeft)
        {
            if(!waitedTime)
            {
                spawnTimer = spawnTimer+delta;
                if(spawnTimer >= currentSpawnWaitTime)
                {
                    waitedTime=true;
                    spawnTimer = 0;
                }
            }
            else
            {
                spawnTimer = spawnTimer+delta;
                if(spawnTimer>=currentSpawnStep)
                {
                    spawnTimer=0f;
                    spawnNewEnemy();
                    spawnedCounter++;
                    if(spawnedCounter==currentSlotNumbers)
                    {
                        carsLeft=false;
                    }
                }

            }
        }
    }

    private void spawnNewEnemy() {
        EnemyActor ea = new EnemyActor(this,135,435,currentLevel);

        carsGroup.addActor(ea);
    }

    public void spawnNewRoad()
    {
        RoadActor rd = new RoadActor(this,0,435,currentLevel);

        backgroundGroup.addActor(rd);
    }

    private void endGame() {

        if (game.scorePrefs.getFloat("highScore"+currentLevel, -1000) == -1000)
        {
            game.scorePrefs.putFloat("highScore"+currentLevel,currentPoints);
        }
        else
        {
            if(game.scorePrefs.getFloat("highScore"+currentLevel)<currentPoints)
            {
                game.scorePrefs.putFloat("highScore"+currentLevel,currentPoints);
            }
        }
        game.scorePrefs.putFloat("currentScore"+currentLevel,currentPoints);


        game.goToResultsScreen(currentLevel,won);
        game.scorePrefs.flush();

    }
}