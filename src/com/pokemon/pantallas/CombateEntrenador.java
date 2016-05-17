package com.pokemon.pantallas;

import java.io.File;
import java.util.ArrayList;

import pokemon.CreadorEquipo;
import pokemon.Pokemon;
import aurelienribon.tweenengine.Tween;
import core.Combate;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.pokemon.entities.Player;
import com.pokemon.tween.SpriteAccessor;
import com.pokemon.utilidades.ArchivoGuardado;

import entrenadores.Jugador;

public class CombateEntrenador extends Enfrentamiento {

	private String idEntrenador;
	private String nombre;
	private Jugador entrenadorE;
	int actual;

	TextureRegion[] spritesEntrenador;
	
	protected Model[] models;
	protected ModelInstance[] instances;
	
	public CombateEntrenador(ArchivoGuardado ctx, Player player, String idEntrenador, Pantalla pantalla) {
		super(ctx, player, pantalla);
		this.fase = 0;
		this.idEntrenador = idEntrenador;
		pkmn = jugador.getPokemon(iPokemon);
		setEntrenador();
		actualPsS = pkmnpokemonEnemigo.getPs();
		combate = new Combate(jugador, pkmnpokemonEnemigo);
		orden = combate.getVelocidad(iPokemon);
		dialogo.procesarDialogo("combate_entrenador");
		actual = 0;
		
		
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
        
        listModels();
        
        camController = new CameraInputController(cam);
	}
	
	private void listModels() {
		int len = entrenadorE.getEquipo().size();
		models = new Model[len];
		instances = new ModelInstance[len];
		modelBatch = new ModelBatch();
        ObjLoader loader = new ObjLoader();
        
        for (int i=0; i<len; i++) {
        	try {
        		String sDir = "res/Models/" + entrenadorE.getEquipo().get(i).getNombre();
            	File dir = new File(sDir);
            	File[] files = dir.listFiles();
            	String modelFile = "";
            	for (int j=0; j<files.length && modelFile.equals(""); j++) {
            		if (files[j].getName().endsWith("obj")) {
            			modelFile = sDir + "/" + files[j].getName();
            			System.out.println(modelFile);
            		}
            	}
            	models[i] = loader.loadModel(Gdx.files.internal(modelFile), true);
                instances[i] = new ModelInstance(models[i]);
        	} catch(Exception e) {
        		/* Si peta al intentar crear el modelo, pone un cubo verde */
        		models[i] = new ModelBuilder().createBox(5f, 5f, 5f, 
        	            new Material(ColorAttribute.createDiffuse(Color.GREEN)),
        	            Usage.Position | Usage.Normal);
        		instances[i] = new ModelInstance(models[i]);
        	}
        	
        }
        
	}

	@Override
	public void render(float delta) {
		camController.update();
		
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		//render3D();
		
		font.setColor(Color.BLACK);
		tweenManager.update(delta);
		batch.begin();
		base.draw(batch);

		baseEnemy.draw(batch);

		message.draw(batch);
		message.setSize(720, 120);

		font.draw(batch, dialogo.getLinea1(), 50, 85);
		font.draw(batch, dialogo.getLinea2(), 50, 45);
	
		if (fase == 0) {

			entrenador.draw(batch);
			protagonista.draw(batch);

		}
		/*
		 * Aparicion de pokemon enemigo
		 */
		if (fase == 1) {

			protagonista.setPosition(100, 120);
			base.setPosition(-70, 120);
			baseEnemy.setPosition(350, 300);
			protagonista.draw(batch);
			
			/*modelBatch.begin(cam);
			modelBatch.render(instances[iPokemonEnemigo], environment);
			modelBatch.end();*/
		}
		/*
		 * Aparicion de pokemon nuestro
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
			dibujarPokeballs();
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
			dibujarPokeballs();
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
			dibujarPokeballs();
			dibujarExp();
		}
		if (fase == 6 || fase == 8) {
			/*
			 * Ataque, vida y comprobaci�n
			 */
			dibujarCajasVida();
			dibujarVidas();
			dibujarPokeballs();
			dibujarExp();
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
			dibujarVidas();
			dibujarPokeballs();
			dibujarExp();
			if (pkmnpokemonEnemigo.getPs() <= 0) {
			} else if (pkmn.getPs() <= 0) {
			}
		}
		/*
		 * Mensajes
		 */
		if (fase == 10 || fase == 11) {
			dibujarCajasVida();
			dibujarVidas();
			dibujarPokeballs();
			dibujarExp();
		}
		if (fase == 12) {
			dibujarCajasVida();
			dibujarVidas();
			dibujarExp();
		}
		if (fase == 13) {
		}
		if (fase == 14) {
			subirNivel();
		}
		if (fase == 16) {
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
		if (fase == 17) {
			if (olvidar) {
				updateSeleccionAtaque();
			}
		}
		batch.end();
		if(fase>0){
			render3D();
		}
		if(fase>1){
			render3DPokemon();
		}
	}
	
	private void render3D() {
			modelBatch.begin(cam);
	        modelBatch.render(instances[iPokemonEnemigo], environment);
	        modelBatch.end();
	}

	@Override
	public void show() {
		super.show();
		create3D();
		entrenador = new Sprite(new Texture("res/imgs/entrenadores/" + idEntrenador + ".png"));
		protagonista = new Sprite(new Texture("res/imgs/entrenadores/prota.png"));
		protagonista.setSize(150, 240);
		if (fase < 1) {
			Tween.set(base, SpriteAccessor.SLIDE).target(500, 120).start(tweenManager);
			Tween.to(base, SpriteAccessor.SLIDE, 2).target(-70, 120).start(tweenManager);
			Tween.set(protagonista, SpriteAccessor.SLIDE).target(500, 120).start(tweenManager);
			Tween.to(protagonista, SpriteAccessor.SLIDE, 2).target(100, 120).start(tweenManager);
			Tween.set(baseEnemy, SpriteAccessor.SLIDE).target(-250, 300).start(tweenManager);
			Tween.to(baseEnemy, SpriteAccessor.SLIDE, 2).target(350, 300).start(tweenManager);
			Tween.set(entrenador, SpriteAccessor.SLIDE).target(-250, 350).start(tweenManager);
			Tween.to(entrenador, SpriteAccessor.SLIDE, 2).target(400, 350).start(tweenManager);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (!dialogo.isWriting()) {
			if (keycode == getCtx().getTeclaA()) {
				if (fase == 0 || fase == 1 || fase == 2) {
					/*
					 * Dialogo de comienzo del combate
					 */
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();

					if (l1 != null) {
						if (l2 == null) {
							l2 = "";
						}
						if (dialogo.getId().equals("combate_entrenador") || dialogo.getId().equals("adelante")) {
							if (l1.contains("${ENTRENADOR}")) {
								l1 = l1.replace("${ENTRENADOR}",  nombre.toUpperCase());
							}
							if (l2.contains("${ENTRENADOR}")) {
								l2 = l2.replace("${ENTRENADOR}", nombre.toUpperCase());
							}
							if (l1.contains("${POKEMONE}")) {
								l1 = l1.replace("${POKEMONE}", pkmnpokemonEnemigo.getNombre());

								fase++;
								// dialogo.procesarDialogo("combate");
							}
							if (l1.contains("${POKEMON}")) {
								l1 = l1.replace("${POKEMON}", jugador.getEquipo().get(iPokemon).getNombre());
								tamanoPokemon = 1;
								fase++;
								dialogo.procesarDialogo("combate");
							}
							if (l1.equals(" ")) {
								fase++;
							}
						} else if (dialogo.getId().equals("combate")) {
							if (l1.contains("${POKEMON}")) {
								l1 = l1.replace("${POKEMON}", jugador.getEquipo().get(iPokemon).getNombre());

							} else {
								fase++;
							}
						}
						/* Escribe letra a letra el dialogo */
						dialogo.setLineas(l1, l2);
					}
				} else if (fase == 3) {
					elegirOpcion(true);
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
					entrenadorE.getEquipo().set(iPokemonEnemigo, pkmnpokemonEnemigo);
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
					entrenadorE.getEquipo().set(iPokemonEnemigo, pkmnpokemonEnemigo);
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
							fase = 14;
						} else if (!entrenadorE.vivo()) {
							fase = 12;
							dialogo.procesarDialogo("combate_ganado");
						} else if (!pkmnpokemonEnemigo.vivo()) {
							/*
							 * Enemigo saca siguiente pokemon
							 */
							nuevoPokemonEnemigo();

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
								l1 = l1.replace("${EXP}", gainExperience(true, pkmnpokemonEnemigo.getNivel()) + "");
								l1 = l1.replace("${POKEMON}", pkmn.getNombre());
								updateExperience(true);
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
						dialogo.limpiar();
						pkmnpokemonEnemigo = pkmnAux;

					} else {
						dialogo.setLineas(l1, l2);
					}

				} else if (fase == 11) {
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();

					if (l1 == null) {
						if (orden) {
							fase = 8;
						} else {
							fase = 6;

						}
						dialogo.limpiar();
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
					fase = 3;
					dialogo.limpiar();
				} else if (fase == 14) {
					/*
					 * Subir nivel
					 */
					String l1 = dialogo.siguienteLinea();
					String l2 = dialogo.siguienteLinea();
					if (subir) {
						subir = false;
						hab = getHabilidadBD();
						if (hab != null) {
							fase = 15;
							dialogo.procesarDialogo("aprender_movimiento");
						} else if (!entrenadorE.vivo()) {
							fase = 12;
							dialogo.procesarDialogo("combate_ganado");
						} else if (!jugador.vivo()) {
							fase = 12;
							dialogo.procesarDialogo("combate_perdido");
						} else if (!pkmnpokemonEnemigo.vivo()) {
							nuevoPokemonEnemigo();
						}

					} else if (l1 == null) {
						pkmn.subirNivel(pkmn.getExperiencia(), experienceToLevel(pkmn.getNivel() + 1));
						subir = true;
					} else {
						l1 = l1.replace("${POKEMON}", pkmn.getNombre());
						l2 = l2.replace("${NIVEL}", "" + (pkmn.getNivel() + 1));
						dialogo.setLineas(l1, l2);
					}
				} else if (fase == 15) {
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
							fase = 16;
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
							if (!jugador.vivo()) {
								fase = 12;
								dialogo.procesarDialogo("combate_perdido");
							} else if (!pkmnpokemonEnemigo.vivo()) {
								nuevoPokemonEnemigo();
							}
						}
					}
				} else if (fase == 16) {
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
							fase = 17;
							dialogo.limpiar();
						} else {
							dialogo.procesarDialogo("no_aprender");
							String l1 = dialogo.siguienteLinea();
							String l2 = dialogo.siguienteLinea();
							l1 = l1.replace("${POKEMON}", pkmn.getNombre());
							l1 = l1.replace("${MOVIMIENTO}", hab.getNombre());
							dialogo.setLineas(l1, l2);
							if (!entrenadorE.vivo()) {
								fase = 12;
								dialogo.procesarDialogo("combate_ganado");
							} else if (!jugador.vivo()) {
								fase = 12;
								dialogo.procesarDialogo("combate_perdido");
							} else if (!pkmnpokemonEnemigo.vivo()) {
								nuevoPokemonEnemigo();
							}

						}
					}

				} else if (fase == 17) {
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
						if (!entrenadorE.vivo()) {
							fase = 12;
							dialogo.procesarDialogo("combate_ganado");
						} else if (!jugador.vivo()) {
							fase = 12;
							dialogo.procesarDialogo("combate_perdido");
						} else if (!pkmnpokemonEnemigo.vivo()) {
							fase = 18;
						}
					}
				} else if (fase == 18) {
					nuevoPokemonEnemigo();
				}

			} else if (keycode == getCtx().getTeclaLeft()) {
				if (fase == 3) {
					if (seleccion != 1) {
						seleccion -= 1;
					}
				} else if (fase == 4 || fase == 17) {
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
				} else if (fase == 4 || fase == 17) {
					if (seleccionAtaque != 2 && seleccionAtaque != 4
							&& pkmn.getHabilidad(seleccionAtaque + 1) != null) {
						seleccionAtaque += 1;
					}
				}
			} else if (keycode == getCtx().getTeclaUp()) {
				if (fase == 4 || fase == 17) {
					if (seleccionAtaque != 1 && seleccionAtaque != 2
							&& pkmn.getHabilidad(seleccionAtaque - 2) != null) {
						seleccionAtaque -= 2;
					}
				}
			} else if (keycode == getCtx().getTeclaDown()) {
				if (fase == 4 || fase == 17) {
					if (seleccionAtaque != 3 && seleccionAtaque != 4
							&& pkmn.getHabilidad(seleccionAtaque + 2) != null) {
						seleccionAtaque += 2;
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

	public void setEntrenador() {
		entrenadorE = new Jugador(idEntrenador, true);
		CreadorEquipo creador = new CreadorEquipo();
		ArrayList<Pokemon> lPoke = creador.crear(idEntrenador);
		entrenadorE.setEquipo(lPoke);
		pkmnpokemonEnemigo = entrenadorE.getEquipo().get(iPokemonEnemigo);
		nombre = creador.nombrar(idEntrenador);
	}

	public void dibujarPokeballs() {
		int nPoke = jugador.getEquipo().size();
		int nPokeEnemigo = entrenadorE.getEquipo().size();
		int xP = 650;
		int xE = 180;
		Sprite[] balls = new Sprite[6];
		Sprite[] ballsEnemigo = new Sprite[6];
		for (int i = 0; i < 6; i++) {
			if (i < nPoke) {
				if (jugador.getEquipo().get(i).vivo()) {
					balls[i] = new Sprite(new Texture("res/imgs/batallas/ballnormal.png"));
				} else {
					balls[i] = new Sprite(new Texture("res/imgs/batallas/ballfainted.png"));
				}
			} else {
				balls[i] = new Sprite(new Texture("res/imgs/batallas/ballempty.png"));

			}
			balls[i].setPosition(xP, 130);
			balls[i].draw(batch);
			xP = xP - 30;
			if (i < nPokeEnemigo) {
				if (entrenadorE.getEquipo().get(i).vivo()) {
					ballsEnemigo[i] = new Sprite(new Texture("res/imgs/batallas/ballnormal.png"));
				} else {
					ballsEnemigo[i] = new Sprite(new Texture("res/imgs/batallas/ballfainted.png"));
				}
			} else {
				ballsEnemigo[i] = new Sprite(new Texture("res/imgs/batallas/ballempty.png"));

			}
			ballsEnemigo[i].setPosition(xE, 370);
			ballsEnemigo[i].draw(batch);
			xE = xE - 30;
		}
		/*
		 * pokeball = pokeballVacio = new Sprite(new Texture(
		 * "res/imgs/batallas/ballempty.png")); pokeballMuerto = new Sprite(new
		 * Texture( "res/imgs/batallas/ballfainted.png"));
		 */
	}

	public void nuevoPokemonEnemigo() {
		String l1, l2;
		iPokemonEnemigo++;
		tamanoPokemon = 1;
		pkmnpokemonEnemigo = entrenadorE.getPokemon(iPokemonEnemigo);
		String[] frase = { "� " + nombre.toUpperCase() + " utiliza a "
				+ entrenadorE.getPokemon(iPokemonEnemigo).getNombre() + "!" };
		fase = 13;
		orden = combate.getVelocidad(iPokemon);
		dialogo.setFrases(frase);
		l1 = dialogo.siguienteLinea();
		l2 = dialogo.siguienteLinea();
		dialogo.setLineas(l1, l2);
	}
}
