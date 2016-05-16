package com.pokemon.dialogo;

import java.io.Serializable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * 
 * @author Javier Beltran, Jorge Cancer, Alejandro Dieste
 * 
 *         Clase que permite mostrar los dialogos en el juego. Un objeto Dialogo
 *         se creara al principio del juego y permitira acceder a los dialogos
 *         de todos los NPCs a traves de un identificador. Los dialogos se
 *         encuentran almacenados en un fichero de propiedades.
 * 
 *         La clase tambien se encarga de pintar en pantalla el dialogo, de modo
 *         que aparezca paginado segun el usuario lo va leyendo.
 * 
 *         La clase implementa el mecanismo de Internalizacion y Localizacion,
 *         que permitiria traducir el programa sin tocar el codigo fuente. Basta
 *         con crear otro fichero de propiedades para otra localizacion.
 * 
 *         Los ficheros de propiedades deben tener la sintaxis
 *         Dialogos_es_ES.properties, donde <es> es el idioma y <ES> es el pais.
 *
 */
public class Dialogo implements Serializable {

	private static final long serialVersionUID = 7029393477725778068L;
	
	private String idioma;
	private String pais;
	private Locale locale;
	protected int indiceActual;
	private int indicePalabra;
	protected String lineaUno = "";
	protected String lineaDos = "";
	protected boolean writing = false;
	protected int len;
	protected String[] frases;
	protected String id;

	public Dialogo(String idioma, String pais) {
		this.idioma = idioma;
		this.pais = pais;
		
		locale = new Locale(idioma, pais);
		len = 0;
	}

	public String getIdioma() {
		return idioma;
	}

	public String getPais() {
		return pais;
	}
	
	public boolean isWriting() {
		return writing;
	}
	
	public String getId() {
		return id;
	}

	public String getLinea1() {
		return lineaUno;
	}
	
	public String getLinea2() {
		return lineaDos;
	}

	/**
	 * Accede al fichero de propiedades correspondiente segun el Locale
	 * configurado al crear el Dialogo, y devuelve el dialogo identificado por
	 * la id.
	 * 
	 * @param id
	 *            la clave que identifica al dialogo.
	 * @return el dialogo cuya clave es id.
	 */
	public void procesarDialogo(String id) {
		ResourceBundle bundle = ResourceBundle.getBundle("Dialogos", locale);
		this.id=id;
		int i = contarFrases(bundle, id);
		String[] dialogo = new String[i];
		for (int j = 0; j < i; j++) {
			dialogo[j] = bundle.getString(id + "_" + (j+1));
		}
		indiceActual = 0;
		indicePalabra = 0;
		frases = dialogo;
	}

	public String getSiguienteLinea(String[] dialogo) {
		String lineaActual = "";
		try {
			lineaActual = dialogo[indiceActual];
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		String salida = "";
		String[] palabras = lineaActual.split(" ");
		String check = "";
		try {
			check = (salida + palabras[indicePalabra]);
		} catch (IndexOutOfBoundsException e) {
		}
		while (check.length() < 40) {
			try {
				salida += palabras[indicePalabra] + " ";
				indicePalabra++;
				check = (salida + palabras[indicePalabra]);
			} catch (IndexOutOfBoundsException e) {
				indicePalabra = 0;
				indiceActual++;
				break;
			}
		}
		/*
		 * if(lineaActual.length()<40){ salida =
		 * lineaActual.substring(0,lineaActual.length()); indiceActual++; } else
		 * { try{ salida =
		 * lineaActual.substring(0+(40*subIndiceActual),39+(40*subIndiceActual))
		 * ; subIndiceActual++; } catch(IndexOutOfBoundsException e){ salida =
		 * lineaActual.substring(39+(40*(subIndiceActual-1)),lineaActual.length(
		 * )-1); subIndiceActual = 0; indiceActual++; } }
		 */

		return salida;
	}

	private int contarFrases(ResourceBundle bundle, String id) {
		int i = 1;
		try {
			while (true) {
				bundle.getString(id + "_" + i);
				i++;
			}
		} catch (MissingResourceException e) {
		}
		return (i-1);
	}

	/**
	 * Muestra las dos lineas por pantalla letra a letra. Escribe una letra cada
	 * 0,05s.
	 * 
	 * @param l1
	 * @param l2
	 */
	public void setLineas(String l1, String l2) {
		/* Antes hay que borrarlas */
		lineaUno = "";
		lineaDos = "";
		String l11, l22;
		if (l1 == null) {
			l11 = "";
		} else {
			l11 = l1;
		}
		if (l2 == null) {
			l22 = "";
		} else {
			l22 = l2;
		}

		Timer.schedule(new Task() {
			int i = 1;
			int j = 1;

			public void run() {
				writing = true;

				if (i < l11.length()) {
					lineaUno = l11.substring(0, i++);
				} else if (j < l22.length()) {
					lineaDos = l22.substring(0, j++);
				} else {
					writing = false;
				}
			}
		}, 0, (float) 0.05, l11.length() + l22.length() + 1);
	}
	
	public void setFrases(String[] frases) {
		this.frases = frases;
		indiceActual = 0;
	}

	public String siguienteLinea() {		
		return getSiguienteLinea(frases);
	}
	
	public void limpiar() {
		lineaUno = "";
		lineaDos = "";
	}

}
