package core;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Launcher {
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "3D";
        config.useGL30 = false;
        config.width = 720;
        config.height = 540;
                
        new LwjglApplication(new LoadModelTest(), config);
	}
}
