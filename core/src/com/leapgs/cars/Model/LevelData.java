package com.leapgs.cars.Model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Leap-Pancho on 6/21/2017.
 */

public class LevelData {

    private float time,spawnWaitTime,timeStep,totalLapTime;
    private int enemyNumber;

    public LevelData(float time,float spawnWaitTime , int enemyNumber) {
        this.time = time;
        this.spawnWaitTime = spawnWaitTime;
        this.enemyNumber = enemyNumber;
        calculateLapParameters();
    }

    public LevelData() {

    }

    public void calculateLapParameters() {
        this.timeStep = time/(enemyNumber+2);
        this.totalLapTime = time+spawnWaitTime;
    }

    public float getSpawnWaitTime() {
        return spawnWaitTime;
    }

    public float getTimeStep() {
        return timeStep;
    }

    public float getTotalLapTime() {
        return totalLapTime;
    }

    public int getEnemyNumber() {
        return enemyNumber;
    }
}