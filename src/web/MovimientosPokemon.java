package web;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MovimientosPokemon {

	static String[] colores = { "Azul", "Rojo", "Verde", "Negro", "Morado",
			"Blanco", "Rosa", "Naranja", "Amarillo", "Púrpura", "Marrón" };

	public static void main(String[] args) {
		try {
			for (int i = 1; i <= 151; i++) {
				System.out.println("Pokemon " + i);
				System.out.println("__________________________");
				getUrlString("http://www.pokexperto.net/index2.php?seccion=nds/nationaldex/movimientos_nivel&pk="
						+ i,i);
				Thread.sleep(4000);
			}
		} catch (Exception e) {
			// si no la encuentra pasa aqui
			e.printStackTrace();
		}
	}

	public static String getUrlString(String url, int indice) {
		URL miURL = null;
		InputStreamReader isReader = null;
		BufferedReader bReader = null;
		String lineaURL;
		String html = "";
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			miURL = new URL(url);
			isReader = new InputStreamReader(miURL.openStream());
			bReader = new BufferedReader(isReader);
			while ((lineaURL = bReader.readLine()) != null) {
				html += lineaURL + "\n";
			}
			bReader.close();
			isReader.close();
			Document doc = Jsoup.parse(html);
			Elements niveles = new Elements();
			int c = 0;
			/*
			 * No va lo de los colores
			 */
			while (c < colores.length) {
				niveles.addAll(doc.getElementsByClass(colores[c]));
				c++;
			}
			Elements nombres = doc.getElementsByClass("nav6c");
			fichero = new FileWriter("mov_nivel.txt",true);
			pw = new PrintWriter(fichero);
			int i = 0;
			int j = 0;
			int old = 0;
			pw.println(indice);
			while (i < niveles.size()) {
				try {
					int nivel = Integer.parseInt(niveles.get(i).text());
					if (nivel >= old) {
						String nombre = nombres.get(j).text();
						System.out.println(nivel + ";" + nombre);
						pw.println(nivel+","+nombre);
						i = i + 2;
						j = j + 2;
						old = nivel;
					} else {
						i++;
					}

				} catch (Exception e) {
					i++;
				}
			}
			pw.println(";");
			pw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
}
