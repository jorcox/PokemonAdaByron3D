package com.pokemon.pantallas;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.pokemon.dialogo.Dialogo;
import com.pokemon.utilidades.ArchivoGuardado;

import entrenadores.Jugador;
import pokemon.Pokemon;

public class MenuPokemon extends Pantalla {

	private Pantalla screen;
	private List<Pokemon> listaPokemon;
	private Dialogo dialogo;
	private boolean combate;
	private boolean opcion = false, cam_sol = true, pokemonCambiar = false, pokemonSoltar = false, soltarSiNo = false;
	private int iAccion = -1;
	private SpriteBatch batch;
	private Sprite dedo, cajaUsar, cajaDialogo;
	private Texture tFondo, tMainVivo, tMainVivoSel, tMainMuerto, tMainMuertoSel, tVivo, tVivoSel, tMuerto, tMuertoSel,
			tHpVivo, tHpMuerto;
	private TextureRegion[] barrasVida;
	Texture barraVida;
	ArrayList<Sprite> spPokemon = new ArrayList<Sprite>();
	FreeTypeFontGenerator generator;
	BitmapFont font, fontCambiar;

	private int selection = 0;

	public MenuPokemon(ArchivoGuardado ctx, List<Pokemon> listaPokemon, Pantalla screen, boolean combate) {
		this.setCtx(ctx);
		this.screen = screen;
		this.listaPokemon = listaPokemon;
		this.combate = combate;
		generator = new FreeTypeFontGenerator(Gdx.files.internal("res/fuentes/PokemonFont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 30;
		font = generator.generateFont(parameter);
		fontCambiar = generator.generateFont(parameter);
		dialogo = new Dialogo("es", "ES");
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);

		batch = new SpriteBatch();
		tFondo = new Texture("res/imgs/listaPokemon/fondo.png");
		tMainVivo = new Texture("res/imgs/listaPokemon/partyPanelRound.png");
		tMainVivoSel = new Texture("res/imgs/listaPokemon/partyPanelRoundSel.png");
		tMainMuerto = new Texture("res/imgs/listaPokemon/partyPanelRoundFnt.png");
		tMainMuertoSel = new Texture("res/imgs/listaPokemon/partyPanelRoundSelFnt.png");
		tVivo = new Texture("res/imgs/listaPokemon/partyPanelRect.png");
		tVivoSel = new Texture("res/imgs/listaPokemon/partyPanelRectSel.png");
		tMuerto = new Texture("res/imgs/listaPokemon/partyPanelRectFnt.png");
		tMuertoSel = new Texture("res/imgs/listaPokemon/partyPanelRectSelFnt.png");
		tHpVivo = new Texture("res/imgs/listaPokemon/partyHP.png");
		tHpMuerto = new Texture("res/imgs/listaPokemon/partyHPfnt.png");
		barraVida = new Texture("res/imgs/batallas/hpbars.png");
		cajaUsar = new Sprite(new Texture("res/imgs/batallas/cajaAprender.png"));
		dedo = new Sprite(new Texture("res/imgs/batallas/aprender.png"));
		cajaDialogo = new Sprite(new Texture("res/imgs/batallas/battleMessage.png"));
		texturesPokemon();
		getBarraVida();
		font.setColor(Color.WHITE);
		fontCambiar.setColor(Color.BLACK);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		batch.draw(tFondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if (listaPokemon.size() > 0) {
			/* El primer slot de pokemon es distinto (fondo redondo) */
			drawMainPokemon();
		}
		for (int i = 1; i < listaPokemon.size(); i++) {
			/* Pinta el resto de slots de pokemon */
			drawPokemon(i);
		}
		dialogoOpcion();
		dialogoSoltar();
		batch.end();
	}

	private void texturesPokemon() {
		for (int i = 0; i < listaPokemon.size(); i++) {
			spPokemon.add(new Sprite(
					new Texture("res/imgs/pokemon/" + listaPokemon.get(i).getNombre().toLowerCase() + ".png")));
		}
	}

	private void drawMainPokemon() {
		Pokemon pokemon = listaPokemon.get(0);
		if (pokemon.vivo()) {
			if (selection != 0) {
				batch.draw(tMainVivo, 3, 400, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / (float) 3.5);
			} else {
				batch.draw(tMainVivoSel, 3, 400, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / (float) 3.5);
			}
			batch.draw(tHpVivo, 120, 450, 180, tHpVivo.getHeight());

		} else {

			if (selection != 0) {
				batch.draw(tMainMuerto, 3, 400, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / (float) 3.5);
			} else {
				batch.draw(tMainMuertoSel, 3, 400, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / (float) 3.5);
			}
			batch.draw(tHpVivo, 120, 450, 180, tHpVivo.getHeight());
		}
		batch.draw(barrasVida[0], 158, 453, (int) (132 * (pokemon.getPs() / (double) pokemon.getPsMax())), 9);
		font.draw(batch, pokemon.getNombre().toUpperCase(), 160, 500);
		font.draw(batch, pokemon.getPs() + "/" + pokemon.getPsMax(), 190, 440);
		font.draw(batch, "Nv� " + pokemon.getNivel(), 60, 440);
		batch.draw(spPokemon.get(0), 60, 460, 60, 60);
	}

	private void drawPokemon(int i) {
		int x, y, xHp, yHp, xName, yName, xPs, yPs, xNv, yNv, xPok, yPok, xVida, yVida;
		if (i % 2 == 0) {
			x = 1;
			y = 268 - 135 * (int) (i / 4);
			xHp = 150;
			yHp = 313 - 135 * (int) (i / 4);
			xName = 160;
			yName = 360 - 135 * (int) (i / 4);
			xPs = 190;
			yPs = 300 - 135 * (int) (i / 4);
			xNv = 60;
			yNv = 300 - 135 * (int) (i / 4);
			xPok = 60;
			yPok = 320 - 135 * (int) (i / 4);
			xVida = 180;
			yVida = 316 - 135 * (int) (i / 4);
		} else {
			x = 360;
			y = 380 - 135 * (int) (i / 2);
			xHp = 500;
			yHp = 425 - 135 * (int) (i / 2);
			xName = 510;
			yName = 475 - 135 * (int) (i / 2);
			xPs = 540;
			yPs = 415 - 135 * (int) (i / 2);
			xNv = 410;
			yNv = 415 - 135 * (int) (i / 2);
			xPok = 420;
			yPok = 440 - 135 * (int) (i / 2);
			xVida = 530;
			yVida = 428 - 135 * (int) (i / 2);
		}
		Pokemon pokemon = listaPokemon.get(i);
		if (pokemon.vivo()) {
			if (selection != i) {
				batch.draw(tVivo, x, y, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / (float) 4);
			} else {
				batch.draw(tVivoSel, x, y, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / (float) 4);
			}
			batch.draw(tHpVivo, xHp, yHp);
			font.draw(batch, pokemon.getNombre().toUpperCase(), xName, yName);
			font.draw(batch, pokemon.getPs() + "/" + pokemon.getPsMax(), xPs, yPs);
			font.draw(batch, "Nv� " + pokemon.getNivel(), xNv, yNv);
			batch.draw(spPokemon.get(i), xPok, yPok, 60, 60);
		} else {
			if (selection != i) {
				batch.draw(tMuerto, x, y, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / (float) 4);
			} else {
				batch.draw(tMuertoSel, x, y, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / (float) 4);
			}

			batch.draw(tHpMuerto, xHp, yHp);
			font.draw(batch, pokemon.getNombre().toUpperCase(), xName, yName);
			font.draw(batch, pokemon.getPs() + "/" + pokemon.getPsMax(), xPs, yPs);
			font.draw(batch, "Nv� " + pokemon.getNivel(), xNv, yNv);
			batch.draw(spPokemon.get(i), xPok, yPok, 60, 60);
		}
		batch.draw(barrasVida[0], xVida, yVida, (int) (100 * (pokemon.getPs() / (double) pokemon.getPsMax())), 9);

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
		dispose();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (!dialogo.isWriting()) {
			if (!opcion || pokemonCambiar) {
				if (keycode == getCtx().getTeclaB()) {
					/* Vuelve al menu */
					Jugador aux = Jugador.nuevoJugador(getCtx().jugador);
					((Game) Gdx.app.getApplicationListener()).setScreen(screen);
					screen.getCtx().jugador = aux;
				} else if (keycode == getCtx().getTeclaA()) {
					action(selection);
				} else if (keycode == getCtx().getTeclaDown()) {
					if (selection != 4 && selection != 5 && !(listaPokemon.size() - selection < 3)) {
						selection = selection + 2;
					}
				} else if (keycode == getCtx().getTeclaUp()) {
					if (selection != 0 && selection != 1) {
						selection = selection - 2;
					}
				} else if (keycode == getCtx().getTeclaRight()) {
					if (selection != 1 && selection != 3 && selection != 5 && !(listaPokemon.size() - selection < 2)) {
						selection = selection + 1;
					}
				} else if (keycode == getCtx().getTeclaLeft()) {
					if (selection != 0 && selection != 2 && selection != 4) {
						selection = selection - 1;
					}
				}
			} else {
				if (keycode == getCtx().getTeclaDown()) {
					if (cam_sol)
						cam_sol = false;
					if (soltarSiNo)
						soltarSiNo = false;
				} else if (keycode == getCtx().getTeclaUp()) {
					if (!cam_sol)
						cam_sol = true;
					if (!soltarSiNo)
						soltarSiNo = true;
				} else if (keycode == getCtx().getTeclaA()) {
					action(selection);
				} else if (opcion == true) {
					opcion = false;
					dialogo.limpiar();
				}
			}
		}
		return false;
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

	private void action(int i) {

		if (combate) {
			if (listaPokemon.get(i).vivo()) {
				Enfrentamiento e = (Enfrentamiento) screen;
				if (i != e.iPokemon) {
					if (e.fase == 9) {
						e.fase = 1;
						e.dialogo.procesarDialogo("adelante");
					} else if (e.fase == 3) {
						e.fase = 6;
						e.veces = 0;
						e.cambio = false;
					}
					e.setIPokemon(i);
					((Game) Gdx.app.getApplicationListener()).setScreen(e);
				}
			}
		} else if (pokemonCambiar) {
			// Intercambiar pokemon
			Pokemon aux = listaPokemon.get(selection);
			listaPokemon.set(selection, listaPokemon.get(iAccion));
			listaPokemon.set(iAccion, aux);
			Sprite auxS = spPokemon.get(selection);
			spPokemon.set(selection, spPokemon.get(iAccion));
			spPokemon.set(iAccion, auxS);
			pokemonCambiar = false;
			opcion = false;
			dialogo.limpiar();
			iAccion = -1;
		} else if (pokemonSoltar) {
			if (soltarSiNo) {
				soltarPokemon();
				pokemonSoltar = false;
				opcion = false;
				iAccion = -1;
				dialogo.limpiar();
			} else {
				pokemonSoltar = false;
				opcion = false;
				iAccion = -1;
				dialogo.limpiar();
			}
		} else if (opcion) {
			if (cam_sol) {
				pokemonCambiar = true;
				iAccion = selection;
				String[] frases = { "Elige el pokemon a cambiar", "" };
				dialogo.setFrases(frases);
				String l1 = dialogo.siguienteLinea();
				String l2 = dialogo.siguienteLinea();
				dialogo.setLineas(l1, l2);
			} else {
				pokemonSoltar = true;
				iAccion = selection;
				String[] frases = { "¿Estás seguro de soltar este pokemon?", "" };
				dialogo.setFrases(frases);
				String l1 = dialogo.siguienteLinea();
				String l2 = dialogo.siguienteLinea();
				dialogo.setLineas(l1, l2);

			}
			dialogo.limpiar();
		} else {
			opcion = true;

			String[] frases = { "¿Qué deseas hacer con este pokemon?", "" };
			dialogo.setFrases(frases);
			String l1 = dialogo.siguienteLinea();
			String l2 = dialogo.siguienteLinea();
			dialogo.setLineas(l1, l2);
		}
	}

	public void getBarraVida() {
		barrasVida = new TextureRegion[3];
		barrasVida[0] = new TextureRegion(barraVida, 0, 0, 180, 18);
		barrasVida[1] = new TextureRegion(barraVida, 0, 20, 180, 18);
		barrasVida[2] = new TextureRegion(barraVida, 0, 36, 180, 18);
	}

	private void dialogoOpcion() {
		if (opcion) {
			cajaDialogo.draw(batch);
			cajaDialogo.setSize(720, 120);
			if (!pokemonSoltar) {
				int yAp = 155;
				if (!cam_sol)
					yAp = 120;
				cajaUsar.setSize(100, 90);
				cajaUsar.setPosition(500, 120);
				cajaUsar.draw(batch);
				dedo.setPosition(610, yAp);
				dedo.setSize(50, 50);
				dedo.draw(batch);
				fontCambiar.draw(batch, "Cambiar", 520, 190);
				fontCambiar.draw(batch, "Soltar", 520, 150);
			}
		}
		fontCambiar.draw(batch, dialogo.getLinea1(), 50, 85);
		fontCambiar.draw(batch, dialogo.getLinea2(), 50, 45);
	}

	private void dialogoSoltar() {
		if (opcion) {
			cajaDialogo.draw(batch);
			cajaDialogo.setSize(720, 120);
			if (pokemonSoltar) {
				int yAp = 155;
				if (!soltarSiNo)
					yAp = 120;
				cajaUsar.setSize(100, 90);
				cajaUsar.setPosition(500, 120);
				cajaUsar.draw(batch);
				dedo.setPosition(610, yAp);
				dedo.setSize(50, 50);
				dedo.draw(batch);
				fontCambiar.draw(batch, "Si", 530, 190);
				fontCambiar.draw(batch, "No", 530, 150);
			}
		}
		fontCambiar.draw(batch, dialogo.getLinea1(), 50, 85);
		fontCambiar.draw(batch, dialogo.getLinea2(), 50, 45);
	}

	public void soltarPokemon() {
		listaPokemon.remove(selection);
		spPokemon.remove(selection);
	}

}
