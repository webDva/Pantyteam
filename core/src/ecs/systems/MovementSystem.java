package ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import ecs.Mappers;
import ecs.components.PhysicsComponent;
import ecs.components.PositionComponent;
import ecs.components.TextureComponent;

public class MovementSystem extends EntitySystem {
	private ImmutableArray<Entity> player;
	public boolean moveLeft, moveRight;

	public MovementSystem() {
		moveLeft = false;
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
			if (moveLeft) {
				Mappers.physics.get(p).body.setLinearVelocity(-10, 0);
			} else if (moveRight) {
				Mappers.physics.get(p).body.setLinearVelocity(10, 0);
			}
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
