package core;

import java.util.Random;
import java.util.Scanner;

import entrenadores.Entrenador;
import habilidad.Categoria;
import habilidad.Habilidad;
import logica.CalculosCombate;
import logica.MatrizEfectividad;
import pokemon.Pokemon;

/**
 * 
 * @author Javier Beltran, Jorge Cancer, Alejandro Dieste
 * 
 *         Clase que contiene la funcionalidad de un combate Pokemon.
 *
 */
public class Combate {

	private Entrenador entrenador;
	private Pokemon pokemon;

	/**
	 * 
	 * Crea un combate pokemon entre un entrenador y un pokemon salvaje. Un
	 * entrenador podra jugar con los pokemon que tenga equipados.
	 * 
	 * @param e
	 *            el entrenador.
	 * @param p
	 *            el pokemon.
	 */
	public Combate(Entrenador e, Pokemon p) {
		entrenador = e;
		pokemon = p;
	}

	/**
	 * Contiene la funcionalidad de un combate pokemon, es decir, pregunta a
	 * cada turno que habilidad desea realizar el jugador y ejecuta la accion
	 * producida por su accion y por la del enemigo.
	 */
	public boolean luchar() {
		Scanner input = new Scanner(System.in);
		Pokemon aliado = entrenador.getEquipo().get(0);
		Pokemon enemigo = this.pokemon;
		printInicioCombate();

		/* Bucle principal del combate */
		do {
			printPregunta();
			int accion = input.nextInt();
			if (accion == 1) {
				printAtaques();
				int ataqueAliado = input.nextInt();
				int ataqueEnemigo = decidir(enemigo);

				/* El pokemon mas veloz ataca antes que el otro */
				if (aliado.getVelocidad() >= enemigo.getVelocidad()) {
					ejecutar(aliado, enemigo, aliado.getHabilidad(ataqueAliado));
					ejecutar(enemigo, aliado, enemigo.getHabilidad(ataqueEnemigo));
				} else {
					ejecutar(enemigo, aliado, enemigo.getHabilidad(ataqueEnemigo));
					ejecutar(aliado, enemigo, aliado.getHabilidad(ataqueAliado));
				}

				/* Comprueba si algun pokemon ha sido derrotado */
				if (!enemigo.vivo()) {
					printEnemigoDerrotado();
					// Subir experiencia
				} else if (!aliado.vivo()) {
					printAliadoDerrotado(aliado);

					if (entrenador.vivo()) {
						// Cambiar pokemon
					}
				}
			} else if (accion == 2) {
				// Cambiar pokemon
			} else if (accion == 3) {
				// Mochila
			} else {
				printHuida();
			}
		} while (entrenador.vivo() && enemigo.vivo());
		input.close();

		if (entrenador.vivo()) {
			printVictoria();
			// Ganar dinero.
			return true;
		} else {
			printDerrota();
			// Perder dinero.
			return false;
		}

	}

	/**
	 * 
	 * El sistema decide que habilidad desea utilizar el pokemon enemigo.
	 * 
	 * @param enemigo
	 *            el pokemon cuyo ataque se va a decidir.
	 * @return el indice de la habilidad que elige utilizar.
	 */
	public int decidir(Pokemon enemigo) {
		Random random = new Random();
		int l = enemigo.getHabilidades().length;
		int ataque=random.nextInt(l)+1;
		while(enemigo.getHabilidad(ataque)==null){
			ataque=random.nextInt(l)+1;
		}
		return ataque;
	}

	/**
	 * 
	 * Realiza la accion de un pokemon haciendo una habilidad en combate. Su
	 * resultado modifica los puntos de vida o el estado de los pokemon
	 * implicados en el combate. Devuelve -1 si ha fallado. 0 se si ha acertado.
	 * 1 si no afecta la habilidad. 2 si no es muy efectivo. 3 si es muy
	 * efectivo
	 * 
	 * @param p1
	 *            el pokemon atacante.
	 * @param p2
	 *            el pokemon que es atacado.
	 * @param habilidad
	 *            la habilidad que p1 realiza sobre p2.
	 */
	public int ejecutar(Pokemon p1, Pokemon p2, Habilidad habilidad) {
		printAtaque(p1, habilidad);
		int opt = 0;
		/* Decide si acierta o no */
		boolean acierto = CalculosCombate.calcularAcierto(p1, p2, habilidad);
		if (acierto) {
			/* Decide si es golpe critico o no */
			boolean critico = CalculosCombate.calcularCritico(p1);
			int dano = CalculosCombate.calcularDano(p1, p2, habilidad);

			if (critico) {
				/* Si es critico, quita el doble de ps */
				printCritico();
				dano *= 2;
			}
			opt = printEfectividad(habilidad, p2);
			p2.restarPs(dano);
		} else {
			printFallo();
		}
		if (acierto) {
			return opt;
		} else {
			return -1;
		}
	}

	public Entrenador getEntrenador() {
		return entrenador;
	}

	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
	}

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}

	// Cambiar todos estos metodos para que se pinte el texto en el juego

	private void printInicioCombate() {
		Pokemon p1 = entrenador.getEquipo().get(0);
		System.out.println("Un " + pokemon.getNombre() + " salvaje quiere luchar!");
		System.out.println(entrenador.getNombre() + " saco a " + p1.getNombre());
	}

	private void printPregunta() {
		System.out.println("Que quieres hacer?");
		System.out.println("1. Atacar.");
		System.out.println("2. Pokemon.");
		System.out.println("3. Mochila.");
		System.out.println("4. Huir.");
	}

	private void printAtaques() {
		System.out.println("1. " + pokemon.getHabilidad1());
		System.out.println("2. " + pokemon.getHabilidad2());
		System.out.println("3. " + pokemon.getHabilidad3());
		System.out.println("4. " + pokemon.getHabilidad4());
	}

	private void printHuida() {
		System.out.println("Escapaste sano y salvo!");
	}

	private void printAliadoDerrotado(Pokemon aliado) {
		System.out.println("Tu " + aliado.getNombre() + " ha sido derrotado!");
	}

	private void printEnemigoDerrotado() {
		System.out.println("El " + pokemon.getNombre() + "enemigo ha sido derrotado!");
	}

	private void printVictoria() {
		System.out.println("Has ganado el combate!");
	}

	private void printDerrota() {
		System.out.println("A " + entrenador.getNombre() + " no le quedan mas pokemon! " + entrenador.getNombre()
				+ " ha sido debilitado!");
	}

	private void printAtaque(Pokemon p, Habilidad h) {
		System.out.println(p.getNombre() + " utilizo " + h.getNombre() + "!");
	}

	private void printFallo() {
		System.out.println("Pero fallo!");
	}

	private void printCritico() {
		System.out.println("Un golpe critico!");
	}

	private int printEfectividad(Habilidad h, Pokemon p) {
		double factor = MatrizEfectividad.getEfectividad(h, p);
		int ret = 0;
		if (factor == 0) {
			System.out.println("No afecto a " + p.getNombre() + "...");
			ret = 1;
		} else if (factor <= 0.5) {
			System.out.println("No es muy efectivo...");
			ret = 2;
		} else if (factor >= 2) {
			System.out.println("Es muy efectivo!");
			ret = 3;
		}
		return ret;
	}

	public void combatir(int ataqueAliado) {
		Pokemon aliado = entrenador.getEquipo().get(0);
		Pokemon enemigo = this.pokemon;
		int ataqueEnemigo = decidir(enemigo);

		/* El pokemon mas veloz ataca antes que el otro */
		if (aliado.getVelocidad() >= enemigo.getVelocidad()) {
			ejecutar(aliado, enemigo, aliado.getHabilidad(ataqueAliado));
			ejecutar(enemigo, aliado, enemigo.getHabilidad(ataqueEnemigo));
		} else {
			ejecutar(enemigo, aliado, enemigo.getHabilidad(ataqueEnemigo));
			ejecutar(aliado, enemigo, aliado.getHabilidad(ataqueAliado));
		}
	}

	public boolean getVelocidad(int i) {
		Pokemon aliado = entrenador.getEquipo().get(i);
		Pokemon enemigo = this.pokemon;
		/* El pokemon mas veloz ataca antes que el otro */
		return aliado.getVelocidad() >= enemigo.getVelocidad();
	}

}
