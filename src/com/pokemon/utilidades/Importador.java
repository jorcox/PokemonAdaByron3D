package com.pokemon.utilidades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Importa el archivo de guardado 
 * 
 */
public class Importador {

	/**
	 * Devuelve el archivo de guardado si lo encuentra, o lanza
	 * FileNotFoundException si no hay archivo de guardado.
	 */
	public static ArchivoGuardado importar() throws IOException, ClassNotFoundException {

		File f = new File(System.getenv("APPDATA")
				+ "//.pokemonAdaByron/jugador.sav");
		if (!f.exists()) {
			throw new FileNotFoundException();
		}
		
		FileInputStream fis = new FileInputStream(f);
		ObjectInputStream ois = new ObjectInputStream(fis);
		ArchivoGuardado ctx = (ArchivoGuardado) ois.readObject();
		ois.close();
		fis.close();
		
		return ctx;
	}
}