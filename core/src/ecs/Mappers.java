package ecs;

import com.badlogic.ashley.core.ComponentMapper;

import ecs.components.PhysicsComponent;

public class Mappers {
	public static final ComponentMapper<PhysicsComponent> physics = ComponentMapper.getFor(PhysicsComponent.class);
}
