package pokemon;

import java.sql.SQLException;
import java.util.Random;

import db.BaseDatos;
import habilidad.Habilidad;
import logica.Tipo;

public class AparicionPokemon {

	private BaseDatos bd;

	private Pokemon[] aparicion;

	public AparicionPokemon() {
		try {
			bd = new BaseDatos("pokemon_base");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void shutdown() {
		try {
			bd.shutdown();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Pokemon[] setPokemonSalvaje(String mapa) {
		double r;
		Habilidad[] habs;
		mapa = mapa.replace(".tmx", "");

		if (mapa.equals("Tranvia_n")) {
			aparicion = new Pokemon[5];
			aparicion[0] = bd.getPokemonTipo(19);
			habs = setHabilidades(307, 300, 27, -1);
			aparicion[0].setHabilidades(habs);
			aparicion[0].setTipo(Tipo.NORMAL);

			aparicion[1] = bd.getPokemonTipo(16);
			habs = setHabilidades(307, 300, 165, -1);
			aparicion[1].setHabilidades(habs);
			aparicion[1].setTipo(Tipo.VOLADOR);

			aparicion[2] = bd.getPokemonTipo(29);
			habs = setHabilidades(64, 437, 308, 110);
			aparicion[2].setHabilidades(habs);
			aparicion[2].setTipo(Tipo.VENENO);

			aparicion[3] = bd.getPokemonTipo(32);
			habs = setHabilidades(64, 437, 166, 110);
			aparicion[3].setHabilidades(habs);
			aparicion[3].setTipo(Tipo.VENENO);

			aparicion[4] = bd.getPokemonTipo(43);
			habs = setHabilidades(208, 434, -1, -1);
			aparicion[4].setHabilidades(habs);
			aparicion[4].setTipo(Tipo.PLANTA);
			int n = 4;
			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < n; j++) {
					if (j < n - 4) {
						aparicion[i].subirNivel(0, 0);
					} else {
						r = new Random().nextDouble();
						if (r > 0.35) {
							aparicion[i].subirNivel(0, 0);
						}
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}

		} else if (mapa.equals("Terraza")) {
			aparicion = new Pokemon[3];
			aparicion[0] = bd.getPokemonTipo(147);
			habs = setHabilidades(56, 536, 60, -1);
			aparicion[0].setHabilidades(habs);
			aparicion[0].setTipo(Tipo.DRAGON);

			aparicion[1] = bd.getPokemonTipo(142);
			habs = setHabilidades(27, 503, 23, 161);
			aparicion[1].setHabilidades(habs);
			aparicion[1].setTipo(Tipo.VOLADOR);

			aparicion[2] = bd.getPokemonTipo(21);
			habs = setHabilidades(158, 156, 32, 321);
			aparicion[2].setHabilidades(habs);
			aparicion[2].setTipo(Tipo.VOLADOR);

			int n = 30;
			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < n; j++) {
					if (j < n - 4) {
						aparicion[i].subirNivel(0, 0);
					} else {
						r = new Random().nextDouble();
						if (r > 0.35) {
							aparicion[i].subirNivel(0, 0);
						}
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}

		} else if (mapa.equals("Sotano")) {
			aparicion = new Pokemon[5];
			aparicion[0] = bd.getPokemonTipo(109);
			habs = setHabilidades(436, 30, 431, 258);
			aparicion[0].setHabilidades(habs);
			aparicion[0].setTipo(Tipo.VENENO);

			aparicion[1] = bd.getPokemonTipo(113);
			habs = setHabilidades(262, 320, 36, 266);
			aparicion[1].setHabilidades(habs);
			aparicion[1].setTipo(Tipo.HADA);

			aparicion[2] = bd.getPokemonTipo(111);
			habs = setHabilidades(291, 292, 508, 226);
			aparicion[2].setHabilidades(habs);
			aparicion[2].setTipo(Tipo.ROCA);

			aparicion[3] = bd.getPokemonTipo(122);
			habs = setHabilidades(459, 204, 320, 457);
			aparicion[3].setHabilidades(habs);
			aparicion[3].setTipo(Tipo.PSIQUICO);

			aparicion[4] = bd.getPokemonTipo(114);
			habs = setHabilidades(323, 206, 34, 205);
			aparicion[4].setHabilidades(habs);
			aparicion[4].setTipo(Tipo.PLANTA);
			int n = 30;
			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < n; j++) {
					if (j < n - 4) {
						aparicion[i].subirNivel(0, 0);
					} else {
						r = new Random().nextDouble();
						if (r > 0.35) {
							aparicion[i].subirNivel(0, 0);
						}
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}

		} else if (mapa.equals("Redes")) {
			aparicion = new Pokemon[5];
			aparicion[0] = bd.getPokemonTipo(136);
			habs = setHabilidades(140, 27, 145, 307);
			aparicion[0].setHabilidades(habs);
			aparicion[0].setTipo(Tipo.FUEGO);

			aparicion[1] = bd.getPokemonTipo(126);
			habs = setHabilidades(138, 431, 139, 137);
			aparicion[1].setHabilidades(habs);
			aparicion[1].setTipo(Tipo.FUEGO);

			aparicion[2] = bd.getPokemonTipo(77);
			habs = setHabilidades(143, 141, 292, 131);
			aparicion[2].setHabilidades(habs);
			aparicion[2].setTipo(Tipo.FUEGO);

			aparicion[3] = bd.getPokemonTipo(37);
			habs = setHabilidades(145, 31, 139, 450);
			aparicion[3].setHabilidades(habs);
			aparicion[3].setTipo(Tipo.FUEGO);

			aparicion[4] = bd.getPokemonTipo(58);
			habs = setHabilidades(27, 140, 271, 141);
			aparicion[4].setHabilidades(habs);
			aparicion[4].setTipo(Tipo.FUEGO);
			int n = 25;
			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < n; j++) {
					if (j < n - 4) {
						aparicion[i].subirNivel(0, 0);
					} else {
						r = new Random().nextDouble();
						if (r > 0.35) {
							aparicion[i].subirNivel(0, 0);
						}
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}

		} else if (mapa.equals("Pasillo")) {
			aparicion = new Pokemon[5];
			aparicion[0] = bd.getPokemonTipo(35);
			habs = setHabilidades(565, 306, 320, -1);
			aparicion[0].setHabilidades(habs);
			aparicion[0].setTipo(Tipo.HADA);

			aparicion[1] = bd.getPokemonTipo(39);
			habs = setHabilidades(306, 565, 320, -1);
			aparicion[1].setHabilidades(habs);
			aparicion[1].setTipo(Tipo.HADA);

			aparicion[2] = bd.getPokemonTipo(83);
			habs = setHabilidades(166, 11, 321, 158);
			aparicion[2].setHabilidades(habs);
			aparicion[2].setTipo(Tipo.NORMAL);

			aparicion[3] = bd.getPokemonTipo(84);
			habs = setHabilidades(166, 307, 313, 321);
			aparicion[3].setHabilidades(habs);
			aparicion[3].setTipo(Tipo.VOLADOR);

			aparicion[4] = bd.getPokemonTipo(133);
			habs = setHabilidades(300, 296, 307, 27);
			aparicion[4].setHabilidades(habs);
			aparicion[4].setTipo(Tipo.NORMAL);
			int n = 15;
			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < n; j++) {
					if (j < n - 4) {
						aparicion[i].subirNivel(0, 0);
					} else {
						r = new Random().nextDouble();
						if (r > 0.35) {
							aparicion[i].subirNivel(0, 0);
						}
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}
		} else if (mapa.equals("Liga")) {

		} else if (mapa.equals("Lab1")) {

		} else if (mapa.equals("Hendrix")) {
			aparicion = new Pokemon[5];
			aparicion[0] = bd.getPokemonTipo(135);
			habs = setHabilidades(77, 307, 110, 13);
			aparicion[0].setHabilidades(habs);
			aparicion[0].setTipo(Tipo.ELECTRICO);

			aparicion[1] = bd.getPokemonTipo(102);
			habs = setHabilidades(459, 318, 272, 207);
			aparicion[1].setHabilidades(habs);
			aparicion[1].setTipo(Tipo.PLANTA);

			aparicion[2] = bd.getPokemonTipo(104);
			habs = setHabilidades(224, 287, 228, 313);
			aparicion[2].setHabilidades(habs);
			aparicion[2].setTipo(Tipo.TIERRA);

			aparicion[3] = bd.getPokemonTipo(108);
			habs = setHabilidades(180, 34, 292, -1);
			aparicion[3].setHabilidades(habs);
			aparicion[3].setTipo(Tipo.NORMAL);

			aparicion[4] = bd.getPokemonTipo(125);
			habs = setHabilidades(70, 115, 296, 74);
			aparicion[4].setHabilidades(habs);
			aparicion[4].setTipo(Tipo.ELECTRICO);
			int n = 4;
			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < n; j++) {
					if (j < n - 4) {
						aparicion[i].subirNivel(0, 0);
					} else {
						r = new Random().nextDouble();
						if (r > 0.35) {
							aparicion[i].subirNivel(0, 0);
						}
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}

		} else if (mapa.equals("Hardware")) {
			aparicion = new Pokemon[6];
			aparicion[0] = bd.getPokemonTipo(81);
			habs = setHabilidades(77, 300, 335, 520);
			aparicion[0].setHabilidades(habs);
			aparicion[0].setTipo(Tipo.ELECTRICO);

			aparicion[1] = bd.getPokemonTipo(100);
			habs = setHabilidades(72, 300, 335, 507);
			aparicion[1].setHabilidades(habs);
			aparicion[1].setTipo(Tipo.ELECTRICO);

			aparicion[2] = bd.getPokemonTipo(88);
			habs = setHabilidades(306, 429, 231, -1);
			aparicion[2].setHabilidades(habs);
			aparicion[2].setTipo(Tipo.VENENO);

			aparicion[3] = bd.getPokemonTipo(52);
			habs = setHabilidades(308, 305, 27, 303);
			aparicion[3].setHabilidades(habs);
			aparicion[3].setTipo(Tipo.NORMAL);

			aparicion[4] = bd.getPokemonTipo(96);
			habs = setHabilidades(306, 459, 287, -1);
			aparicion[4].setHabilidades(habs);
			aparicion[4].setTipo(Tipo.PSIQUICO);

			aparicion[5] = bd.getPokemonTipo(63);
			habs = setHabilidades(459, -1, -1, -1);
			aparicion[5].setHabilidades(habs);
			aparicion[5].setTipo(Tipo.PSIQUICO);
			int n = 4;
			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < n; j++) {
					if (j < n - 4) {
						aparicion[i].subirNivel(0, 0);
					} else {
						r = new Random().nextDouble();
						if (r > 0.35) {
							aparicion[i].subirNivel(0, 0);
						}
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}

		} else if (mapa.equals("Hall")) {

		} else if (mapa.equals("Giga")) {

		} else if (mapa.equals("Geoslab")) {
			aparicion = new Pokemon[5];
			aparicion[0] = bd.getPokemonTipo(115);
			habs = setHabilidades(316, 27, 310, 277);
			aparicion[0].setHabilidades(habs);
			aparicion[0].setTipo(Tipo.TIERRA);

			aparicion[1] = bd.getPokemonTipo(128);
			habs = setHabilidades(291, 32, 271, 452);
			aparicion[1].setHabilidades(habs);
			aparicion[1].setTipo(Tipo.NORMAL);

			aparicion[2] = bd.getPokemonTipo(124);
			habs = setHabilidades(180, 242, 458, 566);
			aparicion[2].setHabilidades(habs);
			aparicion[2].setTipo(Tipo.HIELO);

			aparicion[3] = bd.getPokemonTipo(123);
			habs = setHabilidades(290, 304, 162, 3);
			aparicion[3].setHabilidades(habs);
			aparicion[3].setTipo(Tipo.PLANTA);

			aparicion[4] = bd.getPokemonTipo(127);
			habs = setHabilidades(3, 98, 95, 329);
			aparicion[4].setHabilidades(habs);
			aparicion[4].setTipo(Tipo.BICHO);
			int n = 27;
			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < n; j++) {
					if (j < n - 4) {
						aparicion[i].subirNivel(0, 0);
					} else {
						r = new Random().nextDouble();
						if (r > 0.35) {
							aparicion[i].subirNivel(0, 0);
						}
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}

		} else if (mapa.equals("Estudios")) {

		} else if (mapa.equals("Cueva")) {
			aparicion = new Pokemon[8];
			aparicion[0] = bd.getPokemonTipo(74);
			habs = setHabilidades(300, 504, 507, 233);
			aparicion[0].setHabilidades(habs);
			aparicion[0].setTipo(Tipo.ROCA);

			aparicion[1] = bd.getPokemonTipo(23);
			habs = setHabilidades(437, 27, 434, -1);
			aparicion[1].setHabilidades(habs);
			aparicion[1].setTipo(Tipo.VENENO);

			aparicion[2] = bd.getPokemonTipo(27);
			habs = setHabilidades(308, 437, 507, -1);
			aparicion[2].setHabilidades(habs);
			aparicion[2].setTipo(Tipo.TIERRA);

			aparicion[3] = bd.getPokemonTipo(41);
			habs = setHabilidades(12, 27, 179, -1);
			aparicion[3].setHabilidades(habs);
			aparicion[3].setTipo(Tipo.VENENO);

			aparicion[4] = bd.getPokemonTipo(50);
			habs = setHabilidades(308, 179, 317, 105);
			aparicion[4].setHabilidades(habs);
			aparicion[4].setTipo(Tipo.TIERRA);

			aparicion[5] = bd.getPokemonTipo(56);
			habs = setHabilidades(308, 115, 317, 105);
			aparicion[5].setHabilidades(habs);
			aparicion[5].setTipo(Tipo.LUCHA);

			aparicion[6] = bd.getPokemonTipo(66);
			habs = setHabilidades(105, 115, 101, -1);
			aparicion[6].setHabilidades(habs);
			aparicion[6].setTipo(Tipo.LUCHA);

			aparicion[7] = bd.getPokemonTipo(95);
			habs = setHabilidades(300, 319, 313, 504);
			aparicion[7].setHabilidades(habs);
			aparicion[7].setTipo(Tipo.ROCA);
			int n = 10;
			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < n; j++) {
					if (j < n - 4) {
						aparicion[i].subirNivel(0, 0);
					} else {
						r = new Random().nextDouble();
						if (r > 0.35) {
							aparicion[i].subirNivel(0, 0);
						}
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}

		} else if (mapa.equals("Cafeteria")) {

		} else if (mapa.equals("Bosque")) {
			aparicion = new Pokemon[6];
			aparicion[0] = bd.getPokemonTipo(25);
			habs = setHabilidades(77, 307, 64, -1);
			aparicion[0].setHabilidades(habs);
			aparicion[0].setTipo(Tipo.ELECTRICO);

			aparicion[1] = bd.getPokemonTipo(10);
			habs = setHabilidades(300, 7, -1, -1);
			aparicion[1].setHabilidades(habs);
			aparicion[1].setTipo(Tipo.BICHO);

			aparicion[2] = bd.getPokemonTipo(13);
			habs = setHabilidades(437, 7, -1, -1);
			aparicion[2].setHabilidades(habs);
			aparicion[2].setTipo(Tipo.BICHO);

			aparicion[3] = bd.getPokemonTipo(46);
			habs = setHabilidades(308, 12, -1, -1);
			aparicion[3].setHabilidades(habs);
			aparicion[3].setTipo(Tipo.BICHO);

			aparicion[4] = bd.getPokemonTipo(69);
			habs = setHabilidades(206, 208, -1, -1);
			aparicion[4].setHabilidades(habs);
			aparicion[4].setTipo(Tipo.PLANTA);

			aparicion[5] = bd.getPokemonTipo(48);
			habs = setHabilidades(300, 459, -1, -1);
			aparicion[5].setHabilidades(habs);
			aparicion[5].setTipo(Tipo.BICHO);

			int n = 7;
			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < n; j++) {
					if (j < n - 4) {
						aparicion[i].subirNivel(0, 0);
					} else {
						r = new Random().nextDouble();
						if (r > 0.35) {
							aparicion[i].subirNivel(0, 0);
						}
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}

		} else if (mapa.equals("Aulas")) {
			aparicion = new Pokemon[5];
			aparicion[0] = bd.getPokemonTipo(143);
			habs = setHabilidades(287, 284, 273, 507);
			aparicion[0].setHabilidades(habs);
			aparicion[0].setTipo(Tipo.NORMAL);

			aparicion[1] = bd.getPokemonTipo(137);
			habs = setHabilidades(457, 4, 281, 69);
			aparicion[1].setHabilidades(habs);
			aparicion[1].setTipo(Tipo.NORMAL);

			aparicion[2] = bd.getPokemonTipo(92);
			habs = setHabilidades(180, 181, 173, 25);
			aparicion[2].setHabilidades(habs);
			aparicion[2].setTipo(Tipo.FANTASMA);

			aparicion[3] = bd.getPokemonTipo(106);
			habs = setHabilidades(103, 90, 83, 311);
			aparicion[3].setHabilidades(habs);
			aparicion[3].setTipo(Tipo.LUCHA);

			aparicion[4] = bd.getPokemonTipo(107);
			habs = setHabilidades(106, 523, 109, 311);
			aparicion[4].setHabilidades(habs);
			aparicion[4].setTipo(Tipo.LUCHA);

			int n = 20;
			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < n; j++) {
					if (j < n - 4) {
						aparicion[i].subirNivel(0, 0);
					} else {
						r = new Random().nextDouble();
						if (r > 0.35) {
							aparicion[i].subirNivel(0, 0);
						}
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}
		}
		return aparicion;
	}

	public Pokemon[] setPokemonSalvajeAgua(String mapa) {
		double r;
		Habilidad[] habs;
		mapa = mapa.replace(".tmx", "");

		if (mapa.equals("Tranvia_n")) {
			aparicion = new Pokemon[2];
			aparicion[0] = bd.getPokemonTipo(129);
			habs = setHabilidades(300, -1, -1, -1);
			aparicion[0].setHabilidades(habs);
			aparicion[0].setTipo(Tipo.AGUA);

			aparicion[1] = bd.getPokemonTipo(72);
			habs = setHabilidades(543, 287, 434, -1);
			aparicion[1].setHabilidades(habs);
			aparicion[1].setTipo(Tipo.AGUA);

			int n = 40;
			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < n; j++) {
					if (j < n - 4) {
						aparicion[i].subirNivel(0, 0);
					} else {
						r = new Random().nextDouble();
						if (r > 0.35) {
							aparicion[i].subirNivel(0, 0);
						}
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}

		} else if (mapa.equals("Terraza")) {

		} else if (mapa.equals("Sotano")) {
			aparicion = new Pokemon[2];
			aparicion[0] = bd.getPokemonTipo(129);
			habs = setHabilidades(300, -1, -1, -1);
			aparicion[0].setHabilidades(habs);
			aparicion[0].setTipo(Tipo.AGUA);

			aparicion[1] = bd.getPokemonTipo(116);
			habs = setHabilidades(551, 543, -1, -1);
			aparicion[1].setHabilidades(habs);
			aparicion[1].setTipo(Tipo.AGUA);

			int n = 40;
			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < n; j++) {
					if (j < n - 4) {
						aparicion[i].subirNivel(0, 0);
					} else {
						r = new Random().nextDouble();
						if (r > 0.35) {
							aparicion[i].subirNivel(0, 0);
						}
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}

		} else if (mapa.equals("Redes")) {

		} else if (mapa.equals("Pasillo")) {
			
		} else if (mapa.equals("Liga")) {

		} else if (mapa.equals("Lab1")) {

		} else if (mapa.equals("Hendrix")) {
			aparicion = new Pokemon[2];
			aparicion[0] = bd.getPokemonTipo(129);
			habs = setHabilidades(300, -1, -1, -1);
			aparicion[0].setHabilidades(habs);
			aparicion[0].setTipo(Tipo.AGUA);

			aparicion[1] = bd.getPokemonTipo(118);
			habs = setHabilidades(551, 291, -1, -1);
			aparicion[1].setHabilidades(habs);
			aparicion[1].setTipo(Tipo.AGUA);

			int n = 40;
			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < n; j++) {
					if (j < n - 4) {
						aparicion[i].subirNivel(0, 0);
					} else {
						r = new Random().nextDouble();
						if (r > 0.35) {
							aparicion[i].subirNivel(0, 0);
						}
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}

		} else if (mapa.equals("Hardware")) {

		} else if (mapa.equals("Hall")) {

		} else if (mapa.equals("Giga")) {

		} else if (mapa.equals("Geoslab")) {
			aparicion = new Pokemon[2];
			aparicion[0] = bd.getPokemonTipo(129);
			habs = setHabilidades(300, -1, -1, -1);
			aparicion[0].setHabilidades(habs);
			aparicion[0].setTipo(Tipo.AGUA);

			aparicion[1] = bd.getPokemonTipo(98);
			habs = setHabilidades(551, 549, -1, -1);
			aparicion[1].setHabilidades(habs);
			aparicion[1].setTipo(Tipo.AGUA);
			int n = 40;
			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < n; j++) {
					if (j < n - 4) {
						aparicion[i].subirNivel(0, 0);
					} else {
						r = new Random().nextDouble();
						if (r > 0.35) {
							aparicion[i].subirNivel(0, 0);
						}
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}

		} else if (mapa.equals("Estudios")) {

		} else if (mapa.equals("Cueva")) {
			aparicion = new Pokemon[2];
			aparicion[0] = bd.getPokemonTipo(129);
			habs = setHabilidades(300, -1, -1, -1);
			aparicion[0].setHabilidades(habs);
			aparicion[0].setTipo(Tipo.AGUA);

			aparicion[1] = bd.getPokemonTipo(90);
			habs = setHabilidades(551, 543, -1, -1);
			aparicion[1].setHabilidades(habs);
			aparicion[1].setTipo(Tipo.AGUA);

			int n = 40;
			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < n; j++) {
					if (j < n - 4) {
						aparicion[i].subirNivel(0, 0);
					} else {
						r = new Random().nextDouble();
						if (r > 0.35) {
							aparicion[i].subirNivel(0, 0);
						}
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}

		} else if (mapa.equals("Cafeteria")) {

		} else if (mapa.equals("Bosque")) {
			aparicion = new Pokemon[2];
			aparicion[0] = bd.getPokemonTipo(129);
			habs = setHabilidades(300, -1, -1, -1);
			aparicion[0].setHabilidades(habs);
			aparicion[0].setTipo(Tipo.ELECTRICO);

			aparicion[1] = bd.getPokemonTipo(60);
			habs = setHabilidades(300, 7, -1, -1);
			aparicion[1].setHabilidades(habs);
			aparicion[1].setTipo(Tipo.BICHO);

			int n = 40;
			for (int i = 0; i < aparicion.length; i++) {
				for (int j = 0; j < n; j++) {
					if (j < n - 4) {
						aparicion[i].subirNivel(0, 0);
					} else {
						r = new Random().nextDouble();
						if (r > 0.35) {
							aparicion[i].subirNivel(0, 0);
						}
					}
				}
			}
			for (int i = 0; i < aparicion.length; i++) {
				aparicion[i].setPs(aparicion[i].getPsMax());
			}

		} else if (mapa.equals("Aulas")) {
		}
		return aparicion;
	}

	public Habilidad[] setHabilidades(int hab0, int hab1, int hab2, int hab3) {
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
