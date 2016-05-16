package db;

/* Copyright (c) 2001-2005, The HSQL Development Group
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of the HSQL Development Group nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL HSQL DEVELOPMENT GROUP, HSQLDB.ORG,
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import habilidad.Categoria;
import habilidad.Habilidad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import logica.Tipo;
import pokemon.Pokemon;

/**
 * Title: Testdb Description: simple hello world db example of a standalone
 * persistent db application
 *
 * every time it runs it adds four more rows to sample_table it does a query and
 * prints the results to standard out
 *
 * Author: Karl Meissner karl@meissnersd.com
 */
public class BaseDatos {

	public Connection conn; // our connnection to the db - presist for life of
							// program

	// we dont want this garbage collected until we are done
	public BaseDatos(String db_file_name_prefix) throws Exception { // note more
																	// general
																	// exception

		// Load the HSQL Database Engine JDBC driver
		// hsqldb.jar should be in the class path or made part of the current
		// jar
		Class.forName("org.hsqldb.jdbcDriver");

		// connect to the database. This will load the db files and start the
		// database if it is not alread running.
		// db_file_name_prefix is used to open or create files that hold the
		// state
		// of the db.
		// It can contain directory names relative to the
		// current working directory
		conn = DriverManager.getConnection(
				"jdbc:hsqldb:" + db_file_name_prefix, // filenames
				"pokemon", // username
				""); // password
	}

	public void shutdown() throws SQLException {

		Statement st = conn.createStatement();

		// db writes out to files and performs clean shuts down
		// otherwise there will be an unclean shutdown
		// when program ends
		st.execute("SHUTDOWN");
		conn.close(); // if there are no other open connection
	}

	// use for SQL command SELECT
	public synchronized ResultSet query(String expression) throws SQLException {

		Statement st = null;
		ResultSet rs = null;

		st = conn.createStatement(); // statement objects can be reused with

		// repeated calls to execute but we
		// choose to make a new one each time
		rs = st.executeQuery(expression); // run the query

		// do something with the result set.
		dump(rs);
		st.close(); // NOTE!! if you close a statement the associated ResultSet
					// is

		// closed too
		// so you should copy the contents to some other object.
		// the result set is invalidated also if you recycle an Statement
		// and try to execute some other query before the result set has been
		// completely examined.
		return rs;
	}

	// use for SQL commands CREATE, DROP, INSERT and UPDATE
	public synchronized void update(String expression) throws SQLException {

		Statement st = null;

		st = conn.createStatement(); // statements

		int i = st.executeUpdate(expression); // run the query

		if (i == -1) {
			System.out.println("db error : " + expression);
		}

		st.close();
	} // void update()

	public static void dump(ResultSet rs) throws SQLException {

		// the order of the rows in a cursor
		// are implementation dependent unless you use the SQL ORDER statement
		ResultSetMetaData meta = rs.getMetaData();
		int colmax = meta.getColumnCount();
		int i;
		Object o = null;

		// the result set is a cursor into the data. You can only
		// point to one row at a time
		// assume we are pointing to BEFORE the first row
		// rs.next() points to next row and returns true
		// or false if there is no next row, which breaks the loop
		for (; rs.next();) {
			for (i = 0; i < colmax; ++i) {
				o = rs.getObject(i + 1); // Is SQL the first column is indexed

				// with 1 not 0
				System.out.print(o.toString() + " ");
			}

			System.out.println(" ");
		}
	} // void dump( ResultSet rs )

	public static void main(String[] args) {
		IntroducirDatos pb = new IntroducirDatos();
		// pb.crearPokemon();
		pb.introducirPokemon();
		// LeerDatos pb=new LeerDatos();
		// pb.introducirPokemon();
		// pb.introducirMovimientos();

		// query("SELECT * FROM movimientos");
		// pb.introducirMovPorNivel();
		// pb.query("SELECT * FROM movs_nivel");
		// pb.introducirEvolucion();
		// pb.query("SELECT * FROM pokemon_tipo");
		pb.shutdown();
		/*
		 * BaseDatos db = null;
		 * 
		 * try { db = new BaseDatos("db_file1"); } catch (Exception ex1) {
		 * ex1.printStackTrace(); // could not start db
		 * 
		 * return; // bye bye }
		 * 
		 * try {
		 * 
		 * //make an empty table // // by declaring the id column IDENTITY, the
		 * db will automatically // generate unique values for new rows- useful
		 * for row keys db.update(
		 * "CREATE TABLE sample_table ( id INTEGER IDENTITY, str_col VARCHAR(256), num_col INTEGER)"
		 * ); } catch (SQLException ex2) {
		 * 
		 * //ignore //ex2.printStackTrace(); // second time we run program //
		 * should throw execption since table // already there // // this will
		 * have no effect on the db }
		 * 
		 * try {
		 * 
		 * // add some rows - will create duplicates if run more then once //
		 * the id column is automatically generated db.update(
		 * "INSERT INTO sample_table(str_col,num_col) VALUES('Ford', 100)");
		 * db.update(
		 * "INSERT INTO sample_table(str_col,num_col) VALUES('Toyota', 200)");
		 * db.update(
		 * "INSERT INTO sample_table(str_col,num_col) VALUES('Honda', 300)");
		 * db.update(
		 * "INSERT INTO sample_table(str_col,num_col) VALUES('GM', 400)");
		 * 
		 * // do a query
		 * db.query("SELECT * FROM sample_table WHERE num_col < 500");
		 * 
		 * // at end of program db.shutdown(); } catch (SQLException ex3) {
		 * ex3.printStackTrace(); }
		 */
	} // main()

	public Pokemon getPokemonTipo(int id) {
		Pokemon poke = new Pokemon();
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM pokemon_tipo where id="
					+ id);
			rs.next();
			System.out.println();
			poke.setNombre(rs.getString("nombre"));
			poke.setPs(rs.getInt("ps"));
			poke.setPsMax(rs.getInt("ps"));
			poke.setAtaque(rs.getInt("ataque"));
			poke.setAtaqueEsp(rs.getInt("ataque_esp"));
			poke.setDefensa(rs.getInt("defensa"));
			poke.setDefensaEsp(rs.getInt("defensa_esp"));
			poke.setVelocidad(rs.getInt("velocidad"));
			poke.setEvasion(100);
			poke.setPrecision(100);
			poke.setId(id);
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return poke;
	}

	
	public Pokemon getPokemon(int id) {
		Pokemon poke = new Pokemon();
		Habilidad[] habilidades = new Habilidad[4];
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM pokemon where id="
					+ id);
			rs.next();
			System.out.println();
			poke.setNivel(rs.getInt("nivel"));
			poke.setNombre(rs.getString("nombre"));
			poke.setTipo(getTipo(rs.getString("tipo")));
			poke.setPs(rs.getInt("ps"));
			poke.setPsMax(rs.getInt("ps"));
			poke.setAtaque(rs.getInt("ataque"));
			poke.setAtaqueEsp(rs.getInt("ataque_esp"));
			poke.setDefensa(rs.getInt("defensa"));
			poke.setDefensaEsp(rs.getInt("defensa_esp"));
			poke.setVelocidad(rs.getInt("velocidad"));
			poke.setEvasion(100);
			poke.setPrecision(100);
			habilidades[0] = getHabilidad(rs.getInt("mov1"));
			habilidades[1] = getHabilidad(rs.getInt("mov2"));
			habilidades[2] = getHabilidad(rs.getInt("mov3"));
			habilidades[3] = getHabilidad(rs.getInt("mov4"));
			poke.setHabilidades(habilidades);
			poke.setExperiencia(rs.getInt("experiencia"));
			poke.setEstado(rs.getInt("estado"));
			poke.setId(id);
			poke.setEntrenador(rs.getInt("entrenador"));
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return poke;
	}

	public Habilidad getHabilidad(int id) {
		if (id > 0) {
			Habilidad habilidad = new Habilidad();
			Statement st;
			try {
				st = conn.createStatement();
				ResultSet rs = st
						.executeQuery("SELECT * FROM movimientos where id="
								+ id);
				rs.next();
				habilidad = new Habilidad();
				habilidad.setNombre(rs.getString("nombre"));
				habilidad.setPoder(rs.getInt("poder"));
				habilidad.setTipo(habilidad.getTipo(rs.getString("tipo_mov")));
				habilidad.setCategoria(habilidad.getCategoria(rs
						.getString("tipo")));
				habilidad.setPrecision(rs.getInt("precision"));
				habilidad.setPp(rs.getInt("pp"));
				habilidad.setDescripcion(rs.getString("descripcion"));

				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return habilidad;
		} else {
			return null;
		}
	}

	public Habilidad getHabilidadNombre(String nombre) {
		Habilidad habilidad = new Habilidad();
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT * FROM movimientos where nombre='"
							+ nombre+"'");
			rs.next();
			habilidad = new Habilidad();
			habilidad.setNombre(rs.getString("nombre"));
			habilidad.setPoder(rs.getInt("poder"));
			habilidad.setTipo(habilidad.getTipo(rs.getString("tipo_mov")));
			habilidad
					.setCategoria(habilidad.getCategoria(rs.getString("tipo")));
			habilidad.setPrecision(rs.getInt("precision"));
			habilidad.setPp(rs.getInt("pp"));
			habilidad.setDescripcion(rs.getString("descripcion"));

			rs.close();
		} catch (SQLException e) {
			habilidad = null;
		}
		return habilidad;
	}

	public Habilidad getHabilidadPokemon(int id_poke, int nivel) {
		Habilidad habilidad = null;
		if (id_poke > 0) {
			Statement st;
			try {
				st = conn.createStatement();
				ResultSet rs = st
						.executeQuery("SELECT * FROM movs_nivel where id_poke="
								+ id_poke + " and nivel = " + nivel);

				rs.next();
				habilidad = getHabilidadNombre(rs.getString("nombre"));

				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			return habilidad;
		} else {
			return null;
		}
	}

	public int getIdPoke(String nombre) {
		Statement st;
		int id_poke=0;
		try {
			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT * FROM pokemon_tipo where nombre='"
							+ nombre+"'" );

			rs.next();
			id_poke=rs.getInt("id");
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id_poke;
	}

	public Tipo getTipo(String tipo) {
		switch (tipo) {
		case "STEEL":
			return Tipo.ACERO;
		case "WATER":
			return Tipo.AGUA;
		case "BUG":
			return Tipo.BICHO;
		case "DRAGON":
			return Tipo.DRAGON;
		case "ELECTRIC":
			return Tipo.ELECTRICO;
		case "GHOST":
			return Tipo.FANTASMA;
		case "FIRE":
			return Tipo.FUEGO;
		case "FAIRY":
			return Tipo.HADA;
		case "ICE":
			return Tipo.HIELO;
		case "FIGHTING":
			return Tipo.LUCHA;
		case "NORMAL":
			return Tipo.NORMAL;
		case "GRASS":
			return Tipo.PLANTA;
		case "PSYCHIC":
			return Tipo.PSIQUICO;
		case "ROCK":
			return Tipo.ROCA;
		case "DARK":
			return Tipo.SINIESTRO;
		case "GROUND":
			return Tipo.TIERRA;
		case "POISON":
			return Tipo.VENENO;
		case "FLYING":
			return Tipo.VOLADOR;
		default:
			return null;
		}
	}
} // class Testdb