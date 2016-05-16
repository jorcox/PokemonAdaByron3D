package sonido;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Reproductor {
	Clip clip;
	
	public Reproductor(){
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void reproducir(File file){
		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
			clip.open(inputStream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	public void stop() {
		clip.stop();
		
	}

}
