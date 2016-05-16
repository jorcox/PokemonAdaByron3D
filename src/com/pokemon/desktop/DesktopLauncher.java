package com.pokemon.desktop;
 
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pokemon.PokemonAdaByron;
 
public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Pokemon Ada Byron";
        config.useGL30 = false;
        config.width = 720;
        config.height = 540;
        
        config.addIcon("res/imgs/Pokeball.png", FileType.Internal);
        
        new LwjglApplication(new PokemonAdaByron(), config);
    }
}
