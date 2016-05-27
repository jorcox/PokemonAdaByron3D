package com.pokemon.entities;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.pokemon.dialogo.Dialogo;
import com.pokemon.mochila.Antidoto;
import com.pokemon.mochila.Mochila;
import com.pokemon.mochila.Pocion;
import com.pokemon.pantallas.Play;

public class NPC extends Sprite implements Serializable {

	private Vector2 velocity = new Vector2();

	private float speed = 60 * 2;

	private float gravity = 60 * 1.8f;

	public float animationTime = 0;

	private transient Animation cara, izquierda, derecha, espalda;
	private transient TiledMapTileLayer collisionLayer;
	private transient MapLayer objectLayer;
	private transient MapLayer transLayer;
	private Dialogo dialogo;
	private Play play;

	private boolean SpacePressed = false;

	private int lastPressed; // A=1, W=2, S=3, D=4

	private Mochila mochila;

	private int distanciaVision;

	private String direccionVision;

	private Player player;

	private boolean activo;

	private boolean dialogado = false;

	private String dialogoCode;

	private boolean combate;

	private boolean volver;
	
	private boolean volviendo = false;
	
	private boolean marcos = false;

	private float x, y;

	private float xOriginal, yOriginal;
	
	private String medalla;

	public NPC(TextureAtlas playerAtlas, Animation face, String dir, int disVista, Play play, String dialogoCode,
			boolean combate, String medalla) {
		super(face.getKeyFrame(0));
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
		this.distanciaVision = disVista;
		this.direccionVision = dir;
		this.dialogoCode = dialogoCode;
		this.combate = combate;
		this.medalla = medalla;
		activo = true;
		mochila = new Mochila();
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
		float oldX = super.getX(), oldY = super.getY();
		/*
		 * Movimiento en X
		 */
		setX(super.getX() + velocity.x * delta);
		/*
		 * Movimiento en Y
		 */
		setY(super.getY() + velocity.y * delta);

		/*
		 * Colision de NPC
		 */
		boolean collisionPlayer = false;
		int anchura = 32;
		int altura = 32;

		collisionPlayer |= ((player.getX() + anchura / 1.5) > getX()) && ((player.getX() - anchura / 1.5) < getX())
				&& ((player.getY() + altura / 1.5) > getY()) && ((player.getY() - altura / 1.5) < getY());

		if (collisionPlayer) {
			setX(oldX);
			setY(oldY);
			velocity.x = 0;
			velocity.y = 0;
			player.colisionNPC = true;
		}

		/*
		 * Vuelta a la posicion original
		 */
		if (volviendo) {
			boolean collisionVuelta = false;

			collisionVuelta |= ((xOriginal + anchura / 3) > getX()) && ((xOriginal - anchura / 3) < getX())
					&& ((yOriginal + altura / 3) > getY()) && ((yOriginal - altura / 3) < getY());

			if (collisionVuelta) {
				velocity.x = 0;
				velocity.y = 0;
				//direccionVision = sentidoContrario(direccionVision);
				volviendo = false;
			}
		}

		/*
		 * Actualizar animacion
		 */
		animationTime += delta;
		if (!collisionPlayer && (!(velocity.x == 0) || !(velocity.y == 0))) {
			setRegion(
					velocity.x < 0 ? izquierda.getKeyFrame(animationTime)
							: velocity.x > 0 ? derecha.getKeyFrame(animationTime)
									: velocity.y > 0 ? espalda.getKeyFrame(animationTime)
											: cara.getKeyFrame(animationTime));
		} else {
			if (direccionVision.equals("cara")) {
				setRegion(cara.getKeyFrame(0));
			} else if (direccionVision.equals("espalda")) {
				setRegion(espalda.getKeyFrame(0));
			} else if (direccionVision.equals("izquierda")) {
				setRegion(izquierda.getKeyFrame(0));
			} else if (direccionVision.equals("derecha")) {
				setRegion(derecha.getKeyFrame(1));
			}
		}
	}

	private String sentidoContrario(String direccionVision) {
		if (direccionVision.equals("cara")) {
			return "espalda";
		} else if (direccionVision.equals("espalda")) {
			return "cara";
		} else if (direccionVision.equals("izquierda")) {
			return "derecha";
		} else if (direccionVision.equals("derecha")) {
			return "izquierda";
		}
		return "cara";
	}

	/**
	 * Calcula la distancia entre el jugador y el objeto de texturas t.
	 * 
	 * @param t
	 *            el objeto de texturas.
	 * @return la distancia euclidea.
	 */
	public int distance(TextureMapObject t) {
		double aux = t.getX();
		double dx = Math.pow(getX() - aux, 2);
		double dy = Math.pow(getY() - t.getY(), 2);
		return (int) (Math.sqrt(dx + dy));
	}

	/**
	 * Gestiona las interacciones entre el jugador y el objeto obj, que puede
	 * ser un cartel, pokeball, etc.
	 * 
	 * @param obj
	 *            el objeto declarado en la capa de objetos.
	 */
	private void interact(TextureMapObject obj) {
		if (obj.getProperties().containsKey("cartel")) {
			play.optionsVisible = true;

			/* Leer cartel */
			String value = (String) obj.getProperties().get("cartel");
			dialogo.procesarDialogo("cartel_" + value);
			dialogo.setLineas(dialogo.siguienteLinea(), dialogo.siguienteLinea());
		} else if (obj.getProperties().containsKey("item")) {
			play.optionsVisible = true;

			/* Leer objeto recogido */
			String value = (String) obj.getProperties().get("item");
			dialogo.procesarDialogo("item_" + value);
			dialogo.setLineas(dialogo.siguienteLinea(), dialogo.siguienteLinea());

			/* Introduce en mochila */
			if (value.equals("Poci�n")) {
				mochila.add(new Pocion());
			} else if (value.equals("Ant�doto")) {
				mochila.add(new Antidoto());
			}

		}
	}

	public void moverAPersonaje(Player player) {
		if (direccionVision.equals("cara")) {
			velocity.y = -speed;
		} else if (direccionVision.equals("espalda")) {
			velocity.y = speed;
		} else if (direccionVision.equals("izquierda")) {
			velocity.x = -speed;
		} else if (direccionVision.equals("derecha")) {
			velocity.x = speed;
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

	public int getDistanciaVision() {
		return distanciaVision;
	}

	public void setDistanciaVision(int distanciaVision) {
		this.distanciaVision = distanciaVision;
	}

	public String getDireccionVision() {
		return direccionVision;
	}

	public void setDireccionVision(String direccionVision) {
		this.direccionVision = direccionVision;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getDialogo() {
		return dialogoCode;
	}

	public boolean hayCombate() {
		return combate;
	}

	public boolean isDialogado() {
		return dialogado;
	}

	public void setDialogado(boolean b) {
		dialogado = b;
	}

	public String getDialogoCode() {
		return dialogoCode;
	}

	public void setDialogoCode(String dialogoCode) {
		this.dialogoCode = dialogoCode;
	}

	public Animation getCara() {
		return cara;
	}

	public void setCara(Animation cara) {
		this.cara = cara;
	}

	public void setX(float x) {
		super.setX(x);
		this.x = x;
	}

	public void setY(float y) {
		super.setY(y);
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public boolean isVolver() {
		return volver;
	}

	public void setVolver(boolean volver) {
		this.volver = volver;
	}

	public void setPosicionOriginal(float x, float y) {
		xOriginal = x;
		yOriginal = y;
	}

	public void volver() {
		//setX(xOriginal);
		//setY(yOriginal);
		if (direccionVision.equals("cara")) {
			velocity.y = speed;
		} else if (direccionVision.equals("espalda")) {
			velocity.y = -speed;
		} else if (direccionVision.equals("izquierda")) {
			velocity.x = speed;
		} else if (direccionVision.equals("derecha")) {
			velocity.x = -speed;
		}
		volviendo = true;
	}

	public float getxOriginal() {
		return xOriginal;
	}

	public void setxOriginal(float xOriginal) {
		this.xOriginal = xOriginal;
	}

	public float getyOriginal() {
		return yOriginal;
	}

	public void setyOriginal(float yOriginal) {
		this.yOriginal = yOriginal;
	}

	public boolean isMarcos() {
		return marcos;
	}
	
	public void setMarcos(boolean marcos) {
		this.marcos = marcos;
	}
	
	public String getMedalla() {
		return medalla;
	}

}
