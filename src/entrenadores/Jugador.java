package entrenadores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pokemon.Pokemon;

/**
 * 
 * @author Javier Beltran, Jorge Cancer, Alejandro Dieste
 * 
 * La clase representa a un jugador. Un jugador es un entrenador
 * controlable por el usuario, que tiene todas las caracteristicas
 * de un entrenador y tambien una lista de medallas obtenidas y
 * acceso a su mochila.
 *
 */
public class Jugador extends Entrenador implements Serializable {
	
	private static final long serialVersionUID = 3568306953866442225L;
	
	private List<Medalla> medallas;
	
	/**
	 * 
	 * Crea un Jugador con un nombre y un genero. Un jugador
	 * solo sera creado al inicio de la partida o al cargar una
	 * partida existente.
	 * 
	 * @param nombre el nombre del jugador.
	 * @param esHombre true, si es hombre; false, si es mujer.
	 */
	public Jugador(String nombre, boolean esHombre) {
		this.setNombre(nombre);
		this.setHombre(esHombre);
		setMedallas(new ArrayList<Medalla>());
		setEquipo(new ArrayList<Pokemon>());
	}

	public List<Medalla> getMedallas() {
		return medallas;
	}

	public void setMedallas(List<Medalla> medallas) {
		this.medallas = medallas;
	}
	
	public static Jugador nuevoJugador(Jugador jugador){
		Jugador aux=new Jugador(jugador.getNombre(),jugador.isHombre());
		aux.setMedallas(jugador.getMedallas());
		aux.setEquipo(jugador.getEquipo());
		return aux;
	}
	
}
