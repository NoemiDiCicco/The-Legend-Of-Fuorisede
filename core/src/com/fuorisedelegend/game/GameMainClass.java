package com.fuorisedelegend.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.PlayScreen;

public class GameMainClass extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;	
	public static final float PPM = 100; //pixel per meter
	
	public SpriteBatch batch; //spritebatch is a container that holds all of the images,texture and stuff
	//only one in our game because it uses a lot of memory
	//the batch is public because we want every screen to use it
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen (this)); 
		//we pass the game itself to the class so it can set screens in the future 
	}

	@Override
	public void render () {
		super.render(); //delegates the render method to PlayScreen class
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
