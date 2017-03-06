package com.mygdx.game.Actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mygdx.game.Constantes.PIXELS_IN_METER;


public class Plane extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    public Rectangle getBounds() {
        return bounds;
    }

    private Rectangle bounds;

    public Plane(World world, Texture texture, float x, float y) {
        this.world = world;
        this.texture = texture;

        BodyDef def = new BodyDef();
        def.position.set(x, y);
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        /*Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(-0.5f, 0);
        vertices[1] = new Vector2(-0.25f, 0.25f);
        vertices[2] = new Vector2(0.45f, 0);
        vertices[3] = new Vector2(0.5f, 0);
        vertices[4] = new Vector2(-0.25f, -0.5f);*/
        box.setAsBox(0.5f, 0.5f);

        fixture = body.createFixture(box, 3);

        fixture.setUserData("plane");
        box.dispose();

        bounds = new Rectangle(x, y, getWidth(), getHeight());

        setSize(PIXELS_IN_METER, PIXELS_IN_METER);
        setPosition(x, y);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setXY();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void setXY() {
        bounds.setPosition(getX(), getY());
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
