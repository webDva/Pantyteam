package ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureComponent implements Component {
	public Texture texture;

	public TextureComponent(String path) {
		this.texture = new Texture(Gdx.files.internal(path));
	}

}
