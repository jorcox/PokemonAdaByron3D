package com.pokemon.pantallas;

import habilidad.Habilidad;
import pokemon.Pokemon;
import aurelienribon.tweenengine.Tween;
import core.Combate;

import java.io.File;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.pokemon.entities.Player;
import com.pokemon.mochila.Ball;
import com.pokemon.tween.SpriteAccessor;
import com.pokemon.utilidades.ArchivoGuardado;

import entrenadores.Jugador;

public class CombateP extends Enfrentamiento {

	private boolean show = true;
	private boolean atrapado = false;
	private int xBall = 120, yBall = 120, animacionBall = 60;
	private Sprite ball;
	
	private Model model;
	private ModelInstance instance;

	public CombateP(ArchivoGuardado ctx, Player player, int fase, Pantalla pantalla, Pokemon pkmnpokemonEnemigo) {
		super(ctx, player, pantalla);
		actualPsS = pkmnpokemonEnemigo.getPs();
		combate = new Combate(jugador, pkmnpokemonEnemigo);
		orden = combate.getVelocidad(iPokemon);
		this.fase = fase;
		dialogo.procesarDialogo("salvaje");
		pkmn = jugador.getEquipo().get(iPokemon);
		this.pkmnpokemonEnemigo = pkmnpokemonEnemigo;
		
		
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
        
        obtainModel();
        
        camController = new CameraInputController(cam);
	}
	
	private void obtainModel() {
		modelBatch = new ModelBatch();
		try {
			ObjLoader loader = new ObjLoader();
        	String sDir = "res/Models/" + pkmnpokemonEnemigo.getNombre();
        	File dir = new File(sDir);
        	File[] files = dir.listFiles();
        	String modelFile = "";
        	for (int j=0; j<files.length && modelFile.equals(""); j++) {
        		if (files[j].getName().endsWith("obj")) {
        			modelFile = sDir + "/" + files[j].getName();
        			System.out.println(modelFile);
        		}
        	}
        	model = loader.loadModel(Gdx.files.internal(modelFile), true);
            instance = new ModelInstance(model);
        } catch(Exception e) {
        	/* Si peta al intentar crear el modelo, pone un cubo verde */
    		model = new ModelBuilder().createBox(5f, 5f, 5f, 
    	            new Material(ColorAttribute.createDiffuse(Color.GREEN)),
    	            Usage.Position | Usage.Normal);
    		instance = new ModelInstance(model);
        }
	}

	@Override
	public void render(float delta) {
		camController.update();
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		render3D();
		
		font.setColor(Color.BLACK);
		tweenManager.update(delta);
		batch.begin();
		base.draw(batch);
		baseEnemy.draw(batch);
		message.draw(batch);
		message.setSize(720, 120);
		font.draw(batch, dialogo.getLinea1(), 50, 85);
		font.draw(batch, dialogo.getLinea2(), 50, 45);
		base.setPosition(-70, 120);
		baseEnemy.setPosition(350, 300);
		/*
		 * Aparacion de pokemon pokemonEnemigo
		 */
		if (fase > 2) {
			baseEnemy.setPosition(350, 300);
			base.setPosition(-70, 120);

		}
		/*
		 * Decidir accion (Luchar, Mochila, Pokemon, Huir)
		 */
		if (fase == 3) {
			dibujarMenuCombate();
			dibujarCajasVida();
			dibujarVidas();
			dibujarExp();
		}
		/*
		 * Decisi�n de ataque
		 */
		if (fase == 4) {
			cajaLuchar.setSize(720, 120);
			cajaLuchar.draw(batch);
			dibujarCajasVida();
			dibujarVidas();
			updateSeleccionAtaque();
			dibujarExp();
			vida = 100;
			vidaS = 100;
		}
		if (fase == 5 || fase == 7) {
			/*
			 * Dialogo Ataque
			 */
			dibujarCajasVida();
			dibujarVidas();
			dibujarExp();
		}
		if (fase == 6 || fase == 8) {
			/*
			 * Ataque, vida y comprobaci�n
			 */
			dibujarCajasVida();
			dibujarExp();
			dibujarVidas();
			if ((orden && fase == 6) || (!orden && fase == 8)) {
				if (acierto != -1 && acierto != 1)
					ataqueRecibido(true);
				animacionVida(true);
				dibujarVida(true);
			} else {
				if (acierto != -1 && acierto != 1)
					ataqueRecibido(false);
				animacionVida(false);
				dibujarVida(false);
			}
		}
		if (fase == 9) {
			/*
			 * Dialogo muerte o fase = 3
			 */
			dibujarCajasVida();
			dibujarExp();
			dibujarVidas();
			if (pkmnpokemonEnemigo.getPs() <= 0) {
			} else if (pkmn.getPs() <= 0) {
			}
		}
		if (fase == 10 || fase == 11) {
			dibujarCajasVida();
			dibujarVidas();
			dibujarExp();
		}
		if (fase == 12) {
			dibujarCajasVida();
			dibujarVidas();
			dibujarExp();
		}
		if (fase == 13) {
			subirNivel();
		}

		if (fase == 15) {
			if (!aprender_cuatro) {
				int yAp = 155;
				if (!seleccionAprender)
					yAp = 120;
				cajaAprender.setSize(100, 90);
				cajaAprender.setPosition(500, 120);
				cajaAprender.draw(batch);
				aprender.setPosition(610, yAp);
				aprender.setSize(50, 50);
				aprender.draw(batch);
				font.draw(batch, "Si", 530, 190);
				font.draw(batch, "No", 530, 150);
			}
		}
		if (fase == 16) {
			if (olvidar) {
				updateSeleccionAtaque();
			}
		}
		if (fase == 20 || fase == 21) {
			lanzamientoBall();
		}
		batch.end();
		render3DPokemon();
	}
	
	private void render3D() {
		modelBatch.begin(cam);
        modelBatch.render(instance, environment);
        modelBatch.end();
	}

	@Override
	public void show() {
		create3D();
		super.show();
		
		if (show) {
			show = false;
			Tween.set(base, SpriteAccessor.SLIDE).target(500, 120).start(tweenManager);
			Tween.to(base, SpriteAccessor.SLIDE, 2).target(-70, 120).start(tweenManager);
			Tween.set(baseEnemy, SpriteAccessor.SLIDE).target(-250, 300).start(tweenManager);
			Tween.to(baseEnemy, SpriteAccessor.SLIDE, 2).target(350, 300).start(tweenManager);
		}
	} 

	@Override
	public boolean keyDown(int keycode) {
		if (!dialogo.isWriting()) {
			if (keycode == getCtx().getTeclaA()) {

				if (fase == 1 || fase == 2) {
					/*
					 * Dialogo de comienzo del combate
					 */
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();

					if (l1 != null) {
						if (l2 == null) {
							l2 = "";
						}
						if (dialogo.getId().equals("salvaje") || dialogo.getId().equals("adelante")) {
							if (l1.contains("${SALVAJE}")) {
								l1 = l1.replace("${SALVAJE}", pkmnpokemonEnemigo.getNombre());
							}
							if (l1.contains("${POKEMON}")) {
								l1 = l1.replace("${POKEMON}", pkmn.getNombre());
								fase++;
								dialogo.procesarDialogo("combate");
							}
						} else if (dialogo.getId().equals("combate")) {
							if (l1.contains("${POKEMON}")) {
								l1 = l1.replace("${POKEMON}", pkmn.getNombre());
							}
							if (l1.equals(" ")) {
								fase++;
							}
						}
						/* Escribe letra a letra el dialogo */
						dialogo.setLineas(l1, l2);
					}
				} else if (fase == 3) {
					elegirOpcion(false);
					seleccionEnemigo = combate.decidir(pkmnpokemonEnemigo);
				} else if (fase == 4) {
					/*
					 * Primer ataque
					 */
					fraseAtaque();
				} else if (fase == 5) {
					combate();
				} else if (fase == 6) {
					jugador.getEquipo().set(iPokemon, pkmn);
					veces = 8;
					if (pkmn.getPs() <= 0 || pkmnpokemonEnemigo.getPs() <= 0) {
						fase = 9;
						dialogo.procesarDialogo("pokemon_muerto");
					} else {
						fraseAtaque();
					}
				} else if (fase == 7) {
					combate();
					cambio = true;
				} else if (fase == 8) {
					jugador.getEquipo().set(iPokemon, pkmn);
					veces = 8;
					if (pkmn.getPs() <= 0 || pkmnpokemonEnemigo.getPs() <= 0) {
						fase = 9;
						dialogo.procesarDialogo("pokemon_muerto");
					} else {
						fase = 3;
						seleccionAtaque = 1;
						dialogo.limpiar();
					}
				} else if (fase == 9) {
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();

					if (l1 == null) {
						if (!jugador.vivo()) {
							fase = 12;
							dialogo.procesarDialogo("combate_perdido");
						} else if (pkmn.getExperiencia() > experienceToLevel(pkmn.getNivel() + 1)) {
							/*
							 * Subir nivel
							 */
							dialogo.procesarDialogo("subir_nivel");
							fase = 13;
						} else if (!pkmnpokemonEnemigo.vivo()) {
							fase = 12;
							dialogo.procesarDialogo("combate_ganado");
						} else {
							((Game) Gdx.app.getApplicationListener())
									.setScreen(new MenuPokemon(getCtx(), jugador.getEquipo(), this, true));
						}
					} else {
						if (l1.contains("debilitado")) {
							if (pkmn.getPs() <= 0) {
								l1 = l1.replace("${POKEMON}", pkmn.getNombre());
							} else {
								l1 = l1.replace("${POKEMON}", pkmnpokemonEnemigo.getNombre());
							}
						} else if (l1.contains("${EXP}")) {
							if (pkmn.vivo()) {
								l1 = l1.replace("${EXP}", gainExperience(false, pkmnpokemonEnemigo.getNivel()) + "");
								l1 = l1.replace("${POKEMON}", pkmn.getNombre());
								updateExperience(false);
							}
						}
						if (pkmnpokemonEnemigo.vivo() && l1.contains("puntos")) {

						} else {
							dialogo.setLineas(l1, l2);
						}
					}

				} else if (fase == 10) {
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();

					if (l1 == null) {
						if (orden) {
							fase = 6;
						} else {
							fase = 8;
						}
						pkmnpokemonEnemigo = pkmnAux;
					} else {
						dialogo.setLineas(l1, l2);
					}

				} else if (fase == 11) {
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();

					if (l1 == null) {
						if (!orden) {
							fase = 6;
						} else {
							fase = 8;
						}
						pkmn = pkmnAux;

					} else {
						dialogo.setLineas(l1, l2);
					}
				} else if (fase == 12) {
					String l1, l2;
					l1 = dialogo.siguienteLinea();
					l2 = dialogo.siguienteLinea();
					if (l1 != null) {
						/*
						 * Muere enemigo
						 */

						dialogo.setLineas(l1, l2);
					} else {
						if (dialogo.getId().equals("combate_ganado")) {
							Jugador aux = Jugador.nuevoJugador(jugador);
							((Game) Gdx.app.getApplicationListener()).setScreen(pantalla);
							pantalla.getCtx().jugador = aux;
						}else{
							combatePerdido();
						}
					}
				} else if (fase == 13) {
					/*
					 * Subir nivel
					 */
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();
					if (subir) {
						subir = false;
						hab = getHabilidadBD();
						if (hab != null) {
							fase = 14;
							dialogo.procesarDialogo("aprender_movimiento");
						} else if (!pkmnpokemonEnemigo.vivo()) {
							fase = 12;
							dialogo.procesarDialogo("combate_ganado");
						} else if (!jugador.vivo()) {
							fase = 12;
							dialogo.procesarDialogo("combate_perdido");
						}

					} else if (l1 == null) {
						pkmn.subirNivel(pkmn.getExperiencia(), experienceToLevel(pkmn.getNivel() + 1));
						subir = true;
					} else {
						l1 = l1.replace("${POKEMON}", pkmn.getNombre());
						l2 = l2.replace("${NIVEL}", "" + (pkmn.getNivel() + 1));
						dialogo.setLineas(l1, l2);
					}

				} else if (fase == 14) {
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();
					if (dialogo.getId().equals("aprender_movimiento")) {
						if (l1 != null) {
							l1 = l1.replace("${POKEMON}", pkmn.getNombre());
							l2 = l2.replace("${HABILIDAD}", hab.getNombre());
							dialogo.setLineas(l1, l2);
						} else if (pkmn.numHabilidades() < 4) {
							dialogo.procesarDialogo("aprendido");
						} else {
							fase = 15;
							dialogo.procesarDialogo("aprender_cuatro");
							l1 = dialogo.siguienteLinea();
							l2 = dialogo.siguienteLinea();
							l1 = l1.replace("${POKEMON}", pkmn.getNombre());
							l2 = l2.replace("${HABILIDAD}", hab.getNombre());
							dialogo.setLineas(l1, l2);
						}
					}
					if (dialogo.getId().equals("aprendido")) {
						l1 = dialogo.siguienteLinea();
						l2 = dialogo.siguienteLinea();
						if (l1 != null) {
							l1 = l1.replace("${POKEMON}", pkmn.getNombre());
							l1 = l1.replace("${MOVIMIENTO}", hab.getNombre());
							dialogo.setLineas(l1, l2);
						} else {
							fase = 12;
							dialogo.procesarDialogo("combate_ganado");
						}
					}
				} else if (fase == 15) {
					if (dialogo.getId().equals("aprender_cuatro") && aprender_cuatro) {
						String l1 = dialogo.siguienteLinea();
						String l2 = dialogo.siguienteLinea();
						if (l1 != null) {
							dialogo.setLineas(l1, l2);
						} else {
							aprender_cuatro = false;
						}
					} else {
						if (seleccionAprender) {
							dialogo.procesarDialogo("aprender_olvidar");
							fase = 16;
							dialogo.limpiar();
						} else {
							dialogo.procesarDialogo("no_aprender");
							String l1 = dialogo.siguienteLinea();
							String l2 = dialogo.siguienteLinea();
							l1 = l1.replace("${POKEMON}", pkmn.getNombre());
							l1 = l1.replace("${MOVIMIENTO}", hab.getNombre());
							dialogo.setLineas(l1, l2);
							if (!pkmnpokemonEnemigo.vivo()) {
								fase = 12;
								dialogo.procesarDialogo("combate_ganado");
							} else if (!jugador.vivo()) {
								fase = 12;
								dialogo.procesarDialogo("combate_perdido");
							}

						}
					}

				} else if (fase == 16) {
					if (olvidar) {
						vieja = habilidades[seleccionAtaque - 1];
						elegirOlvidar();
						olvidar = false;
					} else {
						String l1 = dialogo.siguienteLinea();
						String l2 = dialogo.siguienteLinea();
						l1 = l1.replace("${POKEMON}", pkmn.getNombre());
						l1 = l1.replace("${VIEJO}", "" + vieja.getNombre());
						l2 = l2.replace("${NUEVO}", hab.getNombre());
						dialogo.setLineas(l1, l2);
						if (!pkmnpokemonEnemigo.vivo()) {
							fase = 12;
							dialogo.procesarDialogo("combate_ganado");
						} else if (!jugador.vivo()) {
							fase = 12;
							dialogo.procesarDialogo("combate_perdido");
						}
					}
				} else if (fase == 20) {
					atrapado = Ball.atrapar(pkmnpokemonEnemigo);
					if (atrapado) {
						String[] frases = { "...", "...", "...", "...", "�Genial!", "�Has capturado a ${POKEMON}!" };
						dialogo.setFrases(frases);
					} else {
						String[] frases = { "...", "...", "...", "...", "�L�stima!", "�${POKEMON} se ha escapado!" };
						dialogo.setFrases(frases);

					}
					fase = 21;

				} else if (fase == 21) {
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();
					if (l2 != null) {
						l2 = l2.replace("${POKEMON}", pkmnpokemonEnemigo.getNombre());
					}
					dialogo.setLineas(l1, l2);
					if (l1 == null) {
						if (atrapado) {
							jugador.getEquipo().add(pkmnpokemonEnemigo);
							Jugador aux = Jugador.nuevoJugador(jugador);
							((Game) Gdx.app.getApplicationListener()).setScreen(pantalla);
							pantalla.getCtx().jugador = aux;
						} else {
							animacionBall = 60;
							xBall = 120;
							yBall = 120;
							fase = 6;
							veces = 0;
							cambio = false;
						}
					}
				}

			} else if (keycode == getCtx().getTeclaLeft()) {
				if (fase == 3) {
					if (seleccion != 1) {
						seleccion -= 1;
					}
				} else if (fase == 4 || fase == 16) {
					if (seleccionAtaque != 1 && seleccionAtaque != 3
							&& pkmn.getHabilidad(seleccionAtaque - 1) != null) {
						seleccionAtaque -= 1;
					}
				}

			} else if (keycode == getCtx().getTeclaRight()) {
				if (fase == 3) {
					if (seleccion != 4) {
						seleccion += 1;
					}
				} else if (fase == 4 || fase == 16) {
					if (seleccionAtaque != 2 && seleccionAtaque != 4
							&& pkmn.getHabilidad(seleccionAtaque + 1) != null) {
						seleccionAtaque += 1;
					}
				}
			} else if (keycode == getCtx().getTeclaUp()) {
				if (fase == 4 || fase == 16) {
					if (seleccionAtaque != 1 && seleccionAtaque != 2
							&& pkmn.getHabilidad(seleccionAtaque - 2) != null) {
						seleccionAtaque -= 2;
					}
				} else if (fase == 15) {
					if (!seleccionAprender) {
						seleccionAprender = true;
					}
				}
			} else if (keycode == getCtx().getTeclaDown()) {
				if (fase == 4 || fase == 16) {
					if (seleccionAtaque != 3 && seleccionAtaque != 4
							&& pkmn.getHabilidad(seleccionAtaque + 2) != null) {
						seleccionAtaque += 2;
					}
				} else if (fase == 15) {
					if (seleccionAprender) {
						seleccionAprender = false;
					}
				}
			} else if (keycode == Keys.ESCAPE) {
				if (fase == 4) {
					fase = 3;
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

	public void setBall(String nombre) {
		ball = new Sprite(new Texture("res/imgs/batallas/" + nombre + ".png"));
	}

	public void lanzamientoBall() {
		ball.setPosition(xBall, yBall);
		// 470,400
		ball.draw(batch);
		if (animacionBall > 0) {
			xBall = xBall + 6;
			yBall = yBall + 5;
			animacionBall--;
		}
	}
}
