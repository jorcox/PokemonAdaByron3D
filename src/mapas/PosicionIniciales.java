package mapas;

import java.util.HashMap;

public class PosicionIniciales {

	private HashMap<String, Posicion> map;

	public PosicionIniciales() {
		map.put("Tranvia_n.tmx", new Posicion(200, 300));
		map.put("Terraza.tmx", new Posicion(400, 2900));
		map.put("Sotano.tmx", new Posicion(1250, 2750));
		map.put("Redes.tmx", new Posicion(1250, 2500));
		map.put("Pasillo.tmx", new Posicion(700, 1850));
		map.put("Liga.tmx", new Posicion(1500, 100));
		map.put("Lab1.tmx", new Posicion(800, 2300));
		map.put("Hendrix.tmx", new Posicion(315, 150));
		map.put("Hardware.tmx", new Posicion(550, 2400));
		map.put("Hall.tmx", new Posicion(1000, 1050));
		map.put("GimMena.tmx", new Posicion(175, 150));
		map.put("GimHardware.tmx", new Posicion(1025, 75));
		map.put("GimGvalles.tmx", new Posicion(260, 100));
		map.put("GimGiga.tmx", new Posicion(515, 100));
		map.put("Giga.tmx", new Posicion(610, 120));
		map.put("Geoslab.tmx", new Posicion(160, 350));
		map.put("Estudios.tmx", new Posicion(675, 115));
		map.put("Bosque.tmx", new Posicion(550, 200));
		map.put("Cueva.tmx", new Posicion(2800, 2050));
		map.put("Aulas.tmx", new Posicion(250, 2750));
		map.put("Cafeteria_n.tmx", new Posicion(420, 40));
	}

	public HashMap<String, Posicion> getHashMap() {
		return map;
	}

}
