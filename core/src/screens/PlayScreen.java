package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fuorisedelegend.game.GameMainClass;

import scenes.Hud;
import sprites.Fuorisede;
import tools.B2WorldCreator;

public class PlayScreen implements Screen {
	
	private GameMainClass game;
	private TextureAtlas atlas;
	//the gamecam is what the viewport actually displays
	private OrthographicCamera gamecam;
	//a viewport is a window in our game and how the game looks in each device
	private Viewport gamePort;
	private Hud hud;
	
	//Tiled map variables
	private TmxMapLoader maploader; //to load the map 
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	//Box2d variables
	private World world;
	private Box2DDebugRenderer b2dr; 
	
	//player
	private Fuorisede player;
	
	//constructor
	public PlayScreen(GameMainClass game) {
		atlas = new TextureAtlas("Fuorisede and Enemies.pack");
		
		this.game = game;
		gamecam = new OrthographicCamera();
		//Fitviewport keeps the aspect ratio by scaling the world up to fit the screen,
		//adding black bars for the remaining space (the best one)
		//(There are the Stretch one, in which the world is scaled to take the whole screen,
		//and the Screen one, in which the world size is based on the size of the screen)
		gamePort = new FitViewport(GameMainClass.V_WIDTH / GameMainClass.PPM,GameMainClass.V_HEIGHT / GameMainClass.PPM,gamecam);
		hud = new Hud (game.batch);
		
		maploader = new TmxMapLoader();
		map = maploader.load("level1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / GameMainClass.PPM);
		
		//initially set the gamecam to be centered correctly 
		gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		
		//Vector2 is for gravity, for now there's not
		world= new World(new Vector2(0, -10), true);
		b2dr = new Box2DDebugRenderer();
		
		new B2WorldCreator(world,map);
		
		//player creating
		player= new Fuorisede(world, this);
		
	}
	
	public TextureAtlas getAtlas() {
		return atlas;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	public void handleInput(float dt){
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
			player.b2body.applyLinearImpulse(new Vector2(0,4f), player.b2body.getWorldCenter(),true);
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
			player.b2body.applyLinearImpulse(new Vector2(0.1f, 4), player.b2body.getWorldCenter(), true);
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
			player.b2body.applyLinearImpulse(new Vector2(-0.1f, 4), player.b2body.getWorldCenter(), true);	
	}
	
	
	public void update (float dt) {
		//check if is there any input (like a tap)
		handleInput(dt);
		
		world.step(1/60f, 6, 2);
		
		gamecam.position.x = player.b2body.getPosition().x;
		
		//update our gamecam with correct coordinates after changes
		gamecam.update();
		//tell our renderer to draw only what our camera can see in our game world
		renderer.setView(gamecam);
	}

	@Override
	public void render(float delta) {
		//separate our update logic from render
		update(delta);
		
		//clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1); //set the screen color as black
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //actually clear the screen
		
		//render our game map
		renderer.render();
		
		//renderer our Box2dDebugLines
		b2dr.render(world, gamecam.combined);
		
		game.batch.setProjectionMatrix(gamecam.combined);
		game.batch.begin();
		player.draw(game.batch);
		game.batch.end();
		
		//set our batch to now draw what the Hud camera sees
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();
		hud.dispose();
		
	}

}
