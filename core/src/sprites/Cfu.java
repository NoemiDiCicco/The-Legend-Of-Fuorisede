package sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.fuorisedelegend.game.GameMainClass;

public class Cfu extends InteractiveTileObject{

	public Cfu(World world, TiledMap map, Rectangle bounds) {
		super(world, map, bounds);
		fixture.setUserData(this);
		setCategoryFilter(GameMainClass.CFU_BIT);
	}

	@Override
	public void onHeadHit() {
		Gdx.app.log("Cfu","Collision");
		
	}
	
}
