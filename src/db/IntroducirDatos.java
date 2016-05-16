package db;

import java.sql.SQLException;

public class IntroducirDatos {

	BaseDatos db;

	public IntroducirDatos() {
		try {
			db = new BaseDatos("pokemon_base");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void introducirPokemon() {
		try {
			/*db.update("INSERT INTO pokemon(nivel,nombre,tipo,ps,ataque,defensa,ataque_esp,defensa_esp,velocidad,mov1,mov2,mov3,mov4,experiencia,estado,entrenador)"
					+ " VALUES(100, 'Pikachu','ELECTRIC', 100, 100, 100, 100, 100, 100, 100,25,53,400,0, 0,0)");
			db.update("INSERT INTO pokemon(nivel,nombre,tipo,ps,ataque,defensa,ataque_esp,defensa_esp,velocidad,mov1,mov2,mov3,mov4,experiencia,estado,entrenador)"
					+ " VALUES(100, 'Charizard','FIRE', 100, 100, 100, 100, 100, 100, 100,25,53,400,0, 0,0)");
			db.update("INSERT INTO pokemon(nivel,nombre,tipo,ps,ataque,defensa,ataque_esp,defensa_esp,velocidad,mov1,mov2,mov3,mov4,experiencia,estado,entrenador)"
					+ " VALUES(100, 'Mew','FAIRY', 100, 100, 100, 100, 100, 100, 100,25,53,400,0, 0,1)");
			db.update("INSERT INTO pokemon(nivel,nombre,tipo,ps,ataque,defensa,ataque_esp,defensa_esp,velocidad,mov1,mov2,mov3,mov4,experiencia,estado,entrenador)"
					+ " VALUES(100, 'Lapras','WATER', 100, 100, 100, 100, 100, 100, 100,25,53,400,0, 0,1)");
			db.update("INSERT INTO pokemon(nivel,nombre,tipo,ps,ataque,defensa,ataque_esp,defensa_esp,velocidad,mov1,mov2,mov3,mov4,experiencia,estado,entrenador)"
					+ " VALUES(100, 'Caterpie','BUG', 100, 100, 100, 100, 100, 100, 100,25,53,400,0, 0,2)");
			db.update("INSERT INTO pokemon(nivel,nombre,tipo,ps,ataque,defensa,ataque_esp,defensa_esp,velocidad,mov1,mov2,mov3,mov4,experiencia,estado,entrenador)"
					+ " VALUES(100, 'Blastoise','ELECTRIC', 100, 100, 100, 100, 100, 100, 100,25,53,400,0, 0,2)");*/
			db.update("INSERT INTO pokemon(nivel,nombre,tipo,ps,ataque,defensa,ataque_esp,defensa_esp,velocidad,mov1,mov2,mov3,mov4,experiencia,estado,entrenador)"
					+ " VALUES(100, 'Mewtwo','PSYCHIC', 100, 100, 100, 100, 100, 100, 134,449,240,173,0, 0,2)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void crearPokemon() {
		try {
			db.update("CREATE TABLE pokemon ( id INTEGER IDENTITY,nivel INTEGER, nombre VARCHAR(256), tipo VARCHAR(256) ,"
					+ "ps INTEGER, ataque INTEGER, defensa INTEGER, ataque_esp INTEGER, "
					+ "defensa_esp INTEGER, velocidad INTEGER, mov1 INTEGER, mov2 INTEGER, mov3 INTEGER, mov4 INTEGER, experiencia INTEGER, estado INTEGER, entrenador INTEGER)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void shutdown() {
		try {
			db.shutdown();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
