package game.pantyteam;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ecs.Mappers;
import ecs.components.PhysicsComponent;
import ecs.components.PositionComponent;
import ecs.components.TextureComponent;
import ecs.systems.RenderSystem;

public class PlayScreen implements Screen {

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private FitViewport viewport;

	private Stage stage;
	private Skin skin; // use skin, if i can

	private World world;
	private Box2DDebugRenderer debugRenderer;

	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;

	MapLayer platformLayer;
	MapObjects platforms;

	Engine engine;
	Entity player;

	TextureAtlas arrows;
	Button leftArrow, rightArrow;

	public PlayScreen(SpriteBatch batch) {
		this.batch = batch;
	}

	@Override
	public void show() {
		camera = new OrthographicCamera();
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		viewport.apply();

		/************************************ Box2D *************************************************/
		Box2D.init();
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();

		/****************************************** Ashley ECS ****************************************************/
		engine = new Engine();
		player = new Entity();
		player.add(new PositionComponent(500, 500));
		player.add(new TextureComponent("images/first_girl.png"));
		player.add(new PhysicsComponent(world, Mappers.position.get(player).x, Mappers.position.get(player).y, Mappers.texture.get(player).texture));
		engine.addEntity(player);

		engine.addSystem(new RenderSystem(batch));

		/************************* User Interface *************************************/
		stage = new Stage(new ScreenViewport(), batch);
		Gdx.input.setInputProcessor(stage);

		Table table = new Table();
		// table.setFillParent(true);
		table.setPosition(50, Gdx.graphics.getHeight() - 70);
		stage.addActor(table);

		table.setDebug(true);

		arrows = new TextureAtlas(Gdx.files.internal("arrows/arrows.atlas"));

		skin = new Skin();
		skin.addRegions(arrows);

		skin.add("my_font", new BitmapFont(), BitmapFont.class);

		LabelStyle labelStyle = new LabelStyle(skin.getFont("my_font"), skin.getFont("my_font").getColor());
		Label label1 = new Label("Health", labelStyle);

		table.add(label1);

		/********************************************************** arrow buttons ********************/
		leftArrow = new Button(skin.getDrawable("left_arrow"));
		leftArrow.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (leftArrow.isPressed()) {
					Mappers.physics.get(player).body.setLinearVelocity(-10, 0);
				}
			}
		});

		rightArrow = new Button(skin.getDrawable("right_arrow"));
		rightArrow.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (rightArrow.isPressed()) {
					Mappers.physics.get(player).body.setLinearVelocity(10, 0);
				}
			}
		});

		Table arrows = new Table(skin);

		arrows.add(leftArrow);
		arrows.add(rightArrow);

		arrows.setDebug(true);
		stage.addActor(arrows);
		arrows.setPosition(200, 150);

		/********************************** Tiled map ***********************************************/
		tiledMap = new TmxMapLoader().load("map.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, batch);

		MapProperties prop = tiledMap.getProperties();
		camera.position.set(prop.get("width", Integer.class) * prop.get("tilewidth", Integer.class) / 2,
				prop.get("height", Integer.class) * prop.get("tileheight", Integer.class) / 2, 0);
		camera.update();
		tiledMapRenderer.setView(camera);

		platformLayer = tiledMap.getLayers().get("platforms");
		platforms = platformLayer.getObjects();

		BodyDef groundBodyDef = new BodyDef();
		RectangleMapObject rect = ((RectangleMapObject) platforms.get("ground"));
		groundBodyDef.position.set(rect.getRectangle().x, rect.getRectangle().y);
		Body groundBody = world.createBody(groundBodyDef);

		PolygonShape groundBox = new PolygonShape();

		groundBox.setAsBox(rect.getRectangle().width / 2, rect.getRectangle().height / 2);
		groundBody.createFixture(groundBox, 0);
		groundBox.dispose();
		groundBody.setTransform(rect.getRectangle().x + rect.getRectangle().width / 2, rect.getRectangle().y + rect.getRectangle().height / 2, groundBody.getAngle());

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);

		tiledMapRenderer.render();

		engine.update(delta);

		batch.begin();
		batch.end();

		debugRenderer.render(world, camera.combined);

		stage.act();
		stage.draw();

		world.step(1 / 45f, 6, 2);
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
		arrows.dispose();
	}

}
