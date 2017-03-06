package com.mygdx.game.Actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constantes;

import static com.mygdx.game.Constantes.PIXELS_IN_METER;
import static com.mygdx.game.Constantes.SPEED_PLAYER;

public class Hoonicorn extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private boolean alive = true, jumping = false, mustJump = false;

    public Rectangle getBounds() {
        return bounds;
    }

    public Rectangle bounds;

    public Hoonicorn(World world, Texture texture, Vector2 position) {
        this.world = world;
        this.texture = texture;

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        setSize(PIXELS_IN_METER, PIXELS_IN_METER);
        bounds = new Rectangle(position.x, position.y, getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        if (mustJump) {
            setMustJump(false);
            jump();
        }
        if (isAlive()) {
            float speedY = body.getLinearVelocity().y;
            body.setLinearVelocity(SPEED_PLAYER, speedY);
        }
        if (jumping) {
            body.applyForceToCenter(0, Constantes.IMPULSE_JUMP * -1.1f, true);
        }

        setXY();
    }

    public void jump() {
        jumping = true;
        Vector2 position = body.getPosition();
        body.applyLinearImpulse(0, Constantes.IMPULSE_JUMP, position.x, position.y, true);
    }

    public void setMustJump(boolean mustJump) {
        this.mustJump = mustJump;
    }

    public boolean isMustJump() {
        return mustJump;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f) * PIXELS_IN_METER,
                (body.getPosition().y - 0.5f) * PIXELS_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void setXY() {
        bounds.setPosition(getX(), getY());
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }
}
