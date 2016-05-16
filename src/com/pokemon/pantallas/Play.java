package com.pokemon.pantallas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.pokemon.entities.NPC;
import com.pokemon.entities.Player;
import com.pokemon.mochila.Antidoto;
import com.pokemon.mochila.MO;
import com.pokemon.mochila.Pocion;
import com.pokemon.render.TextureMapObjectRenderer;
import com.pokemon.utilidades.ArchivoGuardado;

import aurelienribon.tweenengine.TweenManager;
import db.BaseDatos;
import pokemon.Pokemon;

public class Play extends Pantalla {

	public transient TiledMap map;
	private transient TextureMapObjectRenderer renderer;
	private transient OrthographicCamera camera;
	private transient SpriteBatch batch;
	private transient BitmapFont font;
	private transient FreeTypeFontGenerator generator;
	private transient TweenManager tweenManager;

	private transient TextureAtlas playerAtlas;

	private transient Sprite box;
	public boolean optionsVisible = false;

	// private Player player = new Player(new Sprite(new
	// Texture("assets/maps/tilesInterior.png")));
	private Player player;
	
	private Stage stage;
	
	private ArrayList<NPC> npcs = new ArrayList<>();
	
	private ArrayList<ObjetoMapa> objetos = new ArrayList<>();
	
	private boolean dialogando;

	/* Para pausar el listener de teclas */
	private boolean listener = true;
	/* Para pausar el movimiento sin renunciar al INTRO */
	private boolean movimiento = true;
	/* Para saber si es la primera vez que se crea esta pantalla */
	private boolean primeraVez = true;
	/* String del mapa actual */
	private String mapa;

	public Play(ArchivoGuardado ctx) {
		this.setCtx(ctx);

		Gdx.input.setInputProcessor(this);

		setDialogando(false);

		cargarFuente();

		setJugador();
	}

	public Play(ArchivoGuardado ctx, float x, float y, int lastPressed, String mapa) {
		ctx.x = x;
		ctx.y = y;
		ctx.lastPressed = lastPressed;
		ctx.map = mapa;
		this.setCtx(ctx);

		this.mapa = mapa;

		Gdx.input.setInputProcessor(this);
		setDialogando(false);

		cargarFuente();

		setJugador();
	}

	@Override
	public void show() {
 		tweenManager = new TweenManager();

		TmxMapLoader loader = new TmxMapLoader();

		map = loader.load("res/mapas/" + mapa);

		renderer = new TextureMapObjectRenderer(map);

		camera = new OrthographicCamera();

		playerAtlas = new TextureAtlas("res/imgs/entrenadoresWorld/protagonista.pack");

		if (primeraVez) {
			/* Carga de NPCs */
			MapLayer npcLayer = map.getLayers().get("Personajes");
			for (MapObject o : npcLayer.getObjects()) {
				TextureMapObject t = (TextureMapObject) o;
				/* Carga de atributos */
				String dirVista = (String) t.getProperties().get("dir");
				int disVista = Integer.parseInt((String) t.getProperties().get("dis"));
				String dialogoCode = (String) t.getProperties().get("dialogo");				
				boolean combate = Boolean.parseBoolean((String) t.getProperties().get("combate"));
				boolean activo = true;
				if(t.getProperties().containsKey("activo")){
					activo = Boolean.parseBoolean((String) t.getProperties().get("activo"));
				}
				boolean volver = t.getProperties().containsKey("volver");
				
				TextureAtlas personajePack = new TextureAtlas(
						"res/imgs/entrenadoresWorld/" + (String) t.getProperties().get("pack") + ".pack");
				/* Creación del los NPCs */
				NPC npc = new NPC(personajePack, new Animation(1 / 10f, playerAtlas.findRegions(dirVista)), dirVista,
						disVista, this, dialogoCode, combate);
				npc.setPosition(t.getX(), t.getY());
				//t.getTextureRegion().getTexture().getHeight();
				//t.getTextureRegion().getTexture().get
				npc.setPosicionOriginal(t.getX(), t.getY());
				if(!t.getProperties().containsKey("ancho")){
					npc.setScale((float) 1.5, 1);
				}				
				npc.setActivo(activo);
				npc.setVolver(volver);
				npcs.add(npc);
			}			
			for (MapObject obj : map.getLayers().get("Objetos").getObjects()){
				objetos.add(new ObjetoMapa(obj));
			}
		}
		/*
		 * Caso de recien cargado
		 */
		if (!npcs.isEmpty() && npcs.get(0).getCara() == null) {
			/* Recarga de NPCs */
			MapLayer npcLayer = map.getLayers().get("Personajes");
			for (MapObject o : npcLayer.getObjects()) {
				TextureMapObject t = (TextureMapObject) o;
				String dirVista = (String) t.getProperties().get("dir");
				boolean combate = Boolean.parseBoolean((String) t.getProperties().get("combate"));
				int disVista = Integer.parseInt((String) t.getProperties().get("dis"));
				String dialogoCode = (String) t.getProperties().get("dialogo");
				TextureAtlas personajePack = new TextureAtlas(
						"res/imgs/entrenadoresWorld/" + (String) t.getProperties().get("pack") + ".pack");
				NPC npc = new NPC(personajePack, new Animation(1 / 10f, playerAtlas.findRegions(dirVista)), dirVista,
						disVista, this, dialogoCode, combate);
				if(!t.getProperties().containsKey("ancho")){
					npc.setScale((float) 1.5, 1);
				}	
				for (NPC npcAlmacenado : npcs) {
					if (npc.getDialogoCode().equals(npcAlmacenado.getDialogoCode())) {
						npcs.remove(npcs.indexOf(npcAlmacenado));
						npc.setPosition(npcAlmacenado.getX(), npcAlmacenado.getY());
						npc.setActivo(npcAlmacenado.isActivo());
						npc.setPosicionOriginal(npcAlmacenado.getxOriginal(), npcAlmacenado.getyOriginal());
						npc.setVolver(npcAlmacenado.isActivo());
						break;
					}
				}
				npcs.add(npc);
			}
		}
		if (!objetos.isEmpty()) {
			/* Recarga de objetos */
			MapObjects objs = map.getLayers().get("Objetos").getObjects();
			for (MapObject o : objs) {	
				ObjetoMapa obj = new ObjetoMapa(o);
				for (ObjetoMapa objAlmacenado : objetos) {
					if (objAlmacenado.getProperties().get("ID").equals(obj.getProperties().get("ID"))) {
						objetos.remove(objetos.indexOf(objAlmacenado));
						obj.setProperties(objAlmacenado.getProperties());
						break;
					}
				}
				objetos.add(obj);
			}
		}

		/* Player */
		player = new Player(getCtx(), playerAtlas, (TiledMapTileLayer) map.getLayers().get("Entorno"),
				map.getLayers().get("Objetos"), map.getLayers().get("Trans"), npcs, getCtx().dialogo, this);
		player.setPosition(getCtx().x, getCtx().y);
		player.setLastPressed(getCtx().lastPressed);
		equipoPokemon();

		for (NPC npc : npcs) {
			npc.setPlayer(player);
		}

		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();
		box = new Sprite(new Texture("res/imgs/OptionBox.png"));
		box.setScale((float) 4.5, (float) 1.5);
		box.setX(box.getX() + 160);

		if (font == null)
			cargarFuente();

		font.setColor(Color.BLACK);

		primeraVez = false;

	}

	@Override
	public void render(float delta) {
 		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tweenManager.update(delta);

		camera.position.set(player.getX() + (player.getWidth() / 2), player.getY() + (player.getHeight() / 2), 0);
		camera.zoom = (float) 1;
		camera.update();
		renderer.setView(camera);
		renderer.render();

		/* Begin */
		renderer.getBatch().begin();

		ArrayList<?> render = ordenar(player, npcs, objetos);
		for (Object object : render) {
			if (object instanceof Player) {
				((Player) object).draw(renderer.getBatch());
			} else if (object instanceof NPC) {
				((NPC) object).draw(renderer.getBatch());
			} else {
				renderer.renderObject((ObjetoMapa) object);
			}
		}

		/* End */
		renderer.getBatch().end();

		/* Menu */
		if (player.getSpacePressed()) {
			// Show menu
			// openMenuPlay();
		}

		if (optionsVisible) {
			batch.begin();
			box.draw(batch);
			if (font == null) {
				cargarFuente();
			}
			font.setColor(Color.BLACK);
			font.draw(batch, getCtx().dialogo.getLinea1(), 50, 125);
			font.draw(batch, getCtx().dialogo.getLinea2(), 50, 75);
			batch.end();
		}
	}

	private void cargarFuente() {
		/* Prepara fuente para escritura */
		generator = new FreeTypeFontGenerator(Gdx.files.internal("res/fuentes/PokemonFont.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 35;
		font = generator.generateFont(parameter);
	}

	private ArrayList<Object> ordenar(Player player, ArrayList<NPC> npcs, ArrayList<ObjetoMapa> objetos) {
		ArrayList<Object> componentes = new ArrayList<>();
		componentes.add(player);
		componentes.addAll(npcs);
		for (ObjetoMapa object : objetos) {
			componentes.add(object);
		}
		Collections.sort(componentes, new CustomComparator());

		return componentes;
	}

	public class CustomComparator implements Comparator<Object> {
		@Override
		public int compare(Object com1, Object com2) {
			float Y1 = 0.0f;
			float Y2 = 0.0f;
			if (com1 instanceof Player) {
				Y1 = ((Player) com1).getY();
			} else if (com1 instanceof NPC) {
				Y1 = ((NPC) com1).getY();
			} else {
				Y1 = ((TextureMapObject)((ObjetoMapa) com1).getObj()).getY();
			}
			if (com2 instanceof Player) {
				Y2 = ((Player) com2).getY();
			} else if (com2 instanceof NPC) {
				Y2 = ((NPC) com2).getY();
			} else {
				Y2 = ((TextureMapObject)((ObjetoMapa) com2).getObj()).getY();
			}
			return (int) ((Y2 - Y1));
		}
	}

	public void openMenuPlay() {
		((Game) Gdx.app.getApplicationListener()).setScreen(new MenuPlay(getCtx(), player.getX(), player.getY(),
				player.getLastPressed(), getCtx().map, getCtx().jugador.getEquipo(), this));
	}

	public void pauseListener() {
		listener = false;
	}

	public void resumeListener() {
		listener = true;
	}

	public void pauseMovimiento() {
		movimiento = false;
	}

	public void resumeMovimiento() {
		movimiento = true;
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();

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
		map.dispose();
		renderer.dispose();

		playerAtlas.dispose();

	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (listener) {
			if (movimiento && keycode == getCtx().getTeclaUp()) {
				player.checkCombat();
				player.velocity.y = player.speed;
				player.velocity.x = 0;
				player.animationTime = 0;
				player.setLastPressed(2);
				getCtx().lastPressed = 2;
				if (getCtx().lastPressed == 0) {
					getCtx().lastPressed = 2;
				} 
				player.WPressed = true;
			} else if (movimiento && keycode == getCtx().getTeclaLeft()) {
				player.checkCombat();
				player.velocity.x = -player.speed;
				player.velocity.y = 0;
				player.animationTime = 0;				
				player.setLastPressed(1);
				getCtx().lastPressed = 1;
				if (getCtx().lastPressed == 0) {
					getCtx().lastPressed = 1;
				}
				player.APressed = true;
			} else if (movimiento && keycode == getCtx().getTeclaDown()) {
				player.checkCombat();
				player.velocity.y = -player.speed;
				player.velocity.x = 0;
				player.animationTime = 0;
				player.setLastPressed(3);
				getCtx().lastPressed = 3;
				if (getCtx().lastPressed == 0) {
					getCtx().lastPressed = 3;
				}
				player.SPressed = true;
			} else if (movimiento && keycode == getCtx().getTeclaRight()) {
				player.checkCombat();
				player.velocity.x = player.speed;
				player.velocity.y = 0;
				player.animationTime = 0;
				player.setLastPressed(4);
				getCtx().lastPressed = 4;
				if (getCtx().lastPressed == 0) {
					getCtx().lastPressed = 4;
				}
				player.DPressed = true;
			} else if (keycode == getCtx().getTeclaB()) {
				// player.SpacePressed = true;
				getCtx().getMapas().put(getMapa(), this);
				((Game) Gdx.app.getApplicationListener()).setScreen(new MenuPlay(getCtx(), player.getX(), player.getY(),
						player.getLastPressed(), getCtx().map, getCtx().jugador.getEquipo(), this));
			} else if (keycode == getCtx().getTeclaA()) {
				if (isDialogando()) {
					/* Esta en pleno dialogo, Enter lo va avanzando */
					optionsVisible = true;

					if (!getCtx().dialogo.isWriting()) {
						String l1 = getCtx().dialogo.siguienteLinea();
						String l2 = getCtx().dialogo.siguienteLinea();

						if (l1 != null) {
							if (l2 == null) {
								l2 = "";
							}

							if (l1.contains("${NOMBRE}")) {
								l1 = l1.replace("${NOMBRE}", ArchivoGuardado.nombreJugador);
							} else if (l2.contains("${NOMBRE}")) {
								l2 = l2.replace("${NOMBRE}", ArchivoGuardado.nombreJugador);
							} else if (l1.contains("${CREACION_NOMBRE}") || l2.contains("${CREACION_NOMBRE}")) {
								l1 = l1.replace("${CREACION_NOMBRE}", "");
								l2 = l2.replace("${CREACION_NOMBRE}", "");
								ArchivoGuardado.nombreJugador = "Sara";
							}

							/* Escribe letra a letra el dialogo */
							getCtx().dialogo.setLineas(l1, l2);

							/*
							 * if (script[counter].contains("(OPTION)")) {
							 * script[counter] = script[counter].replace(
							 * "(OPTION)", ""); optionsVisible = true; }
							 */
						} else {
							getCtx().dialogo.limpiar();
							optionsVisible = false;
							setDialogando(false);
						}
					}
				} else {
					/* Busca interactuar con algo */
					for (ObjetoMapa o : objetos) {
						Gdx.app.log("cartel", "distancia " + distance(o.getObj()));

						/* Dispara evento si estan muy cerca jugador y objeto */
						if (distance(o.getObj()) < 50) {
							interact(o);
							break;
						}
					}
				}
			} else if (keycode == Keys.V) {
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new CombateEntrenador(getCtx(), player, "reverte", this));
			}
		}
		return false;
	}

	/**
	 * Gestiona las interacciones entre el jugador y el objeto obj, que puede
	 * ser un cartel, pokeball, etc.
	 * 
	 * @param o
	 *            el objeto declarado en la capa de objetos.
	 */
	private void interact(ObjetoMapa o) {
		if (o.getProperties().containsKey("cartel")) {
			optionsVisible = true;
			setDialogando(true);

			/* Leer cartel */
			String value = (String) o.getProperties().get("cartel");
			getCtx().dialogo.procesarDialogo("cartel_" + value);
			getCtx().dialogo.setLineas(getCtx().dialogo.siguienteLinea(), getCtx().dialogo.siguienteLinea());
		} else if (o.getProperties().containsKey("item") && o.getProperties().containsKey("used")
				&& o.getProperties().get("used").equals("false")) {
			((TextureMapObject) o.getObj()).setScaleX(0);
			((TextureMapObject) o.getObj()).setScaleY(0);
			optionsVisible = true;
			setDialogando(true);

			/* Leer objeto recogido */
			String value = (String) o.getProperties().get("item");
			getCtx().dialogo.procesarDialogo("item_" + value);
			getCtx().dialogo.setLineas(getCtx().dialogo.siguienteLinea(), getCtx().dialogo.siguienteLinea());

			/* Introduce en mochila */
			if (value.equals("Poción")) {
				getCtx().mochila.add(new Pocion());
			}  else if (value.equals("Corte")) {
				getCtx().mochila.add(new MO("Corte"));
			} else if (value.equals("Fuerza")) {
				getCtx().mochila.add(new MO("Fuerza"));
			} else if (value.equals("Surf")) {
				getCtx().mochila.add(new MO("Surf"));
			}

			/* Asi no se puede volver a coger ese item */
			o.getProperties().put("used", "true");
			o.getObj().getProperties().put("used", "true");
			o.getProperties().put("mostrar", "false");
			o.getObj().getProperties().put("mostrar", "false");
		}else if(o.getProperties().containsKey("salud")){
			optionsVisible = true;
			setDialogando(true);
			getCtx().dialogo.procesarDialogo("centro_salud");
			getCtx().dialogo.setLineas(getCtx().dialogo.siguienteLinea(), getCtx().dialogo.siguienteLinea());
			for(Pokemon poke :getCtx().jugador.getEquipo()){
				poke.sanar();
			}
		}
	}

	/**
	 * Gestiona las interacciones entre el jugador y el objeto obj, que puede
	 * ser un cartel, pokeball, etc.
	 * 
	 * @param obj
	 *            el objeto declarado en la capa de objetos.
	 */
	public void interactNPC(NPC npc) {
		optionsVisible = true;
		setDialogando(true);

		/* Iniciar dialogo */
		getCtx().dialogo.procesarDialogo(npc.getDialogo());
		getCtx().dialogo.setLineas(getCtx().dialogo.siguienteLinea(), getCtx().dialogo.siguienteLinea());

		/* Combate(si hay) */
		if (npc.hayCombate()) {

		}

	}

	/**
	 * Calcula la distancia entre el jugador y el objeto de texturas t.
	 * 
	 * @param t
	 *            el objeto de texturas.
	 * @return la distancia euclidea.
	 */
	public int distance(MapObject o) {
		TextureMapObject t = (TextureMapObject) o;
		double aux = t.getX();
		double dx = Math.pow(player.getX() - aux, 2);
		double dy = Math.pow(player.getY() - t.getY(), 2);
		return (int) (Math.sqrt(dx + dy));
	}

	@Override
	public boolean keyUp(int keycode) {
		if (listener) {
			if (movimiento && keycode == getCtx().getTeclaUp()) {
				if (player.SPressed) {
					player.velocity.x = 0;
					player.velocity.y = -player.speed;
				} else if (player.APressed) {
					player.velocity.y = 0;
					player.velocity.x = -player.speed;
				} else if (player.DPressed) {
					player.velocity.y = 0;
					player.velocity.x = player.speed;
				} else {
					player.velocity.y = 0;
				}
				player.animationTime = 0;
				//getCtx().lastPressed = 2;
				player.WPressed = false;
			} else if (movimiento && keycode == getCtx().getTeclaLeft()) {
				if (player.DPressed) {
					player.velocity.y = 0;
					player.velocity.x = player.speed;
				} else if (player.WPressed) {
					player.velocity.x = 0;
					player.velocity.y = player.speed;
				} else if (player.SPressed) {
					player.velocity.x = 0;
					player.velocity.y = -player.speed;
				} else {
					player.velocity.x = 0;
				}
				player.animationTime = 0;
				//getCtx().lastPressed = 1;
				player.APressed = false;
			} else if (movimiento && keycode == getCtx().getTeclaDown()) {
				if (player.WPressed) {
					player.velocity.x = 0;
					player.velocity.y = player.speed;
				} else if (player.APressed) {
					player.velocity.y = 0;
					player.velocity.x = -player.speed;
				} else if (player.DPressed) {
					player.velocity.y = 0;
					player.velocity.x = player.speed;
				} else {
					player.velocity.y = 0;
				}
				player.animationTime = 0;
				//getCtx().lastPressed = 3;
				player.SPressed = false;
			} else if (movimiento && keycode == getCtx().getTeclaRight()) {
				if (player.APressed) {
					player.velocity.y = 0;
					player.velocity.x = -player.speed;
				} else if (player.WPressed) {
					player.velocity.x = 0;
					player.velocity.y = player.speed;
				} else if (player.SPressed) {
					player.velocity.x = 0;
					player.velocity.y = -player.speed;
				} else {
					player.velocity.x = 0;
				}
				player.animationTime = 0;
				//getCtx().lastPressed = 4;
				player.DPressed = false;
			} else if (keycode == getCtx().getTeclaB()) {
				player.SpacePressed = false;
			}
		}
		return false;
	}

	public void setJugador() {
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
			getCtx().jugador.setEquipo(arrayP);
			db.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public boolean isDialogando() {
		return dialogando;
	}

	public void setDialogando(boolean dialogando) {
		this.dialogando = dialogando;
	}

	public String getMapa() {
		return mapa;
	}

	public void setMapa(String mapa) {
		this.mapa = mapa;
	}

	public ArrayList<ObjetoMapa> getObjetos() {
		return objetos;
	}

	public void setObjetos(ArrayList<ObjetoMapa> objetos) {
		this.objetos = objetos;
	}

}