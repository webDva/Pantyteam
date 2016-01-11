package game.pantyteam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PlayScreen implements Screen {

	private SpriteBatch batch;
	Texture img;
	private OrthographicCamera camera;
	private FitViewport viewport;

	private Stage stage;
	private Skin skin; // use skin, if i can

	public PlayScreen(SpriteBatch batch) {
		this.batch = batch;
	}

	@Override
	public void show() {
		img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera();
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		viewport.apply();

		stage = new Stage(new ScreenViewport(), batch);
		Gdx.input.setInputProcessor(stage);

		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		table.setDebug(true);

		skin = new Skin();

		LabelStyle labelStyle = new LabelStyle(new BitmapFont(), Color.WHITE);
		Label label1 = new Label("Health", labelStyle);

		table.add(label1);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();

		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		stage.getViewport().update(width, height, true);
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
		stage.dispose();
	}

}
