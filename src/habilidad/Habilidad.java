package habilidad;

import logica.Tipo;

public class Habilidad {

	private String nombre;
	private String descripcion;
	private int poder;
	private int precision;
	private Tipo tipo;
	private Categoria categoria;
	private int pp;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getPoder() {
		return poder;
	}

	public void setPoder(int poder) {
		this.poder = poder;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public int getPp() {
		return pp;
	}

	public void setPp(int pp) {
		this.pp = pp;
	}

	public Categoria getCategoria(String categoria) {
		switch (categoria) {
		case "Physical":
			return Categoria.FISICO;
		case "Special":
			return Categoria.ESPECIAL;
		case "Status":
			return Categoria.ESTADO;
		default:
			return null;
		}
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
	
	public int getIndiceTextura(){
		switch (tipo) {
		case ACERO:
			return 0;
		case AGUA:
			return 2;
		case BICHO:
			return 6;
		case DRAGON:
			return 16;
		case ELECTRICO:
			return 13;
		case FANTASMA:
			return 9;
		case FUEGO:
			return 10;
		case HADA:
			return 14;
		case HIELO:
			return 11;
		case LUCHA:
			return 1;
		case NORMAL:
			return 8;
		case PLANTA:
			return 12;
		case PSIQUICO:
			return 7;
		case ROCA:
			return 5;
		case SINIESTRO:
			return 17;
		case TIERRA:
			return 4;
		case VENENO:
			return 3;
		case VOLADOR:
			return 15;
		default:
			return -1;
		}
	}
	
	
}
