package ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.Batch;

import ecs.Mappers;
import ecs.components.PhysicsComponent;
import ecs.components.PositionComponent;
import ecs.components.TextureComponent;

public class RenderSystem extends EntitySystem {
	private ImmutableArray<Entity> entitiestoRender;
	private Batch batch;

	public RenderSystem(Batch batch) {
		this.batch = batch;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		entitiestoRender = engine.getEntitiesFor(Family.all(PositionComponent.class, TextureComponent.class, PhysicsComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		for (Entity e : entitiestoRender) {
			Mappers.position.get(e).x = Mappers.physics.get(e).body.getPosition().x;
			Mappers.position.get(e).y = Mappers.physics.get(e).body.getPosition().y;
		}

		batch.setProjectionMatrix(batch.getProjectionMatrix());
		batch.begin();

		for (Entity e : entitiestoRender) {
			batch.draw(Mappers.texture.get(e).texture, Mappers.position.get(e).x, Mappers.position.get(e).y);
		}

		batch.end();
	}

}
