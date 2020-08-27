package scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fuorisedelegend.game.GameMainClass;

//using the scene2d.ui (provided by libgdx), is a layout manager for widget that we can place on the screen, 
//such as labels (plain text placed on "scenes"), that we can update when things change (like time or score)
public class Hud implements Disposable{ //hud is a status bar
	//stage is like an empty box, in which we have to put a table to guarantee some organization
	public Stage stage;
	private Viewport viewport;
	
	private Integer yearTimer;
	private float timeCount;
	private Integer score;
	
	Label countdownLabel;
	Label scoreLabel;
	Label timeLabel;
	Label levelLabel;
	Label yearLabel;
	Label fuorisedeLabel;
	
	//constructor 
	public Hud(SpriteBatch sb) {
		yearTimer = 300;
		timeCount = 0;
		score = 0;
		
		viewport = new FitViewport(GameMainClass.V_WIDTH,GameMainClass.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport,sb);
		
		Table table = new Table();
		//we need that to start from the top of the table (instead of the center)
		table.top(); 
		table.setFillParent(true); //table now is the size of our stage
		
		// "%03" is how many number is the countdown timer there will be, while the "bitmapfont" is set as the standard one
		countdownLabel = new Label(String.format("%03d", yearTimer), new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		levelLabel = new Label("1", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		yearLabel = new Label("YEAR", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		fuorisedeLabel = new Label("FUORISEDE", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		
		//expanding the labels on the length of the screen (X length, Y height)
		//if we have multiple text on the same row, the "expandX" will share them in equal portion of the screen
		table.add(fuorisedeLabel).expandX().padTop(10);
		table.add(yearLabel).expandX().padTop(10);
		table.add(timeLabel).expandX().padTop(10);
		table.row(); //everything below this line will be on a new row
		table.add(scoreLabel).expandX();
		table.add(levelLabel).expandX();
		table.add(countdownLabel).expandX();
		
		stage.addActor(table);
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}
}
