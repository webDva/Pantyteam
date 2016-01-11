package ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;

import ecs.Mappers;
import ecs.components.PhysicsComponent;
import ecs.components.PositionComponent;
import ecs.components.TextureComponent;

public class MovementSystem extends EntitySystem {
	private ImmutableArray<Entity> player;
	private boolean moveUp, moveLeft, moveDown, moveRight;

	public MovementSystem() {
		moveUp = false;
		moveLeft = false;
		moveDown = false;
		moveRight = false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addedToEngine(Engine engine) {
		player = engine.getEntitiesFor(Family.all(PositionComponent.class, TextureComponent.class, PhysicsComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		for (Entity p : player) {
			if (moveLeft)
				Mappers.physics.get(p).body.applyLinearImpulse(new Vector2(-100, 0), Mappers.physics.get(p).body.getPosition(), true);
			if (moveRight)
				Mappers.physics.get(p).body.applyLinearImpulse(new Vector2(100, 0), Mappers.physics.get(p).body.getPosition(), true);
		}
	}

	public void moveLeft(boolean t) {
		if (t)
			this.moveLeft = true;
		else
			this.moveLeft = false;
	}

	public void moveRight(boolean t) {
		if (t)
			this.moveRight = true;
		else
			this.moveRight = false;
	}

}
