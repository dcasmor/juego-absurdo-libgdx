package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class MyGdxGame extends Game {

    private AssetManager manager;

    public AssetManager getManager() {
        return manager;
    }

    @Override
    public void create() {
        manager = new AssetManager();
        manager.load("cloud.png", Texture.class);
        manager.load("plane.png", Texture.class);
        manager.load("unicornio.png", Texture.class);
        manager.load("audio/impact.mp3", Sound.class);
        manager.load("audio/fall.mp3", Sound.class);
        manager.load("audio/pedo.ogg", Sound.class);
       // manager.load("audio/song.ogg", Music.class);
        manager.finishLoading();
        setScreen(new GameScreen(this));
    }
}
