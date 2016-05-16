package logica;

import java.util.Random;

import habilidad.Categoria;
import habilidad.Habilidad;
import pokemon.Pokemon;

/**
 * 
 * @author Javier Beltran, Jorge Cancer, Alejandro Dieste
 * 
 * Clase que contiene los calculos que hay que realizar en un combate
 * pokemon, para calcular la probabilidad de critico, la probabilidad
 * de fallo, los puntos de vida que quita una habilidad, etc.
 *
 */
public class CalculosCombate {
	
	/* 
	 * Vector con las probabilidades de critico asociadas a cada indice
	 * de golpe critico. Si el indice de un pokemon se sale de los limites
	 * del vector, se debe tomar el valor extremo.
	 */
	private static double[] probCritico = {0, 6.25, 12.5, 25, 33.3, 50};
	
	/**
	 * 
	 * Calcula los puntos de vida que un pokemon le quita a otro al
	 * realizar una habilidad contra el. Esta habilidad debe ser
	 * fisica o especial, pero no de estado pues estas no quitan vida. 
	 * 
	 * @param atacante el pokemon que realiza el ataque.
	 * @param oponente el pokemon que recibe el ataque y perdera vida.
	 * @param habilidad la habilidad que el atacante lanza al oponente.
	 * @return la cantidad de puntos de vida que hay que restar al oponente.
	 */
	public static int calcularDano(Pokemon atacante, Pokemon oponente, Habilidad habilidad) {
		/*
		 * n = nivel del atacante.
		 * a = ataque (fisico o especial, segun la habilidad) del atacante.
		 * p = poder de la habilidad.
		 * d = defensa (fisica o especial, segun la habilidad) del oponente.
		 * b = bonificador del 50% si el atacante y la habilidad tienen el mismo tipo.
		 * e = efectividad del tipo de la habilidad frente al tipo del oponente.
		 * v = variacion aleatoria (entre el 85% y el 100%).
		 */
		int n = atacante.getNivel();
		int a = (habilidad.getTipo().equals(Categoria.FISICO)) ? 
				atacante.getAtaque() : atacante.getAtaqueEsp();
		int p = habilidad.getPoder();
		int d = (habilidad.getTipo().equals(Categoria.FISICO)) ?
				oponente.getDefensa() : oponente.getDefensaEsp();
		double b = (atacante.getTipo().equals(habilidad.getTipo())) ? 1.5 : 1;
		double e = MatrizEfectividad.getEfectividad(habilidad, oponente);
		int v = calcularVariacion();
		
		/* Hace el calculo con reales y lo redondea porque los hp son enteros */
		double puntos = 0.01 * b * e * v * (((0.2 * n + 1) * a * p) / (25 * d) + 2);
		return (int) Math.round(puntos);
	}
	
	/**
	 * Calcula la variacion del ataque, que es un valor aleatorio que modifica
	 * ligeramente el ataque final, entre el 85% y el 100% del ataque teorico.
	 * 
	 * @return un numero entre 85 y 100.
	 */
	private static int calcularVariacion() {
		Random random = new Random();
		return random.nextInt(16) + 85;
	}
	
	/**
	 * 
	 * Calcula la probabilidad de acierto de un pokemon al utilizar una habilidad
	 * contra otro pokemon, e indica si la habilidad ha acertado o ha fallado.
	 * 
	 * @param atacante el pokemon que lanza la habilidad.
	 * @param oponente el pokemon que recibe el ataque.
	 * @param habilidad la habilidad que lanza el atacante al oponente.
	 * @return true, si la habilidad ha acertado; o false, si la habilidad ha fallado.
	 */
	public static boolean calcularAcierto(Pokemon atacante, Pokemon oponente, Habilidad habilidad) {
		/* Calcula la probabilidad de acierto del ataque */
		double prob = 0.01 * habilidad.getPrecision() * 
				atacante.getPrecision() / oponente.getEvasion();
		
		/* Decide si el ataque ha acertado en funcion de su probabilidad */
		if (prob >= 1) {
			return true;
		} else if (prob < 0) {
			return false;
		} else {
			Random random = new Random();
			return (prob >= random.nextDouble());
		}
	}
	
	/**
	 * 
	 * Calcula la probabilidad de golpe critico de un pokemon al lanzar una
	 * habilidad, y decide si el golpe es critico o no.
	 * 
	 * @param atacante el pokemon que lanza la habilidad.
	 * @return true, si el golpe es critico; o false, si no lo es.
	 */
	public static boolean calcularCritico(Pokemon atacante) {
		/* Calcula la probabilidad de critico a partir del indice de critico */
		double prob;
		if (atacante.getIndiceCritico() > 5) {
			prob = probCritico[5];
		} else if (atacante.getIndiceCritico() < 0){
			prob = probCritico[0];
		} else {
			prob = probCritico[atacante.getIndiceCritico()];
		}
		prob = prob * 0.01;
		
		/* Decide si el ataque es critico en funcion de su probabilidad */
		Random random = new Random();
		return (prob >= random.nextDouble());
	}
	
}
