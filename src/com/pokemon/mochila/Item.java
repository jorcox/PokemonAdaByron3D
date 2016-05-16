package com.pokemon.mochila;

import java.io.Serializable;

import pokemon.Pokemon;

public abstract class Item implements Serializable {

	private static final long serialVersionUID = -6840269029415974371L;
	
	private String nombre;
	
	public Item(String nombre) {
		this.nombre= nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public abstract void use(Pokemon pokemon);
	
}
