package sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.fuorisedelegend.game.GameMainClass;

import screens.PlayScreen;

public class Fuorisede extends Sprite {
	public enum State {FALLING, JUMPING, STANDING, RUNNING};
	public State currentState;
	public State previousState;
	public World world;
	public Body b2body;
	private TextureRegion fuorisedeStand;
	private Animation fuorisedeRun;
	private Animation fuorisedeJump;
	private float stateTimer;
	private boolean runningRight; 
	
	//constructor
	public Fuorisede (World world, PlayScreen screen) {
		super(screen.getAtlas().findRegion("Fuorisede"));
		this.world = world;
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		runningRight = true;
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		frames.add(new TextureRegion(getTexture(), 7*16 ,0,16,16));
		fuorisedeRun = new Animation(0.1f, frames);
		frames.clear();
		
		frames.add(new TextureRegion(getTexture(),7*16,24,16,16));
		fuorisedeJump = new Animation(0.1f, frames);
		
		//fuorisede is located in the square in which the up left diagonal 
		//is in (96,0) and the low right one (from the previous point) in (16,16)
		fuorisedeStand = new TextureRegion(getTexture(),96,0,16,16);
		
		defineFuorisede();
		setBounds(0,0,16 / GameMainClass.PPM, 16 / GameMainClass.PPM);
		setRegion(fuorisedeStand);
	}
	
	public void update (float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));
	}
	
	public TextureRegion getFrame (float dt) {
		currentState = getState();
		
		TextureRegion region;
		switch(currentState) {
			case JUMPING:
				region = (TextureRegion) fuorisedeJump.getKeyFrame(stateTimer);
				break;
			case RUNNING:
				region = (TextureRegion) fuorisedeRun.getKeyFrame(stateTimer, true);
				break;
			case FALLING:
			case STANDING:
				default:
					region = fuorisedeStand;
					break;
		}
		
		if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
			region.flip(true, false);
			runningRight = false;
			}
		else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
			region.flip(true, false);
			runningRight = true;
		}
		
		stateTimer= currentState == previousState ? stateTimer + dt : 0;
		previousState = currentState;
		return region;
		
	}
	
	public State getState() {
		if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
			return State.JUMPING;
		else if (b2body.getLinearVelocity().y < 0)
			return State.FALLING;
		else if (b2body.getLinearVelocity().x != 0)
			return State.RUNNING;
		else 
			return State.STANDING;
	}
	
	public void defineFuorisede() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(32 / GameMainClass.PPM, 32 / GameMainClass.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(6 / GameMainClass.PPM);
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
		
		EdgeShape head = new EdgeShape();
		head.set(new Vector2(-2 / GameMainClass.PPM,  6 / GameMainClass.PPM), new Vector2(2 / GameMainClass.PPM, 6 / GameMainClass.PPM));
		fdef.shape = head;
		fdef.isSensor = true;
		
		b2body.createFixture(fdef).setUserData("head");
		
	}
	
}
