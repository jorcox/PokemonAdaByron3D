package logica;

import habilidad.Habilidad;
import pokemon.Pokemon;

/**
 * 
 * @author Javier Beltran, Jorge Cancer, Alejandro Dieste
 * 
 * Contiene la matriz de efectividad de los tipos pokemon. Es una matriz de
 * 17x17 (ya que 17 es el numero de tipos elementales), donde cada fila
 * representa el tipo elemental del ataque realizado por el pokemon atacante,
 * y cada columna representa un tipo elemental del pokemon oponente.
 * 
 * La clase contiene los metodos para acceder a los valores de la matriz, lo
 * que permite obtener los multiplicadores de tipo "es muy efectivo", "no es
 * muy efectivo" y "no afecta a..." en un combate pokemon.
 *
 */
public class MatrizEfectividad {
	
	private static double[][] matriz = 
		{{0.5, 0.5, 1, 1, 0.5, 1, 0.5, 2, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1}, // Acero
		{1, 0.5, 1, 0.5, 1, 1, 2, 1, 1, 1, 1, 0.5, 1, 2, 1, 2, 1, 1}, // Agua
		{0.5, 1, 1, 1, 1, 0.5, 0.5, 0.5, 1, 0.5, 1, 2, 2, 1, 2, 1, 0.5, 0.5}, // Bicho
		{0.5, 1, 1, 2, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, // Dragon
		{1, 2, 1, 0.5, 0.5, 1, 1, 1, 1, 1, 1, 0.5, 1, 1, 1, 0, 1, 2}, // Electrico
		{1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 0, 1, 2, 1, 0.5, 1, 1, 1}, // Fantasma
		{2, 0.5, 2, 0.5, 1, 1, 0.5, 1, 2, 1, 1, 2, 1, 0.5, 1, 1, 1, 1}, // Fuego
		{0.5, 1, 1, 2, 1, 1, 0.5, 1, 1, 2, 1, 1, 1, 1, 2, 1, 0.5, 1}, // Hada
		{0.5, 0.5, 1, 2, 1, 1, 0.5, 1, 0.5, 1, 1, 2, 1, 1, 1, 2, 1, 2}, // Hielo
		{2, 1, 0.5, 1, 1, 0, 1, 0.5, 2, 1, 2, 1, 0.5, 2, 2, 1, 0.5, 0.5}, // Lucha
		{0.5, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0.5, 1, 1, 1, 1}, // Normal
		{0.5, 2, 0.5, 0.5, 1, 1, 0.5, 1, 1, 1, 1, 0.5, 1, 2, 1, 2, 0.5, 0.5}, // Planta
		{0.5, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 0.5, 1, 0, 1, 2, 1}, // Psiquico
		{0.5, 1, 2, 1, 1, 1, 2, 1, 2, 0.5, 1, 1, 1, 1, 1, 0.5, 1, 2}, // Roca
		{1, 1, 1, 1, 1, 2, 1, 0.5, 1, 0.5, 1, 1, 2, 1, 0.5, 1, 1, 1}, // Siniestro
		{2, 1, 0.5, 1, 2, 1, 2, 1, 1, 1, 1, 0.5, 1, 2, 1, 1, 2, 0}, // Tierra
		{0, 1, 1, 1, 1, 0.5, 1, 2, 1, 1, 1, 2, 1, 0.5, 1, 0.5, 0.5, 1}, // Veneno
		{0.5, 1, 2, 1, 0.5, 1, 1, 1, 1, 2, 1, 2, 1, 0.5, 1, 1, 1, 1}}; // Volador
	
	/**
	 * 
	 * Devuelve el factor de efectividad de un ataque de un tipo determinado
	 * contra un pokemon de un tipo determinado.
	 * 
	 * @param habilidad la habilidad del pokemon atacante.
	 * @param oponente el pokemon oponente.
	 * @return el valor multiplicador de un ataque de tipo atacante contra
	 * un pokemon de tipo oponente.
	 */
	public static double getEfectividad(Habilidad habilidad, Pokemon oponente) {
		return matriz[habilidad.getTipo().ordinal()][oponente.getTipo().ordinal()];
	}
	
}
