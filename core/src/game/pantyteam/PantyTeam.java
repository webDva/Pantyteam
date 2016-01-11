package game.pantyteam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PantyTeam extends Game {
	SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(batch));
	}

	@Override
	public void render() {
		super.render();
	}
}
