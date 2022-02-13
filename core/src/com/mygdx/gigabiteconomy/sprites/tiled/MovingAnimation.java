package com.mygdx.gigabiteconomy.sprites.tiled;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class MovingAnimation<T> extends Animation {
    private float movementTime = 0f;

    public MovingAnimation(float frameDuration, Array keyFrames, boolean looping) {
        super(frameDuration, keyFrames, looping ? PlayMode.LOOP : PlayMode.NORMAL);
    }

    public float getMovementTime() {
        return movementTime;
    }

    public Object runAnimation(float delta) {
        this.movementTime += delta;
        Object ret = getKeyFrame(this.movementTime);

        return ret;
    }

    public boolean isFinished(float delta) {
        System.out.println(movementTime+delta);
        if (this.isAnimationFinished(movementTime+delta)) {
            movementTime = 0f;
            return true;
        } else {
            return false;
        }
    }
}
