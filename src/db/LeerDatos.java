package db;

import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;

public class LeerDatos {

	BaseDatos db;

	public LeerDatos() {
		try {
			db = new BaseDatos("pokemon_base");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Introduce los pokemon con sus estadisticas base en la base de datos
	 */
	public void introducirPokemon() {
		try {
			db.update("CREATE TABLE pokemon_tipo ( id INTEGER IDENTITY, nombre VARCHAR(256), "
					+ "ps INTEGER, ataque INTEGER, defensa INTEGER, ataque_esp INTEGER, "
					+ "defensa_esp INTEGER, velocidad INTEGER, evolucion INTEGER)");
			db.update("INSERT INTO pokemon_tipo(nombre,ps,ataque,defensa,ataque_esp,defensa_esp,velocidad,evolucion)"
					+ " VALUES('Sara', 1000, 1000, 1000, 1000, 1000, 1000, 0)");
			Scanner linea = new Scanner(new File("pokemon.txt"));
			linea.useDelimiter(",");
			/*
			 * Lectura de cada linea del fichero
			 */
			while (linea.hasNextLine()) {
				/*
				 * 1 - Nombre 2 - PS 3 - Ataque 4 - Defensa 5 - Ataque esp. 6 -
				 * Defensa esp. 7 - Velocidad
				 */
				int i = linea.nextInt();
				String st = "INSERT INTO pokemon_tipo(nombre,ps,ataque,defensa,ataque_esp,defensa_esp,velocidad,evolucion)"
						+ " VALUES('"
						+ linea.next()
						+ "', "
						+ linea.nextInt()
						+ ", "
						+ linea.nextInt()
						+ ", "
						+ linea.nextInt()
						+ ", "
						+ linea.nextInt()
						+ ", "
						+ linea.nextInt()
						+ ", " + linea.nextInt() + ", 0)";
				db.update(st);
				linea.nextLine();

			}
			linea.close();
			db.query("SELECT * FROM pokemon_tipo WHERE id < 151");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void introducirMovimientos() {
		try {
			db.update("CREATE TABLE movimientos ( id INTEGER IDENTITY, name VARCHAR(256),nombre VARCHAR(256), "
					+ "poder INTEGER, tipo_mov VARCHAR(256), tipo VARCHAR(256), "
					+ "precision INTEGER, pp INTEGER, descripcion VARCHAR(256))");
			Scanner linea = new Scanner(new File("moves.txt"), "UTF-8");
			linea.useDelimiter(",");
			/*
			 * Lectura de cada linea del fichero
			 */
			while (linea.hasNextLine()) {
				/*
				 * 1 - name 2 - nombre 3 - poder 4 - tipo_mov 5 - tipo 6 -
				 * precision 7 - pp 8 - descripcion
				 */
				String a = linea.next();
				String name = linea.next();
				String nombre = linea.next();
				linea.next();
				int poder = linea.nextInt();
				String tipo_mov = linea.next();
				String tipo = linea.next();
				int precision = linea.nextInt();
				int pp = linea.nextInt();
				linea.next();
				linea.next();
				linea.next();
				linea.next();
				String descripcion = linea.next();
				String st = "INSERT INTO movimientos(name,nombre,poder,tipo_mov,tipo,precision,pp,descripcion)"
						+ " VALUES('"
						+ name
						+ "', '"
						+ nombre
						+ "', "
						+ poder
						+ ", '"
						+ tipo_mov
						+ "', '"
						+ tipo
						+ "', "
						+ precision
						+ ", " + pp + ", '" + descripcion + "')";
				db.update(st);
				linea.nextLine();
			}
			linea.close();
			db.query("SELECT * FROM pokemon_tipo");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void introducirMovPorNivel() {
		try {
			/*
			 * db.update("CREATE TABLE movs_nivel (" +
			 * "id INTEGER IDENTITY, id_poke INTEGER," + "nivel INTEGER," +
			 * "nombre VARCHAR(256))");
			 */
			Scanner linea = new Scanner(new File("movs_nivel.txt"), "UTF-8");
			linea.useDelimiter(",");
			/*
			 * Lectura de cada linea del fichero
			 */
			int pokemon = 0;
			boolean id_poke = true;
			while (linea.hasNextLine()) {
				if (id_poke) {
					pokemon = linea.nextInt();
					id_poke = false;
					linea.nextLine();
				} else {
					try {
						int nivel = linea.nextInt();
						if (nivel <= 100) {
							String nombre = linea.next();
							String st = "INSERT INTO movs_nivel(id_poke,nivel,nombre)"
									+ " VALUES("
									+ pokemon
									+ ","
									+ nivel
									+ ", '" + nombre + "')";
							db.update(st);
							System.out.println(st);
						}
						linea.nextLine();
					} catch (Exception e) {
						id_poke = true;
						linea.nextLine();
					}
				}
			}
			linea.close();
			db.query("SELECT * FROM movs_nivel");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void introducirEvolucion() {
		try {
			Scanner linea = new Scanner(new File("evolucion_pokemon.txt"),
					"UTF-8");
			linea.useDelimiter(",");
			/*
			 * Lectura de cada linea del fichero
			 */
			int i = 0;
			while (linea.hasNextLine()) {
				int id_pok = linea.nextInt();
				int primera_evo = linea.nextInt();
				int segunda_evo = 0;
				if(linea.hasNextInt())segunda_evo=linea.nextInt();
				if (i == 0) {
					int nivel = primera_evo;
					if(nivel>100)nivel=36;
					String st = "UPDATE pokemon_tipo SET evolucion=" + nivel
							+ "WHERE id=" + id_pok;
					db.update(st);
				} else if (i == 1) {
					int nivel = segunda_evo;
					if(nivel>100)nivel=36;
					String st = "UPDATE pokemon_tipo SET evolucion=" + nivel
							+ " WHERE id=" + id_pok;
					db.update(st);
				}
				i++;
				linea.nextLine();
				if (i == 3 || i==2 && segunda_evo==0) {
					i = 0;
				}
			}
			linea.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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

	public void query(String query) {
		try {
			db.query(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
