package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.tommyettinger.textra.TypingConfig;
import com.github.tommyettinger.textra.TypingLabel;
import com.ray3k.template.*;
import com.ray3k.template.Resources.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.SpineHeaven.*;

public class HeavenScreen extends JamScreen {
    private Stage stage;
    private final static Color BG_COLOR = new Color(Color.BLACK);
    private SpineDrawable spineDrawable;
    
    @Override
    public void show() {
        super.show();
        
        bgm_ending02.setVolume(sfx);
        bgm_ending02.play();
        
        stage = new Stage(new ScreenViewport(), batch);
        Gdx.input.setInputProcessor(stage);
        
        spineDrawable = new SpineDrawable(skeletonRenderer, skeletonData, animationData);
        spineDrawable.getAnimationState().setAnimation(0, animationAnimation, false);
        var image = new Image(spineDrawable);
        image.setFillParent(true);
        stage.addActor(image);
        
        var root = new Table();
        root.setFillParent(true);
        root.bottom();
        stage.addActor(root);
        
        root.defaults().space(10);
        var textButton = new TextButton("Ascend to Heaven", skin);
        root.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                bgm_ending02.stop();
                bgm_ending03a.setVolume(sfx);
                bgm_ending03a.play();
                core.transition(new CreditsScreen());
            }
        });
    
        textButton = new TextButton("Return to Hell", skin);
        root.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                bgm_ending02.stop();
                bgm_ending03b.setVolume(sfx);
                bgm_ending03b.play();
                core.transition(new CreditsScreen());
            }
        });
    }
    
    @Override
    public void act(float delta) {
        stage.act(delta);
        spineDrawable.update(delta);
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
