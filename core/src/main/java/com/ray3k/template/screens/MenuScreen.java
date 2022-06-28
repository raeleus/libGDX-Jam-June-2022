package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.template.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.screens.GameScreen.*;

public class MenuScreen extends JamScreen {
    private Stage stage;
    private final static Color BG_COLOR = new Color(Color.BLACK);
    
    @Override
    public void show() {
        super.show();
    
        stage = new Stage(new ScreenViewport(), batch);
        Gdx.input.setInputProcessor(stage);
    
        if (bgm_game.isPlaying()) {
            stage.addAction(new Action() {
                @Override
                public boolean act(float delta) {
                    bgm_game.setVolume(Utils.approach(bgm_game.getVolume(), 0, .25f * delta));
                    if (MathUtils.isZero(bgm_game.getVolume())) {
                        bgm_game.stop();
                        return true;
                    } else return false;
                }
            });
        }
        
        if (!bgm_menu.isPlaying()) {
            bgm_menu.play();
            bgm_menu.setPosition(bgm_game.getPosition());
            bgm_menu.setVolume(0);
            bgm_menu.setLooping(true);
            stage.addAction(new Action() {
                @Override
                public boolean act(float delta) {
                    bgm_menu.setVolume(Utils.approach(bgm_menu.getVolume(), bgm, .25f * delta));
                    return MathUtils.isEqual(bgm_menu.getVolume(), bgm);
                }
            });
        }
    
        sceneBuilder.build(stage, skin, Gdx.files.internal("menus/main.json"));
        TextButton textButton = stage.getRoot().findActor("play");
        textButton.addListener(sndChangeListener);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.setInputProcessor(null);
    
                powers.clear();
                maxHealth = 3;
                health = maxHealth;
                maxEnergy = 6;
                
                if (!preferences.getBoolean("completedTutorial", false)) {
                    core.transition(new GameScreen("tutorial01"));
                } else {
                    core.transition(new GameScreen("home"));
                }
            }
        });
    
        textButton = stage.getRoot().findActor("options");
        textButton.addListener(sndChangeListener);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.setInputProcessor(null);
                core.transition(new OptionsScreen());
            }
        });
    
        textButton = stage.getRoot().findActor("credits");
        textButton.addListener(sndChangeListener);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.setInputProcessor(null);
                core.transition(new CreditsScreen());
            }
        });
    
        var wolfButton = (ImageButton) stage.getRoot().findActor("wolf");
        wolfButton.setChecked(wolfMode);
        wolfButton.addListener(sndChangeListener);
        wolfButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                wolfMode = wolfButton.isChecked();
            }
        });
    
        var pizzaButton = (ImageButton) stage.getRoot().findActor("pizza");
        pizzaButton.setChecked(pizzaMode);
        pizzaButton.addListener(sndChangeListener);
        pizzaButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                pizzaMode = pizzaButton.isChecked();
            }
        });
    }
    
    @Override
    public void act(float delta) {
        stage.act(delta);
    }
    
    @Override
    public void draw(float delta) {
        Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        stage.draw();
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    
    @Override
    public void pause() {
    
    }
    
    @Override
    public void resume() {
    
    }
    
    @Override
    public void dispose() {
    
    }
}
