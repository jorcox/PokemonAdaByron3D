package com.pokemon.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.pokemon.dialogo.Dialogo;
import com.pokemon.entities.Player;
import com.pokemon.utilidades.ArchivoGuardado;

import db.BaseDatos;
import entrenadores.Jugador;
import habilidad.Habilidad;
import logica.Tipo;
import pokemon.Pokemon;

public class PokemonIniciales extends Pantalla {

	SpriteBatch batch;

	Sprite bg, ball, ballSel, message, cajaPokemon, cajaUsar, dedo;
	Sprite[] pokemon = new Sprite[3];
	protected Dialogo dialogo;
	private int fase = 1;
	private int seleccion = 0;
	private boolean elegir = false, si_no = false;
	FreeTypeFontGenerator generator;
	BitmapFont font, fontC;

	private boolean fin = false;

	public PokemonIniciales(ArchivoGuardado ctx) {
		this.setCtx(ctx);
		Gdx.input.setInputProcessor(this);
		generator = new FreeTypeFontGenerator(Gdx.files.internal("res/fuentes/PokemonFont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 35;
		font = generator.generateFont(parameter); // font size 35 pixels
		dialogo = new Dialogo("es", "ES");
		String[] frase = { "Debes escoger uno de estos tres pokemon para poder petarlo a saco en esta nueva aventura.",
				"" };
		dialogo.setFrases(frase);

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();
		bg = new Sprite(new Texture("res/imgs/iniciales/bg.png"));
		ball = new Sprite(new Texture("res/imgs/iniciales/ball.png"));
		ballSel = new Sprite(new Texture("res/imgs/iniciales/ballSel.png"));
		cajaPokemon = new Sprite(new Texture("res/imgs/OptionBox.png"));
		pokemon[0] = new Sprite(new Texture("res/imgs/pokemon/bulbasaur.png"));
		pokemon[1] = new Sprite(new Texture("res/imgs/pokemon/charmander.png"));
		pokemon[2] = new Sprite(new Texture("res/imgs/pokemon/squirtle.png"));
		message = new Sprite(new Texture("res/imgs/batallas/battleMessage.png"));
		cajaUsar = new Sprite(new Texture("res/imgs/batallas/cajaAprender.png"));
		dedo = new Sprite(new Texture("res/imgs/batallas/aprender.png"));
		font.setColor(Color.BLACK);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		bg.draw(batch);
		bg.setSize(720, 540);
		message.draw(batch);
		message.setSize(720, 120);
		font.draw(batch, dialogo.getLinea1(), 50, 85);
		font.draw(batch, dialogo.getLinea2(), 50, 45);
		if (fase == 1) {
			dibujarBalls();
		} else {
			dibujarBallsSel();
			if (elegir) {
				dialogoElegir();
			}
		}
		batch.end();

	}

	public void dibujarBalls() {
		int y = 200;
		int x = 150;
		for (int i = 0; i < pokemon.length; i++) {
			ball.draw(batch);
			ball.setPosition(x, y);
			ball.setSize(50, 50);
			x = x + 200;
		}
	}

	public void dibujarBallsSel() {
		int y = 200;
		int x = 150;
		for (int i = 0; i < pokemon.length; i++) {
			if (seleccion == i) {
				ballSel.draw(batch);
				ballSel.setPosition(x, y);
				ballSel.setSize(50, 50);
				cajaPokemon.draw(batch);
				cajaPokemon.setSize(200, 300);
				cajaPokemon.setPosition(x - 70, y + 50);
				pokemon[i].draw(batch);
				pokemon[i].setPosition(x - 40, y + 130);
				pokemon[i].setSize(150, 150);
			} else {
				ball.draw(batch);
				ball.setPosition(x, y);
				ball.setSize(50, 50);
			}

			x = x + 200;
		}
		if (fase == 2) {

		}
	}

	public void dialogoElegir() {
		if (elegir) {
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
	public boolean keyDown(int keycode) {
		if (!dialogo.isWriting()) {
			if (keycode == getCtx().getTeclaA()) {

				String l1 = dialogo.siguienteLinea();
				String l2 = dialogo.siguienteLinea();
				if (elegir) {
					if (fin) {
						elegirPokemon(seleccion);

					} else if (si_no) {
						String nombre = "";
						if (seleccion == 0) {
							nombre = "Bulbasaur";
						} else if (seleccion == 1) {
							nombre = "Charmander";
						} else if (seleccion == 2) {
							nombre = "Squirtle";

						}
						String[] frase = { "¡ENHORABUENA! Has elegido a ", nombre + "!!!!" };
						dialogo.setFrases(frase);
						dialogo.setLineas(dialogo.siguienteLinea(), dialogo.siguienteLinea());
						fin = true;

					} else {
						elegir = false;
						dialogo.limpiar();
					}
				} else {
					if (fase == 1) {
						if (l1 != null) {
							dialogo.setLineas(l1, l2);
						} else {
							fase = 2;
							dialogo.limpiar();
						}
					} else if (fase == 2) {
						elegir = true;
						if (seleccion == 0) {
							String[] frase = { "¿Estas seguro de coger al peor pokemon del mundo?", "" };
							dialogo.setFrases(frase);
						} else if (seleccion == 1) {
							String[] frase = { "¿Estas seguro de coger al mierdas este?", "" };
							dialogo.setFrases(frase);
						} else if (seleccion == 2) {
							String[] frase = { "¿Estas seguro de coger al pokemon con mas swag del mundo?", "" };
							dialogo.setFrases(frase);
						}

						dialogo.setLineas(dialogo.siguienteLinea(), dialogo.siguienteLinea());
					}
				}
			} else if (keycode == getCtx().getTeclaLeft()) {

				if (!elegir) {
					if (seleccion != 0) {
						seleccion--;
					}
				}
			} else if (keycode == getCtx().getTeclaRight()) {
				if (!elegir) {
					if (seleccion != 2) {
						seleccion++;
					}
				}
			} else if (keycode == getCtx().getTeclaUp()) {
				if (elegir) {
					if (!si_no)
						si_no = true;
				}
			} else if (keycode == getCtx().getTeclaDown()) {
				if (elegir) {
					if (si_no)
						si_no = false;
				}
			}

		}
		return false;
	}

	private void elegirPokemon(int i) {
		try {
			BaseDatos bd = new BaseDatos("pokemon_base");
			Pokemon poke = bd.getPokemonTipo(seleccion+1);
			Habilidad[] habs = new Habilidad[4];
			if (seleccion == 0) {
				poke.setTipo(Tipo.PLANTA);
				habs[0] = bd.getHabilidad(308);
			} else if (seleccion == 1) {
				poke.setTipo(Tipo.FUEGO);
				habs[0] = bd.getHabilidad(308);

			} else {
				poke.setTipo(Tipo.AGUA);
				habs[0] = bd.getHabilidad(300);
			}
			bd.shutdown();
			poke.setHabilidades(habs);
			for(int j=0;j<5;j++){
				poke.subirNivel(0, 0);
			}
			poke.sanar();
			getCtx().jugador.getEquipo().add(poke);
			Jugador aux = Jugador.nuevoJugador(getCtx().jugador);
			Pantalla pantalla = new Play(getCtx(), 200, 300, 3, "Tranvia_n.tmx");
			((Game) Gdx.app.getApplicationListener()).setScreen(pantalla);
			pantalla.getCtx().jugador = aux;
		} catch (Exception e) {
			e.printStackTrace();
		}

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
