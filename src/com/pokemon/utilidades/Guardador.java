package com.pokemon.utilidades;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Guarda el juego
 * 
 */
public class Guardador {

	/**
	 * Devuelve true si se ha podido guardar la partida.
	 */
	public static boolean guardar(ArchivoGuardado ctx) {
		File dir = new File(System.getenv("APPDATA")
				+ "//.pokemonAdaByron");
		if (dir.exists() && dir.isDirectory()) {
			dir.listFiles()[0].delete();
		} else {
			dir.mkdir();
		}
		File f = new File(System.getenv("APPDATA")
				+ "//.pokemonAdaByron/jugador.sav");
		
		try {
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(ctx);
			oos.close();
			fos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}