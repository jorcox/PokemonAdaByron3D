package entrenadores;

import java.util.List;

import pokemon.Pokemon;

/**
 * 
 * @author Javier Beltran, Jorge Cancer, Alejandro Dieste
 * 
 *         Clase que representa al entrenador pokemon, tanto si se trata del
 *         jugador como de NPCs. Un Entrenador tiene un nombre, un genero y una
 *         lista de pokemon que utilizara en combate.
 *
 */
public abstract class Entrenador {

	private String nombre;
	private boolean esHombre;
	private List<Pokemon> equipo;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Pokemon> getEquipo() {
		return equipo;
	}

	public void setEquipo(List<Pokemon> equipo) {
		this.equipo = equipo;
	}

	public boolean isHombre() {
		return esHombre;
	}

	public void setHombre(boolean genero) {
		this.esHombre = genero;
	}

	public void setPokemon(Pokemon pokemon) {
		if (equipo.size() < 6) {
			this.equipo.add(pokemon);
		} else {
			System.out.println("Solo puedes tener 6 pokemon en tu equipo.");
		}
	}

	public Pokemon getPokemon(int i) {
		if (i < 6)
			return equipo.get(i);
		else
			return null;
	}

	/**
	 * 
	 * Un entrenador se considera vivo si alguno de sus pokemon esta vivo. En
	 * caso contrario, esta debilitado y su combate termina.
	 * 
	 * @return true, si tiene algun pokemon vivo; false, en otro caso.
	 */
	public boolean vivo() {
		boolean vivo = false;
		for (int i = 0; i < equipo.size() && !vivo; i++) {
			if (equipo.get(i).vivo()) {
				vivo = true;
			}
		}
		return vivo;
	}

}
