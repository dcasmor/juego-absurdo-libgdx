package com.mygdx.game.Actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mygdx.game.Constantes.PIXELS_IN_METER;

public class Nube extends Actor {

    private Texture texture;
    private World world;

    public Nube(World world, Texture texture, float x, float y) {
        this.world = world;
        this.texture = texture;

        setSize(PIXELS_IN_METER, PIXELS_IN_METER);
        setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }
}
