package com.cognify.geometry;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class Shape {
	
	int posX, posY;
	int dir;
	Color c;
	String imgSrc;
	Bitmap bmp;
	boolean hole;//true if it is a hole
	
	public enum DIR	{UP, DOWN, LEFT, RIGHT};
	public enum SHAPE {RECTANGLE, CIRCLE, TRIANGLE_RIGHT, TRIANGLE_ISO, TRIANGLE_EQU};
	
	public Shape(String src, SHAPE shape, Color c, boolean hole)	{
		imgSrc = src;
		bmp = BitmapFactory.decodeFile(imgSrc);
		this.hole = hole;
		this.c = c;
		
		switch(shape)	{
		case RECTANGLE:
			
			break;
		case CIRCLE:
			
			break;
		case TRIANGLE_RIGHT:
			
			break;
		case TRIANGLE_ISO:
			
			break;
		case TRIANGLE_EQU:
			
			break;
		}
	}
	
	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public boolean isHole() {
		return hole;
	}

	public void setHole(boolean hole) {
		this.hole = hole;
	}
	
}
