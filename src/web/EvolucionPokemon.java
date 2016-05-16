package web;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class EvolucionPokemon {
	public static void main(String[] args) {
		try {
			for (int i = 1; i <= 151; i++) {
				System.out.println("Pokemon " + i);
				System.out.println("__________________________");
				getUrlString(
						"http://www.pokexperto.net/index2.php?seccion=nds/nationaldex/pkmn&pk="
								+ i, i);
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
			Elements nombres = doc.getElementsByClass("bordeambos");
			Elements niveles = nombres.get(nombres.size()-1).select("td");
			String primerNivel = niveles.get(2).text();
			String segundoNivel = niveles.get(4).text();
			System.out.println(indice + "," + primerNivel + "," + segundoNivel);
			fichero = new FileWriter("evolucion_pokemon.txt", true);
			pw = new PrintWriter(fichero);
			Scanner palabra = new Scanner(primerNivel);
			palabra.next();
			Scanner palabra2 = new Scanner(segundoNivel);
			try {
				int nivel1 = palabra.nextInt();
				palabra2.next();
				try {
					int nivel2 = palabra2.nextInt();
					pw.println(indice + "," + nivel1 + "," + nivel2 + ",");

				} catch (Exception e) {
					pw.println(indice + "," + nivel1 + ",");
				}
			} catch (Exception e) {
				// Nada
			}
			palabra.close();
			palabra2.close();
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
