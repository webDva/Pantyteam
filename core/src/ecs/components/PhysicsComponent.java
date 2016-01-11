package ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsComponent implements Component {
	public BodyDef bodyDef;
	public Body body;
	public FixtureDef fixtureDef;
	public Fixture fixture;

	public PhysicsComponent(World world, float x, float y, Texture texture) {
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;

		body = world.createBody(bodyDef);

		PolygonShape box = new PolygonShape();
		box.setAsBox(texture.getWidth() / 2, texture.getHeight() / 2);

		fixtureDef = new FixtureDef();
		fixtureDef.shape = box;
		fixtureDef.density = 0.2f;
		fixtureDef.friction = 0.6f;
		fixtureDef.restitution = 0;

		fixture = body.createFixture(fixtureDef);

		box.dispose();
	}

}
