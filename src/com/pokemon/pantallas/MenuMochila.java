package com.pokemon.pantallas;

import pokemon.Pokemon;

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
import com.pokemon.dialogo.Dialogo;
import com.pokemon.mochila.Ball;
import com.pokemon.mochila.Mochila;
import com.pokemon.utilidades.ArchivoGuardado;

import entrenadores.Jugador;

public class MenuMochila extends Pantalla {

	private static final int MAX_ITEMS = 6; /*
											 * Items que caben a la vez en
											 * pantalla
											 */
	private static final int NUM_SECCIONES = 3; /* Secciones de la mochila */

	private Pantalla screen;
	private int first; /* Indice del primer elemento de la lista en mostrarse */
	private int pointer; /*
							 * Indice del seleccionado de los MAX_ITEMS que caben
							 */
	private int seccion;
	private boolean usar = false, si_no = true;
	private Dialogo dialogo;
	private Enfrentamiento e;

	private SpriteBatch batch;
	private Sprite dedo, cajaUsar, cajaDialogo;
	private Texture tFondoObj, tFondoBalls, tFondoMOs, tSelected;
	BitmapFont font = new BitmapFont(Gdx.files.internal("res/fuentes/pokemon.fnt"),
			Gdx.files.internal("res/fuentes/pokemon.png"), false);

	public MenuMochila(ArchivoGuardado ctx, Pantalla screen) {
		this.setCtx(ctx);
		this.screen = screen;
		if (screen instanceof Enfrentamiento) {
			e = (Enfrentamiento) screen;
		}
		dialogo = new Dialogo("es", "ES");
		/* Empieza mostrando primera seccion (objeto) marcando primer objeto */
		seccion = 0;
		first = 0;
		pointer = 0;

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		tFondoObj = new Texture("res/imgs/mochila/bag_objetos.png");
		tFondoBalls = new Texture("res/imgs/mochila/bag_pokeball.png");
		tFondoMOs = new Texture("res/imgs/mochila/bag_mo.png");
		tSelected = new Texture("res/imgs/mochila/bag_selected.png");
		cajaUsar = new Sprite(new Texture("res/imgs/batallas/cajaAprender.png"));
		dedo = new Sprite(new Texture("res/imgs/batallas/aprender.png"));
		cajaDialogo = new Sprite(new Texture("res/imgs/batallas/battleMessage.png"));

		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		/* La imagen de fondo ocupa toda la pantalla */
		if (seccion == 0) {
			batch.draw(tFondoObj, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			font.draw(batch, "Bolsillo de Objetos", 150, 100);
		} else if (seccion == 1) {
			batch.draw(tFondoBalls, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			font.draw(batch, "Bolsillo de Pokeballs", 150, 100);
		} else if (seccion == 2) {
			batch.draw(tFondoMOs, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			font.draw(batch, "Bolsillo de MO", 150, 100);
		}
		font.draw(batch, "Pulsa IZDA o DCHA para cambiar de bolsillo", 150, 50);

		drawItems();
		drawSelection();
		dialogoUsar();
		batch.end();
	}

	private void dialogoUsar() {
		if (usar) {
			cajaDialogo.draw(batch);
			cajaDialogo.setSize(720, 120);
			int yAp = 155;
			if (!si_no)
				yAp = 120;
			cajaUsar.setSize(100, 90);
			cajaUsar.setPosition(500, 120);
			cajaUsar.draw(batch);
			dedo.setPosition(610, yAp);
			dedo.setSize(50, 50);
			dedo.draw(batch);
			font.draw(batch, "Si", 530, 190);
			font.draw(batch, "No", 530, 150);
		}
		font.draw(batch, dialogo.getLinea1(), 50, 85);
		font.draw(batch, dialogo.getLinea2(), 50, 45);
	}

	private void drawSelection() {
		if (getCtx().mochila.size(seccion) > 0) {
			batch.draw(tSelected, 280, Gdx.graphics.getHeight() - (80 + 50 * pointer));
		} else {
			font.draw(batch, "No hay items", 300, Gdx.graphics.getHeight() - 200);
		}
	}

	/**
	 * Renderiza hasta 5 elementos de la mochila, empezando por aquel que se
	 * esta recorriendo actualmente.
	 */
	private void drawItems() {
		/* MaxItems es el maximo de objetos a renderizar */
		int maxItems = (getCtx().mochila.size(seccion) < MAX_ITEMS) ? getCtx().mochila.size(seccion) : MAX_ITEMS;
		font.setColor(Color.BLACK);

		/* Dibuja el texto de cada objeto que quepa */
		int pos = 50;
		for (int i = first; i < first + maxItems; i++) {
			font.draw(batch, getCtx().mochila.get(seccion, i).getNombre(), 300, Gdx.graphics.getHeight() - pos);
			pos += 50;
		}
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
			if (keycode == getCtx().getTeclaB()) {
				/* Vuelve al menu */
				screen.setCtx(this.getCtx());
				Jugador aux = Jugador.nuevoJugador(getCtx().jugador);
				((Game) Gdx.app.getApplicationListener()).setScreen(screen);
				screen.getCtx().jugador = aux;
			} else if (keycode == getCtx().getTeclaA()) {
				if (!usar) {
					usar = true;
					dialogo.procesarDialogo("objeto_mochila");
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();
					l1 = l1.replace("${OBJETO}", getCtx().mochila.get(seccion, pointer).getNombre());
					dialogo.setLineas(l1, l2);

				} else {

					String l1 = dialogo.siguienteLinea(); 
					String l2 = dialogo.siguienteLinea();

					if (si_no) {
						boolean ok = true;
						if (screen instanceof CombateP && getCtx().mochila.get(seccion, pointer) instanceof Ball) {
							CombateP p = (CombateP) screen;
							if (p.jugador.getEquipo().size() == 6) {
								ok = false;
								String[] frase = {
										"¡Ya tienes 6 Pokemon, no puedes llevar más!", "" };
								dialogo.setFrases(frase);
								dialogo.setLineas(dialogo.siguienteLinea(), dialogo.siguienteLinea());
							
							}
						}
						if (l1 != null && ok) {
							l1 = l1.replace("${OBJETO}", getCtx().mochila.get(seccion, pointer).getNombre());
							dialogo.setLineas(l1, l2);
							Pokemon pokemon=null;
							if(screen instanceof Enfrentamiento){
								pokemon = e.jugador.getPokemon(e.iPokemon);
							}
							else{
								pokemon = getCtx().jugador.getPokemon(0);
							}
							getCtx().mochila.get(seccion, pointer).use(pokemon);

						} else if (ok) {
							if (getCtx().mochila.get(seccion, pointer) instanceof Ball) {
								if (screen instanceof CombateP) {
									CombateP p = (CombateP) screen;
									if (p.jugador.getEquipo().size() < 6) {
										p.fase = 20;
										p.setBall(getCtx().mochila.get(seccion, pointer).getNombre().toLowerCase());
										((Game) Gdx.app.getApplicationListener()).setScreen(p);
									}
								}
							} else if (screen instanceof Enfrentamiento) {
								Enfrentamiento e = (Enfrentamiento) screen;
								e.fase = 6;
								e.veces = 0;
								e.cambio = false;
								((Game) Gdx.app.getApplicationListener()).setScreen(e);
							}
							usar = false;
							dialogo.limpiar();
						}
					} else {
						dialogo.limpiar();
						usar = false;
					}

				}
			} else if (keycode == getCtx().getTeclaDown()) {
				if (usar) {
					if (si_no)
						si_no = false;
				} else {
					/* Desciende en la lista de la mochila */
					if (pointer < MAX_ITEMS - 1 && pointer < getCtx().mochila.size(seccion) - 1) {
						/* Baja el puntero si quedan posiciones por bajar */
						pointer++;
					} else if ((first + MAX_ITEMS - 1) < getCtx().mochila.size(seccion) - 1) {
						/* Mueve la lista abajo si el puntero esta ya abajo */
						first++;
					}
				}
			} else if (keycode == getCtx().getTeclaUp()) {
				if (usar) {
					if (!si_no)
						si_no = true;
				} else {
					/* Asciende en la lista de la mochila */
					if (pointer > 0) {
						/* Si el puntero no esta arriba, se sube */
						pointer--;
					} else if (first > 0) {
						/* Si el puntero ya esta arriba, se sube la lista */
						first--;
					}
				}
			} else if (keycode == getCtx().getTeclaRight()) {
				if (!usar) {
					/* Pasa a la siguiente lista de la mochila */
					seccion = (seccion + 1) % NUM_SECCIONES;
					first = 0;
					pointer = 0;
				}
			} else if (keycode == getCtx().getTeclaLeft()) {
				if (!usar) {
					/* Pasa a la anterior lista de la mochila */
					seccion = (seccion - 1) % NUM_SECCIONES;
					if (seccion < 0) {
						seccion += NUM_SECCIONES;
					}
					first = 0;
					pointer = 0;
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

}
