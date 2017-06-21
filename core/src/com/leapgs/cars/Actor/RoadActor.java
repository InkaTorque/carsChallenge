package com.leapgs.cars.Actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.leapgs.cars.Constants.Constants;
import com.leapgs.cars.Screens.GameplayScreen;

/**
 * Created by Leap-Pancho on 6/21/2017.
 */

public class RoadActor extends Actor{

    Texture roadTex;
    float movementSpeed;
    GameplayScreen screen;
    boolean nextRoadSpawned;

    public RoadActor(GameplayScreen screen, float x , float y, int level) {
        roadTex = new Texture("sprites/road.png");
        setSize(roadTex.getWidth()*5,roadTex.getHeight()*5);
        this.screen = screen;
        setPosition(x,y);
        assignMovementSpeed(level);
        nextRoadSpawned = false;
    }

    private void assignMovementSpeed(int level) {
        switch (level)
        {
            case 1:movementSpeed = Constants.roadMovementSpeedLevell;break;
            case 2:movementSpeed = Constants.roadMovementSpeedLevel2;break;
            case 3:movementSpeed = Constants.roadMovementSpeedLevel3;break;
            case 4:movementSpeed = Constants.roadMovementSpeedLevel4;break;
            case 5:movementSpeed = Constants.roadMovementSpeedLevel5;break;
        }
    }

    @Override
    public void act(float delta) {
        setPosition(getX(),getY()-delta*movementSpeed);
        if(getY()+getHeight()<-450)
        {
            remove();
        }
        else
        {
            if(getY()+getHeight()<450 && !nextRoadSpawned)
            {
                nextRoadSpawned=true;
                screen.spawnNewRoad();
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(roadTex , getX(),getY(),getWidth(),getHeight());
    }


}
