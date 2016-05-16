package com.pokemon.mochila;

import java.util.Random;

import pokemon.Pokemon;

public abstract class Ball extends Item {
	private static double  bonus=1;

	public Ball(String nombre) {
		super(nombre);
	}
	
	public static boolean atrapar(Pokemon pokemon){
		double a = ((3 * pokemon.getPsMax() - 2 * pokemon.getPs()) * bonus)/3*pokemon.getPsMax(); 
		Random random=new Random();
		double atrapar=random.nextDouble();
		if(atrapar>=0.5){
			return true;
		}
		else return false;
	}
	
}
