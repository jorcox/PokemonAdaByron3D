package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import com.badlogic.gdx.utils.FloatArray;

public class Arreglador {
	
	final static FloatArray verts = new FloatArray(300);
	final static FloatArray norms = new FloatArray(300);
	final static FloatArray uvs = new FloatArray(200);
	
	public static void main(String[] args) {

		String line;
		String[] tokens;
		char firstChar;
		
		String pok = "Kingler";

		File file = new File("All Pokemon/" + pok + "/BR_" + pok + ".obj");
		File fileOut = new File("All Pokemon/" + pok + "/" + pok + ".obj");
		try {
			if (!fileOut.exists()) {
				fileOut.createNewFile();
			}
			
	
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)), 4096);
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileOut), 4096);
		
			while ((line = reader.readLine()) != null) {

				tokens = line.split("\\s+");
				if (tokens.length < 1) break;

				if (tokens[0].length() == 0) {
					continue;
				} else if ((firstChar = tokens[0].toLowerCase().charAt(0)) == '#') {
					writer.write(line + "\n");
					continue;
				} else if (firstChar == 'v') {
					writer.write(line + "\n");
				} else if (firstChar == 'f') {
					writer.write(line + "\n");
				} else if (firstChar == 'o' ){

				} else if (firstChar == 'g' ){
					writer.write(line + "\n");
				} else if (firstChar == 's' ){
					writer.write(line + "\n");
				} else if (tokens[0].equals("mtllib")) {
					writer.write(line + "\n");
				} else if (tokens[0].equals("usemtl")) {
					writer.write("g " + tokens[1] + "\n");
					writer.write(line + "\n");
				}
			}
			reader.close();
			writer.close();
		} catch (IOException e) {
		}
	}

}
