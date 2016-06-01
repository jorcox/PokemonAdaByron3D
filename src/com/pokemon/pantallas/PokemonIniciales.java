package com.pokemon.pantallas;

import java.io.File;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
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
	Model[] modelPokemon = new Model[3];
	ModelInstance[] instancePokemon = new ModelInstance[3];
	ModelBatch modelBatch;
	protected Environment environment;
	protected CameraInputController camController;
	protected PerspectiveCamera cam;
	protected InputMultiplexer inputMultiplexer;
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
		create3D();
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(this);
		inputMultiplexer.addProcessor(camController);
		Gdx.input.setInputProcessor(inputMultiplexer);
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
		camController.update();
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
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
		if (fase != 1) {
			render3D();
		}
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
				/*
				 * pokemon[i].draw(batch); pokemon[i].setPosition(x - 40, y +
				 * 130); pokemon[i].setSize(150, 150);
				 */
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
						int indice = 1;
						if (seleccion == 0) {
							indice = 1;
						} else if (seleccion == 1) {
							indice = 4;
						} else if (seleccion == 2) {
							indice = 7;
						}
						elegirPokemon(indice);

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
			Pokemon poke = bd.getPokemonTipo(i);
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
			for (int j = 0; j < 5; j++) {
				poke.subirNivel(0, 0);
			}
			poke.sanar();
			getCtx().jugador.getEquipo().add(poke);
			Jugador aux = Jugador.nuevoJugador(getCtx().jugador);
			Pantalla pantalla = new Play(getCtx(), 200, 300, 3, "Tranvia_n.png");
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

	private void create3D() {
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(30f, 20f, 50f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		obtainModel(0);
		obtainModel(1);
		obtainModel(2);

		camController = new CameraInputController(cam);
	}

	private void obtainModel(int i) {
		String nombre = "";
		if (i == 0) {
			nombre = "Bulbasaur";
		} else if (i == 1) {
			nombre = "Charmander";
		} else if (i == 2) {
			nombre = "Squirtle";
		}
		modelBatch = new ModelBatch();
		try {
			ObjLoader loader = new ObjLoader();
			String sDir = "res/Models/" + nombre;
			File dir = new File(sDir);
			File[] files = dir.listFiles();
			String modelFile = "";
			for (int j = 0; j < files.length && modelFile.equals(""); j++) {
				if (files[j].getName().endsWith("obj")) {
					modelFile = sDir + "/" + files[j].getName();
					System.out.println(modelFile);
				}
			}
			modelPokemon[i] = loader.loadModel(Gdx.files.internal(modelFile), true);
			instancePokemon[i] = new ModelInstance(modelPokemon[i]);
		} catch (Exception e) {
			/* Si peta al intentar crear el modelo, pone un cubo verde */
			modelPokemon[i] = new ModelBuilder().createBox(5f, 5f, 5f,
					new Material(ColorAttribute.createDiffuse(Color.GREEN)), Usage.Position | Usage.Normal);
			instancePokemon[i] = new ModelInstance(modelPokemon[i]);
		}
	}

	private void render3D() {
		
		Matrix4 tr = null;
		if (seleccion == 0) {
			tr = new Matrix4();
			tr.setToTranslation(10, 17, 35);
			Matrix4 rt = new Matrix4();
			rt.setToRotation(Vector3.Y, 60);
			tr = tr.mul(rt);
		} else if (seleccion == 1) {
			tr = new Matrix4();
			tr.setToTranslation(22, 17, 35);
			Matrix4 rt = new Matrix4();
			rt.setToRotation(Vector3.Y, 30);
			tr = tr.mul(rt);
		} else {
			tr = new Matrix4();
			tr.setToTranslation(29, 17, 35);
			Matrix4 rt = new Matrix4();
			rt.setToRotation(Vector3.Y, 15);
			tr = tr.mul(rt);
		}
		modelBatch.begin(cam);
		instancePokemon[seleccion].transform = tr;
		modelBatch.render(instancePokemon[seleccion], environment);
		modelBatch.end();
	}

}
