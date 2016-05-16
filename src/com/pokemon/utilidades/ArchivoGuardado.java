package com.pokemon.utilidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.pokemon.dialogo.Dialogo;
import com.pokemon.entities.Player;
import com.pokemon.mochila.Antidoto;
import com.pokemon.mochila.MO;
import com.pokemon.mochila.Mochila;
import com.pokemon.mochila.Pocion;
import com.pokemon.mochila.Pokeball;
import com.pokemon.mochila.Superball;
import com.pokemon.pantallas.Pantalla;

import entrenadores.Jugador;

/**
 * Contiene los datos de la partida del jugador
 */
public class ArchivoGuardado implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Coordenadas del jugador en 2D
	 */
	public static Vector2 posicionJugador;

	/*
	 * Indica la musica que estaba sonando
	 */
	public static Music musica;

	/*
	 * El nombre de la pieza de musica que estaba sonando
	 */
	public static String nombreMusica;

	/*
	 * Mapa en el que estaba el jugador
	 */
	public static String mapaActual;

	/*
	 * Genero del jugador
	 */
	public static String generoJugador;

	/*
	 * Nombre del jugador
	 */
	public static String nombreJugador;

	/*
	 * Dinero actual del jugador
	 */
	public static float dineroJugador;
	
	/*
	 * Mochila del jugador
	 */
	public Mochila mochila;
	
	/* 
	 * Vector de tecla asignada a cada accion.
	 * 0: UP. 1: DOWN. 2: LEFT. 3: RIGHT. 4: A. 5: B.
	 */
	private List<Integer> teclas;
	
	/* 
	 * HashMap de mapas
	 */
	private HashMap<String, Pantalla> mapas = new HashMap<>();
	
	public Dialogo dialogo;
	
	public float x;
	public float y;
	public int lastPressed;
	public String map;
	
	public Jugador jugador;
	public Player player;
	
	public static boolean existe = true;
	
	public ArchivoGuardado() {
		mochila = new Mochila();
		teclas = new ArrayList<>(6);
		setDefaultKeys();
		dialogo = new Dialogo("es", "ES");
		
		jugador = new Jugador("Sara", false);
		
		// Objetos de prueba metidos a pelo
		mochila.add(new Pocion());
		mochila.add(new Pokeball());
		mochila.add(new Superball());
		mochila.add(new Pokeball());
		mochila.add(new MO("Surf"));
	}
	
	private void setDefaultKeys() {
		teclas.add(Keys.UP);
		teclas.add(Keys.DOWN);
		teclas.add(Keys.LEFT);
		teclas.add(Keys.RIGHT);
		teclas.add(Keys.ENTER);
		teclas.add(Keys.SPACE);
	}
	
	public int getTeclaUp() {
		return teclas.get(Tecla.UP.ordinal());
	}

	public void setTeclaUp(int teclaUp) {
		setAndSwap(teclaUp, Tecla.UP.ordinal());
	}

	public int getTeclaDown() {
		return teclas.get(Tecla.DOWN.ordinal());
	}

	public void setTeclaDown(int teclaDown) {
		setAndSwap(teclaDown, Tecla.DOWN.ordinal());
	}

	public int getTeclaLeft() {
		return teclas.get(Tecla.LEFT.ordinal());
	}

	public void setTeclaLeft(int teclaLeft) {
		setAndSwap(teclaLeft, Tecla.LEFT.ordinal());
	}

	public int getTeclaRight() {
		return teclas.get(Tecla.RIGHT.ordinal());
	}

	public void setTeclaRight(int teclaRight) {
		setAndSwap(teclaRight, Tecla.RIGHT.ordinal());
	}

	public int getTeclaA() {
		return teclas.get(Tecla.A.ordinal());
	}

	public void setTeclaA(int teclaA) {
		setAndSwap(teclaA, Tecla.A.ordinal());
	}

	public int getTeclaB() {
		return teclas.get(Tecla.B.ordinal());
	}

	public void setTeclaB(int teclaB) {
		setAndSwap(teclaB, Tecla.B.ordinal());
	}
	
	public HashMap<String, Pantalla> getMapas() {
		return mapas;
	}

	public void setMapas(HashMap<String, Pantalla> mapas) {
		this.mapas = mapas;
	}

	/**
	 * Antes de asignar una tecla, comprueba si no se ha asignado ya.
	 * En ese caso, se intercambian las teclas en conflicto.
	 * @param value tecla a asignar.
	 * @param pos accion (posicion de vector teclas) asignada.
	 */
	private void setAndSwap(int value, int pos) {
		if (teclas.contains(value)) {
			/* Swap es necesario, esa tecla ya esta asociada */
			int index = teclas.indexOf(value);
			int aux = teclas.get(pos);
			teclas.set(index, aux);
		}
		/* Asigna la tecla a la accion indicada */
		teclas.set(pos, value);
	}
	
	/* En teclas se almacenan en este orden */
	public enum Tecla { UP, DOWN, LEFT, RIGHT, A, B }

}