package com.juliansaavedra.game.ui;

/**
 * Created by 343076 on 27/05/2015.
 */
public class Box {

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public boolean contains(float x, float y) {
        return x > this.x - width / 2 &&
                x < this.x + width / 2 &&
                y > this.y - height / 2 &&
                y < this.y + height / 2;
    }

}
