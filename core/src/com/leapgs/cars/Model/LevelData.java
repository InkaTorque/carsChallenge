package com.leapgs.cars.Model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Leap-Pancho on 6/21/2017.
 */

public class LevelData {

    public float time,spawnWaitTime,timeStep,totalLapTime;
    public int enemyNumber;

    public LevelData(float time,float spawnWaitTime , int enemyNumber) {
        this.time = time;
        this.spawnWaitTime = spawnWaitTime;
        this.enemyNumber = enemyNumber;
        this.timeStep = time/(enemyNumber+2);
        this.totalLapTime = time+spawnWaitTime;

    }
}