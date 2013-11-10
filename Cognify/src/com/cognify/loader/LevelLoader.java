package com.cognify.loader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.cognify.geometry.Shape;
import com.cognify.geometry.Shape.SHAPE;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.util.Xml;

public class LevelLoader {
	File xmlFile;
	Context context;
	AssetManager assetManager;
	
	public LevelLoader(Context context)
	{
		this.context = context;
		assetManager = context.getAssets(); 
		 // To get names of all files inside the "Files" folder
		  try {
		   String[] files = assetManager.list("levels");
		   
		   Log.v("num files", ""+files.length);
		   
		   for(int i=0; i<files.length; i++){
			   Log.d("files", files[i]);
		   }
		  } catch (IOException e1) {
		   e1.printStackTrace();
		  }
		  
		/*  
		try {
			this.parse(assetManager.open("levels/test.xml"));
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void loadLevel(int num){
		try {
			this.parse(assetManager.open("levels/"+num+".xml"));
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List parse(InputStream in) throws XmlPullParserException, IOException{
		Log.v("parser", "started");
		try{
			
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return readFeed(parser);
		} finally {
			in.close();
		}
		
	}
	
	private List<Shape> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException{
		List<Shape> shapes = new ArrayList<Shape>();
		//Log.v("feed", "reading");
		
		while(parser.next() != XmlPullParser.END_TAG)
		{
			//Log.v("feed", "continuing");
			if(parser.getEventType() != XmlPullParser.START_TAG)
				continue;
			String name = parser.getName();
			
			if(name.equals("shape"))
			{
				//Log.v("feed", "name is shape");
				shapes.add(readEntry(parser));
			}
			else
				skip(parser);
		}
		
		return shapes;
	}
	
	//entries in theirs = shapes in ours
	
	
	private Shape readEntry(XmlPullParser parser) throws XmlPullParserException, IOException{
		String type = null;
		int x = 0;
		int y = 0;
		
		while(parser.next() != XmlPullParser.END_TAG)
		{
			if(parser.getEventType() != XmlPullParser.START_TAG)
				continue;
			String name = parser.getName();
			Log.v("reading entry", name);
			if(name.equals("type"))
				type = readType(parser);
			else if (name.equals("x"))
				x = readX(parser);
			else if(name.equals("y"))
				y = readY(parser);
			else
				skip(parser);
			
		}
		
		return new Shape(SHAPE.SQUARE, x, y, Shape.COLOR.BLUE, false, context);
		
		
	}
	
	private String readType(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, null, "type");
		String type = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "type");
		return type;
	}
	
	private int readX(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, null, "x");
		String x= readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "x");
		return Integer.parseInt(x);
		
	}
	
	private int readY(XmlPullParser parser) throws XmlPullParserException, IOException{
		parser.require(XmlPullParser.START_TAG, null, "y");
		String y= readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "y");
		return Integer.parseInt(y);
		
	}
	
	private String readText(XmlPullParser parser) throws XmlPullParserException, IOException{
		String result = "";
		if(parser.next() == XmlPullParser.TEXT)
		{
			result = parser.getText();
			parser.nextTag();
		}
		
		return result;
	}
	
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException{
		if(parser.getEventType() != XmlPullParser.START_TAG)
			throw new IllegalStateException();
		
		int depth = 1;
		while(depth != 0)
		{
			switch(parser.next())
			{
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}
	

}


