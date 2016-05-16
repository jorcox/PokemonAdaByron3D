package pokemon;

import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import db.BaseDatos;
import habilidad.Habilidad;
import logica.Tipo;

public class CreadorEquipo {
	
	private BaseDatos bd;
	private ResourceBundle bundle;
	private ResourceBundle nombres;
	
	public CreadorEquipo() {
		try {
			bd = new BaseDatos("pokemon_base");
			bundle = ResourceBundle.getBundle("equipos");
			nombres = ResourceBundle.getBundle("nombres");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Pokemon> crear(String id) {
		ArrayList<Pokemon> lista = new ArrayList<>();
		
		int n = contarPokemon(id);
		for (int i=0; i<n; i++) {
			lista.add(getPokemon(id+"_"+(i+1)));
		}
		
		return lista;
	}
	
	public String nombrar(String id) {
		return nombres.getString(id);
	}
	
	private int contarPokemon(String id) {
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
	
	private Pokemon getPokemon(String id) {
		String line = bundle.getString(id);
		String[] parts = line.split(",");
		Pokemon p = null;
		if (parts.length == 7) {
			p = bd.getPokemonTipo(Integer.parseInt(parts[0]));
			Habilidad[] habs = setHabilidades(Integer.parseInt(parts[2]),
					Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), 
					Integer.parseInt(parts[5]));
			p.setHabilidades(habs);
			p.setTipo(Tipo.valueOf(parts[6]));
			int nivel = Integer.parseInt(parts[1]);
			for (int i=0; i<nivel; i++) {
				p.subirNivel(0, 0);
			}
			p.setPs(p.getPsMax());
		}
		return p;
	}
	
	private Habilidad[] setHabilidades(int hab0, int hab1, int hab2, int hab3) {
		Habilidad[] habilidades = new Habilidad[4];
		if (hab0 >= 0) {
			habilidades[0] = bd.getHabilidad(hab0);
		}
		if (hab1 >= 0) {
			habilidades[1] = bd.getHabilidad(hab1);
		}
		if (hab2 >= 0) {
			habilidades[2] = bd.getHabilidad(hab2);
		}
		if (hab3 >= 0) {
			habilidades[3] = bd.getHabilidad(hab3);
		}
		return habilidades;
	}
	
}
