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
		 
		 int[] allpixels;
		 
		switch(shape)	{
		case RECTANGLE:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.rectangle, opt);
			changeColor(bmp, Color.RED);
			break;
		case CIRCLE:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.circle, opt);
			changeColor(bmp, Color.GREEN);
			break;
		case TRIANGLE_RIGHT:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.triangle_right, opt);
			changeColor(bmp, Color.CYAN);
			break;
		case TRIANGLE_EQU:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.triangle_equal, opt);
			changeColor(bmp, Color.BLUE);
			break;
		case ARROW:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.arrow, opt);
			changeColor(bmp, Color.YELLOW);
			break;
		case HEXAGON:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.hexagon, opt);
			changeColor(bmp, Color.BLACK);
			break;
		case OVAL:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.oval, opt);
			changeColor(bmp, Color.WHITE);
			break;
		case PENTAGON:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.pentagon, opt);
			changeColor(bmp, Color.MAGENTA);
			break;
		case RHOMBUS:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.rhombus, opt);
			changeColor(bmp, Color.MAGENTA);
			break;
		case SQUARE:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.square, opt);
			changeColor(bmp, Color.GREEN);
			break;
		case STAR:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.star, opt);
			changeColor(bmp, Color.CYAN);
			break;
		case TRAPEZOID:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.trapezoid, opt);
			changeColor(bmp, Color.RED);
			break;
		case OCTAGON:
			bmp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.octagon, opt);
			changeColor(bmp, Color.BLACK);
			break;
		}
		
		
		
		/*if(hole)//Turns shape gray if it's a hole
		{
			changeColor(bmp, Color.GRAY);
		}*/
		
		
	}
	
	public void changeColor(Bitmap src, int col){
		int currentC = Color.rgb(63, 72, 204);//present blue color
		int[] allpixels = new int [bmp.getHeight()*bmp.getWidth()];

		 bmp.getPixels(allpixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
		 
		 int red = Color.red(bmp.getPixel(20, 20));
		 int green = Color.green(bmp.getPixel(20, 20));
		 int blue = Color.blue(bmp.getPixel(20, 20));
		 
		 Log.v("red", ""+red);
		 Log.v("green", ""+green);
		 Log.v("blue", ""+blue);
		 
		 if(hole)
			 col = Color.GRAY;

		 for(int i =0; i<bmp.getHeight()*bmp.getWidth();i++){
			 
			 if(allpixels[i] == currentC)
				 allpixels[i] = col;
				 	
		  }
		 
		  bmp.setPixels(allpixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
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































































//hi




