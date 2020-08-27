package sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.fuorisedelegend.game.GameMainClass;

import screens.PlayScreen;

public class Fuorisede extends Sprite {
	public World world;
	public Body b2body;
	private TextureRegion fuorisedeStand;
	
	//constructor
	public Fuorisede (World world, PlayScreen screen) {
		super(screen.getAtlas().findRegion("Fuorisede"));
		this.world = world;
		defineFuorisede();
		//fuorisede is located in the square in which the up left diagonal 
		//is in (0,0) and the low right one in (16,16)
		fuorisedeStand = new TextureRegion(getTexture(),0,0,48,32);
		setBounds(0,0,16 / GameMainClass.PPM, 16 / GameMainClass.PPM);
		setRegion(fuorisedeStand);
	}
	
	public void defineFuorisede() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(32 / GameMainClass.PPM,32 / GameMainClass.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(5 / GameMainClass.PPM);
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
		
	}
	
}
