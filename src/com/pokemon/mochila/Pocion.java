package com.pokemon.mochila;

import pokemon.Pokemon;

public class Pocion extends Objeto {

	public Pocion() {
		super("Poción");
	}

	@Override
	public void use(Pokemon pokemon) {
		int ps=pokemon.getPs()+20;
		if(ps>pokemon.getPsMax()){
			ps=pokemon.getPsMax();
		}
		pokemon.setPs(ps);
	}

}
