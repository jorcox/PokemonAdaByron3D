package com.pokemon.experience;

/**
 * 
 * @author Javier Beltran, Jorge Cancer, Alejandro Dieste
 * 
 *         Clase que implementa los metodos necesarios para saber los puntos de
 *         experiencia que necesita un pokemon para subir de nivel, y tambien
 *         los puntos de experience que se ganan tras un combate
 */
public class Experiencia {

	private static double factor = 4 / 5.0;

	/**
	 * Return the experience points that you earn after a fight
	 * 
	 * @param trainer
	 * @param level
	 * @return
	 */
	public static int gainExperience(boolean trainer, int level) {
		double factor = 1.0;
		if (trainer) {
			factor = 1.5;
		}
		double baseExperience = baseExperience(level);
		return (int) (factor * baseExperience * level);
	}

	/**
	 * Return the amount of experience needed to get a level
	 * 
	 * @param level
	 * @return
	 */
	public static int experienceToLevel(int level) {
		return (int)(factor * Math.pow(level, 3));
	}

	/**
	 * Return the amount of base experience that have a pokemon at level level
	 * 
	 * @param level
	 * @return
	 */
	public static int baseExperience(int level) {
		int acc = 0;
		for (int i = 1; i <= level; i++) {
			acc += experienceToLevel(i);
		}
		return acc;
	}

}
