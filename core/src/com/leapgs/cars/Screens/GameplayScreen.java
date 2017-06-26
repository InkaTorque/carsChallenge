package com.leapgs.cars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.leapgs.cars.Actor.EnemyActor;
import com.leapgs.cars.Actor.PlayerActor;
import com.leapgs.cars.Actor.RoadActor;
import com.leapgs.cars.Constants.Constants;
import com.leapgs.cars.InputProcessor.GameplayInputProcessor;
import com.leapgs.cars.MainGame;
import com.leapgs.cars.Model.LevelData;

import java.util.Random;

/**
 * Created by Leap-Pancho on 6/21/2017.
 */

public class GameplayScreen extends BaseScreen {

    private float currentPoints , currentSpawnWaitTime,currentSpawnStep;
    private float currentSlotNumbers,currentPassedCars,spawnTimer,lapTime,lapTimer,currentPlayerLane;
    private boolean won,waitedTime,carsLeft;
    private int currentLevel,spawnedCounter,currentSameLaneProbability,carsInCompetition;
    private PlayerActor player;
    private LevelData currentLevelData;

    private Random ran;

    private Group carsGroup,backgroundGroup;

    GameplayInputProcessor gameplayInputProcessor;

    Label metersLeftLabel,positionLabel;
    private Label.LabelStyle style;

    public GameplayScreen(MainGame mainGame, int level) {
        super(mainGame);
        currentLevel = level;

        style = new Label.LabelStyle();
        style.font=game.font;
        style.fontColor= Color.WHITE;

        metersLeftLabel = new Label("",style);
        positionLabel = new Label("",style);

        won = false;

        carsGroup = new Group();
        backgroundGroup = new Group();

        setUpLevelData(currentLevel);

        currentSameLaneProbability = Constants.LEVEL_1_SAME_LANE_PROBABILITY;

        gameplayInputProcessor = new GameplayInputProcessor(this);

        ran = new Random();
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
        Gdx.input.setInputProcessor(new GestureDetector(gameplayInputProcessor));


        addLabelToStage(metersLeftLabel,200,375,150,25,Color.WHITE);
        addLabelToStage(positionLabel,200,350,150,25,Color.WHITE);

        RoadActor rd = new RoadActor(this,0,0,currentLevel);
        player = new PlayerActor(this, Constants.rightLanePosition,25);
        currentPlayerLane = Constants.rightLanePosition;

        backgroundGroup.addActor(rd);
        carsGroup.addActor(player);

        gameplayGroup.addActorAt(0,backgroundGroup);
        gameplayGroup.addActorAt(1,carsGroup);

        //stage.setDebugAll(game.testing);
        setUpCurrentLevel();

    }

    private void addLabelToStage(Label label, float x , float y , float width , float height , Color color) {
        label.setAlignment(Align.center);
        label.setColor(color);
        label.setSize(width,height);
        label.setPosition(x-width/2,y-height/2);
        overlayGroup.addActor(label);
    }

    private void setUpCurrentLevel() {
        currentPassedCars=0;
        currentSlotNumbers = currentLevelData.enemyNumber+2;
        currentSpawnStep = currentLevelData.timeStep;
        currentSpawnWaitTime = currentLevelData.spawnWaitTime;
        lapTime = currentLevelData.totalLapTime;
        carsInCompetition = currentLevelData.enemyNumber+1;
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
            lapTimer=0;
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

        metersLeftLabel.setText("METERS LEFT : "+lapTimer);
        positionLabel.setText("PLACE "+(int)(carsInCompetition-currentPassedCars-1)+"/"+carsInCompetition);
    }

    private void spawnNewEnemy() {

        EnemyActor ea;
        int index;

        index = ran.nextInt(100);

        if(index<=currentSameLaneProbability)
        {
            ea = new EnemyActor(this,currentPlayerLane,435,currentLevel,player);
            currentSameLaneProbability = Constants.LEVEL_1_SAME_LANE_PROBABILITY;
        }
        else
        {
            if(currentPlayerLane == Constants.rightLanePosition)
            {
                ea = new EnemyActor(this,Constants.leftLanePosition,435,currentLevel,player);
            }
            else
            {
                ea = new EnemyActor(this,Constants.rightLanePosition,435,currentLevel,player);
            }
            switch (currentSameLaneProbability)
            {
                case Constants.LEVEL_1_SAME_LANE_PROBABILITY :
                    currentSameLaneProbability = Constants.LEVEL_2_SAME_LANE_PROBABILITY;break;
                case Constants.LEVEL_2_SAME_LANE_PROBABILITY :
                    currentSameLaneProbability = Constants.LEVEL_3_SAME_LANE_PROBABILITY;break;
            }
        }

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

    public void changePlayerLane() {

        if(player.getX()==Constants.leftLanePosition)
        {
            player.setX(Constants.rightLanePosition);
            currentPlayerLane=Constants.rightLanePosition;
        }
        else
        {
            player.setX(Constants.leftLanePosition);
            currentPlayerLane = Constants.leftLanePosition;
        }
    }

    public void addPassedCarsCounter()
    {
        currentPassedCars++;
    }
}