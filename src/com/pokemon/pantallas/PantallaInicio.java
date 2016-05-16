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
import com.pokemon.utilidades.ArchivoGuardado;

public class PantallaInicio extends Pantalla {

	PokemonAdaByron game;

	Music music = Gdx.audio.newMusic(Gdx.files.internal("res/musica/PokemonHGSS.mp3"));

	Texture portada;

	Sprite bg;

	SpriteBatch batch;

	// TweenManager manager;

	BitmapFont font = new BitmapFont(Gdx.files.internal("res/fuentes/pokemon.fnt"),
			Gdx.files.internal("res/fuentes/pokemon.png"), false);

	FreeTypeFontGenerator generator;

	boolean startFading = false;

	private int musicState = 0;

	private boolean letras = true;
	
	public PantallaInicio(ArchivoGuardado ctx) {
		this.setCtx(ctx);
		
		/*
		 * Configuracion musica
		 */
		music.play();
		music.setLooping(true);
		music.setVolume(0.01f);
		/*
		 * Listener teclas
		 */
		Gdx.input.setInputProcessor(this);
		/*
		 * Configuracion fuente
		 */
		generator = new FreeTypeFontGenerator(Gdx.files.internal("res/fuentes/PokemonFont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 40;
		font = generator.generateFont(parameter); // font size 30 pixels

		new Timer().scheduleTask(new Task() {
			public void run() {
				letras = letras ? false : true;
			}
		}, (float) 0.5,(float) 0.5);
	}

	@Override
	public void render(float delta) {
		/*
		 * Eso no se que es pero tiene que estar
		 */
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_ARRAY_BUFFER);

		font.setColor(Color.BLACK);
		// manager.update(delta);
		batch.begin();
		batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// bg.draw(batch);

		if (letras) {
			font.draw(batch, "Presiona ENTER", (Gdx.graphics.getWidth() / 2) - 45, 100);
		}
		batch.end();

		changeMusicVol(delta);
	}

	private void changeMusicVol(float delta) {
		float volume = music.getVolume();
		switch (musicState) {
		case 1:
			if (music.isLooping() || music.isPlaying()) {
				if (volume > 0.10f)
					volume -= delta;
				else {
					changeToMainMenu();
				}
				music.setVolume(Math.abs(volume));
			}
			break;
		case 0:
			if (music.isLooping() || music.isPlaying()) {
				if (volume < 1.0f)
					volume += delta * 3;
				else {
					volume = 1.0f;
				}
				music.setVolume(volume);
			}
			break;
		}
	}

	private void changeToMainMenu() {
		music.stop();
		((Game) Gdx.app.getApplicationListener()).setScreen(new Menu(getCtx()));
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		portada = new Texture("res/imgs/Portada.jpg");
		bg = new Sprite(portada);
		batch = new SpriteBatch();

		// Tween.registerAccessor(BitmapFont.class,
		// new BitmapFontTween());

		// manager = new TweenManager();

		// Tween.to(font, BitmapFontTween.BETA, 1f).target(1)
		// .repeatYoyo(1000000, .5f).start(manager);
	}

	protected void tweenCompleted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
		music.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (Keys.ENTER == keycode) {
			musicState = 1;
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
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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