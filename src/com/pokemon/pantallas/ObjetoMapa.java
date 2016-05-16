package com.pokemon.pantallas;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;

public class ObjetoMapa implements Serializable {
	private static final long serialVersionUID = 2341561L;
	
	private HashMap<String, Object> properties = new HashMap<>();
	
	private transient MapObject obj;

	public ObjetoMapa(MapObject o) {
		obj = o;
		Iterator<String> it = o.getProperties().getKeys();
		while (it.hasNext()) {
			String key = it.next();
			this.properties.put(key, o.getProperties().get(key));
		}
	}

	public HashMap<String, Object> getProperties() {
		return this.properties;
	}

	public void setProperties(HashMap<String, Object> properties) {
		this.properties = properties;
	}

	public MapObject getObj() {
		return obj;
	}

	public void setObj(MapObject obj) {
		this.obj = obj;
	}
	
	

}
