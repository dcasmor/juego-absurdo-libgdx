package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game.Actores.Hoonicorn;
import com.mygdx.game.Actores.Nube;
import com.mygdx.game.Actores.Plane;

import java.util.ArrayList;
import java.util.List;


public class GameScreen extends BaseScreen {
    private Stage stage;
    private World world;
    private Hoonicorn hoonicorn;

    private Sound jumpSound, dieSound, fall;
    private boolean sonido;

    private Texture cloud, plane;
    private List<Nube> listaNube = new ArrayList<Nube>();
    private List<Plane> listaPlane = new ArrayList<Plane>();

    public GameScreen(MyGdxGame game) {
        super(game);
        sonido = false;
        jumpSound = game.getManager().get("audio/pedo.ogg");
        cloud = game.getManager().get("cloud.png");
        plane = game.getManager().get("plane.png");
        dieSound = game.getManager().get("audio/impact.mp3");
        fall = game.getManager().get("audio/fall.mp3");
        //music = game.getManager().get("audio/song.ogg");
        stage = new Stage(new ExtendViewport(1080, 1920));
        world = new World(new Vector2(0, -10), true);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                generaPlane();
            }
        }, 0.5f, 0.75f);

    }

    @Override
    public void show() {
        Texture playerTexture = game.getManager().get("unicornio.png");
        hoonicorn = new Hoonicorn(world, playerTexture, new Vector2(1.5f, 12.5f));

        stage.addActor(hoonicorn);
    }

    @Override
    public void hide() {
        //hoonicorn.detach();
        hoonicorn.remove();

        for (Nube nube : listaNube) {
            nube.remove();
        }

        for (Plane plane : listaPlane) {
            plane.remove();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (Plane plane : listaPlane) {
            if (hoonicorn.getBounds().overlaps(plane.getBounds()) && !sonido) {
                dieSound.play();
                fall.play();
                sonido = true;
                hoonicorn.setAlive(false);
                System.out.println("Game Over");
            }
        }

        if (hoonicorn.getX() > 150) {
            stage.getCamera().translate(Constantes.SPEED_PLAYER * delta * Constantes.PIXELS_IN_METER, 0, 0);
        }

        if (hoonicorn.getY() < 0 - hoonicorn.getHeight()) {
            hoonicorn.setMustJump(false);
            hoonicorn.setAlive(false);
        }

        if (Gdx.input.justTouched() && hoonicorn.isAlive()) {
            generaNube();
            jumpSound.play();
            hoonicorn.jump();
        }
        stage.act();
        world.step(delta, 6, 2);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }

    public void generaNube() {
        Nube n = new Nube(world, cloud, hoonicorn.getX(), hoonicorn.getY());
        listaNube.add(n);
        for (Nube nube : listaNube) {
            stage.addActor(nube);
        }
    }

    public void generaPlane() {
        float aux = MathUtils.random(120, 1620);
        Plane n = new Plane(world, plane, hoonicorn.getX() + 2750, aux);
        listaPlane.add(n);
        for (Plane plane : listaPlane) {
            stage.addActor(plane);
        }
    }
}
