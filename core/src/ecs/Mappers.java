package ecs;

import com.badlogic.ashley.core.ComponentMapper;

import ecs.components.PhysicsComponent;
import ecs.components.PositionComponent;

public class Mappers {
	public static final ComponentMapper<PhysicsComponent> physics = ComponentMapper.getFor(PhysicsComponent.class);
	public static final ComponentMapper<PositionComponent> position = ComponentMapper.getFor(PositionComponent.class);
}
