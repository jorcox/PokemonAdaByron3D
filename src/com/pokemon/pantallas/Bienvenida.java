package com.pokemon.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.pokemon.PokemonAdaByron;
import com.pokemon.dialogo.Dialogo;
import com.pokemon.utilidades.ArchivoGuardado;
import com.pokemon.utilidades.Importador;

public class Bienvenida extends Pantalla {

	PokemonAdaByron game;

	private int counter = 0; 

	private Sprite optionBox;

	BitmapFont font;

	SpriteBatch batch;

	ArchivoGuardado guardado;

	String playerGender = "male";

	Sprite bg, box, prof;
	
	FreeTypeFontGenerator generator;

	private boolean optionsBoxVisible = false;

	// private boolean profVisible = true;
	private Music m = Gdx.audio.newMusic(Gdx.files
			.internal("res/musica/ProfessorIntro.mp3"));

	private int optionsScreen = 1;

	private boolean optionsVisible = false;
	
	private Dialogo dialogo;

	public Bienvenida(ArchivoGuardado ctx, PokemonAdaByron game) {
		this.setCtx(ctx);
		
		dialogo = new Dialogo("es", "ES");
		//super("es", "ES");
		this.game = game;
		Gdx.input.setInputProcessor(this);
		m.play();
		m.setLooping(true);
		generator = new FreeTypeFontGenerator(Gdx.files.internal("res/fuentes/PokemonFont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 35;
		font = generator.generateFont(parameter); // font size 35 pixels
		dialogo.procesarDialogo("jamarro_intro");
	}


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		font.setColor(Color.BLACK);
		batch.begin();
		bg.draw(batch);
		if (optionsBoxVisible) {
			box.draw(batch);
		}
		prof.draw(batch);
		prof.setSize(169, (float) 417.5);
		prof.setX(540);
		prof.setY(55);
		font.draw(batch, dialogo.getLinea1(), 50, 125);
		font.draw(batch, dialogo.getLinea2(), 50, 75);

		if (optionsVisible) {
			optionBox.draw(batch);
			font.draw(batch, "Yes", 60, 255);
			font.draw(batch, "No", 60, 215);
		}

		batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		optionBox = new Sprite(new Texture("res/imgs/OptionBox.png"));
		optionBox.setPosition(25, 150);

		bg = new Sprite(new Texture("res/imgs/WelcomeBG.png"));
		box = new Sprite(new Texture("res/imgs/OptionBox.png"));
		prof = new Sprite(new Texture("res/imgs/jamarro.png"));
		prof.setX(500);

		batch = new SpriteBatch();
		font.setColor(Color.BLACK);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
	
	

	@Override
	public boolean keyDown(int keycode) {
		if (!dialogo.isWriting()) {
			switch (keycode) {
			case (Keys.ENTER):
				String l1 = dialogo.siguienteLinea();
				String l2 = dialogo.siguienteLinea();
				
				if (l1 != null) {
					if (l2 == null) {
						l2 = "";
					}
					optionsVisible = false;

					if (l1.contains("${NOMBRE}")) {
						l1 = l1.replace("${NOMBRE}",
								ArchivoGuardado.nombreJugador);
					} else if (l2.contains("${NOMBRE}")) {
						l2 = l2.replace("${NOMBRE}",
								ArchivoGuardado.nombreJugador);
					} else if (l1.contains("${CREACION_NOMBRE}")
							|| l2.contains("${CREACION_NOMBRE}")) {
						l1 = l1.replace("${CREACION_NOMBRE}", "");
						l2 = l2.replace("${CREACION_NOMBRE}", "");
						ArchivoGuardado.nombreJugador = "Sara";
					}

					/* Escribe letra a letra el dialogo */
					dialogo.setLineas(l1, l2);
					
					/*
					 * if (script[counter].contains("(OPTION)")) {
					 * script[counter] = script[counter].replace( "(OPTION)",
					 * ""); optionsVisible = true; }
					 */
				} else {
					m.stop();
					((Game) Gdx.app.getApplicationListener())
							.setScreen(new Play(getCtx(), 60,60,3,"Tranvia_n.tmx"));
				}
				break;
			case (Keys.UP):
				if (optionsScreen == 1) {

				}
				break;
			case (Keys.DOWN):
				if (optionsScreen == 1) {

				}
				break;
			}
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		return false;
	}

	@Override
	public boolean keyTyped(char character) {

		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer,
			int button) {

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer,
			int button) {

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		return false;
	}

	@Override
	public boolean scrolled(int amount) {

		return false;
	}

}