package com.leapgs.cars.Actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.leapgs.cars.Constants.Constants;
import com.leapgs.cars.Screens.GameplayScreen;

/**
 * Created by Leap-Pancho on 6/21/2017.
 */

public class EnemyActor extends Actor{

    public Rectangle bounds;
    Texture enemyText;
    float movementSpeed;
    GameplayScreen screen;
    PlayerActor player;
    boolean alreadyCollided;

    public EnemyActor(GameplayScreen screen, float x , float y, int level,PlayerActor player) {
        enemyText = new Texture("sprites/enemy.png");
        setSize(enemyText.getWidth()*5,enemyText.getHeight()*5);
        this.screen = screen;
        setPosition(x,y);
        this.player = player;
        assignMovementSpeed(level);
        alreadyCollided = false;
        bounds=new Rectangle((int)getX(), (int)getY(), (int)getWidth(), (int)getHeight());
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
        bounds.set(getX(),getY(),getWidth(),getHeight());
        if(checkCollision())
        {
            setPosition(getX(),getY()+delta*movementSpeed);
            if(getY()+getHeight()>450)
            {
                remove();
            }
        }
        else
        {
            setPosition(getX(),getY()-delta*movementSpeed);
            if(getY()+getHeight()<-25)
            {
                remove();
                screen.addPassedCarsCounter();
            }
        }


    }

    private boolean checkCollision() {

        if(alreadyCollided)
            return true;
        if(bounds.overlaps(player.bounds))
        {
            alreadyCollided=true;
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(enemyText , getX(),getY(),getWidth(),getHeight());
    }
}
