package tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.fuorisedelegend.game.GameMainClass;

import sprites.Brick;
import sprites.Cfu;

public class B2WorldCreator {
	public B2WorldCreator (World world, TiledMap map) {
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		//create ground bodies/fixture
		for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle(); //cast from Rectangle to RectangleMapObject
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameMainClass.PPM, (rect.getY() + rect.getHeight() / 2) / GameMainClass.PPM);
			
			body = world.createBody(bdef);
			shape.setAsBox(rect.getWidth() / 2 / GameMainClass.PPM, rect.getHeight() / 2 / GameMainClass.PPM);
			fdef.shape = shape;
			body.createFixture(fdef);
		}
		
		//create pipe bodies/fixture
		for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle(); //cast from Rectangle to RectangleMapObject
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameMainClass.PPM, (rect.getY() + rect.getHeight() / 2) / GameMainClass.PPM);
			
			body = world.createBody(bdef);
			shape.setAsBox(rect.getWidth() / 2 / GameMainClass.PPM, rect.getHeight() / 2 / GameMainClass.PPM);
			fdef.shape = shape;
			body.createFixture(fdef);
		}
		
		//create brick bodies/fixture
		
		for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle(); //cast from Rectangle to RectangleMapObject
			
			new Brick(world,map,rect);
		}
		
		//create coin bodies/fixture
		for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle(); //cast from Rectangle to RectangleMapObject
			
			new Cfu(world,map,rect);
		}
		
	}
}
