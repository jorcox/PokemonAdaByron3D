package com.pokemon.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pokemon.utilidades.ArchivoGuardado;

public class OpcionesTeclas extends Pantalla {

	private Pantalla screen;
	private int posicion;
	private boolean cambiando;
	
	private SpriteBatch batch;
	private Texture tFondo, tOpcion, tOpcionSel;
	
	BitmapFont font = new BitmapFont(
			Gdx.files.internal("res/fuentes/pokemon.fnt"),
			Gdx.files.internal("res/fuentes/pokemon.png"), false);
	
	public OpcionesTeclas(ArchivoGuardado ctx, Pantalla screen) {
		this.setCtx(ctx);
		this.screen = screen;
		posicion = 0;
		cambiando = false;
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		tFondo = new Texture("res/imgs/loadbg.png");
		tOpcion = new Texture("res/imgs/menubutton.png");
		tOpcionSel = new Texture("res/imgs/menubuttonselected.png");
		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		
		batch.draw(tFondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(tOpcion, 50, 475, 650, tOpcion.getHeight());
		
		if (posicion == 0) {
			batch.draw(tOpcionSel, 50, 400);
			batch.draw(tOpcionSel, 500, 400, 200, tOpcionSel.getHeight());
		} else {
			batch.draw(tOpcion, 50, 400);
			batch.draw(tOpcion, 500, 400, 200, tOpcion.getHeight());
		}
		
		if (posicion == 1) {
			batch.draw(tOpcionSel, 50, 325);
			batch.draw(tOpcionSel, 500, 325, 200, tOpcionSel.getHeight());		
		} else {
			batch.draw(tOpcion, 50, 325);
			batch.draw(tOpcion, 500, 325, 200, tOpcion.getHeight());
		}
		
		if (posicion == 2) {
			batch.draw(tOpcionSel, 50, 250);
			batch.draw(tOpcionSel, 500, 250, 200, tOpcionSel.getHeight());
		} else {
			batch.draw(tOpcion, 50, 250);
			batch.draw(tOpcion, 500, 250, 200, tOpcion.getHeight());
		}
		
		if (posicion == 3) {
			batch.draw(tOpcionSel, 50, 175);
			batch.draw(tOpcionSel, 500, 175, 200, tOpcionSel.getHeight());
		} else {
			batch.draw(tOpcion, 50, 175);
			batch.draw(tOpcion, 500, 175, 200, tOpcion.getHeight());
		}
		
		if (posicion == 4) {
			batch.draw(tOpcionSel, 50, 100);
			batch.draw(tOpcionSel, 500, 100, 200, tOpcionSel.getHeight());
		} else {
			batch.draw(tOpcion, 50, 100);
			batch.draw(tOpcion, 500, 100, 200, tOpcion.getHeight());
		}
		
		if (posicion == 5) {
			batch.draw(tOpcionSel, 50, 25);
			batch.draw(tOpcionSel, 500, 25, 200, tOpcionSel.getHeight());
		} else {
			batch.draw(tOpcion, 50, 25);
			batch.draw(tOpcion, 500, 25, 200, tOpcion.getHeight());
		}
		
		font.draw(batch, "Pulsa CTRL para cambiar tecla y otra vez para confirmar", 75, 520);
		font.draw(batch, "Mover Arriba", 75, 445);
		font.draw(batch, "Mover Abajo", 75, 370);
		font.draw(batch, "Mover Izquierda", 75, 295);
		font.draw(batch, "Mover Derecha", 75, 220);
		font.draw(batch, "Boton A", 75, 145);
		font.draw(batch, "Boton B", 75, 70);
		
		font.draw(batch, Keys.toString(getCtx().getTeclaUp()), 525, 445);
		font.draw(batch, Keys.toString(getCtx().getTeclaDown()), 525, 370);
		font.draw(batch, Keys.toString(getCtx().getTeclaLeft()), 525, 295);
		font.draw(batch, Keys.toString(getCtx().getTeclaRight()), 525, 220);
		font.draw(batch, Keys.toString(getCtx().getTeclaA()), 525, 145);
		font.draw(batch, Keys.toString(getCtx().getTeclaB()), 525, 70);
		
		batch.end();
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
		if (!cambiando) {
			if (keycode == getCtx().getTeclaDown()) {
				/* Baja el cursor */
				if (posicion < 5) {
					posicion++;
				}
			} else if (keycode == getCtx().getTeclaUp()) {
				/* Sube el cursor */ 
				if (posicion > 0) {
					posicion--;
				}
			} else if (keycode == getCtx().getTeclaB()) {
				/* Vuelve a la pantalla anterior */
				screen.setCtx(this.getCtx());
				((Game) Gdx.app.getApplicationListener()).setScreen(screen);
			} else if (keycode == Keys.CONTROL_LEFT) {
				cambiando = true;
			}
		} else {
			if (keycode == Keys.CONTROL_LEFT) {
				cambiando = false;
			} else {
				if (posicion == 0) {
					getCtx().setTeclaUp(keycode);
				} else if (posicion == 1) {
					getCtx().setTeclaDown(keycode);
				} else if (posicion == 2) {
					getCtx().setTeclaLeft(keycode);
				} else if (posicion == 3) {
					getCtx().setTeclaRight(keycode);
				} else if (posicion == 4) {
					getCtx().setTeclaA(keycode);
				} else if (posicion == 5) {
					getCtx().setTeclaB(keycode);
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
