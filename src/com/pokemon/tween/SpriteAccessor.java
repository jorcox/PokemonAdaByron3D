package com.pokemon.tween;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

public class SpriteAccessor implements TweenAccessor<Sprite> {

	public static final int ALPHA = 0;
	public static final int SLIDE = 1;
	public static final int COLOR = 2;
	public static final int SIZE = 3;

	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		case SLIDE:
			returnValues[0] = target.getX();
			returnValues[1] = target.getY();
			return 2;
		case COLOR:
			returnValues[0] = target.getColor().r;
			returnValues[1] = target.getColor().g;
			returnValues[2] = target.getColor().b;
			return 3;
		case SIZE:
			returnValues[0] = target.getScaleX();
			returnValues[1] = target.getScaleY();
			return 2;
		default:
			assert false;
			return -1;
		}
	}

	@Override
	public void setValues(Sprite target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g,
					target.getColor().b, returnValues[0]);
			break;
		case SLIDE:
			target.setPosition(returnValues[0], returnValues[1]);
			break;
		case COLOR:
            Color c = target.getColor();
            c.set(returnValues[0], returnValues[1], returnValues[2], 
            		1);
            target.setColor(c);
            break;
		case SIZE:
			target.setSize(returnValues[0],returnValues[1]);
			break;
		default:
			assert false;
		}
	}

}
