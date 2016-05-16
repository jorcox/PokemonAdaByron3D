package com.pokemon.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokemon.PokemonAdaByron;
import com.pokemon.utilidades.ArchivoGuardado;

public class Menu extends Pantalla {

	PokemonAdaByron game;

	Texture t, button, conButton;

	BitmapFont font = new BitmapFont(
			Gdx.files.internal("res/fuentes/pokemon.fnt"),
			Gdx.files.internal("res/fuentes/pokemon.png"), false);

	Texture selButton, selConButton;

	SpriteBatch batch;

	Sprite bg, regButton, regButton2, regButton3, butContinue;

	private boolean archivoGuardado = ArchivoGuardado.existe;

	private int seleccion = 2;

	public Menu(ArchivoGuardado ctx) {
		this.setCtx(ctx);
		ArchivoGuardado.musica = null;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		bg.draw(batch);
		updateSelection();

		regButton2.draw(batch);
		regButton3.draw(batch);
		
		if (archivoGuardado) {
			regButton.draw(batch);			
			font.draw(batch, "Continuar", 190, 475);
			font.draw(batch, "Nueva Partida", 190, 190);
			font.draw(batch, "Creditos", 190, 90);

		} else {
			//font.draw(batch, "Continuar", 190, 440);
			font.draw(batch, "Nueva Partida", 190, 340);
			font.draw(batch, "Creditos", 190, 240);
		}
		batch.end();

	}

	private void updateSelection() {
		resetSelection();
		switch (seleccion) {
			case 1:
				if (archivoGuardado) {
					regButton = new Sprite(selConButton);
					regButton.setX(150);
					regButton.setY(250);
				} else {
					regButton = new Sprite(selButton);
					regButton.setX(150);
					regButton.setY(400);
				}
				break;
			case 2:
				regButton2 = new Sprite(selButton);
				if (archivoGuardado) {
					regButton2.setX(150);
					regButton2.setY(150);
				} else {
					regButton2.setX(150);
					regButton2.setY(300);
				}
				break;
			case 3:
				regButton3 = new Sprite(selButton);
				if (archivoGuardado) {
					regButton3.setX(150);
					regButton3.setY(50);
				} else {
					regButton3.setX(150);
					regButton3.setY(200);
				}
				break;

		}

	}

	private void resetSelection() {
		if (archivoGuardado) {
			regButton = new Sprite(conButton);
			regButton2 = new Sprite(button);
			regButton3 = new Sprite(button);

			regButton.setX(150);
			regButton.setY(250);

			regButton2.setX(150);
			regButton2.setY(150);

			regButton3.setX(150);
			regButton3.setY(50);
		} else {
			regButton = new Sprite(button);
			regButton2 = new Sprite(button);
			regButton3 = new Sprite(button);

			regButton.setX(150);
			regButton.setY(400);

			regButton2.setX(150);
			regButton2.setY(300);

			regButton3.setX(150);
			regButton3.setY(200);
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

		Gdx.input.setInputProcessor(this);
		bg = new Sprite(new Texture("res/imgs/loadbg.png"));

		button = new Texture("res/imgs/menubutton.png");
		conButton = new Texture("res/imgs/buttoncontinue.png");

		selButton = new Texture("res/imgs/menubuttonselected.png");
		selConButton = new Texture(
				"res/imgs/PokemonMenuContinueSelected.png");

		regButton2 = new Sprite(button);
		regButton3 = new Sprite(button);

		if (archivoGuardado) {
			regButton = new Sprite(conButton);
			regButton.setX(150);
			regButton.setY(250);

			regButton2.setX(150);
			regButton2.setY(150);

			regButton3.setX(150);
			regButton3.setY(50);

		} else {
			regButton = new Sprite(button);
			regButton.setX(150);
			regButton.setY(400);

			regButton2.setX(150);
			regButton2.setY(300);

			regButton3.setX(150);
			regButton3.setY(200);
		}

		batch = new SpriteBatch();
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
		batch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case (Keys.UP):
				if (archivoGuardado) {
					if (seleccion != 1) {
						seleccion -= 1;
					}
				} else{
					if (seleccion != 2) {
						seleccion -= 1;
					}
				}
				
				break;
			case (Keys.DOWN):
				if (seleccion != 3) {
					seleccion += 1;
				}
				break;
			case (Keys.ENTER):
				hacerAccion(seleccion);
				break;
			case (Keys.ESCAPE):
				Gdx.app.exit();
				break;
		}
		return true;
	}

	private void hacerAccion(int i) {
		if (i == 1) {
			if (archivoGuardado) {
				Gdx.app.log(PokemonAdaByron.LOG, "Cargar juego de un fichero de guardado");
				// TODO				
			} else {
				Gdx.app.log(PokemonAdaByron.LOG, "Crear nueva partida");
				// TODO
				((Game) Gdx.app.getApplicationListener())
				.setScreen(new Bienvenida(getCtx(), game));
			}
		} else if (i == 2) {
			
				Gdx.app.log(PokemonAdaByron.LOG, "Crear nueva partida");
				//m.stop();
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new Bienvenida(getCtx(), game));
		} else if (i == 3) {
			Gdx.app.log(PokemonAdaByron.LOG, "Creditos");
			// TODO
			//((Game) Gdx.app.getApplicationListener())
			//		.setScreen(new ScrollingCredits());
		}
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