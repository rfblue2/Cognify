package com.cognify.geometry;

import com.cognify.main.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class Shape {
	
	float posX, posY;
	int dir;
	COLOR c;
	Bitmap bmp;
	boolean hole;//true if it is a hole
	Context context;
	
	public enum DIR	{UP, DOWN, LEFT, RIGHT};
	public enum SHAPE {RECTANGLE, CIRCLE, TRIANGLE_RIGHT, TRIANGLE_EQU,
		ARROW, HEXAGON, OVAL, PENTAGON, RHOMBUS, SQUARE, STAR};
	public enum COLOR {PURPLE, GREEN, RED, YELLOW, BLUE};
	
	public Shape(SHAPE shape, int x, int y, COLOR c, boolean hole, Context context)	{
		this.hole = hole;
		this.c = c;
		this.context = context;
		
		switch(shape)	{
		case RECTANGLE:
			//make rectangle later
			break;
		case CIRCLE:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.circle_blue);
			break;
		case TRIANGLE_RIGHT:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.triangle_right_blue);
			break;
		case TRIANGLE_EQU:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.triangle_equal_blue);
			break;
		case ARROW:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.arrow_blue);
			break;
		case HEXAGON:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.hexagon_blue);
			break;
		case OVAL:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.oval_blue);
			break;
		case PENTAGON:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.pentagon_blue);
			break;
		case RHOMBUS:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.rhombus_blue);
			break;
		case SQUARE:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.square_blue);
			break;
		case STAR:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.star_blue);
			break;
		}
		
	}
	
	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public COLOR getC() {
		return c;
	}

	public void setC(COLOR c) {
		this.c = c;
	}

	public boolean isHole() {
		return hole;
	}

	public void setHole(boolean hole) {
		this.hole = hole;
	}

	public Bitmap getBmp() {
		return bmp;
	}

	public void setBmp(Bitmap bmp) {
		this.bmp = bmp;
	}
	
}


