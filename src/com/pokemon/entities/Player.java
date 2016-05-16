package com.pokemon.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.pokemon.dialogo.Dialogo;
import com.pokemon.pantallas.CombateEntrenador;
import com.pokemon.pantallas.CombateP;
import com.pokemon.pantallas.ObjetoMapa;
import com.pokemon.pantallas.Pantalla;
import com.pokemon.pantallas.Play;
import com.pokemon.utilidades.ArchivoGuardado;

import entrenadores.Jugador;
import pokemon.AparicionPokemon;
import pokemon.Pokemon;

public class Player extends Sprite implements Serializable {

	public Vector2 velocity = new Vector2();

	public float speed = 60 * 2;

	private float gravity = 60 * 1.8f;

	public float animationTime = 0;

	private transient Animation cara, izquierda, derecha, espalda;
	private transient TiledMapTileLayer collisionLayer;
	private transient MapLayer objectLayer;
	private transient MapLayer transLayer;
	private Dialogo dialogo;
	private Play play;

	public boolean APressed = false;

	public boolean WPressed = false;

	public boolean SPressed = false;

	public boolean DPressed = false;

	public boolean SpacePressed = false;

	public boolean EnterPressed = false;

	private int p;

	private boolean esperando = false;

	public boolean colisionNPC = false;

	private NPC npcInteractuando;

	public boolean terminado = false;

	ArrayList<NPC> npcs;

	private int lastPressed; // A=1, W=2, S=3, D=4

	public Jugador jugador;

	private boolean nadando = false;

	private float x, y;

	public Player(ArchivoGuardado ctx, TextureAtlas playerAtlas, TiledMapTileLayer collisionLayer, MapLayer objectLayer,
			MapLayer transLayer, ArrayList<NPC> npcs, Dialogo dialogo, Play play) {
		super(new Animation(1 / 10f, playerAtlas.findRegions("cara")).getKeyFrame(0));
		cara = new Animation(1 / 10f, playerAtlas.findRegions("cara"));
		derecha = new Animation(1 / 10f, playerAtlas.findRegions("derecha"));
		izquierda = new Animation(1 / 10f, playerAtlas.findRegions("izquierda"));
		espalda = new Animation(1 / 10f, playerAtlas.findRegions("espalda"));
		cara.setPlayMode(Animation.PlayMode.LOOP);
		derecha.setPlayMode(Animation.PlayMode.LOOP);
		izquierda.setPlayMode(Animation.PlayMode.LOOP);
		espalda.setPlayMode(Animation.PlayMode.LOOP);
		this.collisionLayer = collisionLayer;
		this.setTransLayer(transLayer);
		this.objectLayer = objectLayer;
		this.transLayer = transLayer;
		this.dialogo = dialogo;
		this.play = play;
		this.npcs = npcs;

		p = 0;
	}

	@Override
	public void draw(Batch spriteBach) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBach);

	}

	public void update(float delta) {
		/*
		 * Guardar la posicion anterior
		 */
		float oldX = getX(), oldY = getY(), tileWidth = collisionLayer.getTileWidth(),
				tileHeight = collisionLayer.getTileHeight();
		boolean collisionX = false, collisionY = false, collisionObj = false, collisionNpc = false;
		boolean puedeNadar = play.getCtx().mochila.tieneSurf();

		/*
		 * Movimiento en X
		 */
		setX(getX() + velocity.x * delta);

		if (velocity.x < 0) {
			// Top left
			// collisionX = collisionLayer.getCell((int) (getX() / tileWidth),
			// (int) ((getY() + getHeight()) / tileHeight))
			// .getTile().getProperties().containsKey("blocked");
			// Middle left
			if (!collisionX) {
				collisionX |= collisionLayer
						.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)).getTile()
						.getProperties().containsKey("blocked");
				collisionX |= (collisionLayer
						.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)).getTile()
						.getProperties().containsKey("water") && !puedeNadar);
			}

			// Bottom left
			if (!collisionX) {
				collisionX |= collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight)).getTile()
						.getProperties().containsKey("blocked");
				collisionX |= (collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight)).getTile()
						.getProperties().containsKey("water") && !puedeNadar);
			}
		} else if (velocity.x > 0) {
			// Top right
			// collisionX = collisionLayer
			// .getCell((int) ((getX() + getWidth()) / tileWidth), (int)
			// ((getY() + getHeight()) / tileHeight))
			// .getTile().getProperties().containsKey("blocked");
			// Middle right
			if (!collisionX) {
				collisionX |= collisionLayer
						.getCell((int) ((getX() + getWidth()) / tileWidth),
								(int) ((getY() + getHeight() / 2) / tileHeight))
						.getTile().getProperties().containsKey("blocked");
				collisionX |= (collisionLayer
						.getCell((int) ((getX() + getWidth()) / tileWidth),
								(int) ((getY() + getHeight() / 2) / tileHeight))
						.getTile().getProperties().containsKey("water") && !puedeNadar);
			}

			// Bottom right
			if (!collisionX) {
				collisionX |= collisionLayer
						.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeight)).getTile()
						.getProperties().containsKey("blocked");
				collisionX |= (collisionLayer
						.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeight)).getTile()
						.getProperties().containsKey("water") && !puedeNadar);
			}
		}
		/*
		 * Reaccion a colision en X
		 */
		if (collisionX) {
			setX(oldX);
			velocity.x = 0;
		}

		/*
		 * Movimiento en Y
		 */
		setY(getY() + velocity.y * delta);
		if (velocity.y < 0) {
			// Bottom left
			collisionY = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight)).getTile()
					.getProperties().containsKey("blocked");
			collisionY |= (collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight)).getTile()
					.getProperties().containsKey("water") && !puedeNadar);
			// Bottom middle
			if (!collisionY) {
				collisionY |= collisionLayer
						.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) ((getY()) / tileHeight)).getTile()
						.getProperties().containsKey("blocked");
				collisionY |= (collisionLayer
						.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) ((getY()) / tileHeight)).getTile()
						.getProperties().containsKey("water") && !puedeNadar);
			}

			// Bottom right
			if (!collisionY) {
				collisionY |= collisionLayer
						.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeight)).getTile()
						.getProperties().containsKey("blocked");
				collisionY |= (collisionLayer
						.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeight)).getTile()
						.getProperties().containsKey("water") && !puedeNadar);
			}

		} else if (velocity.y > 0) {
			// Top left
			// collisionY = collisionLayer.getCell((int) (getX() / tileWidth),
			// (int) ((getY() + getHeight()) / tileHeight))
			// .getTile().getProperties().containsKey("blocked");
			// Top middle
			if (!collisionY) {
				collisionY |= collisionLayer
						.getCell((int) ((getX() + getWidth() / 2) / tileWidth),
								(int) ((getY() + getHeight() / 2) / tileHeight))
						.getTile().getProperties().containsKey("blocked");
				collisionY |= (collisionLayer
						.getCell((int) ((getX() + getWidth() / 2) / tileWidth),
								(int) ((getY() + getHeight() / 2) / tileHeight))
						.getTile().getProperties().containsKey("water") && !puedeNadar);
				collisionY |= collisionLayer
						.getCell((int) ((getX() + getWidth() / 2) / tileWidth),
								(int) ((getY() + getHeight() / 2) / tileHeight))
						.getTile().getProperties().containsKey("abajo");
			}

			// Top right
			// if (!collisionY)
			// collisionY |= collisionLayer
			// .getCell((int) ((getX() + getWidth()) / tileWidth), (int)
			// ((getY() + getHeight()) / tileHeight))
			// .getTile().getProperties().containsKey("blocked");
		}
		/*
		 * Reacción al colisionar en Y
		 */
		if (collisionY) {
			setY(oldY);
			velocity.y = 0;
		}

		/* Transicion de mapa */
		if (collisionX || collisionY) {
			for (MapObject o : transLayer.getObjects()) {
				TextureMapObject t = (TextureMapObject) o;
				/* Transicion solo si estan muy cerca jugador y casilla */
				if (play.distance(o) < 50) {
					String mapa = (String) t.getProperties().get("mapa");
					if (t.getProperties().containsKey("encara")) {
						int encara = Integer.parseInt((String) t.getProperties().get("encara"));
						play.getCtx().lastPressed = encara;
					}
					if (!play.getCtx().getMapas().containsKey(mapa + ".tmx")) {
						play.getCtx().getMapas().put(play.getMapa(), play);
						Pantalla p = new Play(play.getCtx(), Integer.parseInt((String) t.getProperties().get("x")),
								Integer.parseInt((String) t.getProperties().get("y")), getLastPressed(), mapa + ".tmx");
						play.getCtx().getMapas().put(mapa + ".tmx", p);
						((Game) Gdx.app.getApplicationListener()).setScreen(p);
					} else {
						play.getCtx().getMapas().put(play.getMapa(), play);
						play.getCtx().x = Integer.parseInt((String) t.getProperties().get("x"));
						play.getCtx().y = Integer.parseInt((String) t.getProperties().get("y"));
						play.getCtx().lastPressed = getLastPressed();
						((Game) Gdx.app.getApplicationListener())
								.setScreen(play.getCtx().getMapas().get(mapa + ".tmx"));
					}
					break;
				}
			}
		}

		/*
		 * Colision de objetos
		 */
		for (ObjetoMapa object : play.getObjetos()) {
			boolean currentCollision = false;
			TextureMapObject texture = (TextureMapObject) object.getObj();
			if (object.getProperties().containsKey("mostrar")) {
				if (object.getProperties().get("mostrar").equals("true")) {
					currentCollision |= ((texture.getX() + texture.getTextureRegion().getRegionWidth() / 1.5) > getX())
							&& ((texture.getX() - texture.getTextureRegion().getRegionWidth() / 1.5) < getX())
							&& ((texture.getY() + texture.getTextureRegion().getRegionHeight() / 1.5) > getY())
							&& ((texture.getY() - texture.getTextureRegion().getRegionHeight() / 2) < getY());
				}
			} else {
				System.err.println("Tu objeto que esta en X:" + texture.getX() + " Y:" + texture.getY()
						+ " no tiene puesto el atributo mostrar (ponlo con valor true en el TiledMap)");
			}
			/* Fuerza/Romper */

			// TODO Comprobar Fuerza/Romper del personaje
			if (currentCollision && texture.getProperties().containsKey("rompible")
					&& texture.getProperties().get("rompible").equals("true")) {
				object.getProperties().put("mostrar", "false");
				texture.getProperties().put("mostrar", "false");
			}
			/* Actualiza el flag global de colision */
			collisionObj |= currentCollision;
		}

		/*
		 * Colision de NPC
		 */
		int anchura = 32;
		int altura = 32;
		for (NPC npc : npcs) {
			collisionNpc |= ((npc.getX() + anchura / 1.5) > getX()) && ((npc.getX() - anchura / 1.5) < getX())
					&& ((npc.getY() + altura / 1.5) > getY()) && ((npc.getY() - altura / 1.5) < getY());
		}
		if (collisionObj || collisionNpc) {
			setX(oldX);
			setY(oldY);
			velocity.x = 0;
			velocity.y = 0;
		}

		if (collisionLayer.getCell((int) ((getX()) / tileWidth), (int) (getY() / tileHeight)).getTile().getProperties()
				.containsKey("water") && !nadando) {
			nadando = true;
			TextureAtlas playerAtlas = new TextureAtlas("res/imgs/entrenadoresWorld/boy_surf_offset.pack");
			cara = new Animation(1 / 10f, playerAtlas.findRegions("cara"));
			derecha = new Animation(1 / 10f, playerAtlas.findRegions("derecha"));
			izquierda = new Animation(1 / 10f, playerAtlas.findRegions("izquierda"));
			espalda = new Animation(1 / 10f, playerAtlas.findRegions("espalda"));
			cara.setPlayMode(Animation.PlayMode.LOOP);
			derecha.setPlayMode(Animation.PlayMode.LOOP);
			izquierda.setPlayMode(Animation.PlayMode.LOOP);
			espalda.setPlayMode(Animation.PlayMode.LOOP);
		} else if (!collisionLayer.getCell((int) ((getX()) / tileWidth), (int) (getY() / tileHeight)).getTile()
				.getProperties().containsKey("water") && nadando) {
			nadando = false;
			TextureAtlas playerAtlas = new TextureAtlas("res/imgs/entrenadoresWorld/protagonista.pack");
			cara = new Animation(1 / 10f, playerAtlas.findRegions("cara"));
			derecha = new Animation(1 / 10f, playerAtlas.findRegions("derecha"));
			izquierda = new Animation(1 / 10f, playerAtlas.findRegions("izquierda"));
			espalda = new Animation(1 / 10f, playerAtlas.findRegions("espalda"));
			cara.setPlayMode(Animation.PlayMode.LOOP);
			derecha.setPlayMode(Animation.PlayMode.LOOP);
			izquierda.setPlayMode(Animation.PlayMode.LOOP);
			espalda.setPlayMode(Animation.PlayMode.LOOP);
		}

		interaccionEntrenadores();

		/*
		 * Actualizar animacion y posicion en contexto
		 */
		play.getCtx().x = getX();
		play.getCtx().y = getY();
		animationTime += delta;
		if ((WPressed || APressed || SPressed || DPressed) && (!collisionX && !collisionY)
				&& (!(velocity.x == 0) || !(velocity.y == 0))) {
			setRegion(
					velocity.x < 0 ? izquierda.getKeyFrame(animationTime)
							: velocity.x > 0 ? derecha.getKeyFrame(animationTime)
									: velocity.y > 0 ? espalda.getKeyFrame(animationTime)
											: cara.getKeyFrame(animationTime));
		} else {
			switch (play.getCtx().lastPressed) {
			case 1: // A
				setRegion(izquierda.getKeyFrame(0));
				break;
			case 2: // W
				setRegion(espalda.getKeyFrame(0));
				break;
			case 3: // S
				setRegion(cara.getKeyFrame(0));
				break;
			case 4: // D
				setRegion(derecha.getKeyFrame(1));
				break;
			}
		}
	}

	private void interaccionEntrenadores() {
		/* Interacción entrenadores */
		for (NPC npc : npcs) {
			if (visible(npc) && npc.isActivo()) {
				npcInteractuando = npc;
				npc.setActivo(false);
				velocity.x = 0;
				velocity.y = 0;
				play.pauseMovimiento();
				npc.moverAPersonaje(this);
				esperando = true;
			}
		}

		/* Esperando la llegada de entrenador a personaje */
		if (esperando) {
			if (colisionNPC) {
				/*
				 * Una vez el NPC ha llegado al personaje empieza la conversacion 
				 */
				if (!npcInteractuando.isDialogado()) {
					play.interactNPC(npcInteractuando);
					npcInteractuando.setDialogado(true);
				} else {
					/*
					 * Cuando deja de dialogar se procede a devolver la libertad
					 * al jugador o iniciar el compate
					 */
					if (!play.isDialogando()) {
						if (npcInteractuando.hayCombate()) {
							if(npcInteractuando.isVolver()){
								npcInteractuando.volver();
							}
							iniciarCombate(npcInteractuando.getDialogoCode());
						}
						if(npcInteractuando.isVolver()){
							npcInteractuando.volver();
						}
						resetMovimiento();
						play.resumeMovimiento();
						esperando = false;
						colisionNPC = false;
					}
				}
			}
		}
	}

	private void resetMovimiento() {
		velocity.x = 0;
		velocity.y = 0;
		WPressed = false;
		APressed = false;
		SPressed = false;
		DPressed = false;	
	}

	private void iniciarCombate(String id) {
		play.getCtx().getMapas().put(play.getMapa(), play);
		((Game) Gdx.app.getApplicationListener())
				.setScreen(new CombateEntrenador(play.getCtx(), this, id, play));
	}

	private boolean visible(NPC npc) {
		String dir = npc.getDireccionVision();
		int dis = npc.getDistanciaVision();
		float npcX = npc.getX();
		float npcY = npc.getY();
		float minX = 0, maxX = 0, minY = 0, maxY = 0;
		if (dir.equals("cara")) {
			minX = npcX - 3;
			maxX = npcX + 3;
			minY = npcY - (32 * dis);
			maxY = npcY;
		} else if (dir.equals("espalda")) {
			minX = npcX - 3;
			maxX = npcX + 3;
			minY = npcY;
			maxY = npcY + (32 * dis);
		} else if (dir.equals("izquierda")) {
			minX = npcX - (32 * dis);
			maxX = npcX;
			minY = npcY - 3;
			maxY = npcY + 3;
		} else if (dir.equals("derecha")) {
			minX = npcX;
			maxX = npcX + (32 * dis);
			minY = npcY - 3;
			maxY = npcY + 3;
		}
		return (maxX > getX()) && (minX < getX()) && (maxY > getY()) && (minY < getY());
	}

	/**
	 * Si el jugador esta en casilla de combate, elige aleatoriamente si le
	 * aparece un pokemon salvaje con una probabilidad del 20%.
	 * 
	 * Por ahora solo comprueba al cambiar de tecla de moverse (arreglar).
	 */
	public void checkCombat() {
		if (collisionLayer
				.getCell((int) ((getX() + getWidth()) / collisionLayer.getTileWidth()),
						(int) ((getY() + getHeight()) / collisionLayer.getTileHeight()))
				.getTile().getProperties().containsKey("combat")) {
			double combatOdds = new Random().nextDouble();
			if (combatOdds < 0.08) {
				AparicionPokemon ap = new AparicionPokemon();
				Pokemon[] pkmns = ap.setPokemonSalvaje(play.getMapa());
				Pokemon p = pkmns[new Random().nextInt(pkmns.length)];
				ap.shutdown();
				((Game) Gdx.app.getApplicationListener()).setScreen(new CombateP(play.getCtx(), this, 1, play, p));
			}
		}
		if (collisionLayer
				.getCell((int) ((getX() + getWidth()) / collisionLayer.getTileWidth()),
						(int) ((getY() + getHeight()) / collisionLayer.getTileHeight()))
				.getTile().getProperties().containsKey("water")) {
			double combatOdds = new Random().nextDouble();
			if (combatOdds < 0.08) {
				AparicionPokemon ap = new AparicionPokemon();
				Pokemon[] pkmns = ap.setPokemonSalvajeAgua(play.getMapa());
				Pokemon p = pkmns[new Random().nextInt(pkmns.length)];
				ap.shutdown();
				((Game) Gdx.app.getApplicationListener()).setScreen(new CombateP(play.getCtx(), this, 1, play, p));
			}
		}
	}

	public boolean getSpacePressed() {
		return SpacePressed;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	public int getLastPressed() {
		return lastPressed;
	}

	public void setLastPressed(int lastPressed) {
		this.lastPressed = lastPressed;
	}

	public MapLayer getTransLayer() {
		return transLayer;
	}

	public void setTransLayer(MapLayer transLayer) {
		this.transLayer = transLayer;
	}

	public float getAnimationTime() {
		return animationTime;
	}

	public void setAnimationTime(float animationTime) {
		this.animationTime = animationTime;
	}

}