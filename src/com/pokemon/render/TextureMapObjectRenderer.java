package com.pokemon.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.pokemon.pantallas.ObjetoMapa;

public class TextureMapObjectRenderer extends OrthogonalTiledMapRenderer {

	public TextureMapObjectRenderer(TiledMap map) {
		super(map);
		// TODO Auto-generated constructor stub
	}

	public void renderObject(ObjetoMapa object) {
		if (object instanceof ObjetoMapa) {			
			if (object.getProperties().containsKey("mostrar")
					&& object.getProperties().get("mostrar").equals("false")) {
				return;
			}
			TextureMapObject texture = (TextureMapObject) object.getObj();
			batch.draw(texture.getTextureRegion(), texture.getX(), texture.getY());
		}
	}
}
