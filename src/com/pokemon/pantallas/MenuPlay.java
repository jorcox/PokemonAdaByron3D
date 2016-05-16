package com.pokemon.pantallas;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokemon.PokemonAdaByron;
import com.pokemon.mochila.Mochila;
import com.pokemon.utilidades.ArchivoGuardado;
import com.pokemon.utilidades.Guardador;

import entrenadores.Jugador;
import pokemon.Pokemon;

public class MenuPlay extends Pantalla {

	PokemonAdaByron game;

	private float x, y;
	private int lastPressed;
	private String map;
	private Play play;
	
	private boolean writing = false;

	Texture t, button, selButton, p, pSel, b, bSel, o, oSel, s, sSel;

	BitmapFont font = new BitmapFont(
			Gdx.files.internal("res/fuentes/pokemon.fnt"),
			Gdx.files.internal("res/fuentes/pokemon.png"), false);

	SpriteBatch batch;

	Sprite bg, regButton, regButton2, regButton3, regButton4, poke, bag, opt,
			save;
	
	private int seleccion = 1;
	private List<Pokemon> listaPokemon;

	public MenuPlay(ArchivoGuardado ctx, float x, float y, int lastPressed, String map,
			List<Pokemon> listaPokemon, Play play) {
		ArchivoGuardado.musica = null;
		this.x = x;
		this.y = y;
		this.lastPressed = lastPressed;
		this.map = map;
		this.listaPokemon = listaPokemon;
		this.play = play;
		this.setCtx(ctx);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(bg, 0, 0, 720, 540);
		updateSelection();
		batch.draw(regButton, 100, 350);
		batch.draw(regButton2, 100, 200);
		batch.draw(regButton3, 400, 350);
		batch.draw(regButton4, 400, 200);
		batch.draw(poke, 108, 365);
		batch.draw(bag, 108, 215);
		batch.draw(opt, 408, 365);
		batch.draw(save, 408, 215);
		font.draw(batch, "Pokemon", 170, 395);
		font.draw(batch, "Bolsa", 170, 245);
		font.draw(batch, "Opciones", 470, 395);
		font.draw(batch, "Guardar", 470, 245);
		batch.end();
	}

	private void updateSelection() {
		resetSelection();
		switch (seleccion) {
		case 1:
			regButton = new Sprite(selButton);
			poke = new Sprite(pSel);
			break;
		case 2:
			regButton2 = new Sprite(selButton);
			bag = new Sprite(bSel);
			break;
		case 3:
			regButton3 = new Sprite(selButton);
			opt = new Sprite(oSel);
			break;
		case 4:
			regButton4 = new Sprite(selButton);
			save = new Sprite(sSel);
			break;

		}

	}

	private void resetSelection() {
		regButton = new Sprite(button);
		regButton2 = new Sprite(button);
		regButton3 = new Sprite(button);
		regButton4 = new Sprite(button);
		poke = new Sprite(p);
		bag = new Sprite(b);
		opt = new Sprite(o);
		save = new Sprite(s);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {		
		Gdx.input.setInputProcessor(this);
		bg = new Sprite(new Texture("res/imgs/background.png"));
		button = new Texture("res/imgs/panel.png");
		selButton = new Texture("res/imgs/panel2.png");
		p = new Texture("res/imgs/party.png");
		pSel = new Texture("res/imgs/party2.png");
		b = new Texture("res/imgs/bag.png");
		bSel = new Texture("res/imgs/bag2.png");
		o = new Texture("res/imgs/options.png");
		oSel = new Texture("res/imgs/options.png");
		s = new Texture("res/imgs/save.png");
		sSel = new Texture("res/imgs/save2.png");

		regButton = new Sprite(button);
		regButton2 = new Sprite(button);
		regButton3 = new Sprite(button);
		regButton4 = new Sprite(button);
		poke = new Sprite(p);
		bag = new Sprite(b);
		opt = new Sprite(o);
		save = new Sprite(s);
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
		if (keycode == getCtx().getTeclaUp()) {
			if (seleccion != 1 && seleccion != 3) {
				seleccion -= 1;
			}
		} else if (keycode == getCtx().getTeclaDown()) {
			if (seleccion != 2 && seleccion != 4) {
				seleccion += 1;
			}
		} else if (keycode == getCtx().getTeclaLeft()) {
			if (seleccion != 1 && seleccion != 2) {
				seleccion -= 2;
			}
		} else if (keycode == getCtx().getTeclaRight()) {
			if (seleccion != 3 && seleccion != 4) {
				seleccion += 2;
			}
		} else if (keycode == getCtx().getTeclaA()) {
			hacerAccion(seleccion);
		} else if (keycode == Keys.ESCAPE) {
			Gdx.app.exit();
		} else if (keycode == getCtx().getTeclaB()) {
			if(play.getCtx().getMapas().containsKey(map)){				
				Jugador aux = Jugador.nuevoJugador(getCtx().jugador);
				Pantalla pant=play.getCtx().getMapas().get(map);
				((Game) Gdx.app.getApplicationListener()).setScreen(pant);
				pant.getCtx().jugador=aux;
			} else {
				((Game) Gdx.app.getApplicationListener()).setScreen(new Play(getCtx(), x, y,
						lastPressed, map));
			}
		}
		return true;
	}

	private void hacerAccion(int seleccion) {
		switch (seleccion) {
		case 1:
			((Game) Gdx.app.getApplicationListener())
					.setScreen(new MenuPokemon(getCtx(), listaPokemon, this, false));
			break;
		case 2:
			((Game) Gdx.app.getApplicationListener()).setScreen(new MenuMochila(getCtx(), this));
			break;
		case 3:
			((Game) Gdx.app.getApplicationListener()).setScreen(new OpcionesTeclas(getCtx(), this));
			break;
		case 4:
			if (!writing) {
				guardar();
			} else {
				getCtx().dialogo.limpiar();
				writing = false;
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * Guarda el objeto ArchivoGuardado en un fichero binario.
	 */
	private void guardar() {
		/* Actualiza posicion del jugador antes de guardar */
		getCtx().x = x;
		getCtx().y = y;
		getCtx().lastPressed = lastPressed;
		getCtx().map = map;
		getCtx().getMapas().put(play.getMapa(), play);
		
		boolean saved = Guardador.guardar(getCtx());
		writing = true;
		if (saved) {
			getCtx().dialogo.procesarDialogo("guardado_1");
		} else {
			getCtx().dialogo.procesarDialogo("guardado_2");
		}
		
		String l1 = getCtx().dialogo.siguienteLinea();
		String l2 = getCtx().dialogo.siguienteLinea();

		if (l1 != null) {
			if (l2 == null) {
				l2 = "";
			}
			getCtx().dialogo.setLineas(l1, l2);
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