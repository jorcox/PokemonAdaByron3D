package com.pokemon.mochila;

import java.io.Serializable;
import java.util.ArrayList;

public class Mochila implements Serializable {
	
	private static final long serialVersionUID = 3828754512223630068L;
	
	private ArrayList<Objeto> objetos;
	private ArrayList<Ball> balls;
	private ArrayList<MO> mos;
	
	private boolean tieneCorte;
	private boolean tieneFuerza;
	private boolean tieneSurf;
	
	public Mochila() {
		objetos = new ArrayList<>();
		balls = new ArrayList<>();
		mos = new ArrayList<>();
		tieneCorte = false;
		tieneFuerza = false;
		tieneSurf = false;
	}
	
	public void add(Objeto obj) {
		objetos.add(obj);
	}
	
	public void add(Ball ball) {
		balls.add(ball);
	}
	
	public void add(MO mo) {
		mos.add(mo);
		if (mo.getNombre().equals("Corte")) {
			tieneCorte = true;
		} else if (mo.getNombre().equals("Fuerza")) {
			tieneFuerza = true;
		} else if (mo.getNombre().equals("Surf")) {
			tieneSurf = true;
		}
	}
	
	/**
	 * Devuelve un objeto de una lista.
	 * 
	 * @param seccion 0 (objetos), 1 (balls), 2 (mos).
	 * @param i indice del elemento en la lista.
	 * @return el elemento i-esimo de la seccion indicada,
	 * o null si se indica una seccion inexistente.
	 */
	public Item get(int seccion, int i) {
		if (seccion == 0) {
			return objetos.get(i);
		} else if (seccion == 1) {
			return balls.get(i);
		} else if (seccion == 2) {
			return mos.get(i);
		} else {
			return null;
		}
	}
	
	/**
	 * Devuelve el num de elementos de una lista.
	 * 
	 * @param seccion 0 (objetos), 1 (balls), 2 (mos).
	 * @return el numero de elementos de esa seccion,
	 * o -1 si la seccion no existe.
	 */
	public int size(int seccion) {
		if (seccion == 0) {
			return objetos.size();
		} else if (seccion == 1) {
			return balls.size();
		} else if (seccion == 2) {
			return mos.size();
		} else {
			return -1;
		}
	}

	public ArrayList<Objeto> getObjetos() {
		return objetos;
	}

	public void setObjetos(ArrayList<Objeto> objetos) {
		this.objetos = objetos;
	}

	public ArrayList<Ball> getBalls() {
		return balls;
	}

	public void setBalls(ArrayList<Ball> balls) {
		this.balls = balls;
	}

	public ArrayList<MO> getMos() {
		return mos;
	}

	public void setMos(ArrayList<MO> mos) {
		this.mos = mos;
	}
	
	public boolean tieneCorte() {
		return tieneCorte;
	}
	
	public boolean tieneFuerza() {
		return tieneFuerza;
	}
	
	public boolean tieneSurf() {
		return tieneSurf;
	}
	
}
