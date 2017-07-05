package com.leapgs.cars.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.leapgs.cars.Actor.EnemyActor;
import com.leapgs.cars.Actor.PlayerActor;
import com.leapgs.cars.Actor.RoadActor;
import com.leapgs.cars.Constants.Constants;
import com.leapgs.cars.InputProcessor.GameplayInputProcessor;
import com.leapgs.cars.MainGame;
import com.leapgs.cars.Model.LevelData;
import com.sun.org.apache.xpath.internal.SourceTree;

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

        currentSameLaneProbability = Constants.LEVEL_1_SAME_LANE_PROBABILITY;

        gameplayInputProcessor = new GameplayInputProcessor(this);

        ran = new Random();

        setUpCurrentLevel(currentLevel);
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

        stage.setDebugAll(game.testing);

    }

    private void addLabelToStage(Label label, float x , float y , float width , float height , Color color) {
        label.setAlignment(Align.center);
        label.setColor(color);
        label.setSize(width,height);
        label.setPosition(x-width/2,y-height/2);
        overlayGroup.addActor(label);
    }

    private void setUpCurrentLevel(int currentLevel) {

        FileHandle file = Gdx.files.local("levels/level"+currentLevel+".json");
        String levelString = file.readString();
        Json json = new Json();

        currentLevelData = json.fromJson(LevelData.class,levelString);
        currentLevelData.calculateLapParameters();

        currentSlotNumbers = currentLevelData.getEnemyNumber()+2;
        currentSpawnStep = currentLevelData.getTimeStep();
        currentSpawnWaitTime = currentLevelData.getSpawnWaitTime();
        lapTime = currentLevelData.getTotalLapTime();
        carsInCompetition = currentLevelData.getEnemyNumber()+1;
        waitedTime = false;
        carsLeft = true;
        spawnTimer=0;
        lapTimer = lapTime;
        currentPassedCars=0;
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
                System.out.println("WAITING TO FIRST SPAWN");
                spawnTimer = spawnTimer+delta;
                if(spawnTimer >= currentSpawnWaitTime)
                {
                    waitedTime=true;
                    spawnTimer = 0;
                }
            }
            else
            {
                System.out.println("SPAWNING TIMER ACTIVATED");
                spawnTimer = spawnTimer+delta;
                if(spawnTimer>=currentSpawnStep)
                {
                    System.out.println("time to spawn");
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