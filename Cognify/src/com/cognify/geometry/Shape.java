package com.cognify.geometry;

import com.cognify.main.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.util.Log;

public class Shape {
	
	float posX, posY;
	int dir;
	COLOR c;
	Bitmap bmp;
	boolean hole;//true if it is a hole
	Context context;
	ColorFilter holeFilter;
	SHAPE shape;
	
	public enum DIR	{UP, DOWN, LEFT, RIGHT};
	public enum SHAPE {RECTANGLE, CIRCLE, TRIANGLE_RIGHT, TRIANGLE_EQU,
		ARROW, HEXAGON, OVAL, PENTAGON, RHOMBUS, SQUARE, STAR, TRAPEZOID, OCTAGON};
	public enum COLOR {PURPLE, GREEN, RED, YELLOW, BLUE};
	
	public Shape(SHAPE shape, int x, int y, COLOR c, boolean hole, Context context)	{
		this.shape = shape;
		this.hole = hole;
		this.c = c;
		this.context = context;
		
		this.posX = x;
		this.posY = y;
		
		 BitmapFactory.Options opt = new BitmapFactory.Options();
		 opt.inMutable = true;
		 
		 
		switch(shape)	{
		case RECTANGLE:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.rectangle, opt);
			break;
		case CIRCLE:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.circle, opt);
			break;
		case TRIANGLE_RIGHT:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.triangle_right, opt);
			break;
		case TRIANGLE_EQU:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.triangle_equal, opt);
			break;
		case ARROW:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.arrow, opt);
			break;
		case HEXAGON:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.hexagon, opt);
			break;
		case OVAL:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.oval, opt);
			break;
		case PENTAGON:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.pentagon, opt);
			break;
		case RHOMBUS:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.rhombus, opt);
			break;
		case SQUARE:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.square, opt);
			break;
		case STAR:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.star, opt);
			Log.v("created star", ""+R.drawable.star);
			break;
		case TRAPEZOID:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.trapezoid, opt);
			break;
		case OCTAGON:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.octagon, opt);
			break;
		}
		
		if(hole)//Turns shape gray if it's a hole
		{
			 int[] allpixels = new int [bmp.getHeight()*bmp.getWidth()];
	
			 bmp.getPixels(allpixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
	
			 for(int i =0; i<bmp.getHeight()*bmp.getWidth();i++){
				
				 
				 if(allpixels[i] == Color.BLUE)
					 allpixels[i] = Color.GRAY;
					 	
			  }
			 
			  bmp.setPixels(allpixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
		 }
		
		
	}
	
	public SHAPE getShape(){
		return shape;
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


