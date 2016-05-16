package com.pokemon.pantallas;

import java.io.Serializable;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.pokemon.utilidades.ArchivoGuardado;

public abstract class Pantalla implements Screen, InputProcessor, Serializable {
	
	private ArchivoGuardado ctx;

	public void setCtx(ArchivoGuardado ctx) {
		this.ctx = ctx;
	}

	public ArchivoGuardado getCtx() {
		return ctx;
	}
	
}
