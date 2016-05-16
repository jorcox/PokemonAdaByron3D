package com.pokemon.pantallas;

import java.sql.SQLException;
import java.util.ArrayList;

import pokemon.Pokemon;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.pokemon.dialogo.Dialogo;
import com.pokemon.tween.SpriteAccessor;
import com.pokemon.utilidades.ArchivoGuardado;

import db.BaseDatos;
import entrenadores.Jugador;

public class Evolucion extends Pantalla {

	private Screen screen;
	private Jugador jugador;
	private Dialogo dialogo;
	private Pokemon pkmn, pkmnEvolucion;
	private int fase = 0;
	private Sprite evolucionBg, pokemon, evolucion, message;
	private TweenManager tweenManager;
	BaseDatos bd;
	FreeTypeFontGenerator generator;
	BitmapFont font;
	SpriteBatch batch;

	public Evolucion(ArchivoGuardado ctx, Screen screen, Jugador jugador, int i) {
		this.setCtx(ctx);
		this.screen=screen;
		dialogo = new Dialogo("es", "ES");
		dialogo.procesarDialogo("evolucion");

		setJugador();
		pkmn = this.jugador.getPokemon(i);
		Gdx.input.setInputProcessor(this);
		generator = new FreeTypeFontGenerator(
				Gdx.files.internal("res/fuentes/PokemonFont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 35;
		font = generator.generateFont(parameter); // font size 35 pixels
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();
		evolucionBg = new Sprite(new Texture(
				"res/imgs/evolucion/evolutionbg.png"));
		message = new Sprite(new Texture("res/imgs/batallas/battleMessage.png"));
		setPokemon();
		font.setColor(Color.BLACK);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		font.setColor(Color.BLACK);
		batch.begin();
		dibujarBg();
		if (fase == 1) {
			pokemon.draw(batch);
		}
		if (fase == 2) {
			evolucion.draw(batch);
		}
		batch.end();
	}

	private void dibujarBg() {
		evolucionBg.setSize(720, 540);
		evolucionBg.draw(batch);
		message.setSize(720, 120);
		message.draw(batch);
		font.draw(batch, dialogo.getLinea1(), 50, 85);
		font.draw(batch, dialogo.getLinea2(), 50, 45);
	}

	public void setPokemon() {
		pokemon = new Sprite(new Texture("res/imgs/pokemon/" + pkmn.getNombre()
				+ ".png"));
		pokemon.setSize(120, 120);
		pokemon.setPosition(280, 250);
		try { 
			bd = new BaseDatos("pokemon_base");
			pkmnEvolucion = bd
					.getPokemonTipo(bd.getIdPoke(pkmn.getNombre()) + 1);
			bd.shutdown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		evolucion = new Sprite(new Texture("res/imgs/pokemon/"
				+ pkmnEvolucion.getNombre() + ".png"));
		evolucion.setSize(120, 120);
		evolucion.setPosition(280, 250);
	}

	
	@Override
	public boolean keyDown(int keycode) {
		if (!dialogo.isWriting()) {
			if (keycode == getCtx().getTeclaA()) {
				if(fase==0)fase = 1;
				String l1 = dialogo.siguienteLinea();
				String l2 = dialogo.siguienteLinea();
				if (l1 != null) {
					if (l1.contains("ENHORABUENA")) {
						fase=2;
					} else {
						l1 = l1.replace("${POKEMON}", pkmn.getNombre());
						l1 = l1.replace("${EVOLUCION}",
								pkmnEvolucion.getNombre());
					}
				}
				if (l2 != null) {
					l2 = l2.replace("${EVOLUCION}", pkmnEvolucion.getNombre());
					l2 = l2.replace("${POKEMON}", pkmn.getNombre());
				}
				if(l1==null){
					pkmn.evolucionar(pkmnEvolucion.getNombre());
					((Game) Gdx.app.getApplicationListener())
					.setScreen(screen);
				}

				dialogo.setLineas(l1, l2);
			}
		}
		return false;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setJugador() {
		jugador = new Jugador("Sara", false);
		equipoPokemon();
	}

	public void equipoPokemon() {
		ArrayList<Pokemon> arrayP = new ArrayList<Pokemon>();
		try {
			BaseDatos db = new BaseDatos("pokemon_base");
			arrayP.add(db.getPokemon(6));
			arrayP.add(db.getPokemon(1));
			arrayP.add(db.getPokemon(2));
			arrayP.add(db.getPokemon(3));
			arrayP.add(db.getPokemon(0));
			arrayP.add(db.getPokemon(5));
			jugador.setEquipo(arrayP);
			db.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
